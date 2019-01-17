package com.aimprosoft.android.optima.centralizedApp.service;

import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.CategorieUsoDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ClassePrelievoGasDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyMeterDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDateIntervalDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDetailsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.FonteDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasDetailOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasOfferDateIntervalDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TownDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDateInterval;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Fonte;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasDetailOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOfferDateInterval;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.util.SDCUtils.SDCEnergyReportImpl;
import com.aimprosoft.android.optima.centralizedApp.util.SDCUtils.SDCGasReportImpl;
import com.aimprosoft.android.optima.centralizedApp.util.SDCUtils.SDCReport;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.EnergiaJarUtil;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.GasJarUtil;
import com.optima.pricing.domain.Pricing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flexjson.JSONSerializer;

public class BuildJarInputParamsJsonUtil {

    ///////////////////// DATI MOBILE ////////////////////////////
    private static final String DATI_MOBILE = "datiMobile";
    private static final String DETTAGLIO_MOBILE = "dettaglioMobile";
    private static final String TIPO_PACCHETTO = "tipoPacchetto";
    private static final String NUMERO_SIM = "numeroSim";

    ////////////////////// DATI ENERGIA //////////////////////////
    private final String DATI_ENERGIA = "datiEnergia";
    private final String DETTAGLIO_ENERGIA = "dettaglioEnergia";
    private final String ID_CAMPAGNA_ATTIVAZIONE = "idCampagnaAttivazione";
    private final String DATA_INIZIO_VALIDITA = "dataInizioValidita";
    private final String DATA_FINE_VALIDITA = "dataFineValidita";
    private final String CAMPAGNA_OFFERTA = "campagnaOfferta";
    private final String TARIFFA_TRASPORTO = "tariffaTrasporto";
    private final String MISURATORE = "misuratore";
    private final String POTENZA_CONTATORE = "potenzaContatore";
    private final String GIA_CLIENTE = "giaCliente";
    private final String DATA_TARIFFA = "dataTariffa";
    private final String IVA = "iva";
    private final String CONSUMI = "consumi";//
    private final String CONSUMO_ANNUO_TOTALE = "consumoAnnuoTotale";
    private final String CONSUMI_FATTURA = "consumiFattura";
    private final String COMPETENZE = "competenze";
    private final String MESE_DA = "meseDa";
    private final String MESE_A = "meseA";
    private final String CONSUMO_F1 = "consumoF1";
    private final String CONSUMO_F2 = "consumoF2";
    private final String CONSUMO_F3 = "consumoF3";
    private final String TIPO_DATO = "tipoDato";
    private final String COSTO_ACQUISTO = "costoAcquisto";
    private final String POTENZA = "potenza";
    private final String TRASPORTO = "trasporto";//
    private final String ENEL_DTO = "enelDTO";
    private final String POD = "pod";
    private final String CAP = "cap";
    private final String INDIRIZZO = "indirizzo";
    private final String COMUNE = "comune";
    private final String PIVA = "piva";
    private final String NOME = "nome";
    private final String CODICE_FISCALE = "codiceFiscale";
    private final String COGNOME = "cognome";
    private final String ESTIMATEDCONSUMPTION = "estimatedConsumption";
    private final String MESE_RIFERIMENTO = "meseRiferimento";

    private final String INFO_QUESTIONARIO = "infoQuestionario";
    private final String INFORMAZIONE_ABITAZIONE = "informazioneAbitazione";
    private final String ABITANTI_CASA = "abitantiCasa";
    private final String PERIODO_USO = "periodoUso";
    private final String TIPOLOGIA_ILLUMINAZIONE = "tipologiaIlluminazione";
    private final String ELETTRODIMESTICI = "elettrodimestici";
    private final String INFO_ELETTRODOMESTICO = "infoElettrodomestico";
    private final String CODICE_ELETTRODOMESTICO = "codiceElettrodomestico";
    private final String ANNO_ACQUISTO = "annoAcquisto";
    private final String NUM_UTILIZZO = "numUtilizzo";
    private final String NUM_SPLIT = "numSplit";
    private final String CE = "ce";
    private final String DIMENSIONE = "dimensione";

    ////////////////////// DATI GAS //////////////////////////////
    public final String DATI_GAS = "datiGas";
    private final String DETTAGLIO_GAS = "dettaglioGas";
    private final String COMUNE_FORNITURA = "comuneFornitura";
    private final String TIPOLOGIA_IMPOSTE = "tipologiaImposte";
    private final String TIPOLOGIA_PDR = "tipologiaPDR";
    private final String CATEGORIA_USO = "categoriaUso";
    private final String CLASSE_PRELIEVO = "classePrelievo";
    private final String DATA_DA = "dataDa";
    private final String DATA_A = "dataA";
    private final String CONSUMO = "consumo";
    private final String CONSUMO_CUMULATO = "consumoCumulato";
    private final String TIPOLOGIA_ABITAZIONE = "tipologiaAbitazione";
    private final String SUPERFICIE_ABITAZIONE = "superficieAbitazione";
    private final String ANNO_COSTRUZIONE = "annoCostruzione";
    private final String PROFILO_UTILIZZO = "profiloUtilizzo";
    private final String CODICE_PROFILO_UTILIZZO = "codiceProfiloUtilizzo";

    ////////////////////// DATI VOICE ////////////////////////////
    private final String DATI_VOICE = "datiVoce";
    private final String DETTAGLIO_VOCE = "dettaglioVoce";
    private final String ID_CAMPAGNA = "idCampagna";
    private final String MINUTI_FISSO = "minutiFisso";
    private final String MINUTI_MOBILE = "minutiMobile";
    private final String LINEA_VOCE = "lineaVoce";
    private final String NUMERO_LINEE = "numeroLinee";
    private final String OPERATORE = "operatore";
    private final String CLI = "cli";
    private final String COSTO_LINEA_GIA_CLIENTE = "costoLineaGiaCliente";
    private final String NUMERO_AGGIUNTIVI = "numeroAggiuntivi";
    private final String OPZIONE_LINEA = "opzioneLinea";
    private final String CODICE_OPZIONE = "codiceOpzione";
    private final String TIPO_OPZIONE = "tipoOpzione";
    private final String TIPO_LINEA = "tipoLinea";
    private final String TIPO_RETE = "tipoRete";
    ////////////////////// DATI ADSL ////////////////////////////
    private final String DATI_ADSL = "datiAdsl";
    private final String DETTAGLIO_ADSL = "dettaglioAdsl";
    private final String IP_AGGIUNTIVI = "ipAggiuntivi";
    private final String LINEA_SOLO_DATI = "lineaSoloDati";
    private final String ROUTER = "router";
    private final String TIPO_ADSL = "tipoAdsl";
    private final String OPZIONI_LINEA = "opzioniLinea";


    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private EnergyOfferDateIntervalDAOImpl energyDateIntervalDAO = new EnergyOfferDateIntervalDAOImpl();
    private GasOfferDateIntervalDAOImpl gasOfferDateIntervalDAO = new GasOfferDateIntervalDAOImpl();
    //    private TlcDetailsOfferDAOImpl tlcDetailsOfferDAO = new TlcDetailsOfferDAOImpl();
//    private LineNumbersDAOImpl lineNumbersDAO = new LineNumbersDAOImpl();
    private TownDAOImpl townDAO = new TownDAOImpl();

    public BuildJarInputParamsJsonUtil() {
    }

    public String getJson(Pricing pricing, Offer offer) {
        Map<String, Object> rootMap = new HashMap<>();
        if (pricing.getDatiEnergia() != null) {
            rootMap.put(DATI_ENERGIA, buildDatiEnergia(pricing, offer));
        }
        if (pricing.getDatiGas() != null) {
            rootMap.put(DATI_GAS, buildDatiGas(pricing, offer));
        }
        if (pricing.getDatiVoce() != null) {
            rootMap.put(DATI_VOICE, buildDatiVoce(pricing));
        }
        if (pricing.getDatiAdsl() != null) {
            rootMap.put(DATI_ADSL, buildDatiAdsl(pricing));
        }
        rootMap.put(Offer.PRICING_OFFER_ID, offer.getPricingOfferId());
        return new JSONSerializer().deepSerialize(rootMap);
    }

    public Map<String, Object> getJsonMap(Pricing pricing, Offer offer) {
        Map<String, Object> rootMap = new HashMap<>();
        if (pricing.getDatiEnergia() != null) {
            rootMap.put(DATI_ENERGIA, buildDatiEnergia(pricing, offer));
        }
        if (pricing.getDatiGas() != null) {
            rootMap.put(DATI_GAS, buildDatiGas(pricing, offer));
        }
        if (pricing.getDatiVoce() != null) {
            rootMap.put(DATI_VOICE, buildDatiVoce(pricing));
        }
        if (pricing.getDatiAdsl() != null) {
            rootMap.put(DATI_ADSL, buildDatiAdsl(pricing));
        }
        if (pricing.getDatiMobile() != null) {
            rootMap.put(DATI_MOBILE, buildDatiMobile(pricing));
        }
        rootMap.put(Offer.PRICING_OFFER_ID, offer.getPricingOfferId());
        return rootMap;
    }

    //------------------------------------Energia---------------------------------------------------
    private Map<String, Object> buildDatiEnergia(Pricing pricing, Offer offer) {
        Map<String, Object> dettaglioEnergiaMap = new HashMap();
        List<Map<String, Object>> dettaglioList = new ArrayList<>();
        List<Pricing.DatiEnergia.DettaglioEnergia> dettaglioEnergiaList = pricing.getDatiEnergia().getDettaglioEnergia();
        List<EnergyOfferDetails> energyOfferDetailsList = new EnergyOfferDetailsDAOImpl().getEnergyOfferDetailsByEnergyOfferId(offer.getEnergyOfferId());
        EnergyOfferDetails energyOfferDetails;
        Pricing.DatiEnergia.DettaglioEnergia dettaglioEnergia;
        for (int i = 0; i < energyOfferDetailsList.size(); i++) {
            energyOfferDetails = energyOfferDetailsList.get(i);
            dettaglioEnergia = dettaglioEnergiaList.get(i);
            Map<String, Object> dettaglioMap = new HashMap<>();
            dettaglioMap.put(DATA_INIZIO_VALIDITA, dettaglioEnergia.getDataInizioValidita());
            dettaglioMap.put(DATA_FINE_VALIDITA, dettaglioEnergia.getDataFineValidita());
            dettaglioMap.put(CAMPAGNA_OFFERTA, dettaglioEnergia.getCampagnaOfferta());
            dettaglioMap.put(TARIFFA_TRASPORTO, dettaglioEnergia.getTariffaTrasporto());
            dettaglioMap.put(MISURATORE, dettaglioEnergia.getMisuratore());
            dettaglioMap.put(POTENZA_CONTATORE, dettaglioEnergia.getPotenzaContatore());
            dettaglioMap.put(SDCReport.SDC, new SDCEnergyReportImpl().getSingleOfferSDCDataList(offer, energyOfferDetails));
            dettaglioMap.put(GIA_CLIENTE, dettaglioEnergia.getGiaCliente());
            dettaglioMap.put(DATA_TARIFFA, dettaglioEnergia.getDataTariffa());
            dettaglioMap.put(IVA, dettaglioEnergia.getIva());
            dettaglioMap.put(CONSUMI, buildEnergiaConsumi(energyOfferDetails, offer, pricing.getDatiEnergia().getDettaglioEnergia().get(i)));
            dettaglioList.add(dettaglioMap);
        }
        dettaglioEnergiaMap.put(DETTAGLIO_ENERGIA, dettaglioList);
        dettaglioEnergiaMap.put(ID_CAMPAGNA_ATTIVAZIONE, pricing.getDatiEnergia().getIdCampagnaAttivazione());
        dettaglioEnergiaMap.put(Offer.CARTACEO, offer.isCartaceo());
        return dettaglioEnergiaMap;
    }

    private Map<String, Object> buildEnergiaConsumi(EnergyOfferDetails energyOfferDetails, Offer offer, Pricing.DatiEnergia.DettaglioEnergia dettaglioEnergia) {
        Map<String, Object> consumiMap = new HashMap<>();
        consumiMap.put(CONSUMO_ANNUO_TOTALE, energyOfferDetails.getYearlyKwh() != null ? energyOfferDetails.getYearlyKwh() : 0);
        EnergyMeterDAOImpl energyMeterDAO = new EnergyMeterDAOImpl();
        List<Map<String, Object>> competenzeDetailsList = new ArrayList<>();
        List<EnergyOfferDateInterval> energyOfferDateIntervalList = energyDateIntervalDAO.getOfferDateIntervalByDetailsId(energyOfferDetails.getEnergyDetailOfferId());
        for (EnergyOfferDateInterval energyOfferDateInterval : energyOfferDateIntervalList) {
            Map<String, Object> competenzeDetails = new HashMap<>();
            competenzeDetails.put(MESE_DA, simpleDateFormat.format(energyOfferDateInterval.getDateFrom()));
            competenzeDetails.put(MESE_A, simpleDateFormat.format(energyOfferDateInterval.getDateTo()));
            competenzeDetails.put(CONSUMO_F1, String.valueOf(energyOfferDateInterval.getKwh1()));
            competenzeDetails.put(CONSUMO_F2, energyOfferDateInterval.getKwh2() != Constants.NO_VALUE ? String.valueOf(energyOfferDateInterval.getKwh2()) : "");
            competenzeDetails.put(CONSUMO_F3, energyOfferDateInterval.getKwh3() != Constants.NO_VALUE ? String.valueOf(energyOfferDateInterval.getKwh3()) : "");
            competenzeDetails.put(TIPO_DATO, "");
            competenzeDetails.put(COSTO_ACQUISTO, "");
            competenzeDetails.put(POTENZA, energyDateIntervalDAO.getPotenzaImpegnataByDetailsIdAndMonth(energyOfferDetails.getEnergyDetailOfferId(),
                    new SimpleDateFormat("MM").format(energyOfferDateInterval.getDateFrom())));
            competenzeDetails.put(TRASPORTO, energyOfferDetails.getEnergyMeter() > 6 ? energyMeterDAO.getEnergiMeterById(energyOfferDetails.getEnergyMeter()).getTariffOption() : "");
            competenzeDetailsList.add(competenzeDetails);
        }
        Map<String, Object> competenzeMap = new HashMap<>();
        competenzeMap.put(COMPETENZE, competenzeDetailsList);
        consumiMap.put(CONSUMI_FATTURA, competenzeMap);
        consumiMap.put(INFO_QUESTIONARIO, questionarioEnergyMap(energyOfferDetails, offer));///informazioneAbitazione
        consumiMap.put(ENEL_DTO, buildEnelDTO(offer));

        Pricing.DatiEnergia.DettaglioEnergia.Consumi consumi = dettaglioEnergia.getConsumi();
        List<Pricing.DatiEnergia.DettaglioEnergia.Consumi.Competenza> competenzaList = consumi.getCompetenza();
        List<Map<String, Object>> competenzeInputParamList = new ArrayList<>();
        FonteDAOImpl fonteDAO = new FonteDAOImpl();
        for (Pricing.DatiEnergia.DettaglioEnergia.Consumi.Competenza competenza : competenzaList) {
            Map<String, Object> competenzaMap = new HashMap<>();
            competenzaMap.put(MESE_RIFERIMENTO, competenza.getMeseRiferimento());
            competenzaMap.put(COSTO_ACQUISTO, competenza.getCostoAcquisto());
            competenzaMap.put(CONSUMO, competenza.getConsumo());
            competenzaMap.put(CONSUMO_F1, competenza.getConsumoF1());
            competenzaMap.put(CONSUMO_F2, competenza.getConsumoF2());
            competenzaMap.put(CONSUMO_F3, competenza.getConsumoF3());
//            competenzaMap.put(Fonte.FONTE_DESC, fonteDAO.getFonteById(competenza.getFonte()).getFonteDesc()); //// TODO: 30.03.16 return prod value here
            competenzaMap.put(Fonte.FONTE_DESC, "");
            competenzeInputParamList.add(competenzaMap);
        }
        consumiMap.put(ESTIMATEDCONSUMPTION, competenzeInputParamList);
        return consumiMap;
    }

    //questionario part
    private Map<String, Object> questionarioEnergyMap(EnergyOfferDetails energyOfferDetails, Offer offer) {
        Map<String, Object> questionarioMap = new HashMap<>();
//        Consume.DatiEnergia datiEnergia = new EnergiaJarUtil().getConsumeDatiEnergia(energyOfferDetails, offer);
//        Consume.DatiEnergia.InfoQuestionario infoQuestionario = datiEnergia.getInfoQuestionario();
//        if (infoQuestionario != null) {
//            Consume.DatiEnergia.InfoQuestionario.InformazioneAbitazione informazioneAbitazione = infoQuestionario.getInformazioneAbitazione();
//            Consume.DatiEnergia.InfoQuestionario.Elettrodimestici elettrodimestici = infoQuestionario.getElettrodimestici();
//
//            Map<String, Object> abitazioneMap = new HashMap<>();
//            abitazioneMap.put(ABITANTI_CASA, informazioneAbitazione.getAbitantiCasa());
//            abitazioneMap.put(PERIODO_USO, informazioneAbitazione.getPeriodoUso());
//            abitazioneMap.put(TIPOLOGIA_ILLUMINAZIONE, informazioneAbitazione.getTipologiaIlluminazione());
//            questionarioMap.put(INFORMAZIONE_ABITAZIONE, abitazioneMap);
//            Map<String, Object> elettrodimesticiMap = new HashMap<>();
//            elettrodimesticiMap.put(POTENZA_CONTATORE, elettrodimestici.getPotenzaContatore());
//            List<Map<String, Object>> infoElList = new ArrayList<>();
//            List<Consume.DatiEnergia.InfoQuestionario.Elettrodimestici.InfoElettrodomestico> infoElettrodomesticoList = elettrodimestici.getInfoElettrodomestico();
//            for (Consume.DatiEnergia.InfoQuestionario.Elettrodimestici.InfoElettrodomestico infoElettrodomestico : infoElettrodomesticoList) {
//                Map<String, Object> infoElMap = new HashMap<>();
//                infoElMap.put(CODICE_ELETTRODOMESTICO, infoElettrodomestico.getCodiceElettrodomestico());
//                infoElMap.put(ANNO_ACQUISTO, infoElettrodomestico.getAnnoAcquisto());
//                infoElMap.put(NUM_UTILIZZO, infoElettrodomestico.getNumUtilizzo());
//                infoElMap.put(NUM_SPLIT, infoElettrodomestico.getNumSplit());
//                infoElMap.put(CE, infoElettrodomestico.getCe());
//                infoElMap.put(POTENZA, infoElettrodomestico.getPotenza());
//                infoElMap.put(DIMENSIONE, infoElettrodomestico.getDimensione());
//                infoElList.add(infoElMap);
//            }
//            elettrodimesticiMap.put(INFO_ELETTRODOMESTICO, infoElList);
//            questionarioMap.put(ELETTRODIMESTICI, elettrodimesticiMap);
//        }
        return questionarioMap;
    }

    private Map<String, Object> buildEnelDTO(Offer offer) {
        Map<String, Object> enelDTO = new HashMap<>();
        enelDTO.put(POD, "");
        enelDTO.put(CAP, "");
        enelDTO.put(INDIRIZZO, "");
        enelDTO.put(COMUNE, townDAO.getTownById(offer.getTownId()).getTownDesc());
        enelDTO.put(PIVA, offer.getPiva());
        enelDTO.put(NOME, offer.getName());
        enelDTO.put(CODICE_FISCALE, "");
        enelDTO.put(COGNOME, "");
        return enelDTO;
    }

    //---------------------------------------------Gas----------------------------------------------
    private Map<String, Object> buildDatiGas(Pricing pricing, Offer offer) {
        Map<String, Object> dettaglioGasMap = new HashMap<>();
        List<Map<String, Object>> dettaglioList = new ArrayList<>();
        List<Pricing.DatiGas.DettaglioGas> dettaglioGasList = pricing.getDatiGas().getDettaglioGas();
        List<GasDetailOffers> gasOfferDetailsList = new GasDetailOffersDAOImpl().getGasOfferDetailsByGasOfferId(offer.getGasOfferId());
        GasDetailOffers gasDetailOffers;
        Pricing.DatiGas.DettaglioGas dettaglioGas;
        for (int i = 0; i < gasOfferDetailsList.size(); i++) {
            gasDetailOffers = gasOfferDetailsList.get(i);
            dettaglioGas = dettaglioGasList.get(i);
            Map<String, Object> dettaglioMap = new HashMap<>();
            dettaglioMap.put(DATA_INIZIO_VALIDITA, dettaglioGas.getDataInizioValidita());
            dettaglioMap.put(DATA_FINE_VALIDITA, dettaglioGas.getDataFineValidita());
            dettaglioMap.put(MISURATORE, dettaglioGas.getMisuratore());
            dettaglioMap.put(DATA_TARIFFA, dettaglioGas.getDataTariffa());
            dettaglioMap.put(IVA, dettaglioGas.getIva());
            dettaglioMap.put(SDCReport.SDC, new SDCGasReportImpl().getSingleOfferSDCDataList(offer, gasDetailOffers));
            dettaglioMap.put(CAMPAGNA_OFFERTA, dettaglioGas.getCampagnaOfferta());
            dettaglioMap.put(COMUNE_FORNITURA, dettaglioGas.getComuneFornitura());
            dettaglioMap.put(TIPOLOGIA_IMPOSTE, dettaglioGas.getTipologiaImposte());
            dettaglioMap.put(TIPOLOGIA_PDR, dettaglioGas.getTipologiaPDR());
            dettaglioMap.put(GIA_CLIENTE, dettaglioGas.getGiaCliente());
            dettaglioMap.put(CONSUMI, buildGasConsumi(gasDetailOffers, offer, dettaglioGas));
            dettaglioList.add(dettaglioMap);
        }

        dettaglioGasMap.put(DETTAGLIO_GAS, dettaglioList);
        dettaglioGasMap.put(ID_CAMPAGNA_ATTIVAZIONE, pricing.getDatiGas().getIdCampagnaAttivazione());
        dettaglioGasMap.put(Offer.CARTACEO, offer.isCartaceo());
        return dettaglioGasMap;
    }

    private Map<String, Object> buildGasConsumi(GasDetailOffers gasDetailOffers, Offer offer, Pricing.DatiGas.DettaglioGas dettaglioGas) {
        Map<String, Object> conumiMap = new HashMap<>();
        conumiMap.put(CATEGORIA_USO, new CategorieUsoDAOImpl().getCategorieUsoDescById(gasDetailOffers.getUserTypeId()));
        conumiMap.put(COMUNE, townDAO.getTownById(offer.getTownId()).getTownDescCentralized());
        conumiMap.put(CLASSE_PRELIEVO, new ClassePrelievoGasDAOImpl().getClasseDiPrelievoDescById(gasDetailOffers.getDayClassId()));
        conumiMap.put(CONSUMO_ANNUO_TOTALE, gasDetailOffers.getYearlySmc());
        List<Map<String, Object>> competenzeDetailList = new ArrayList<>();
        List<GasOfferDateInterval> gasOfferDateIntervalList = gasOfferDateIntervalDAO.getOfferDateIntervalByGasDetailsId(gasDetailOffers.getGasDetailsOfferId());
        if (!gasOfferDateIntervalList.isEmpty() && gasOfferDateIntervalList.get(0).getDateFrom() != null) {
            for (GasOfferDateInterval gasOfferDateInterval : gasOfferDateIntervalList) {
                Map<String, Object> competenzeDatails = new HashMap<>();
                competenzeDatails.put(DATA_DA, simpleDateFormat.format(gasOfferDateInterval.getDateFrom()));
                competenzeDatails.put(DATA_A, simpleDateFormat.format(gasOfferDateInterval.getDateTo()));
                competenzeDatails.put(CONSUMO, gasOfferDateInterval.getSmc());
                competenzeDatails.put(COSTO_ACQUISTO, "");
                competenzeDetailList.add(competenzeDatails);
            }
        }
        Map<String, Object> competenzeMap = new HashMap<>();
        competenzeMap.put(COMPETENZE, competenzeDetailList);
        conumiMap.put(CONSUMI_FATTURA, competenzeMap);
        conumiMap.put(INFO_QUESTIONARIO, questionarioGasMap(gasDetailOffers, offer)); ///infoQuestionario gas

        Pricing.DatiGas.DettaglioGas.Consumi consumi = dettaglioGas.getConsumi();
        List<Pricing.DatiGas.DettaglioGas.Consumi.Competenza> competenzaList = consumi.getCompetenza();
        List<Map<String, Object>> competenzeInputParamList = new ArrayList<>();
        for (Pricing.DatiGas.DettaglioGas.Consumi.Competenza competenza : competenzaList) {
            Map<String, Object> competenzaMap = new HashMap<>();
            competenzaMap.put(MESE_RIFERIMENTO, competenza.getMeseRiferimento());
            competenzaMap.put(COSTO_ACQUISTO, competenza.getCostoAcquisto());
            competenzaMap.put(CONSUMO, competenza.getConsumo());
            competenzaMap.put(CONSUMO_CUMULATO, competenza.getConsumoCumulato());
            competenzeInputParamList.add(competenzaMap);
        }
        conumiMap.put(ESTIMATEDCONSUMPTION, competenzeInputParamList);

        return conumiMap;
    }

    private Map<String, Object> questionarioGasMap(GasDetailOffers gasDetailOffers, Offer offer) {
        Map<String, Object> questionarioMap = new HashMap<>();
//        Consume.DatiGas datiGas = new GasJarUtil().getConsumeDatiGas(offer, gasDetailOffers);
//
//        Consume.DatiGas.InfoQuestionario infoQuestionario = datiGas.getInfoQuestionario();
//        if (infoQuestionario != null) {
//            Consume.DatiGas.InfoQuestionario.InformazioneAbitazione informazioneAbitazione = infoQuestionario.getInformazioneAbitazione();
//            Consume.DatiGas.InfoQuestionario.Elettrodomestici elettrodomestici = infoQuestionario.getElettrodomestici();
//            List<Consume.DatiGas.InfoQuestionario.Elettrodomestici.ProfiloUtilizzo> profiloUtilizzoList = elettrodomestici.getProfiloUtilizzo();
//
//            Map<String, Object> abitazioneMap = new HashMap<>();
//            abitazioneMap.put(ABITANTI_CASA, informazioneAbitazione.getAbitantiCasa());
//            abitazioneMap.put(TIPOLOGIA_ABITAZIONE, informazioneAbitazione.getTipologiaAbitazione());
//            abitazioneMap.put(SUPERFICIE_ABITAZIONE, informazioneAbitazione.getSuperficieAbitazione());
//            abitazioneMap.put(ANNO_COSTRUZIONE, informazioneAbitazione.getAnnoCostruzione());
//            questionarioMap.put(INFORMAZIONE_ABITAZIONE, abitazioneMap);///informazioneAbitazione
//
//            Map<String, Object> elettrodimesticiMap = new HashMap<>();
//            List<Map<String, Object>> infoGasList = new ArrayList<>();
//            for (Consume.DatiGas.InfoQuestionario.Elettrodomestici.ProfiloUtilizzo profiloUtilizzo : profiloUtilizzoList) {
//                Map<String, Object> profileMap = new HashMap<>();
//                profileMap.put(CODICE_PROFILO_UTILIZZO, profiloUtilizzo.getCodiceProfiloUtilizzo());
//                infoGasList.add(profileMap);
//            }
//            elettrodimesticiMap.put(PROFILO_UTILIZZO, infoGasList);
//            questionarioMap.put(ELETTRODIMESTICI, elettrodimesticiMap);///elettrodomestici
//        }
        return questionarioMap;
    }

    //--------------------------------------------Voice---------------------------------------------
    private Map<String, Object> buildDatiVoce(Pricing pricing) {
        Map<String, Object> dettaglioVoiceMap = new HashMap<>();
        List<Map<String, Object>> dettaglioList = new ArrayList<>();
        List<Pricing.DatiVoce.DettaglioVoce> dettaglioVoceList = pricing.getDatiVoce().getDettaglioVoce();
        Pricing.DatiVoce.DettaglioVoce dettaglioVoce;
        for (int i = 0; i < dettaglioVoceList.size(); i++) {
            dettaglioVoce = dettaglioVoceList.get(i);
            Map<String, Object> dettaglioMap = new HashMap<>();
            dettaglioMap.put(DATA_INIZIO_VALIDITA, dettaglioVoce.getDataInizioValidita());
            dettaglioMap.put(DATA_FINE_VALIDITA, dettaglioVoce.getDataFineValidita());
            dettaglioMap.put(ID_CAMPAGNA, dettaglioVoce.getIdCampagna());
            dettaglioMap.put(IVA, dettaglioVoce.getIva());
            dettaglioMap.put(MINUTI_FISSO, dettaglioVoce.getMinutiFisso());
            dettaglioMap.put(MINUTI_MOBILE, dettaglioVoce.getMinutiMobile());
            dettaglioMap.put(LINEA_VOCE, buildLineaVoce(dettaglioVoce));
            dettaglioList.add(dettaglioMap);
        }
        dettaglioVoiceMap.put(DETTAGLIO_VOCE, dettaglioList);
        dettaglioVoiceMap.put(ID_CAMPAGNA_ATTIVAZIONE, pricing.getDatiVoce().getIdCampagnaAttivazione());
        return dettaglioVoiceMap;
    }

    private List<Map<String, Object>> buildLineaVoce(Pricing.DatiVoce.DettaglioVoce dettaglioVoce) {
        List<Map<String, Object>> lineaVoiceList = new ArrayList<>();
        List<Pricing.DatiVoce.DettaglioVoce.LineaVoce> lineaVoces = dettaglioVoce.getLineaVoce();
        for (int i = 0; i < lineaVoces.size(); i++) {
            Pricing.DatiVoce.DettaglioVoce.LineaVoce lineaVoce = lineaVoces.get(i);
            Map<String, Object> lineaMap = new HashMap<>();
            lineaMap.put(NUMERO_LINEE, lineaVoce.getNumeroLinee());
            lineaMap.put(OPERATORE, lineaVoce.getOperatore());
            lineaMap.put(CLI, lineaVoce.getCli());
            lineaMap.put(COSTO_LINEA_GIA_CLIENTE, lineaVoce.getCostoLineaGiaCliente());
            lineaMap.put(GIA_CLIENTE, lineaVoce.getGiaCliente());
            lineaMap.put(NUMERO_AGGIUNTIVI, lineaVoce.getNumeroAggiuntivi());
            lineaMap.put(OPZIONE_LINEA, buildOpzioneLinea(lineaVoce));
            lineaMap.put(TIPO_LINEA, lineaVoce.getTipoLinea());
            lineaMap.put(TIPO_RETE, lineaVoce.getTipoRete());
            lineaVoiceList.add(lineaMap);
        }
        return lineaVoiceList;
    }

    private List<Map<String, Object>> buildOpzioneLinea(Pricing.DatiVoce.DettaglioVoce.LineaVoce lineaVoce) {
        List<Map<String, Object>> opzioneList = new ArrayList<>();
        List<Pricing.DatiVoce.DettaglioVoce.LineaVoce.OpzioneLinea> opzioneLineaList = lineaVoce.getOpzioneLinea();
        for (int i = 0; i < opzioneLineaList.size(); i++) {
            Map<String, Object> buildLineaMap = new HashMap<>();
            Pricing.DatiVoce.DettaglioVoce.LineaVoce.OpzioneLinea opzioneLinea = opzioneLineaList.get(i);
            buildLineaMap.put(CODICE_OPZIONE, opzioneLinea.getCodiceOpzione());
//            buildLineaMap.put(TIPO_OPZIONE, opzioneLinea.getTipoOpzione());
            opzioneList.add(buildLineaMap);
        }
        return opzioneList;
    }

    //--------------------------------------------Adsl----------------------------------------------
    private Map<String, Object> buildDatiAdsl(Pricing pricing) {
        Map<String, Object> dettagloiAdslMap = new HashMap<>();
        List<Pricing.DatiAdsl.DettaglioAdsl> dettaglioAdslsList = pricing.getDatiAdsl().getDettaglioAdsl();
        List<Map<String, Object>> adslDatiList = new ArrayList<>();
        Pricing.DatiAdsl.DettaglioAdsl dettaglioAdsl;
        for (int i = 0; i < dettaglioAdslsList.size(); i++) {
            dettaglioAdsl = dettaglioAdslsList.get(i);
            Map<String, Object> dettaglioMap = new HashMap<>();
            dettaglioMap.put(DATA_INIZIO_VALIDITA, dettaglioAdsl.getDataInizioValidita());
            dettaglioMap.put(DATA_FINE_VALIDITA, dettaglioAdsl.getDataFineValidita());
            dettaglioMap.put(IVA, dettaglioAdsl.getIva());
            dettaglioMap.put(ID_CAMPAGNA, dettaglioAdsl.getIdCampagna());
            dettaglioMap.put(GIA_CLIENTE, dettaglioAdsl.getGiaCliente());
            List<Pricing.DatiAdsl.DettaglioAdsl.OpzioniLinea> lineaList = dettaglioAdsl.getOpzioniLinea();
            List<String> optioniLineaList = new ArrayList<>();
            for (int j = 0; j < lineaList.size(); j++) {
                optioniLineaList.add(lineaList.get(j).getCodiceOpzione());
            }
            dettaglioMap.put(OPZIONI_LINEA, optioniLineaList);
            dettaglioMap.put(TIPO_ADSL, dettaglioAdsl.getTipoAdsl());
            adslDatiList.add(dettaglioMap);
        }
        dettagloiAdslMap.put(DETTAGLIO_ADSL, adslDatiList);
        dettagloiAdslMap.put(ID_CAMPAGNA_ATTIVAZIONE, pricing.getDatiAdsl().getIdCampagnaAttivazione());
        return dettagloiAdslMap;
    }

    private Map<String, Object> buildDatiMobile(Pricing pricing) {
        Map<String, Object> dettaglioMobileMap = new HashMap<>();
        List<Pricing.DatiMobile.DettaglioMobile> dettaglioMobilesList = pricing.getDatiMobile().getDettaglioMobile();
        List<Map<String, Object>> mobileDatiList = new ArrayList<>();
        Pricing.DatiMobile.DettaglioMobile dettaglioMobile;
        for (int i = 0; i < dettaglioMobilesList.size(); i++) {
            dettaglioMobile = dettaglioMobilesList.get(i);
            Map<String, Object> dettaglioMap = new HashMap<>();
            dettaglioMap.put(DATA_INIZIO_VALIDITA, dettaglioMobile.getDataInizioValidita());
            dettaglioMap.put(DATA_FINE_VALIDITA, dettaglioMobile.getDataFineValidita());
            dettaglioMap.put(IVA, dettaglioMobile.getIva());
            dettaglioMap.put(ID_CAMPAGNA, dettaglioMobile.getIdCampagna());
            dettaglioMap.put(TIPO_PACCHETTO, dettaglioMobile.getTipoPacchetto());
            dettaglioMap.put(NUMERO_SIM, dettaglioMobile.getNumeroSim());
            mobileDatiList.add(dettaglioMap);
        }
        dettaglioMobileMap.put(DETTAGLIO_MOBILE, mobileDatiList);
        dettaglioMobileMap.put(ID_CAMPAGNA_ATTIVAZIONE, pricing.getDatiMobile().getIdCampagnaAttivazione());
        return dettaglioMobileMap;
    }
}
