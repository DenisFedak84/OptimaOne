package com.aimprosoft.android.optima.centralizedApp.util;

import android.content.Context;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.configuratoreEngineDAO.LineTypeDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.BundleDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.CarriersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.CategorieUsoDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ClassePrelievoGasDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ClientDetailsTlcOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyMeterDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDateIntervalDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDetailsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.FiscalClassDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasDetailOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasOfferDateIntervalDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.InternetDetailOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.LineNumbersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.LineTypesDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.MobileDetailsOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.MobileOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.NetworksDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ServicesDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TLCOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TlcDetailsOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WlrOfferDetailsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WlrOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.AssicurazioneOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Bundle;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Carriers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDateInterval;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasDetailOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOfferDateInterval;
import com.aimprosoft.android.optima.centralizedApp.db.entity.InternetDetailOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.LineTypes;
import com.aimprosoft.android.optima.centralizedApp.db.entity.MobileDetailsOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.MobileOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Networks;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.OfferDevice;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Services;
import com.aimprosoft.android.optima.centralizedApp.db.entity.TLCOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.TlcDetailsOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WlrOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WlrOffers;
import com.aimprosoft.android.optima.centralizedApp.service.GetOffersForJsonService;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.AbstractJarUtil;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.CentralizedCostUtil;
import com.optima.pricing.db.QueryManager;
import com.optima.pricing.domain.Pricing;
import com.optima.pricing.domain.ResponsePrincing;
import com.optima.pricing.service.impl.ConfiguratoreCentralizzato;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OffersUtils {
    private Map<String, Integer> offerMap = new HashMap<>();
    private String ENERGY_OFFER = "energyOffer";
    private String GAS_OFFER = "gasOffer";
    private String ADSL_OFFER = "adslOffer";
    private String TLC_OFFER = "tlcOffer";
    private int LOCAL_BUNDLE_TYPE = 1;
    private int MOBILE_BUNDLE_TYPE = 2;

    private Offer offer;
    private BundleDAOImpl bundleDAO;
    private DecimalFormat decimalFormat;
    private DecimalFormat decimalFormatForPercent;
    private SimpleDateFormat dateFormat;
    private double sumOfPercent = 0.00;
    private Context context;

    private int cpsLineCount;
    private int wlrLineCount;
    private int voipLineCount = 1;

    private boolean isWlrPresent;
    private boolean isVoipPresent;

    public OffersUtils(Offer offer) {
        this.offer = offer;
        bundleDAO = new BundleDAOImpl();
        context = MyApplication.getContext();
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.UK);
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormat = new DecimalFormat("#.##", decimalFormatSymbols);
        decimalFormatForPercent = new DecimalFormat("#.#", decimalFormatSymbols);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
        if (offer.getTlcOfferId() != null || offer.getWlrOfferId() != null)
            offerMap.put(TLC_OFFER, offer.getTlcOfferId() != null ? offer.getTlcOfferId() : offer.getWlrOfferId());
        if (offer.getEnergyOfferId() != null) offerMap.put(ENERGY_OFFER, offer.getEnergyOfferId());
        if (offer.getGasOfferId() != null) offerMap.put(GAS_OFFER, offer.getGasOfferId());
        if (offer.getInternetOfferId() != null)
            offerMap.put(ADSL_OFFER, offer.getInternetOfferId());
    }

    public Collection<? extends Map<String, Object>> processDeviceOffer(List<OfferDevice> offerDevices) {
        List<Map<String, Object>> paramsList = new ArrayList<>();
        for (OfferDevice device : offerDevices) {
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put(GetOffersForJsonService.SERVICE_ID, Constants.DEVICE_SERVICE_ID);
            paramsMap.put(GetOffersForJsonService.BRANCH, null);
            paramsMap.put(GetOffersForJsonService.SUBSCRIBER, Constants.EMPTY_STRING);
            paramsMap.put(OfferDevice.DEVICE_DESC, device.getDeviceDesc());
            paramsMap.put(OfferDevice.DEVICE_COST, device.getDeviceCost());
            paramsMap.put(OfferDevice.DEVICE_COST_IVA, device.getDeviceCostIva());
            paramsMap.put(OfferDevice.ACTIVATION_COSTO_EXTRA, device.getActivationCostoExtra());
            paramsMap.put(OfferDevice.DEVICE_TYPE, device.getDeviceType());
            paramsMap.put(OfferDevice.PRIORITY, device.getPriority());
            paramsList.add(paramsMap);
        }
        return paramsList;
    }

    public Collection<? extends Map<String, Object>> processAssicurazione(List<AssicurazioneOffer> assicurazioneOffers) {
        List<Map<String, Object>> paramsList = new ArrayList<>();
        for (AssicurazioneOffer assicurazione : assicurazioneOffers) {
            Map<String, Object> offerParams = new HashMap<>();
            offerParams.put(GetOffersForJsonService.SERVICE_ID, Constants.ASSICURAZIONE_SERVICE_ID);
            offerParams.put(GetOffersForJsonService.DATA_INIZIO_POLIZZA, dateFormat.format(assicurazione.getAssicurazioneDate()));
            offerParams.put(GetOffersForJsonService.SUBSCRIBER, "");
            offerParams.put(GetOffersForJsonService.TARGA, assicurazione.getTarga());
            offerParams.put(GetOffersForJsonService.COSTO, decimalFormat.format(assicurazione.getAssicurazioneCost() / 12));
            paramsList.add(offerParams);
        }
        return paramsList;
    }
    public Collection<? extends Map<String, Object>> processMobileOffer() {
        List<Map<String, Object>> paramsList = new ArrayList<>();
        List<MobileDetailsOffer> mobileDetailsOffers = new MobileDetailsOfferDAOImpl().getMobileOfferDetailsByMobileOfferId(
                offer.getMobileOfferId() != null ? offer.getMobileOfferId() : 0);

        MobileOffer mobileOffer = new MobileOfferDAOImpl().getMobileOfferById(offer.getMobileOfferId());

        double totalCost = 0.0;

        int simCount = mobileDetailsOffers.size();

        for (int i = 0; i < simCount; i++) {
            MobileDetailsOffer mobileDetailsOffer = mobileDetailsOffers.get(i);
            totalCost += mobileDetailsOffer.getCost();
        }

        totalCost -= mobileOffer.getPromoCost();

        Map<String, Object> offerParams = new HashMap<>();
        offerParams.put(GetOffersForJsonService.SERVICE_ID, Constants.MOBILE_SERVICE_ID);
        offerParams.put(GetOffersForJsonService.SIM_COUNT, simCount);
        offerParams.put(GetOffersForJsonService.MONTHLY_AMOUNT, decimalFormat.format(totalCost));
        paramsList.add(offerParams);
        return paramsList;
    }

    public Collection<? extends Map<String, Object>> processVoceOffer(Pricing pricing) {

        List<Map<String, Object>> paramsList = new ArrayList<>();
//        String existingClientCostVersion = Constants.DEFAULT_ZERO_STRING;

        //old part
        WlrOffers wlrOffers = offer.getWlrOfferId() != null ? new WlrOffersDAOImpl().getWlrOfferById(offer.getWlrOfferId()) : null;
        TLCOffer tlcOffers = offer.getTlcOfferId() != null ? new TLCOfferDAOImpl().getTLCOfferById(offer.getTlcOfferId()) : null;
        List<TlcDetailsOffer> tlcDetailsOfferList = new TlcDetailsOfferDAOImpl().getAllTlcDetailsByTlcOfferId(tlcOffers != null ? tlcOffers.getTlcOfferId() : 0);
        List<WlrOfferDetails> wlrDetailsOfferList = new WlrOfferDetailsDAOImpl().getWlrOfferDetailsByWlrOfferId(wlrOffers != null ? wlrOffers.getWlrOfferId() : 0, true);
        defineVoceLineeCount(tlcDetailsOfferList, wlrDetailsOfferList);
        ResponsePrincing.PricingVoceResponse voceResponse = null;
        try {
            Pricing vocePricing = new CentralizedCostUtil().buildSingleVocePricing(offer);
            String urlLite = "jdbc:sqlite:" + SDCardHelper.DATABASE_LOCATION_DIR + File.separator + SDCardHelper.CPI2_DB_NAME;
            QueryManager queryManager = new QueryManager(AbstractJarUtil.SQLITE_DRIVER, urlLite, true);
            Connection connection = queryManager.openConnection(AbstractJarUtil.SQLITE_DRIVER, urlLite);
            voceResponse = new ConfiguratoreCentralizzato(AbstractJarUtil.getConfigMap(AbstractJarUtil.COST_JAR))
                    .calculatePackagePrice(vocePricing, connection, queryManager).getPricingVoceResponse();
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "error while building voce object", e);
        }

        if ((wlrOffers != null || tlcOffers != null) && voceResponse != null && !voceResponse.getResponseOfferta().isEmpty()) {
            ResponsePrincing.PricingVoceResponse.ResponseOfferta voceResponceOfferta = voceResponse.getResponseOfferta().get(0);
            double vocePercentage = 100 - sumOfPercent;
            double voceCost = voceResponceOfferta.getCostoPacchettoMensile();
//            double voceFissoCost = voceResponceOfferta.getCostoMinutiVersoFisso();
//            double voceMobileCost = voceResponceOfferta.getCostoMinutiVersoMobile();

            double localBrunchPercentage = 0;
//            if (tlcOffers != null) {
//                vocePercentage = Double.valueOf(getFormattedPercent(TLC_OFFER, tlcOffers.getPercentage()));
//                voceCost = tlcOffers.getBundleCost().doubleValue();
//            } else {
//                vocePercentage = Double.valueOf(getFormattedPercent(TLC_OFFER, wlrOffers.getPercentage()));
//                voceCost = wlrOffers.getOfferCost();
//            }

            sumOfPercent = 100;


//            SimpleBundleModel localBrunchModel = AbstractJarUtil.getBundleInfoFromJarDB(tlcOffers != null ? tlcDetailsOfferList.get(0).getLocalBundleId() :
//                    wlrOffers.getLocalBundleId(), pricing.getDatiVoce().getIdCampagnaAttivazione(), 1);
//            SimpleBundleModel mobileBrunchModel = AbstractJarUtil.getBundleInfoFromJarDB(tlcOffers != null ? tlcDetailsOfferList.get(0).getMobileBundleId() :
//                    wlrOffers.getMobileBundleId(), pricing.getDatiVoce().getIdCampagnaAttivazione(), 2);
            Map<String, Object> offerParams = new HashMap<>();
            offerParams.put(GetOffersForJsonService.SERVICE_ID, Constants.VOCE_SERVICE_ID);
            offerParams.put(GetOffersForJsonService.SUBSCRIBER, "");
            offerParams.put(TLCOffer.CODICE_PMC_PS, offer.getTlcOfferId() != null ? tlcOffers.getCodicePmcPS() : wlrOffers.getCodicePmcPS());
            if (voceResponceOfferta.getMinutiVersoFisso() != null) {
//                offerParams.put(GetOffersForJsonService.EXISTING_CLIENT_COST_VERSION, existingClientCostVersion);
//                offerParams.put(GetOffersForJsonService.SERVICES_COST_VERSION, serviceCostVersion);
//                offerParams.put(GetOffersForJsonService.ADDITIONAL_NUMBER_COST_VERSION, additionalNumberCostVersion);
//                offerParams.put(GetOffersForJsonService.BUNDLE_COST_VERSION, bundleCostVersion);
                offerParams.put(GetOffersForJsonService.MONTHLY_AMOUNT, decimalFormat.format(voceResponceOfferta.getCostoMinutiVersoFisso()));
                localBrunchPercentage = voceResponceOfferta.getMinutiVersoMobile() != null ?
                        voceResponceOfferta.getCostoMinutiVersoMobile() * vocePercentage / voceCost : vocePercentage;
                offerParams.put(GetOffersForJsonService.PERCENTAGE, decimalFormatForPercent.format(localBrunchPercentage));
                HashMap<String, Object> tlcOfferParams = new HashMap<>(offerParams);
                tlcOfferParams.put(GetOffersForJsonService.BRANCH, "1");
                tlcOfferParams.put(GetOffersForJsonService.YEARLY_UNITS, getNormalizedVoceYearlyUnits(voceResponceOfferta.getMinutiVersoFisso()));
//                tlcOfferParams.put(GetOffersForJsonService.YEARLY_UNITS, bundleDAO.getBundleDescByBundleId(!tlcDetailsOfferList.isEmpty() ?
//                        tlcDetailsOfferList.get(0).getLocalBundleId() :
//                        (wlrOffers != null && wlrOffers.getLocalBundleId() != null) ? wlrOffers.getLocalBundleId() : -1));
                tlcOfferParams.put(GetOffersForJsonService.CPS, tlcOffers != null);
                tlcOfferParams.put(GetOffersForJsonService.WLR, isWlrPresent);
                tlcOfferParams.put(GetOffersForJsonService.VOIP, isVoipPresent);
                tlcOfferParams.put(GetOffersForJsonService.VOCE_LINEE_COUNT, wlrLineCount);
                tlcOfferParams.put(GetOffersForJsonService.CPS_LINE_NUMBER, cpsLineCount);
                tlcOfferParams.put(GetOffersForJsonService.VOIP_LINEE_COUNT, isVoipPresent ? voipLineCount : 0);
                tlcOfferParams.put(GetOffersForJsonService.LINEE_COUNT, wlrDetailsOfferList.size() + tlcDetailsOfferList.size());
//                tlcOfferParams.put(GetOffersForJsonService.WLR, wlrOffers != null);
//                tlcOfferParams.put(GetOffersForJsonServiceImpl.DETAILS, detailsList);
                paramsList.add(tlcOfferParams);
            }
//            double monthlyAmount = 0;
            if (voceResponceOfferta.getMinutiVersoMobile() != null) {
//                monthlyAmount = paramsList.size() == 0 ?
//                        wlrOffer.getOfferCost() + (offer.getTlcOfferId() == null ? wlrOffer.getBundleCost() : tlcOffer.getBundleCost().doubleValue())
//                        : monthlyAmountBranch2;
//                offerParams.put(GetOffersForJsonService.EXISTING_CLIENT_COST_VERSION, existingClientCostVersion);
//                offerParams.put(GetOffersForJsonService.SERVICES_COST_VERSION, serviceCostVersion);
//                offerParams.put(GetOffersForJsonService.ADDITIONAL_NUMBER_COST_VERSION, additionalNumberCostVersion);
//                offerParams.put(GetOffersForJsonService.BUNDLE_COST_VERSION, bundleCostVersion);
                offerParams.put(GetOffersForJsonService.MONTHLY_AMOUNT, voceResponceOfferta.getCostoMinutiVersoMobile());
                offerParams.put(GetOffersForJsonService.PERCENTAGE, decimalFormatForPercent.format(localBrunchPercentage != 0 ?
                        vocePercentage - localBrunchPercentage : vocePercentage));
                HashMap<String, Object> tlcOfferParams = new HashMap<>(offerParams);
                tlcOfferParams.put(GetOffersForJsonService.BRANCH, "2");
                tlcOfferParams.put(GetOffersForJsonService.YEARLY_UNITS, getNormalizedVoceYearlyUnits(voceResponceOfferta.getMinutiVersoMobile()));
//                tlcOfferParams.put(GetOffersForJsonService.YEARLY_UNITS, bundleDAO.getBundleDescByBundleId(!tlcDetailsOfferList.isEmpty() ?
//                        tlcDetailsOfferList.get(0).getMobileBundleId() :
//                        (wlrOffers != null && wlrOffers.getMobileBundleId() != null) ? wlrOffers.getMobileBundleId() : -1));
                tlcOfferParams.put(GetOffersForJsonService.CPS, tlcOffers != null);
                tlcOfferParams.put(GetOffersForJsonService.WLR, isWlrPresent);
                tlcOfferParams.put(GetOffersForJsonService.VOIP, isVoipPresent);
                tlcOfferParams.put(GetOffersForJsonService.VOCE_LINEE_COUNT, wlrLineCount);
                tlcOfferParams.put(GetOffersForJsonService.CPS_LINE_NUMBER, cpsLineCount);
                tlcOfferParams.put(GetOffersForJsonService.VOIP_LINEE_COUNT, isVoipPresent ? voipLineCount : 0);
                tlcOfferParams.put(GetOffersForJsonService.LINEE_COUNT, wlrDetailsOfferList.size() + tlcDetailsOfferList.size());
//                tlcOfferParams.put(GetOffersForJsonServiceImpl.DETAILS, buildTlcOfferDetailsMap(tlcDetailsOffer));/////
                paramsList.add(tlcOfferParams);
            }
        }
        return paramsList;
    }

    private String getNormalizedVoceYearlyUnits(String rawYearlyUnits) {
        String result;
        if (rawYearlyUnits.equalsIgnoreCase(Constants.FLAT_VOIP) ||
                rawYearlyUnits.equalsIgnoreCase(Constants.FLAT)) {
            result = "-1";
        } else if (rawYearlyUnits.equalsIgnoreCase(Constants.VOIP50)) {
            result = "50";
        } else if (rawYearlyUnits.equalsIgnoreCase(Constants.VOIP100)) {
            result = "100";
        } else {
            result = rawYearlyUnits;
        }
        return result;
    }

    private void defineVoceLineeCount(List<TlcDetailsOffer> tlcDetailsOfferList, List<WlrOfferDetails> wlrDetailsOfferList) {

        cpsLineCount = 0;
        for (TlcDetailsOffer tlcLinee : tlcDetailsOfferList) {
            cpsLineCount += tlcLinee.getNumLines();
        }

        wlrLineCount = 0;
        for (WlrOfferDetails wlrLinee : wlrDetailsOfferList) {
            LineTypes lineType = LineTypeDAOImpl.getLineTypeById(wlrLinee.getLineId());
            if (!wlrLinee.getRete().equalsIgnoreCase(Constants.VOIP)) {
                wlrLineCount += lineType.getLineDesc().equals(LineTypeDAOImpl.LineaType.LINEA_ISDN.getLineaDesc()) ? wlrLinee.getNumLines() * 2 : wlrLinee.getNumLines();
            }
        }

        isWlrPresent = wlrLineCount > 0;
        isVoipPresent = !isWlrPresent && cpsLineCount == 0;
    }

    private String getFormattedPercent(String serviceKey, float percent) {
        offerMap.remove(serviceKey);
        float formattedPerc = offerMap.isEmpty() ? (float) (100 - sumOfPercent) : percent;
        sumOfPercent += percent;
        return decimalFormatForPercent.format(formattedPerc);
    }

    private int getBundleMinutes(int bundleId) {
        Bundle bundle = bundleDAO.getBundleById(bundleId);
        return bundle.getBundleMinutes() != null ? bundle.getBundleMinutes() : 0;
    }

    public Collection<? extends Map<String, Object>> processEnergyOffer() {
        List<Map<String, Object>> paramsList = new ArrayList<>();
        List<EnergyOfferDetails> energyOfferDetails = new EnergyOfferDetailsDAOImpl().getEnergyOfferDetailsByEnergyOfferId(
                offer.getEnergyOfferId() != null ? offer.getEnergyOfferId() : 0);
        EnergyMeterDAOImpl energyMeterDAO = new EnergyMeterDAOImpl();
        for (int i = 0; i < energyOfferDetails.size(); i++) {
            EnergyOfferDetails energyOfferDetail = energyOfferDetails.get(i);
//            if (energyOfferDetails.size() - 1 == i) offerMap.remove(ENERGY_OFFER);
            Map<String, Object> offerParams = new HashMap<>();
            offerParams.put(GetOffersForJsonService.SERVICE_ID, Constants.ENERGY_SERVICE_ID);
            offerParams.put(GetOffersForJsonService.COST_VERSION, energyOfferDetail.getCostVersion());
            offerParams.put(GetOffersForJsonService.BRANCH, null);
//            offerParams.put(SDCReport.SDC, new SDCEnergyReportImpl().getSingleOfferSDCDataList(energyOfferDetail.getEnergyDetailOfferId()));
            offerParams.put(GetOffersForJsonService.TIPO_MISURATORE, context.getString(R.string.tipoContatoreIntegratore));
            offerParams.put(GetOffersForJsonService.POTENZA_E_OPZIONE_TARIFFARIA, energyMeterDAO.getEnergiMeterById(energyOfferDetail.getEnergyMeter()).getTariffOption());
            offerParams.put(GetOffersForJsonService.SUBSCRIBER, energyOfferDetail.isExistingClientOffer() ? energyOfferDetail.getPod() : "");
            offerParams.put(GetOffersForJsonService.REMOTE_SERVICE_STATUS, energyOfferDetail.isRemoteServiceStatus());
            offerParams.put(GetOffersForJsonService.SUM_ATTIVA, energyOfferDetail.getSumAttiva() != 0 ? energyOfferDetail.getSumAttiva() : null);
            offerParams.put(GetOffersForJsonService.COUNT_POD, energyOfferDetail.getCountPod() != 0 ? energyOfferDetail.getCountPod() : null);
            offerParams.put(GetOffersForJsonService.COUNT_MESE, energyOfferDetail.getCountMese() != 0 ? energyOfferDetail.getCountMese() : null);
            offerParams.put(GetOffersForJsonService.MONTHLY_AMOUNT, decimalFormat.format(energyOfferDetail.getOfferCost()));
            offerParams.put(GetOffersForJsonService.YEARLY_UNITS, energyOfferDetail.getYearlyConsumption());
            offerParams.put(GetOffersForJsonService.ENERGY_POD_COUNT, energyOfferDetails.size());
            offerParams.put(EnergyOfferDetails.CODICE_PMC_PS, energyOfferDetail.getCodicePmcPS());
            offerParams.put(GetOffersForJsonService.PERCENTAGE, getFormattedPercent(ENERGY_OFFER, energyOfferDetail.getPercentage()));
//            offerParams.put(GetOffersForJsonService.PERCENTAGE, decimalFormatForPercent.format(getPercentage(offer.getOfferCost(), energyOfferDetail.getOfferCost())));
//            offerParams.put(GetOffersForJsonServiceImpl.DETAILS, buildEnergyOfferDetailsMap(energyOfferDetail));
            paramsList.add(offerParams);
        }
        return paramsList;
    }

//    private boolean isCurrentVoceEntityLast(int tlcListSize, int wlrListSize, int currentEntityCount) {
//        return ((tlcListSize != 0 ? tlcListSize - 1 : 0) + (wlrListSize != 0 ? wlrListSize - 1 : 0)) == currentEntityCount;
//    }

    public Collection<? extends Map<String, Object>> processInternetOffer() {
        List<Map<String, Object>> paramsList = new ArrayList<>();
        List<InternetDetailOffers> internetDetailOffersList = new InternetDetailOffersDAOImpl().getInternetDetailsOfferByInternetOfferId(
                offer.getInternetOfferId() != null ? offer.getInternetOfferId() : 0);
        for (int i = 0; i < internetDetailOffersList.size(); i++) {
            InternetDetailOffers internetDetailOffer = internetDetailOffersList.get(i);
//            if (internetDetailOffersList.size() - 1 == i) offerMap.remove(ADSL_OFFER);
            Map<String, Object> offerParams = new HashMap<>();
            offerParams.put(GetOffersForJsonService.BUNDLE_COST_VERSION, internetDetailOffer.getBundleCostVersion());
            offerParams.put(GetOffersForJsonService.SERVICES_COST_VERSION, internetDetailOffer.getServiceCostVersion());
            offerParams.put(GetOffersForJsonService.SERVICE_ID, Constants.INTERNET_SERVICE_ID);
            offerParams.put(GetOffersForJsonService.BRANCH, null);
            offerParams.put(GetOffersForJsonService.SUBSCRIBER, internetDetailOffer.isExistingClientOffer() ? internetDetailOffer.getAdsl() : "");
            offerParams.put(GetOffersForJsonService.MONTHLY_AMOUNT, decimalFormat.format(internetDetailOffer.getCost()));
            offerParams.put(GetOffersForJsonService.YEARLY_UNITS, getAdslNumByDesc(new BundleDAOImpl().getBundleDescByBundleId(internetDetailOffer.getBundleId())));
            offerParams.put(GetOffersForJsonService.ADSL_POD_COUNT, internetDetailOffersList.size());
            offerParams.put(GetOffersForJsonService.FIBRA, internetDetailOffer.getBundleId() == Constants.FIBRA_BUNDLE_ID);
            offerParams.put(GetOffersForJsonService.PERCENTAGE, getFormattedPercent(ADSL_OFFER, internetDetailOffer.getPercentage()));
//            offerParams.put(GetOffersForJsonService.PERCENTAGE, decimalFormatForPercent.format(getPercentage(offer.getOfferCost(), BigDecimal.valueOf(internetDetailOffer.getCost()))));
//            offerParams.put(GetOffersForJsonServiceImpl.DETAILS, buildInternetOfferDetailsMap(internetDetailOffer));
            paramsList.add(offerParams);
        }
        return paramsList;
    }

    public Collection<? extends Map<String, Object>> processGasOffer() {

        List<Map<String, Object>> paramsList = new ArrayList<>();
        List<GasDetailOffers> gasDetailOffersList = new GasDetailOffersDAOImpl().getGasOfferDetailsByGasOfferId(
                offer.getGasOfferId() != null ? offer.getGasOfferId() : 0);
//        if (offer.getGasOfferId() != null) offerMap.put(GAS_OFFER, offer.getGasOfferId());
        CategorieUsoDAOImpl categorieUsoDAO = new CategorieUsoDAOImpl();
        ClassePrelievoGasDAOImpl classePrelievoGasDAO = new ClassePrelievoGasDAOImpl();
        for (int i = 0; i < gasDetailOffersList.size(); i++) {
            GasDetailOffers gasDetailOffer = gasDetailOffersList.get(i);
//            if (gasDetailOffersList.size() - 1 == i) offerMap.remove(GAS_OFFER);
            Map<String, Object> offerParams = new HashMap<>();
            offerParams.put(GetOffersForJsonService.SERVICE_ID, Constants.GAS_SERVICE_ID);
            offerParams.put(GetOffersForJsonService.COST_VERSION, gasDetailOffer.getCostVersion());
            offerParams.put(GetOffersForJsonService.CATEGORIA_DI_UTILIZZO, categorieUsoDAO.getCategorieUsoDescById(gasDetailOffer.getUserTypeId()));
            offerParams.put(GetOffersForJsonService.CLASSE_PRELIEVO, classePrelievoGasDAO.getClasseDiPrelievoDescById(gasDetailOffer.getDayClassId()));
            offerParams.put(GetOffersForJsonService.REGIME_FISCALE,
                    gasDetailOffer.getFiscalClass() != 1 ? context.getString(R.string.fiscale_industriale) : context.getString(R.string.fiscale_civile));
            offerParams.put(GetOffersForJsonService.BRANCH, null);
            offerParams.put(GetOffersForJsonService.SUBSCRIBER, gasDetailOffer.isExistingClientOffer() ? gasDetailOffer.getPdr() : "");
            offerParams.put(GetOffersForJsonService.MONTHLY_AMOUNT, decimalFormat.format(gasDetailOffer.getOfferCost()));
            offerParams.put(GetOffersForJsonService.YEARLY_UNITS, gasDetailOffer.getYearlyConsumption());
            offerParams.put(GetOffersForJsonService.GAS_POD_COUNT, gasDetailOffersList.size());
            offerParams.put(GasDetailOffers.CODICE_PMC_PS, gasDetailOffer.getCodicePmcPS());
            offerParams.put(GetOffersForJsonService.PERCENTAGE, getFormattedPercent(GAS_OFFER, gasDetailOffer.getPercentage()));
//            offerParams.put(GetOffersForJsonService.PERCENTAGE, decimalFormatForPercent.format(getPercentage(offer.getOfferCost(), gasDetailOffer.getOfferCost())));
//            offerParams.put(GetOffersForJsonServiceImpl.DETAILS, buildGasOfferDetailsMap(gasDetailOffer));
            paramsList.add(offerParams);
        }
        return paramsList;
    }

    private Object buildEnergyOfferDetailsMap(EnergyOfferDetails energyOfferDetail) {
        Map<String, Object> details = new HashMap<>();
        details.put(GetOffersForJsonService.ENERGY_METER, new EnergyMeterDAOImpl().getEnergyMeterDescById(energyOfferDetail.getEnergyMeter()));
        details.put(GetOffersForJsonService.TIPO_CONTATORE, "");
        details.put(GetOffersForJsonService.TENSIONE, energyOfferDetail.getTensione() != 0 ? energyOfferDetail.getTensione() == 1 ? "BT" : "MT" : "");
        details.put(GetOffersForJsonService.YEARLY_CONSUMPTION, energyOfferDetail.getYearlyConsumption());
        details.put(GetOffersForJsonService.ANNUO_KWH, energyOfferDetail.getYearlyKwh() != null ? energyOfferDetail.getYearlyKwh() : "");
        details.put(GetOffersForJsonService.ANNUO_KWH_2, energyOfferDetail.getYearlyKwh2() != null ? energyOfferDetail.getYearlyKwh2() : "");
        details.put(GetOffersForJsonService.ANNUO_KWH_3, energyOfferDetail.getYearlyKwh3() != null ? energyOfferDetail.getYearlyKwh3() : "");
        List<EnergyOfferDateInterval> energyOfferDateIntervals = new EnergyOfferDateIntervalDAOImpl().getOfferDateIntervalByDetailsId(energyOfferDetail.getEnergyDetailOfferId());
        if (energyOfferDateIntervals.size() > 0) {
            List<Map<String, Object>> intervals = new ArrayList<>();
            for (EnergyOfferDateInterval energyOfferDateInterval : energyOfferDateIntervals) {
                Map<String, Object> currentInterval = new HashMap<>();
                currentInterval.put(GetOffersForJsonService.INTERVAL_KWH, energyOfferDateInterval.getKwh1() != Constants.NO_VALUE ? energyOfferDateInterval.getKwh1() : "");
                currentInterval.put(GetOffersForJsonService.INTERVAL_KWH_2, energyOfferDateInterval.getKwh2() != Constants.NO_VALUE ? energyOfferDateInterval.getKwh2() : "");
                currentInterval.put(GetOffersForJsonService.INTERVAL_KWH_3, energyOfferDateInterval.getKwh3() != Constants.NO_VALUE ? energyOfferDateInterval.getKwh3() : "");
                currentInterval.put(GetOffersForJsonService.PERIODO_DA, dateFormat.format(energyOfferDateInterval.getDateFrom()));
                currentInterval.put(GetOffersForJsonService.PERIODO_A, dateFormat.format(energyOfferDateInterval.getDateTo()));
                intervals.add(currentInterval);
            }
            details.put(GetOffersForJsonService.ENERGY_INTERVALS, intervals);
        }
        return details;
    }

    private Map<String, Object> buildInternetOfferDetailsMap(InternetDetailOffers internetDetailOffer) {
        Map<String, Object> details = new HashMap<>();
        if (internetDetailOffer.getServiceId().length() > 0) {
            List<Map<String, Object>> services = new ArrayList<>();
            ServicesDAOImpl servicesDAO = new ServicesDAOImpl();
            List<Services> serviceses = servicesDAO.getServicesByIds(internetDetailOffer.getServiceId().split(", "));
            for (Services service : serviceses) {
                Map<String, Object> currentService = new HashMap<>();
                currentService.put(GetOffersForJsonService.ADSL_SERVICE_ID, service.getServiceId());
                currentService.put(GetOffersForJsonService.ADSL_SERVICE_DESC, service.getServiceDesc());
                currentService.put(GetOffersForJsonService.ADSL_SERVICE_COST, service.getCost());
                services.add(currentService);
            }
            if (internetDetailOffer.getRouter() != 0) {
                Map<String, Object> currentService = new HashMap<>();
                Services routerService = servicesDAO.getServicesByFieldName(GetOffersForJsonService.ROUTER);
                currentService.put(GetOffersForJsonService.ADSL_SERVICE_ID, routerService.getServiceId());
                currentService.put(GetOffersForJsonService.ADSL_SERVICE_DESC, routerService.getServiceDesc());
                currentService.put(GetOffersForJsonService.ADSL_SERVICE_COST, routerService.getCost());
                services.add(currentService);
            }
            details.put(GetOffersForJsonService.ADSL_SERVICES, services);
        }
        return details;
    }

    private Map<String, Object> buildWlrOfferDetailsMap(WlrOfferDetails wlrOfferDetails) {
        Map<String, Object> details = new HashMap<>();
        Carriers carriers = new CarriersDAOImpl().getCarrierById(wlrOfferDetails.getCarrierId());
        details.put(GetOffersForJsonService.CARRIER_ID, carriers.getCarrierId());
        details.put(GetOffersForJsonService.CARRIER_DESC, carriers.getCarrierDesc());
        Networks networks = new NetworksDAOImpl().getNetworkById(wlrOfferDetails.getNetworkId());
        details.put(GetOffersForJsonService.NETWORK_ID, networks.getNetworkId());
        details.put(GetOffersForJsonService.NETWORK_DESC, networks.getNetworkDesc());
        LineTypes lineTypes = new LineTypesDAOImpl().getLineTypesById(wlrOfferDetails.getLineId());
        details.put(GetOffersForJsonService.LINE_TYPE, lineTypes.getLineDesc());
        details.put(GetOffersForJsonService.LINE_DESC, lineTypes.getLineDet());
        details.put(GetOffersForJsonService.LINE_COST, lineTypes.getLineCost());
        details.put(GetOffersForJsonService.LINES_NUMBER, new LineNumbersDAOImpl().getLineNumbersById(wlrOfferDetails.getNumLines()).getLineNumberDesc());
        if (wlrOfferDetails.getServicesId().length() > 0) {
            List<Map<String, Object>> services = new ArrayList<>();
            List<Services> serviceses = new ServicesDAOImpl().getServicesByIds(wlrOfferDetails.getServicesId().split(", "));
            for (Services service : serviceses) {
                Map<String, Object> currentService = new HashMap<>();
                currentService.put(GetOffersForJsonService.VOCE_SERVICE_ID, service.getServiceId());
                currentService.put(GetOffersForJsonService.VOCE_SERVICE_DESC, service.getServiceDesc());
                currentService.put(GetOffersForJsonService.VOCE_SERVICE_COST, service.getCost());
                if (service.getServiceDesc().equals("Numerazione aggiuntiva")) {
                    currentService.put(GetOffersForJsonService.NUMERAZIONE_AGGIUNTIVA, wlrOfferDetails.getServiceAddictionNumber());
                }
                services.add(currentService);
            }
            details.put(GetOffersForJsonService.VOCE_SERVICES, services);
        }
        return details;
    }

    private Map<String, Object> buildTlcOfferDetailsMap(TlcDetailsOffer tlcDetailsOffer) {
        Map<String, Object> details = new HashMap<>();
        details.put(GetOffersForJsonService.LINES_NUMBER, tlcDetailsOffer.getNumLines());
        return details;
    }

    private Object buildGasOfferDetailsMap(GasDetailOffers gasDetailOffer) {
        Map<String, Object> details = new HashMap<>();
        details.put(GetOffersForJsonService.YEARLY_CONSUMPTION, gasDetailOffer.getYearlyConsumption());
        details.put(GetOffersForJsonService.YEARLY_SMC, gasDetailOffer.getYearlySmc());
        details.put(GetOffersForJsonService.FISCAL_CLASS, new FiscalClassDAOImpl().getFiscalClassById(gasDetailOffer.getFiscalClass()).getDescFiscalClass());
        details.put(GetOffersForJsonService.DAY_CLASS, new ClassePrelievoGasDAOImpl().getClasseDiPrelievoDescById(gasDetailOffer.getDayClassId()));
        details.put(GetOffersForJsonService.USER_TYPE, new CategorieUsoDAOImpl().getCategorieUsoDescById(gasDetailOffer.getUserTypeId()));
        List<GasOfferDateInterval> gasOfferDateIntervals = new GasOfferDateIntervalDAOImpl().getOfferDateIntervalByGasDetailsId(gasDetailOffer.getGasDetailsOfferId());
        if (gasOfferDateIntervals.size() > 0) {
            if (gasOfferDateIntervals.get(0).getDateFrom() != null) {
                List<Map<String, Object>> intervals = new ArrayList<>();
                for (GasOfferDateInterval gasOfferDateInterval : gasOfferDateIntervals) {
                    Map<String, Object> currentInterval = new HashMap<>();
                    currentInterval.put(GetOffersForJsonService.INTERVAL_YEARLY_SMC, gasOfferDateInterval.getSmc());
                    currentInterval.put(GetOffersForJsonService.PERIODO_DA, dateFormat.format(gasOfferDateInterval.getDateFrom()));
                    currentInterval.put(GetOffersForJsonService.PERIODO_A, dateFormat.format(gasOfferDateInterval.getDateTo()));
                    intervals.add(currentInterval);
                }
                details.put(GetOffersForJsonService.GAS_INTEVALS, intervals);
            } else {
                details.put(GetOffersForJsonService.YEARLY_SMC, gasOfferDateIntervals.get(0).getSmc());
            }
        }
        return details;
    }

    private BigDecimal getPercentage(BigDecimal total, BigDecimal cost) {
        BigDecimal percentageCost = cost.multiply(BigDecimal.valueOf(100));
//        BigDecimal computationResult = percentageCost.divide(total, 1, BigDecimal.ROUND_HALF_UP);
        double percent = offerMap.size() == 0 ? Double.valueOf(decimalFormatForPercent.format(getLastPercent(sumOfPercent))) :
                Double.valueOf(decimalFormatForPercent.format((cost.doubleValue() / total.doubleValue()) * 100.00));
        sumOfPercent += percent;
        return new BigDecimal(percent);
    }

    private double getLastPercent(double sumOfPercent) {
        return Math.floor((100 - sumOfPercent) * 100) / 100;
    }


//    private double getLocalBranchMounthlyAmountForWlr(WlrOffers wlrOffer, WlrOfferDetails wlrOfferDetails) {
//        double result = 0.0;
//        if (!wlrOfferDetails.isExistingClientOffer()){
//            result = wlrOfferDetails.getcostBundle localBundle = new BundleDAOImpl().getBundleCostByBundleId(wlrOffer.getLocalBundleId())
//        }
//        return result;
//    }

    private double getMobileBundleCost(boolean isExistingClient, String lineName, Integer mobileBundle) {
        double result;
        if (isExistingClient) {
            result = new ClientDetailsTlcOffersDAOImpl().getClientDetailTlcOffersByLine(lineName).getMobileOfferCost();
        } else {
            result = new BundleDAOImpl().getBundleCostByBundleId(mobileBundle != null ? mobileBundle : 0);
        }
        return result;
    }

    private String getYearlyUnits(boolean isExistingClient, Integer bundleId) {
        String result;
        if (isExistingClient) {
            result = bundleId != null ? String.valueOf(bundleId) : "";
        } else {
            String bundleDesc = new BundleDAOImpl().getBundleDescByBundleId(bundleId != null ? bundleId : 0);
            result = bundleDesc.equals("Flat") ? "-1" : bundleDesc;
        }
        return result;
    }

    private String getAdslNumByDesc(String desc) {
        String result = "";
        switch (desc) {
            case "Fibra – fino a 100 MB":
                result = "100";
                break;
            case "ADSL - fino a 20 MB plus":
                result = "200";
                break;
        }
        return result;
    }
}
