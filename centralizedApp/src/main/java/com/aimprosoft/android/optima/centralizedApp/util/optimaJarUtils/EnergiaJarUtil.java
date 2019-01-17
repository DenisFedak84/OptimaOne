package com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDateIntervalDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDetailsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.FonteDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.HouseHolderDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.LightingTypesDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TownDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDateInterval;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.util.SDCardHelper;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.questionarioEnergy.QuestionarioValidation;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.questionarioEnergy.impl.AltraApparecchiatura;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.questionarioEnergy.impl.Asciugatrice;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.questionarioEnergy.impl.CondizionatoreRaffreddamento;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.questionarioEnergy.impl.CondizionatoreRiscaldamento;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.questionarioEnergy.impl.Forno;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.questionarioEnergy.impl.Frigorifero;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.questionarioEnergy.impl.Lavastoviglie;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.questionarioEnergy.impl.Lavatrice;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.questionarioEnergy.impl.Scaldabagno;
import com.optima.consumi.domain.Consume;
import com.optima.consumi.domain.ResponseConsume;
import com.optima.consumi.service.impl.CosumiCentralizzato;
import com.optima.potenza.domain.ResponsePotenza;
import com.optima.pricing.db.QueryManager;
import com.optima.pricing.domain.Pricing;

import java.io.File;
import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EnergiaJarUtil extends AbstractJarUtil {
    private EnergyOfferDetailsDAOImpl energyOfferDetailsDAO = new EnergyOfferDetailsDAOImpl();
    private float sumPotenza = 0f;
    private Set<Integer> potenzaSet = new HashSet<>();
    private final String IVA_CONSUMER_ALTRI_USI = "0.22";

    @Override
    void buildPricing(Pricing pricing, Offer offer) {
        pricing.setDatiEnergia(buildDatiEnergia(offer));
    }

    private Pricing.DatiEnergia buildDatiEnergia(Offer offer) {
        Pricing.DatiEnergia datiEnergia = new Pricing.DatiEnergia();
        datiEnergia.setModStimaEnergia(0.03f);
        List<EnergyOfferDetails> energyOfferDetailses = energyOfferDetailsDAO.getEnergyOfferDetailsByEnergyOfferId(offer.getEnergyOfferId());
        List<Pricing.DatiEnergia.DettaglioEnergia> dettaglioEnergiaList = datiEnergia.getDettaglioEnergia();

        for (EnergyOfferDetails energyOfferDetails : energyOfferDetailses) {
            Pricing.DatiEnergia.DettaglioEnergia dettaglioEnergia = new Pricing.DatiEnergia.DettaglioEnergia();

            Pricing.DatiEnergia.DettaglioEnergia.Consumi consumi = new Pricing.DatiEnergia.DettaglioEnergia.Consumi();
            buildEnergiaCompetenzaList(consumi.getCompetenza(), energyOfferDetails, offer);
            dettaglioEnergia.setConsumi(consumi);

            dettaglioEnergia.setDataInizioValidita(getDateInizio());
            dettaglioEnergia.setDataFineValidita(getDateFine());
//          dettaglioEnergia.setDataTariffa();
//          dettaglioEnergia.setGiaCliente("");
//          dettaglioEnergia.setMisuratore("");
//          dettaglioEnergia.setTariffaTrasporto(energyOfferDetails.getEnergyMeter() > 6 && offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR ?
//                    energyMeterDAO.getEnergiMeterById(energyOfferDetails.getEnergyMeter()).getTariffOption() :
//                    "BT");
            dettaglioEnergia.setPotenzaContatore(getPotenzaValue(offer, energyOfferDetails));
            dettaglioEnergia.setTariffaTrasporto(getTariffTransporto(offer, energyOfferDetails));
            dettaglioEnergia.setTipoTariffa(getTipoTariffa(offer));

            if (offer.getConfiguratorType() == Constants.CONSUMER_CONFIGURATOR
                    && energyOfferDetails.getTipologiaContratto() == Constants.ALTRI_USI) {
                dettaglioEnergia.setIva(IVA_CONSUMER_ALTRI_USI);
            }
            dettaglioEnergia.setCampagnaOfferta(offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR ? ID_CAMPAGNA_ENERGIA : ID_CAMPAGNA_ENERGIA_CONSUMER);
            dettaglioEnergiaList.add(dettaglioEnergia);
        }
        datiEnergia.setIdCampagnaAttivazione(offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR ? ID_CAMPAGNA_ENERGIA_SF : ID_CAMPAGNA_ENERGIA_SF_CONSUMER);
        datiEnergia.setDataStipula(getDataStipula());
        return datiEnergia;
    }

    private String getTipoTariffa(Offer offer) {
        String tipoTariffa;
        if (offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR) {
            tipoTariffa = Constants.NON_RESIDENZIALE_CODE;
        } else {
            tipoTariffa = offer.getClientTypeId() == Constants.RESIDENZIALE ? Constants.RESIDENZIALE_CODE : Constants.NON_RESIDENZIALE_CODE;
        }
        return tipoTariffa;
    }

    private void buildEnergiaCompetenzaList(List<Pricing.DatiEnergia.DettaglioEnergia.Consumi.Competenza> competenzaList, EnergyOfferDetails energyOfferDetails, Offer offer) {
        ResponseConsume responseConsume = getEnergyEstimation(energyOfferDetails, offer);
        receivePotenzaStimata(responseConsume, energyOfferDetails);
        if (responseConsume != null && responseConsume.getConsumeEnergiaResponse() != null && responseConsume.getConsumeEnergiaResponse().getResponseConsumi() != null) {
//            FonteDAOImpl fonteDAO = new FonteDAOImpl();
            for (ResponseConsume.ConsumeEnergiaResponse.ResponseConsumi responseConsumi : responseConsume.getConsumeEnergiaResponse().getResponseConsumi()) {
                Pricing.DatiEnergia.DettaglioEnergia.Consumi.Competenza competenza = new Pricing.DatiEnergia.DettaglioEnergia.Consumi.Competenza();
                competenza.setConsumo(responseConsumi.getConsumo());
                competenza.setConsumoF1(responseConsumi.getConsumoF1());
                competenza.setConsumoF2(responseConsumi.getConsumoF2());
                competenza.setConsumoF3(responseConsumi.getConsumoF3());
//            competenza.setFonte(fonteDAO.getFonteByDesc(responseConsumi.getFonte()).getFonteId());
                competenza.setMeseRiferimento(String.valueOf(responseConsumi.getMeseRiferimento()));
                competenzaList.add(competenza);
            }
        }
    }

    private void receivePotenzaStimata(ResponseConsume responseConsume, EnergyOfferDetails energyOfferDetails) {
        if (energyOfferDetails!=null &&
                energyOfferDetails.getTensione() > 1 &&
                !energyOfferDetails.isExistingClientOffer()) {
            if (potenzaSet.size() > 1 && responseConsume!=null) {
                ResponsePotenza responsePotenza = new PotenzaStimataUtil().getPotenzaStimata(responseConsume, energyOfferDetails);
                if (responsePotenza != null && responsePotenza.getPotenzaEnergiaResponse()!=null) {
                    energyOfferDetails.setPotenzaStimata(Math.round(responsePotenza.getPotenzaEnergiaResponse().getPotenza()));
                }
            } else {
                energyOfferDetails.setPotenzaStimata(potenzaSet.iterator().next());
            }
            energyOfferDetailsDAO.update(energyOfferDetails);
        }
    }

    public ResponseConsume getEnergyEstimation(EnergyOfferDetails energyOfferDetails, Offer offer) {
        CosumiCentralizzato cosumiCentralizzato = new CosumiCentralizzato(getConfigMap(ESTIMATION_JAR));

        Consume consume = new Consume();
        consume.setDatiEnergia(getConsumeDatiEnergia(energyOfferDetails, offer));
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
            Log.e(getClass().getName(), "error while estimation", e);
        }

        return responseConsumi;
    }

    public Consume.DatiEnergia getConsumeDatiEnergia(EnergyOfferDetails energyOfferDetails, Offer offer) {
        Consume.DatiEnergia datiEnergia = new Consume.DatiEnergia();
        if (!energyOfferDetails.isQuestionarioUsing()) {
            List<EnergyOfferDateInterval> intervals = new EnergyOfferDateIntervalDAOImpl().getOfferDateIntervalByDetailsId(energyOfferDetails.getEnergyDetailOfferId());
            if (!intervals.isEmpty()) {
                Consume.DatiEnergia.Consumi consumi = new Consume.DatiEnergia.Consumi();
                List<Consume.DatiEnergia.Consumi.Competenze> competenzeList = consumi.getCompetenze();
                for (EnergyOfferDateInterval interval : intervals) {
                    Consume.DatiEnergia.Consumi.Competenze competenze = new Consume.DatiEnergia.Consumi.Competenze();
                    competenze.setConsumoF1(String.valueOf(interval.getKwh1()));
                    competenze.setConsumoF2(interval.getKwh2() != Constants.NO_VALUE ? String.valueOf(interval.getKwh2()) : "");
                    competenze.setConsumoF3(interval.getKwh3() != Constants.NO_VALUE ? String.valueOf(interval.getKwh3()) : "");
                    competenze.setMeseDa(formatterFullDateWithSlash.format(interval.getDateFrom()));
                    competenze.setMeseA(formatterFullDateWithSlash.format(interval.getDateTo()));
                    competenze.setTipoDato("FATTURA");
                    sumPotenza += interval.getPotenzaImpegnata();
                    potenzaSet.add(interval.getPotenzaImpegnata());
                    competenzeList.add(competenze);
//              competenze.setTipoDato();
                }
                datiEnergia.setConsumi(consumi);
            }
            datiEnergia.setConsumoAnnuoTotale(energyOfferDetails.getYearlyKwh() != null ?
                    (float) (energyOfferDetails.getComputedYearlyConsumption()) : 0f);
        } else {
            Consume.DatiEnergia.InfoQuestionario infoQuestionario = new Consume.DatiEnergia.InfoQuestionario();
            Consume.DatiEnergia.InfoQuestionario.Elettrodimestici elettrodimestici = new Consume.DatiEnergia.InfoQuestionario.Elettrodimestici();
            Consume.DatiEnergia.InfoQuestionario.InformazioneAbitazione informazioneAbitazione = new Consume.DatiEnergia.InfoQuestionario.InformazioneAbitazione();
            buildElettrodomesticoList(energyOfferDetails, elettrodimestici.getInfoElettrodomestico());

            elettrodimestici.setPotenzaContatore(energyOfferDetails.getTensione() != 1 ? sumPotenza : getPotenzaValue(offer, energyOfferDetails));
            informazioneAbitazione.setAbitantiCasa(Integer.valueOf(new HouseHolderDAOImpl().getHouseHolderDescById(offer.getHouseHolderId()).replaceAll("\\+", "")));
            informazioneAbitazione.setPeriodoUso(offer.getMesi());
            informazioneAbitazione.setTipologiaIlluminazione(new LightingTypesDAOImpl().getLightingTypesDescById(energyOfferDetails.getLightingType()));
            infoQuestionario.setElettrodimestici(elettrodimestici);
            infoQuestionario.setInformazioneAbitazione(informazioneAbitazione);
            datiEnergia.setInfoQuestionario(infoQuestionario);
        }
        datiEnergia.setModStimaEnergia(0.03f);
//        datiEnergia.setEnelDTO(getEnelDTO(offer, energyOfferDetails));
        return datiEnergia;
    }

    private Consume.DatiEnergia.EnelDTO getEnelDTO(Offer offer, EnergyOfferDetails energyOfferDetails) {
        Consume.DatiEnergia.EnelDTO enelDTO = new Consume.DatiEnergia.EnelDTO();
        //Comment follow code to Avoid invoke remote service
        enelDTO.setCap(offer.getCap());
        enelDTO.setCodiceFiscale(offer.getCodiceFiscale());
        enelDTO.setCognome(offer.getSurname());

        enelDTO.setComune(offer.getTownId() != 0 ?
                new TownDAOImpl().getTownById(offer.getTownId()).toString() : Constants.EMPTY_STRING);
        enelDTO.setIndirizzo(offer.getIndirizzoDiFornitura());
        enelDTO.setNome(offer.getNome());
        enelDTO.setPiva(offer.getPiva());
        enelDTO.setPod(energyOfferDetails.getPod());
        enelDTO.setRagioneSociale(offer.getName());
        return enelDTO;
    }

    private void buildElettrodomesticoList(EnergyOfferDetails energyOfferDetails, List<Consume.DatiEnergia.InfoQuestionario.Elettrodimestici.InfoElettrodomestico> elettrodomesticoList) {
        QuestionarioValidation[] questionarioValidationArray = new QuestionarioValidation[]{
                new AltraApparecchiatura(energyOfferDetails),
                new Asciugatrice(energyOfferDetails),
                new CondizionatoreRaffreddamento(energyOfferDetails),
                new CondizionatoreRiscaldamento(energyOfferDetails),
                new Forno(energyOfferDetails),
                new Frigorifero(energyOfferDetails),
                new Lavastoviglie(energyOfferDetails),
                new Lavatrice(energyOfferDetails),
                new Scaldabagno(energyOfferDetails)
        };
        for (QuestionarioValidation questionarioValidation : questionarioValidationArray) {
            if (questionarioValidation.validationElettrodomestico()) {
                elettrodomesticoList.add(questionarioValidation.getInfoElettrodomestico());
            }
        }
        elettrodomesticoList.size();
    }
}
