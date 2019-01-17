package com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.CategorieUsoDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.FiscalClassDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.FlatTypeDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasDetailOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasOfferDateIntervalDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasUtilizationProfileClassDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasYearOfReferenceDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.HouseHolderDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TownDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasDetailOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOfferDateInterval;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.util.SDCardHelper;
import com.optima.consumi.domain.Consume;
import com.optima.consumi.domain.ResponseConsume;
import com.optima.consumi.service.impl.CosumiCentralizzato;
import com.optima.pricing.db.QueryManager;
import com.optima.pricing.domain.Pricing;

import java.io.File;
import java.sql.Connection;
import java.util.List;

public class GasJarUtil extends AbstractJarUtil {

    private final String TIPO_DOMESTICO = "DOM";
    private final String TIPO_ALTRI_USI = "A";
    private final String CLASSE_PRELIEVO_DEFAULT = "1";

    private TownDAOImpl townDAO = new TownDAOImpl();

    @Override
    void buildPricing(Pricing pricing, Offer offer) {
        pricing.setDatiGas(buildDatiGas(offer));
    }

    private Pricing.DatiGas buildDatiGas(Offer offer) {
        Pricing.DatiGas datiGas = new Pricing.DatiGas();
        datiGas.setModStimaGas(0.03f);
        List<Pricing.DatiGas.DettaglioGas> dettaglioGasList = datiGas.getDettaglioGas();

        FiscalClassDAOImpl fiscalClassDAO = new FiscalClassDAOImpl();
        List<GasDetailOffers> detailOffersList = new GasDetailOffersDAOImpl().getGasOfferDetailsByGasOfferId(offer.getGasOfferId());
        for (GasDetailOffers gasDetailOffers : detailOffersList) {
            Pricing.DatiGas.DettaglioGas dettaglioGas = new Pricing.DatiGas.DettaglioGas();

            Pricing.DatiGas.DettaglioGas.Consumi consumi = new Pricing.DatiGas.DettaglioGas.Consumi();
            buildGasCompetenzaList(consumi.getCompetenza(), gasDetailOffers, offer);

            dettaglioGas.setConsumi(consumi);
            dettaglioGas.setDataInizioValidita(getDateInizio());
            dettaglioGas.setDataFineValidita(getDateFine());
            dettaglioGas.setCampagnaOfferta(offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR ? ID_CAMPAGNA_GAS : ID_CAMPAGNA_GAS_CONSUMER);
            dettaglioGas.setComuneFornitura(getTownDescription(offer, gasDetailOffers));
            dettaglioGas.setTipologiaImposte(fiscalClassDAO.getFiscalClassCodeById(gasDetailOffers.getFiscalClass()));
            dettaglioGas.setTipologiaPDR(getTipologiaPDR(gasDetailOffers.getTipoContratto()));
//        dettaglioGas.setDataTariffa("205001");
//        dettaglioGas.setGiaCliente("");
//        dettaglioGas.setMisuratore("");

            dettaglioGasList.add(dettaglioGas);
        }
        datiGas.setIdCampagnaAttivazione(offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR ? ID_CAMPAGNA_GAS_SF : ID_CAMPAGNA_GAS_SF_CONSUMER);
        return datiGas;
    }

    private void buildGasCompetenzaList(List<Pricing.DatiGas.DettaglioGas.Consumi.Competenza> competenzaList, GasDetailOffers gasDetailOffers, Offer offer) {
        ResponseConsume responseConsume = getGasEstimation(offer, gasDetailOffers);
        if (responseConsume != null && responseConsume.getConsumeGasResponse() != null && responseConsume.getConsumeGasResponse().getResponseConsumi() != null) {
            for (ResponseConsume.ConsumeGasResponse.ResponseConsumi consumi : responseConsume.getConsumeGasResponse().getResponseConsumi()) {
                Pricing.DatiGas.DettaglioGas.Consumi.Competenza competenza = new Pricing.DatiGas.DettaglioGas.Consumi.Competenza();
                competenza.setConsumo(consumi.getConsumo());
                competenza.setMeseRiferimento(String.valueOf(consumi.getMeseRiferimento()));
                competenza.setConsumoCumulato(consumi.getConsumoCumulato());
                competenzaList.add(competenza);
            }
        }
    }

    public Consume.DatiGas getConsumeDatiGas(Offer offer, GasDetailOffers gasDetailOffers) {
        Consume.DatiGas datiGas = new Consume.DatiGas();
        GasOffer gasOffer = new GasOfferDAOImpl().getGasOfferById(offer.getGasOfferId());
        if (!gasOffer.isQuestionarioUsing()) {
            List<GasOfferDateInterval> intervals = new GasOfferDateIntervalDAOImpl().getOfferDateIntervalByGasDetailsId(gasDetailOffers.getGasDetailsOfferId());
            if (!intervals.isEmpty() && intervals.get(0).getDateFrom() != null) {
                Consume.DatiGas.Consumi consumi = new Consume.DatiGas.Consumi();
                List<Consume.DatiGas.Consumi.Competenze> competenzeList = consumi.getCompetenze();
                for (GasOfferDateInterval interval : intervals) {
                    Consume.DatiGas.Consumi.Competenze competenze = new Consume.DatiGas.Consumi.Competenze();
                    competenze.setConsumo(interval.getSmc());
                    competenze.setDataDa(formatterFullDateWithSlash.format(interval.getDateFrom()));
                    competenze.setDataA(formatterFullDateWithSlash.format(interval.getDateTo()));
                    competenzeList.add(competenze);
                }
                datiGas.setConsumi(consumi);
            }
            datiGas.setConsumoAnnuoTotale((float) gasDetailOffers.getYearlySmc());
        } else {
            Consume.DatiGas.InfoQuestionario.InformazioneAbitazione informazioneAbitazione = new Consume.DatiGas.InfoQuestionario.InformazioneAbitazione();
            informazioneAbitazione.setAbitantiCasa(Integer.valueOf(new HouseHolderDAOImpl().getHouseHolderDescById(offer.getHouseHolderId()).replaceAll("\\+", "")));
            informazioneAbitazione.setAnnoCostruzione(new GasYearOfReferenceDAOImpl().getGasYearOfReferenceValueById(offer.getGasYearOfReference()));
            informazioneAbitazione.setSuperficieAbitazione(offer.getSuperficie());
            informazioneAbitazione.setTipologiaAbitazione(String.valueOf(new FlatTypeDAOImpl().getFlatTypeValueById(offer.getFlatType())));

            Consume.DatiGas.InfoQuestionario.Elettrodomestici elettrodomestici = new Consume.DatiGas.InfoQuestionario.Elettrodomestici();
            List<Consume.DatiGas.InfoQuestionario.Elettrodomestici.ProfiloUtilizzo> profiloUtilizzoList = elettrodomestici.getProfiloUtilizzo();
            Consume.DatiGas.InfoQuestionario.Elettrodomestici.ProfiloUtilizzo profiloUtilizzo;
            for (String codice : gasDetailOffers.getProfiloDiUtilizzo().split(", ")) {
                profiloUtilizzo = new Consume.DatiGas.InfoQuestionario.Elettrodomestici.ProfiloUtilizzo();
                profiloUtilizzo.setCodiceProfiloUtilizzo(codice);
                profiloUtilizzoList.add(profiloUtilizzo);
            }
            Consume.DatiGas.InfoQuestionario infoQuestionario = new Consume.DatiGas.InfoQuestionario();
            infoQuestionario.setInformazioneAbitazione(informazioneAbitazione);
            infoQuestionario.setElettrodomestici(elettrodomestici);
            datiGas.setInfoQuestionario(infoQuestionario);
            datiGas.setConsumoAnnuoTotale(null);
        }

        datiGas.setCategoriaUso(offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR || !gasOffer.isQuestionarioUsing() ?
                String.valueOf(new CategorieUsoDAOImpl().getCodiceCategoriaById(gasDetailOffers.getUserTypeId())) :
                new GasUtilizationProfileClassDAOImpl().getClassCode(gasDetailOffers.getValidationMap()));
        datiGas.setClassePrelievo(offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR ?
                String.valueOf(gasDetailOffers.getDayClassId()) : CLASSE_PRELIEVO_DEFAULT);
        datiGas.setComune(getTownDescription(offer, gasDetailOffers));
        datiGas.setModStimaGas(0.03f);
        datiGas.setComune(getTownDescription(offer, gasDetailOffers));
        return datiGas;
    }

    private String getTownDescription(Offer offer, GasDetailOffers gasDetailOffers) {
        String townDesc = townDAO.getTownById((offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR && gasDetailOffers.getTownId() != 0 ?
                gasDetailOffers.getTownId() : offer.getTownId())).getTownDescCentralized();
//        if(townDesc.contains("'")){
//            townDesc = townDesc.replaceAll("'", "''");
//        }
        return townDesc;
    }

    public ResponseConsume getGasEstimation(Offer offer, GasDetailOffers gasDetailOffers) {
        CosumiCentralizzato cosumiCentralizzato = new CosumiCentralizzato(getConfigMap(ESTIMATION_JAR));

        Consume consume = new Consume();
        consume.setDatiGas(getConsumeDatiGas(offer, gasDetailOffers));
        ResponseConsume responseConsumi = null;

        try {
            String urlLite = "jdbc:sqlite:" + SDCardHelper.DATABASE_LOCATION_DIR + File.separator + SDCardHelper.CPI2_DB_NAME;
            QueryManager queryManager = new QueryManager(AbstractJarUtil.SQLITE_DRIVER, urlLite, true);
            Connection connection = queryManager.openConnection(AbstractJarUtil.SQLITE_DRIVER, urlLite);
            responseConsumi = cosumiCentralizzato.calculateConsumes(consume, queryManager, connection);
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            Log.e(getClass().getName(), MyApplication.getContext().getResources().getString(R.string.estimation_error_message), e);
        }
        return responseConsumi;
    }

    private String getTipologiaPDR(int tipoContratto) {
        return tipoContratto != Constants.DOMESTICO ? TIPO_ALTRI_USI : TIPO_DOMESTICO;
    }
}
