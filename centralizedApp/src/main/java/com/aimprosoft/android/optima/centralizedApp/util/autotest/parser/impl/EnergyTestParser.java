package com.aimprosoft.android.optima.centralizedApp.util.autotest.parser.impl;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.activity.optilife.BaseActivity;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyMeterDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDateIntervalDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDetailsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyResultDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TariffaDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.BaseEntity;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDateInterval;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyResult;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.OfferResult;
import com.aimprosoft.android.optima.centralizedApp.service.AbstractService;
import com.aimprosoft.android.optima.centralizedApp.service.net.EnergyAdditionalWebServices;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.listener.OnParsingErrorListener;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.parser.AbstractTestParser;

import org.apache.poi.ss.usermodel.Row;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnergyTestParser extends AbstractTestParser {

    private final String TENSIONE_BT = "BT/potenza fissa";
    private final String TENSIONE_BTA6 = "BTA6/potenza variabile";
    private final String TENSIONE_MT = "MT";
    private final String DOMESTICO = "Domestico";
    private final String RESIDENZIALE = "Residenziale";

    private EnergyOffer energyOffer;
    private EnergyOfferDetails energyOfferDetails;
    private EnergyOfferDAOImpl energyOfferDAO = new EnergyOfferDAOImpl();
    private EnergyOfferDetailsDAOImpl energyOfferDetailsDAO = new EnergyOfferDetailsDAOImpl();
    private EnergyOfferDateIntervalDAOImpl energyOfferDateIntervalDAO = new EnergyOfferDateIntervalDAOImpl();
    private EnergyResultDAOImpl energyResultDAO = new EnergyResultDAOImpl();
    private TariffaDAOImpl tariffaDAO = new TariffaDAOImpl();
    private OfferDAOImpl offerDAO = new OfferDAOImpl();
    private EnergyMeterDAOImpl energyMeterDAO = new EnergyMeterDAOImpl();
    private DecimalFormat potenzaDecimalFormat;
    private DecimalFormatSymbols decimalFormatSymbols;
    private OnParsingErrorListener onParsingErrorListener;

    public EnergyTestParser() {
    }

    @Override
    public void parse(List<Row> rowList, Offer offer, OfferResult offerResult) {
        defineTestMode(offer);
        energyOffer = new EnergyOffer();
        energyOfferDAO.insert(energyOffer);
        offer.setEnergyOfferId(energyOffer.getEnergyOfferId());
        String lastPodNumber = null;
        String podNumber = null;
        try {
            for (Row row : rowList) {
                podNumber = row.getCell(resources.getInteger(R.integer.id_pod)).getStringCellValue();
                if (lastPodNumber == null || !lastPodNumber.equals(podNumber)) {
                    initializeOfferResult(offerResult, row);
                    createEnergyOfferDetails(row, podNumber, offer, offerResult);
                } else {
                    createEnergyDateInterval(row);
                    listener.onParsingComplete(podNumber);
                }
                lastPodNumber = podNumber;
            }
        } catch (Exception e) {
            if (onParsingErrorListener != null) {
                onParsingErrorListener.onParsingError(MyApplication.getContext()
                        .getString(R.string.energy_parsing_error_message, podNumber, e.getMessage()));
            }
        }
    }


    private void createEnergyOfferDetails(Row row, final String podNumber, final Offer offer,
                                          OfferResult offerResult) {
        if (podNumber.isEmpty()) {
            listener.onParsingComplete(podNumber);
            return;
        }
        energyOfferDetails = new EnergyOfferDetails();

        offer.setClientTypeId(getTipoTariffaId(row));
        energyOfferDetails.setPod(row
                .getCell(getCellIndexByTestMode(R.integer.codice_pod_business, R.integer.codice_pod_consumer))
                .getStringCellValue());

        int kwh1 = getIntFromCell(row
                .getCell(getCellIndexByTestMode(R.integer.consumo_annuo_f1_f0_business, R.integer.consumo_annuo_f1_f0_consumer)));

        energyOfferDetails.setYearlyKwh(kwh1 != 0 && kwh1 != Constants.NO_VALUE ? kwh1 : null);

        int kwh2 = getIntFromCell(row
                .getCell(getCellIndexByTestMode(R.integer.consumo_annuo_f2_business, R.integer.consumo_annuo_f2_consumer)));

        energyOfferDetails.setYearlyKwh2(kwh2 != 0 && kwh2 != Constants.NO_VALUE ? kwh2 : null);

        int kwh3 = getIntFromCell(row
                .getCell(getCellIndexByTestMode(R.integer.consumo_annuo_f3_business, R.integer.consumo_annuo_f3_consumer)));

        energyOfferDetails.setYearlyKwh3(kwh3 != 0 && kwh3 != Constants.NO_VALUE ? kwh3 : null);

        energyOfferDetails.setComputedYearlyConsumption(kwh1 + kwh2 + kwh3);

        energyOfferDetails.setEnergyOfferId(energyOffer.getEnergyOfferId());

        energyOfferDetails.setTipologiaContratto(getTipoContratto(row));

        int tensione = getTensioneId(row);
        energyOfferDetails.setTensione(tensione);
        energyOfferDetails.setTariffaId(getTariffaId(row, tensione));
        energyOfferDetails.setEnergyMeter(getEnergyMeterByConfiguratorType(row, energyOfferDetails.getTipologiaContratto()));
        energyOfferDetailsDAO.insert(energyOfferDetails);
        createEnergyDateInterval(row);

        Map<String, BaseEntity> entityMap = new HashMap<>();
        entityMap.put(EnergyAdditionalWebServices.OFFER, offer);
        entityMap.put(EnergyAdditionalWebServices.ENERGY_OFFER_DETAILS, energyOfferDetails);

        createEnergyResult(row, offerResult, podNumber);

        EnergyAdditionalWebServices webService = new EnergyAdditionalWebServices(
                new AbstractService.OnTaskCompleted<EnergyAdditionalWebServices>() {
                    @Override
                    public void onTaskCompleted(EnergyAdditionalWebServices service) {
                        Map<String, Long> resultMap = service.getResult();
                        energyOfferDetails.setCountMese(getWebserviceResultParam(resultMap.get(EnergyAdditionalWebServices.COUNT_MESE)));
                        energyOfferDetails.setCountMese(getWebserviceResultParam(resultMap.get(EnergyAdditionalWebServices.COUNT_POD)));
                        energyOfferDetails.setCountMese(getWebserviceResultParam(resultMap.get(EnergyAdditionalWebServices.SUM_ATTIVA)));
                        energyOfferDetailsDAO.update(energyOfferDetails);
                        offerDAO.update(offer);
                        listener.onParsingComplete(podNumber);
                    }
                });
        webService.setOffer(offer);
        webService.setEnergyOfferDetails(energyOfferDetails);
        webService.execute();
    }

    private void createEnergyResult(Row row, OfferResult offerResult, String podNumber) {
        EnergyResult energyResult = new EnergyResult();
        energyResult.setPotenza(getDoubleFromCell(row.getCell(getCellIndexByTestMode(R.integer.result_potenza_business,
                R.integer.potenza_consumer))));
        energyResult.setCodiceTariffa(row.getCell(getCellIndexByTestMode(R.integer.result_codice_tariffa_business,
                R.integer.result_codice_tariffa_consumer)).getStringCellValue());
        energyResult.setMancatoConsumo(getDoubleFromCell(row.getCell(getCellIndexByTestMode(R.integer.result_mancato_consumo_business,
                R.integer.result_mancato_consumo_consumer))));
        energyResult.setSforamento(getDoubleFromCell(row.getCell(getCellIndexByTestMode(R.integer.result_sforamento_business,
                R.integer.result_sforamento_consumer))));
        energyResult.setTagliaMese(getDoubleFromCell(row.getCell(getCellIndexByTestMode(R.integer.result_taglia_mese_business,
                R.integer.result_taglia_mese_consumer))));
        energyResult.setPrezzo(getDoubleFromCell(row.getCell(getCellIndexByTestMode(R.integer.result_prezzo_ee_con_business,
                R.integer.result_prezzo_ee_con_consumer))));
        energyResult.setPrezzoConIva(getDoubleFromCell(row.getCell(getCellIndexByTestMode(R.integer.result_prezzo_ee_con_iva_business,
                R.integer.result_prezzo_ee_con_iva_consumer))));
        energyResult.setOpzioneTariffaria(row.getCell(getCellIndexByTestMode(R.integer.result_opzione_tariffaria_business,
                R.integer.result_opzione_tariffaria_consumer)).getStringCellValue());
        energyResult.setEnergyPerc(Double.valueOf(decimalFormat.format(getDoubleFromCell(row.getCell(getCellIndexByTestMode(R.integer.result_perc_energy_business,
                R.integer.result_perc_energy_consumer))) * 100)));
        energyResult.setPodNumber(podNumber);
        energyResult.setResultOfferId(offerResult.getResultOfferId());
        energyResultDAO.insert(energyResult);
    }

    private void createEnergyDateInterval(Row podRow) {
        Date dateDa = podRow.getCell(
                getCellIndexByTestMode(R.integer.intervallo_di_tiempo_da_business,
                        R.integer.intervallo_di_tiempo_da_consumer)).getDateCellValue();
        if (dateDa != null) {
            String stringDateDa = BaseActivity.simpleDateFormat.format(dateDa);
            EnergyOfferDateInterval energyOfferDateInterval = new EnergyOfferDateInterval();
            energyOfferDateInterval.setDateFrom(stringDateDa);
            Date dateA = podRow.getCell(
                    getCellIndexByTestMode(R.integer.intervallo_di_tiempo_a_business,
                            R.integer.intervallo_di_tiempo_a_consumer)).getDateCellValue();
            energyOfferDateInterval.setDateTo(BaseActivity.simpleDateFormat.format(dateA));
            energyOfferDateInterval.setIdEnergyDetailOffer(energyOfferDetails.getEnergyDetailOfferId());

            int kwh1 = getIntFromCell(podRow.getCell(
                    getCellIndexByTestMode(R.integer.consumo_intervallo_di_tiempo_f0_f1_business,
                            R.integer.consumo_intervallo_di_tiempo_f0_f1_consumer)));
            energyOfferDateInterval.setKwh1(kwh1 != 0 ? kwh1 : Constants.NO_VALUE);

            int kwh2 = getIntFromCell(podRow.getCell(
                    getCellIndexByTestMode(R.integer.consumo_intervallo_di_tiempo_f2_business,
                            R.integer.consumo_intervallo_di_tiempo_f2_consumer)));
            energyOfferDateInterval.setKwh2(kwh2 != 0 ? kwh2 : Constants.NO_VALUE);

            int kwh3 = getIntFromCell(podRow.getCell(
                    getCellIndexByTestMode(R.integer.consumo_intervallo_di_tiempo_f3_business,
                            R.integer.consumo_intervallo_di_tiempo_f3_consumer)));
            energyOfferDateInterval.setKwh3(kwh3 != 0 ? kwh3 : Constants.NO_VALUE);

            energyOfferDateInterval.setPotenzaImpegnata(getManualPotenza(podRow));
            energyOfferDateIntervalDAO.insert(energyOfferDateInterval);
        }
    }

    private int getManualPotenza(Row podRow) {
        int potenzaImegnata = 0;
        if (energyOfferDetails.getTensione() != Constants.BT) {
            potenzaImegnata = getIntFromCell(podRow.getCell(resources.getInteger(R.integer.potenza_business)));
        }
        return potenzaImegnata;
    }

    private int getTariffaId(Row row, int tensione) {
        int tipoTariffa = 0;
        if (tensione == Constants.MT) {
            String tariffa = row.getCell(resources.getInteger(R.integer.tariffa_business)).getStringCellValue();
            tipoTariffa = tariffaDAO.getTariffaByDesc(tariffa).getTariffaId();
        }
        return tipoTariffa;
    }

    private int getTensioneId(Row podRow) {
        int tensione = 1;

        if (isBusinessMode) {
            String tensioneStr = podRow.getCell(resources.getInteger(R.integer.tensione_business))
                    .getStringCellValue();
            switch (tensioneStr) {
                case TENSIONE_BT:
                    tensione = 1;
                    break;
                case TENSIONE_BTA6:
                    tensione = 2;
                    break;
                case TENSIONE_MT:
                    tensione = 3;
                    break;
                default:
                    tensione = 1;
                    break;
            }
        }
        return tensione;
    }

    private Integer getWebserviceResultParam(Long value) {
        return value != null ? value.intValue() : 0;
    }

    private int getTipoContratto(Row podRow) {
        int tipoContratto = 0;
        if (!isBusinessMode) {
            tipoContratto = podRow.getCell(resources.getInteger(R.integer.tipologia_duso)).getStringCellValue()
                    .equals(DOMESTICO) ? 2 : 1;
        }
        return tipoContratto;
    }

    public int getTipoTariffaId(Row podRow) {
        int tipoTariffaId = 1;
        if (!isBusinessMode) {
            tipoTariffaId = podRow
                    .getCell(resources.getInteger(R.integer.tipologia_cliente_consumer))
                    .getStringCellValue().equals(RESIDENZIALE) ? 1 : 2;
        }
        return tipoTariffaId;
    }

    public int getEnergyMeterByConfiguratorType(Row row, int tariffaId) {
        initializeDecimalFormat();
        int energyMeterId;
        String tariffOption = row
                .getCell(getCellIndexByTestMode(R.integer.tariffa_business, R.integer.tariffa_consumer))
                .getStringCellValue().replace(SPECIAL_SPACE, "").trim();
        if (isBusinessMode) {
            energyMeterId = energyMeterDAO.getEnergiMeterByTariffOptionAndProjectType(tariffOption,
                    getSystem()).getEnergyMeterId();
        } else {
            double potenza = getDoubleFromCell(row.getCell(resources.getInteger(R.integer.potenza_consumer)));
            energyMeterId = energyMeterDAO.getEnergiMeterByFollowingParams(tariffOption,
                    potenza > 6 && energyOfferDetails.getTipologiaContratto() == Constants.DOMESTICO ? Constants.SPECIAL_POTENZA_VALUE : potenzaDecimalFormat.format(potenza),
                    tariffaId == Constants.DOMESTICO ? 1 : 2,
                    getSystem()).getEnergyMeterId();
        }
        return energyMeterId;
    }

    private void initializeDecimalFormat() {
        if (potenzaDecimalFormat == null) {
            decimalFormatSymbols = new DecimalFormatSymbols();
            decimalFormatSymbols.setDecimalSeparator(',');
            String PATTERN = "##.#";
            potenzaDecimalFormat = new DecimalFormat(PATTERN, decimalFormatSymbols);
        }
    }

    public OnParsingErrorListener getOnParsingErrorListener() {
        return onParsingErrorListener;
    }

    public void setOnParsingErrorListener(OnParsingErrorListener onParsingErrorListener) {
        this.onParsingErrorListener = onParsingErrorListener;
    }
}
