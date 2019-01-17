package com.aimprosoft.android.optima.centralizedApp.util.autotest.parser.impl;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.activity.optilife.BaseActivity;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.CategorieUsoDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ClassePrelievoGasDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.FiscalClassDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasDetailOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasOfferDateIntervalDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasResultDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TownDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasDetailOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOfferDateInterval;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasResult;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.OfferResult;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.listener.OnParsingErrorListener;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.parser.AbstractTestParser;

import org.apache.poi.ss.usermodel.Row;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.List;

public class GasTestParser extends AbstractTestParser {

    private GasOffer gasOffer;
    private GasDetailOffers gasDetailOffer;
    private GasOfferDAOImpl gasOfferDAO = new GasOfferDAOImpl();
    private GasDetailOffersDAOImpl gasDetailOffersDAO = new GasDetailOffersDAOImpl();
    private GasOfferDateIntervalDAOImpl gasOfferDateIntervalDAO = new GasOfferDateIntervalDAOImpl();
    private GasResultDAOImpl gasResultDAO = new GasResultDAOImpl();
    private OfferDAOImpl offerDAO = new OfferDAOImpl();
    private FiscalClassDAOImpl fiscalClassDAO = new FiscalClassDAOImpl();
    private CategorieUsoDAOImpl categorieUsoDAO = new CategorieUsoDAOImpl();
    private ClassePrelievoGasDAOImpl classePrelievoGasDAO = new ClassePrelievoGasDAOImpl();
    private TownDAOImpl townDAO = new TownDAOImpl();
    private OnParsingErrorListener onParsingErrorListener;

    private final String DOMESTICO = "Domestico";
    private final String USI_DIVERSI = "Usi diversi";
    private final String CONDOMINIO = "Condominio";
    private final int CLASSE_PRELIEVO_DEFAULT = 1;

    public GasTestParser() {
    }

    @Override
    public void parse(List<Row> rowList, Offer offer, OfferResult offerResult) {
        defineTestMode(offer);
        gasOffer = new GasOffer();
        gasOfferDAO.insert(gasOffer);
        offer.setGasOfferId(gasOffer.getGasOfferId());
        offerDAO.update(offer);
        String lastPdrNumber = null;
        String pdrNumber = null;
        try {
            for (Row row : rowList) {
                pdrNumber = row.getCell(
                        getCellIndexByTestMode(R.integer.id_pdr_business, R.integer.id_pdr_consumer))
                        .getStringCellValue();
                if (lastPdrNumber == null || !lastPdrNumber.equals(pdrNumber)) {
                    initializeOfferResult(offerResult, row);
                    createGasOfferDetails(row, pdrNumber, offer, offerResult);
                } else {
                    createGasDateInterval(row, pdrNumber);
                }
                lastPdrNumber = pdrNumber;
            }
        } catch (Exception e){
            if (onParsingErrorListener != null) {
                onParsingErrorListener.onParsingError(MyApplication.getContext()
                        .getString(R.string.gas_parsing_error_message, pdrNumber, e.getMessage()));
            }
        }
    }

    private void createGasDateInterval(Row pdrRow, String pdrNumber) {
        Date dateDa = pdrRow.getCell(
                getCellIndexByTestMode(R.integer.intervallo_tempo_da_gas_business,
                        R.integer.intervallo_tempo_da_gas_consumer)).getDateCellValue();
        if (dateDa != null) {
            String stringDateDa = BaseActivity.simpleDateFormat.format(dateDa);
            GasOfferDateInterval gasOfferDateInterval = new GasOfferDateInterval();
            gasOfferDateInterval.setDateFrom(stringDateDa);
            Date dateA = pdrRow.getCell(
                    getCellIndexByTestMode(R.integer.intervallo_tempo_a_gas_business,
                            R.integer.intervallo_tempo_a_gas_consumer)).getDateCellValue();
            gasOfferDateInterval.setDateTo(BaseActivity.simpleDateFormat.format(dateA));
            gasOfferDateInterval.setGasDetailOfferId(gasDetailOffer.getGasDetailsOfferId());
            gasOfferDateInterval.setSmc(getIntFromCell(pdrRow.getCell(
                    getCellIndexByTestMode(R.integer.consumo_intervallo_tempo_gas_busines,
                            R.integer.consumo_intervallo_tempo_gas_consumer))));
            gasOfferDateIntervalDAO.insert(gasOfferDateInterval);
        }
        listener.onParsingComplete(pdrNumber);
    }

    private void createGasOfferDetails(Row row, String pdrNumber, Offer offer, OfferResult offerResult) {
        if (pdrNumber.isEmpty()) {
            listener.onParsingComplete(pdrNumber);
            return;
        }

        gasDetailOffer = new GasDetailOffers();

        gasDetailOffer.setYearlySmc(getIntFromCell(row
                .getCell(getCellIndexByTestMode(R.integer.consumo_annuo_business, R.integer.consumo_annuo_consumer))));

        gasDetailOffer.setFiscalClass(getRegimeFiscale(row));

        gasDetailOffer.setTipoContratto(getTipologiaDusoId(row));

        gasDetailOffer.setUserTypeId(categorieUsoDAO.getCategorieUsoDescAndSystemById(
                row.getCell(getCellIndexByTestMode(
                        R.integer.categoria_di_utilizzo_business,
                        R.integer.categoria_di_utilizzo_consumer)).getStringCellValue(),
                getSystem()).getIdCategoriaUso());

        gasDetailOffer.setDayClassId(getDayClass(row));

        int townId = townDAO.getTownByDesc(row
                .getCell(getCellIndexByTestMode(R.integer.comune_business, R.integer.comune_consumer))
                .getStringCellValue().trim())
                .getTownId();

        gasDetailOffer.setTownId(townId);
        offer.setTownId(townId);
        createGasResult(row, offerResult, pdrNumber);

        gasDetailOffer.setGasOfferId(gasOffer.getGasOfferId());
        gasDetailOffersDAO.insert(gasDetailOffer);
        offerDAO.update(offer);
        createGasDateInterval(row, pdrNumber);
    }

    private void createGasResult(Row row, OfferResult offerResult, String pdrNumber) {
        GasResult gasResult = new GasResult();
        gasResult.setTagliaMese(getDoubleFromCell(row.getCell(getCellIndexByTestMode(R.integer.result_taglia_mese_gas_business,
                R.integer.result_taglia_mese_gas_consumer))));
        gasResult.setPrezzo(getDoubleFromCell(row.getCell(getCellIndexByTestMode(R.integer.result_prezzo_gas_business,
                R.integer.result_prezzo_gas_consumer))));
        gasResult.setPrezzoConIva(getDoubleFromCell(row.getCell(getCellIndexByTestMode(R.integer.result_prezzo_gas_con_iva_business,
                R.integer.result_prezzo_gas_con_iva_consumer))));
        gasResult.setCodiceTariffa(row.getCell(getCellIndexByTestMode(R.integer.result_codice_tariffa_gas_business,
                R.integer.result_codice_tariffa_gas_consumer)).getStringCellValue());
        gasResult.setComune(row.getCell(getCellIndexByTestMode(R.integer.result_comune_gas_business,
                R.integer.comune_consumer)).getStringCellValue());
        gasResult.setGasPerc(Double.valueOf(decimalFormat.format(getDoubleFromCell(row.getCell(getCellIndexByTestMode(R.integer.result_perc_gas_business,
                R.integer.result_perc_gas_consumer))) * 100)));
        gasResult.setMancatoConsumo(getDoubleFromCell(row.getCell(getCellIndexByTestMode(R.integer.result_mancato_consumo_gas_business,
                R.integer.result_mancato_consumo_gas_consumer))));
        gasResult.setSforamento(getDoubleFromCell(row.getCell(getCellIndexByTestMode(R.integer.result_sforamento_gas_business,
                R.integer.result_sforamento_gas_consumer))));
        gasResult.setPdrNumber(pdrNumber);
        gasResult.setResultOfferId(offerResult.getResultOfferId());
        gasResultDAO.insert(gasResult);
    }

    private int getDayClass(Row row) {
        return isBusinessMode ? classePrelievoGasDAO.getClasseDiPrelievoByDesc(
                row.getCell(resources.getInteger(R.integer.classe_di_prelievo_business))
                        .getStringCellValue()).getIdClassePrelievo()
                : CLASSE_PRELIEVO_DEFAULT;
    }

    private int getRegimeFiscale(Row row) {
        int regimeFiscale = 1;
        if (isBusinessMode) {
            String regimeFiscaleDesc = row.getCell(resources.getInteger(R.integer.regime_fiscale_business))
                    .getStringCellValue();
            regimeFiscale = fiscalClassDAO.getFiscalClassIdByDesc(regimeFiscaleDesc);
        }
        return regimeFiscale;
    }

    private int getTipologiaDusoId(Row podRow) {
        int categoriaDuso = 1;
        if (!isBusinessMode) {
            String tensioneStr = podRow.getCell(resources.getInteger(R.integer.tipologia_duso_business))
                    .getStringCellValue();
            switch (tensioneStr) {
                case USI_DIVERSI:
                    categoriaDuso = 1;
                    break;
                case DOMESTICO:
                    categoriaDuso = 2;
                    break;
                case CONDOMINIO:
                    categoriaDuso = 3;
                    break;
            }
        }
        return categoriaDuso;
    }

    public OnParsingErrorListener getOnParsingErrorListener() {
        return onParsingErrorListener;
    }

    public void setOnParsingErrorListener(OnParsingErrorListener onParsingErrorListener) {
        this.onParsingErrorListener = onParsingErrorListener;
    }
}

