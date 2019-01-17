package com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ActivationCostDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.AssicurazioneOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDetailsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergySDCDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasDetailOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasSDCDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.InternetDetailOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.InternetOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.MobileOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDeviceDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TLCOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WlrOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.ActivationCost;
import com.aimprosoft.android.optima.centralizedApp.db.entity.AssicurazioneOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergySDC;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasDetailOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasSDC;
import com.aimprosoft.android.optima.centralizedApp.db.entity.InternetDetailOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.InternetOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.MobileOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.OfferDevice;
import com.aimprosoft.android.optima.centralizedApp.db.entity.TLCOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WlrOffers;
import com.aimprosoft.android.optima.centralizedApp.util.SDCardHelper;
import com.optima.pricing.db.QueryManager;
import com.optima.pricing.domain.Pricing;
import com.optima.pricing.domain.ResponsePrincing;
import com.optima.pricing.service.impl.ConfiguratoreCentralizzato;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CentralizedCostUtil {

    public static final String RESPONCE_PRICING = "ResponsePrincing";
    public static final String PRICING = "Pricing";
    public static final String DEVICE_ACTIVATION_COST_BUSINESS_DESC = "DeviceBusiness";
    public static final String DEVICE_ACTIVATION_COST_CONSUMER_DESC = "DeviceConsumer";

    private double costoAttivazionePacchetto = 0;
    private boolean isOfferChanged;
    private boolean isBusinessConfigurator = true;
    private final Pattern lastIntPattern = Pattern.compile("[^0-9]+([0-9]+)$");

    public Map<String, Object> getTotalPrice(Offer offer) {
        Map<String, Object> map = new HashMap();
        ResponsePrincing responsePrincing = null;
        Pricing pricing = buildPricing(offer);

        try {
            pricing = buildPricing(offer);
            String urlLite = "jdbc:sqlite:" + SDCardHelper.DATABASE_LOCATION_DIR + File.separator + SDCardHelper.CPI2_DB_NAME;
            QueryManager queryManager = new QueryManager(AbstractJarUtil.SQLITE_DRIVER, urlLite, true);
            Connection connection = queryManager.openConnection(AbstractJarUtil.SQLITE_DRIVER, urlLite);
            responsePrincing = new ConfiguratoreCentralizzato(AbstractJarUtil.getConfigMap(AbstractJarUtil.COST_JAR))
                    .calculatePackagePrice(pricing, connection, queryManager);
            updateOfferPrices(responsePrincing, offer);
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            Log.e(getClass().getName(), "error while computing ResponsePricing", e);
            cleanOfferPrice(offer);
        }
        map.put(RESPONCE_PRICING, responsePrincing);
        map.put(PRICING, pricing);
        return map;
    }

    private void cleanOfferPrice(Offer offer) {
        BigDecimal clearPrice = new BigDecimal("0");
        offer.setOfferCost(clearPrice);
        offer.setOfferCostIVA(clearPrice);
        offer.setOfferCostExtra(clearPrice);
        offer.setConto_relax(clearPrice.doubleValue());
        new OfferDAOImpl().update(offer);
    }

    public Pricing buildPricing(Offer offer) {
        Pricing pricing = new Pricing();

        List<AbstractJarUtil> datiUtilList = buildDatiUtilList(offer);
        for (AbstractJarUtil abstractJarUtil : datiUtilList) {
            abstractJarUtil.buildPricing(pricing, offer);
        }
        return pricing;
    }

    public Pricing buildSingleVocePricing(Offer offer) {
        Pricing pricing = new Pricing();
        new VoceJarUtil().buildPricing(pricing, offer);
        return pricing;
    }

    private void updateOfferPrices(ResponsePrincing responsePrincing, Offer offer) {
        isOfferChanged = offer.isPdfSendingRequired();
        isBusinessConfigurator = offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR;
        updateEnergyPrice(responsePrincing, offer.getEnergyOfferId());
        updateGasPrice(responsePrincing, offer.getGasOfferId());
        updateADSLPrice(responsePrincing, offer.getInternetOfferId());
        updateVocePrice(responsePrincing, offer);
        updateMobilePrice(responsePrincing, offer);

        List<OfferDevice> offerDeviceList = new OfferDeviceDAOImpl().getSortedDevicesByOfferId(offer.getOfferId(), true);
        addDeviceCostToTotal(offer, responsePrincing, offerDeviceList);
        defineDeviceActivationCost(offerDeviceList, offer);

        addAssicurazioneCost(offer);

        offer.setOfferCostExtra(new BigDecimal(costoAttivazionePacchetto / 4));
        offer.setConto_relax(responsePrincing.getContoRelax());
        offer.setPdfSendingRequired(isOfferChanged);
        new OfferDAOImpl().update(offer);
    }

    private void updateMobilePrice(ResponsePrincing responsePrincing, Offer offer) {
        if (responsePrincing.getPricingMobileResponse() != null) {
            ResponsePrincing.PricingMobileResponse pricingMobileResponse = responsePrincing.getPricingMobileResponse();
            MobileOfferDAOImpl mobileOfferDAO = new MobileOfferDAOImpl();
            MobileOffer mobileOffer = mobileOfferDAO.getMobileOfferById(offer.getMobileOfferId());
            mobileOffer.setPromoCost(Double.valueOf(pricingMobileResponse.getPromoMobile()));
            mobileOffer.setOfferCost(pricingMobileResponse.getCostoTotalePacchetto());
            mobileOffer.setOfferCostIva(pricingMobileResponse.getCostoTotalePacchettoIva());
            mobileOffer.setOfferCostConPromo(pricingMobileResponse.getCanoneMobileConPromo());
            mobileOfferDAO.update(mobileOffer);
        }
    }

    private void addAssicurazioneCost(Offer offer) {
        List<AssicurazioneOffer> assicurazioneOffers = new AssicurazioneOfferDAOImpl().getAssicurazioneByOfferId(offer.getOfferId());
        double assicurazioneCost = 0.0;
        double assicurazioneCostIva = 0.0;
        if (!assicurazioneOffers.isEmpty()) {
            for (AssicurazioneOffer assicurazioneOffer : assicurazioneOffers) {
                double monthlyAssicurazioneCost = assicurazioneOffer.getAssicurazioneCost() / 12;
                assicurazioneCost += monthlyAssicurazioneCost + assicurazioneOffer.getCostoGestioneIntegrata();
                assicurazioneCostIva += monthlyAssicurazioneCost + assicurazioneOffer.getCostoGestioneIntegrataIva();
            }
            offer.setOfferCost(offer.getOfferCost().add(new BigDecimal(assicurazioneCost)));
            offer.setOfferCostIVA(offer.getOfferCostIVA().add(new BigDecimal(assicurazioneCostIva)));
        }
    }

    private void addDeviceCostToTotal(Offer offer, ResponsePrincing responsePrincing, List<OfferDevice> offerDeviceList) {
        float costoPacchetto = responsePrincing.getCostoPacchetto();
        float costoPacchettoIva = responsePrincing.getCostoPacchettoIva();

        for (OfferDevice device : offerDeviceList) {
            costoPacchetto += device.getDeviceCost();
            costoPacchettoIva += device.getDeviceCostIva();
        }

        offer.setOfferCost(new BigDecimal(costoPacchetto));
        offer.setOfferCostIVA(new BigDecimal(costoPacchettoIva));
    }

    private double defineDeviceActivationCost(List<OfferDevice> offerDeviceList, Offer offer) {
        if (!offerDeviceList.isEmpty()) {
            costoAttivazionePacchetto += offerDeviceList.get(0).getActivationCost();
            offerDeviceList.remove(0);
            for (OfferDevice offerDevice : offerDeviceList) {
                costoAttivazionePacchetto += offerDevice.getActivationCostoExtra();
            }

            ActivationCost deviceActivationCost = new ActivationCostDAOImpl()
                    .getActivationCostByServiceDesc(
                            isBusinessConfigurator ?
                                    DEVICE_ACTIVATION_COST_BUSINESS_DESC : DEVICE_ACTIVATION_COST_CONSUMER_DESC);
            if (deviceActivationCost != null) {
                costoAttivazionePacchetto += deviceActivationCost.getActivationCost();
            }
        }
        return costoAttivazionePacchetto;
    }

    private void updateVocePrice(ResponsePrincing responsePrincing, Offer offer) {
        if (responsePrincing.getPricingVoceResponse() != null) {
            ResponsePrincing.PricingVoceResponse pricingVoceResponse = responsePrincing.getPricingVoceResponse();
            ResponsePrincing.PricingVoceResponse.ResponseOfferta responseOfferta = pricingVoceResponse.getResponseOfferta().get(0);
            ResponsePrincing.PricingVoceResponse.ResponseOfferta.PrezzoSforamentoMancatoConsumo prezzoSforamentoMancatoConsumo =
                    responseOfferta.getPrezzoSforamentoMancatoConsumo().get(0);
            updateTlcOffer(offer.getTlcOfferId(), responseOfferta, prezzoSforamentoMancatoConsumo);
            updateWlrOffer(offer.getWlrOfferId(), responseOfferta, prezzoSforamentoMancatoConsumo);
            costoAttivazionePacchetto += responseOfferta.getCostoAttivazionePacchetto();
        }
    }

    private void updateWlrOffer(Integer wlrOfferId,
                                ResponsePrincing.PricingVoceResponse.ResponseOfferta responseOfferta,
                                ResponsePrincing.PricingVoceResponse.ResponseOfferta.PrezzoSforamentoMancatoConsumo prezzoSforamentoMancatoConsumo) {
        if (wlrOfferId != null) {
            WlrOffersDAOImpl wlrOffersDAO = new WlrOffersDAOImpl();
            WlrOffers wlrOffers = wlrOffersDAO.getWlrOfferById(wlrOfferId);
            wlrOffers.setOfferCost(responseOfferta.getCostoPacchettoMensile());
            wlrOffers.setOfferCostIVA(responseOfferta.getCostoPacchettoMensileIva());
            wlrOffers.setCodicePmcPS(prezzoSforamentoMancatoConsumo.getCodice());
            wlrOffers.setPs(String.valueOf(prezzoSforamentoMancatoConsumo.getPrezzoSforamento()));
            wlrOffers.setPmc(String.valueOf(prezzoSforamentoMancatoConsumo.getPrezzoMancatoConsumo()));
            wlrOffers.setPercentage(responseOfferta.getPercentuale());
            wlrOffersDAO.update(wlrOffers);
        }
    }

    private void updateTlcOffer(Integer tlcOfferId,
                                ResponsePrincing.PricingVoceResponse.ResponseOfferta responseOfferta,
                                ResponsePrincing.PricingVoceResponse.ResponseOfferta.PrezzoSforamentoMancatoConsumo prezzoSforamentoMancatoConsumo) {
        if (tlcOfferId != null) {
            TLCOfferDAOImpl tlcOfferDAO = new TLCOfferDAOImpl();
            TLCOffer tlcOffer = tlcOfferDAO.getTLCOfferById(tlcOfferId);
            tlcOffer.setBundleCost(new BigDecimal(String.valueOf(responseOfferta.getCostoPacchettoMensile())));
            tlcOffer.setBundleCostIVA(new BigDecimal(String.valueOf(responseOfferta.getCostoPacchettoMensileIva())));
            tlcOffer.setCodicePmcPS(prezzoSforamentoMancatoConsumo.getCodice());
            tlcOffer.setPs(String.valueOf(prezzoSforamentoMancatoConsumo.getPrezzoSforamento()));
            tlcOffer.setPmc(String.valueOf(prezzoSforamentoMancatoConsumo.getPrezzoMancatoConsumo()));
            tlcOffer.setPercentage(responseOfferta.getPercentuale());
            tlcOfferDAO.update(tlcOffer);
        }
    }

    private void updateADSLPrice(ResponsePrincing responsePrincing, Integer adslOfferId) {
        if (responsePrincing.getPricingAdslResponse() != null) {
            ResponsePrincing.PricingAdslResponse pricingAdslResponse = responsePrincing.getPricingAdslResponse();
            InternetOfferDAOImpl internetOfferDAO = new InternetOfferDAOImpl();
            InternetDetailOffersDAOImpl internetDetailOffersDAO = new InternetDetailOffersDAOImpl();
            InternetOffer internetOffer = internetOfferDAO.getInternetOfferById(adslOfferId);
            List<InternetDetailOffers> internetDetailOffersList = internetDetailOffersDAO.getInternetDetailsOfferByInternetOfferId(adslOfferId);
            List<ResponsePrincing.PricingAdslResponse.ResponseOfferta> responseOffertaList = pricingAdslResponse.getResponseOfferta();
            sortADSLResponcePriceByMisuratore(responseOffertaList);
            for (int i = 0; i < responseOffertaList.size(); i++) {
                ResponsePrincing.PricingAdslResponse.ResponseOfferta responseOfferta = responseOffertaList.get(i);
                InternetDetailOffers internetDetailOffers = internetDetailOffersList.get(i);
                internetDetailOffers.setCost(responseOfferta.getCostoPacchettoMensile());
                internetDetailOffers.setCostIVA(responseOfferta.getCostoPacchettoMensileIva());
                internetDetailOffers.setPercentage(responseOfferta.getPercentuale());
                //costoAttivazionePacchetto += responseOfferta.getCostoAttivazionePacchetto();
                internetDetailOffersDAO.update(internetDetailOffers);
            }
            internetOffer.setCost(pricingAdslResponse.getCostoTotalePacchettoMensile());
            internetOffer.setCostIVA(pricingAdslResponse.getCostoTotalePacchettoMensileIva());
            costoAttivazionePacchetto += pricingAdslResponse.getCostoAttivazionePacchetto();
            internetOfferDAO.update(internetOffer);
        }
    }

    private void updateGasPrice(ResponsePrincing responsePrincing, Integer gasOfferId) {
        if (responsePrincing.getPricingGasResponse() != null) {
            ResponsePrincing.PricingGasResponse pricingGasResponse = responsePrincing.getPricingGasResponse();
            GasOfferDAOImpl gasOfferDAO = new GasOfferDAOImpl();
            GasDetailOffersDAOImpl gasDetailOffersDAO = new GasDetailOffersDAOImpl();
            GasOffer gasOffer = gasOfferDAO.getGasOfferById(gasOfferId);
            List<GasDetailOffers> gasDetailOffersList = gasDetailOffersDAO.getGasOfferDetailsByGasOfferId(gasOfferId);
            List<ResponsePrincing.PricingGasResponse.ResponseOfferta> responseOffertaList = pricingGasResponse.getResponseOfferta();
            sortGasResponcePriceByMisuratore(responseOffertaList);
            for (int i = 0; i < responseOffertaList.size(); i++) {
                ResponsePrincing.PricingGasResponse.ResponseOfferta responseOfferta = responseOffertaList.get(i);
                GasDetailOffers gasDetailOffers = gasDetailOffersList.get(i);
                if (!gasDetailOffers.getOfferCost().equals(new BigDecimal(String.valueOf(responseOfferta.getCostoPacchettoMensile())))
                        || gasDetailOffers.getYearlyConsumption() != Math.round(responseOfferta.getConsumoMensileTotale())) {
                    isOfferChanged = true;
                }

                if (!isBusinessConfigurator) {
                    saveGasSDC(gasDetailOffers, responseOfferta.getSdc());
                }

                gasDetailOffers.setOfferCost(new BigDecimal(String.valueOf(responseOfferta.getCostoPacchettoMensile())));
                gasDetailOffers.setOfferCostIVA(new BigDecimal(String.valueOf(responseOfferta.getCostoPacchettoMensileIva())));
                gasDetailOffers.setOfferCostIVA(new BigDecimal(String.valueOf(responseOfferta.getCostoPacchettoMensileIva())));
                gasDetailOffers.setPercentage(responseOfferta.getPercentuale());
                gasDetailOffers.setYearlyConsumption(Math.round(responseOfferta.getConsumoMensileTotale()));

                ResponsePrincing.PricingGasResponse.ResponseOfferta.PrezzoSforamentoMancatoConsumo prezzoSforamentoMancatoConsumo =
                        responseOfferta.getPrezzoSforamentoMancatoConsumo().get(0);
                gasDetailOffers.setPmc(String.valueOf(prezzoSforamentoMancatoConsumo.getPrezzoMancatoConsumo()));
                gasDetailOffers.setCodicePmcPS(prezzoSforamentoMancatoConsumo.getCodice());
                gasDetailOffers.setPs(String.valueOf(prezzoSforamentoMancatoConsumo.getPrezzoSforamento()));
                gasDetailOffersDAO.update(gasDetailOffers);
            }
            gasOffer.setOfferCost(new BigDecimal(String.valueOf(pricingGasResponse.getCostoTotalePacchettoMensile())));
            gasOffer.setOfferCostIVA(new BigDecimal(String.valueOf(pricingGasResponse.getCostoTotalePacchettoMensileIva())));
            costoAttivazionePacchetto += pricingGasResponse.getCostoAttivazionePacchetto();
            gasOfferDAO.update(gasOffer);
        }
    }

    private void saveGasSDC(GasDetailOffers gasDetailOffers, List<ResponsePrincing.PricingGasResponse.ResponseOfferta.Sdc> sdcList) {
        GasSDCDAOImpl gasSDCDAO = new GasSDCDAOImpl();
        gasSDCDAO.deleteGasSDCByDetailsId(gasDetailOffers.getGasDetailsOfferId());
        if (gasDetailOffers.getTipoContratto() != Constants.ALTRI_USI && sdcList != null && !sdcList.isEmpty()) {
            GasSDC gasSDC;
            for (ResponsePrincing.PricingGasResponse.ResponseOfferta.Sdc sdc : sdcList) {
                gasSDC = new GasSDC();
                gasSDC.setGasDetailsOfferId(gasDetailOffers.getGasDetailsOfferId());
                gasSDC.setConsumoAnnuo(sdc.getConsumoAnnuo());
                gasSDC.setStimaSpesaRif(sdc.getStimaSpesaRif());
                gasSDC.setDataInizio(sdc.getDataInizio());
                gasSDC.setDataFine(sdc.getDataFine());
                gasSDC.setCostoTotSenImpG(sdc.getCostoTotSenImpG());
                gasSDCDAO.insert(gasSDC);
            }
        }
    }

    private void updateEnergyPrice(ResponsePrincing responsePrincing, Integer energyOfferId) {
        if (responsePrincing.getPricingEnergiaResponse() != null) {
            ResponsePrincing.PricingEnergiaResponse pricingEnergiaResponse = responsePrincing.getPricingEnergiaResponse();
            EnergyOfferDAOImpl energyOfferDAO = new EnergyOfferDAOImpl();
            EnergyOfferDetailsDAOImpl energyOfferDetailsDAO = new EnergyOfferDetailsDAOImpl();
            EnergyOffer energyOffer = energyOfferDAO.getEnergyOfferById(energyOfferId);
            List<EnergyOfferDetails> energyOfferDetailsList = energyOfferDetailsDAO.getEnergyOfferDetailsByEnergyOfferId(energyOfferId);
            List<ResponsePrincing.PricingEnergiaResponse.ResponseOfferta> responseOffertaList = pricingEnergiaResponse.getResponseOfferta();
            sortEnergyResponcePriceByMisuratore(responseOffertaList);
            for (int i = 0; i < responseOffertaList.size(); i++) {
                ResponsePrincing.PricingEnergiaResponse.ResponseOfferta responseOfferta = responseOffertaList.get(i);
                EnergyOfferDetails energyOfferDetails = energyOfferDetailsList.get(i);
                if (!energyOfferDetails.getOfferCost().equals(new BigDecimal(String.valueOf(responseOfferta.getCostoPacchettoMensile())))
                        || energyOfferDetails.getYearlyConsumption() != Math.round(responseOfferta.getConsumoMensileTotale())) {
                    isOfferChanged = true;
                }

                if (!isBusinessConfigurator) {
                    saveEnergySDC(energyOfferDetails, responseOfferta.getSdc());
                }

                energyOfferDetails.setOfferCost(new BigDecimal(String.valueOf(responseOfferta.getCostoPacchettoMensile())));
                energyOfferDetails.setOfferCostIVA(new BigDecimal(String.valueOf(responseOfferta.getCostoPacchettoMensileIva())));
                energyOfferDetails.setPercentage(responseOfferta.getPercentuale());
                energyOfferDetails.setYearlyConsumption(Math.round(responseOfferta.getConsumoMensileTotale()));

                ResponsePrincing.PricingEnergiaResponse.ResponseOfferta.PrezzoSforamentoMancatoConsumo prezzoSforamentoMancatoConsumo =
                        responseOfferta.getPrezzoSforamentoMancatoConsumo();
                energyOfferDetails.setPmc(String.valueOf(prezzoSforamentoMancatoConsumo.getPrezzoMancatoConsumo()));
                energyOfferDetails.setCodicePmcPS(prezzoSforamentoMancatoConsumo.getCodice());
                energyOfferDetails.setPs(String.valueOf(prezzoSforamentoMancatoConsumo.getPrezzoSforamento()));
                energyOfferDetailsDAO.update(energyOfferDetails);
            }
            energyOffer.setOfferCost(new BigDecimal(String.valueOf(pricingEnergiaResponse.getCostoTotalePacchettoMensile())));
            energyOffer.setOfferCostIVA(new BigDecimal(String.valueOf(pricingEnergiaResponse.getCostoTotalePacchettoMensileIva())));
            costoAttivazionePacchetto += pricingEnergiaResponse.getCostoAttivazionePacchetto();
            energyOfferDAO.update(energyOffer);
        }
    }

    private void saveEnergySDC(EnergyOfferDetails energyOfferDetails, List<ResponsePrincing.PricingEnergiaResponse.ResponseOfferta.Sdc> sdcList) {
        EnergySDCDAOImpl energySDCDAO = new EnergySDCDAOImpl();
        energySDCDAO.deleteEnergySDCByDetailsId(energyOfferDetails.getEnergyDetailOfferId());
        if (energyOfferDetails.getTipologiaContratto() != Constants.ALTRI_USI && sdcList != null && !sdcList.isEmpty()) {
            EnergySDC energySDC;
            for (ResponsePrincing.PricingEnergiaResponse.ResponseOfferta.Sdc sdc : sdcList) {
                energySDC = new EnergySDC();
                energySDC.setEnergyDetailsOfferId(energyOfferDetails.getEnergyDetailOfferId());
                energySDC.setConsumoAnnuo(sdc.getConsumoAnnuo());
                energySDC.setPrezzoBiorarioPunta(sdc.getPrezzoBiorarioPunta());
                energySDC.setPrezzoMonorario(sdc.getPrezzoMonorario());
                energySDC.setPrezzoBiorarioNoPunta(sdc.getPrezzoBiorarioNoPunta());
                energySDC.setDataInizio(sdc.getDataInizio());
                energySDC.setDataFine(sdc.getDataFine());
                energySDC.setCostoTotSenImpE(sdc.getCostoTotSenImpE());
                energySDCDAO.insert(energySDC);
            }
        }
    }

    private List<AbstractJarUtil> buildDatiUtilList(Offer offer) {
        List<AbstractJarUtil> datiUtilList = new ArrayList<>();
        if (offer.getEnergyOfferId() != null) datiUtilList.add(new EnergiaJarUtil());
        if (offer.getGasOfferId() != null) datiUtilList.add(new GasJarUtil());
        if (offer.getInternetOfferId() != null) datiUtilList.add(new ADSLJarUtil());
        if (offer.getMobileOfferId() != null) datiUtilList.add(new MobileJarUtil());
        if (offer.getWlrOfferId() != null || offer.getTlcOfferId() != null)
            datiUtilList.add(new VoceJarUtil());
        return datiUtilList;
    }

    private void sortEnergyResponcePriceByMisuratore(List<ResponsePrincing.PricingEnergiaResponse.ResponseOfferta> responseOffertaList) {
        Collections.sort(responseOffertaList, new Comparator<ResponsePrincing.PricingEnergiaResponse.ResponseOfferta>() {
            @Override
            public int compare(ResponsePrincing.PricingEnergiaResponse.ResponseOfferta lhs, ResponsePrincing.PricingEnergiaResponse.ResponseOfferta rhs) {
                return getLastMisuratoreSegment(lhs.getMisuratore()).compareTo(getLastMisuratoreSegment(rhs.getMisuratore()));
            }
        });
    }

    private void sortGasResponcePriceByMisuratore(List<ResponsePrincing.PricingGasResponse.ResponseOfferta> responseOffertaList) {
        Collections.sort(responseOffertaList, new Comparator<ResponsePrincing.PricingGasResponse.ResponseOfferta>() {
            @Override
            public int compare(ResponsePrincing.PricingGasResponse.ResponseOfferta lhs, ResponsePrincing.PricingGasResponse.ResponseOfferta rhs) {
                return getLastMisuratoreSegment(lhs.getMisuratore()).compareTo(getLastMisuratoreSegment(rhs.getMisuratore()));
            }
        });
    }

    private void sortADSLResponcePriceByMisuratore(List<ResponsePrincing.PricingAdslResponse.ResponseOfferta> responseOffertaList) {
        Collections.sort(responseOffertaList, new Comparator<ResponsePrincing.PricingAdslResponse.ResponseOfferta>() {
            @Override
            public int compare(ResponsePrincing.PricingAdslResponse.ResponseOfferta lhs, ResponsePrincing.PricingAdslResponse.ResponseOfferta rhs) {
                return getLastMisuratoreSegment(lhs.getCli()).compareTo(getLastMisuratoreSegment(rhs.getCli()));
            }
        });
    }

    private Integer getLastMisuratoreSegment(String misuratore) {
        Matcher matcher = lastIntPattern.matcher(misuratore);
        Integer lastSegment;
        if (matcher.find()) {
            String someNumberStr = matcher.group(1);
            lastSegment = Integer.parseInt(someNumberStr);
        } else {
            lastSegment = 0;
        }
        return lastSegment;
    }

}
