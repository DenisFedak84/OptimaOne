package com.aimprosoft.android.optima.centralizedApp.util.autotest.parser.impl;

import android.text.TextUtils;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.configuratoreEngineDAO.ServicesDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ADSLResultDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.BundleDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.InternetDetailOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.InternetOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.ADSLResult;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Bundle;
import com.aimprosoft.android.optima.centralizedApp.db.entity.InternetDetailOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.InternetOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.OfferResult;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Services;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.listener.OnParsingErrorListener;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.parser.AbstractTestParser;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.AbstractJarUtil;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.IdCampganaUtils;

import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;

public class ADSLTestParser extends AbstractTestParser {

    private InternetOffer internetOffer;
    private InternetDetailOffers internetDetailOffers;
    private InternetOfferDAOImpl internetOfferDAO = new InternetOfferDAOImpl();
    private InternetDetailOffersDAOImpl internetDetailOffersDAO = new InternetDetailOffersDAOImpl();
    private ADSLResultDAOImpl adslResultDAO = new ADSLResultDAOImpl();

    private OfferDAOImpl offerDAO = new OfferDAOImpl();
    private BundleDAOImpl bundleDAO = new BundleDAOImpl();
    private OnParsingErrorListener onParsingErrorListener;

    private final int ADSL_TYPE = 3;
    private final int ROUTER_PRESENT = 1;
    private final int ROUTER_ABSENT = 0;
    private int campagnaId;

    public ADSLTestParser() {
    }

    @Override
    public void parse(List<Row> rowList, Offer offer, OfferResult offerResult) {
        defineTestMode(offer);
        campagnaId = IdCampganaUtils.getOfferIdCampagnaByServiceType(IdCampganaUtils.ServiceType.ADSL,
                offer.getConfiguratorType(), offer.getIsOldTlcOfferUsed());
        String adslNumber = null;
        try {
            for (Row row : rowList) {
                adslNumber = row.getCell(getCellIndexByTestMode(
                        R.integer.id_adsl_business, R.integer.id_adsl_consumer))
                        .getStringCellValue();
                initializeOfferResult(offerResult, row);
                createAdslOfferDetails(row, adslNumber, offer, offerResult);
            }
        } catch (Exception e) {
            if (onParsingErrorListener != null) {
                onParsingErrorListener.onParsingError(MyApplication.getContext()
                        .getString(R.string.adsl_parsing_error_message, adslNumber, e.getMessage()));
            }
        }
    }

    private void createAdslOfferDetails(Row row, String adslNumber, Offer offer, OfferResult offerResult) {
        if (adslNumber.isEmpty()) {
            listener.onParsingComplete(adslNumber);
            return;
        }
        createAdslResult(row, offerResult, adslNumber);
        checkAdslObject(offer);

        InternetDetailOffers internetDetailOffers = new InternetDetailOffers();
        internetDetailOffers.setInternetOfferId(internetOffer.getInternetOfferId());

        Bundle adslBundle = bundleDAO.getBundleBySpecialParams(
                row.getCell(getCellIndexByTestMode(R.integer.tipologia_adsl_business,
                        R.integer.tipologia_adsl_consumer)).getStringCellValue(),
                ADSL_TYPE);
        internetDetailOffers.setBundleId(adslBundle.getBundleId());

        internetDetailOffers.setRouter(ROUTER_ABSENT);
        internetDetailOffers.setServiceId(buildServiceCode(row, adslBundle));
        internetDetailOffersDAO.insert(internetDetailOffers);
        listener.onParsingComplete(adslNumber);
    }

    private void createAdslResult(Row row, OfferResult offerResult, String adslNumber) {
        ADSLResult adslResult = new ADSLResult();
        adslResult.setPrezzo(getDoubleFromCell(row.getCell(getCellIndexByTestMode(R.integer.result_prezzo_adsl_business,
                R.integer.result_prezzo_adsl_consumer))));
        adslResult.setPrezzoConIva(getDoubleFromCell(row.getCell(getCellIndexByTestMode(R.integer.result_prezzo_adsl_con_iva_business,
                R.integer.result_prezzo_adsl_con_iva_consumer))));
        adslResult.setAdslPerc(Double.valueOf(decimalFormat.format(getDoubleFromCell(row.getCell(getCellIndexByTestMode(R.integer.result_perc_adsl_business,
                R.integer.result_perc_adsl_consumer))) * 100)));
        adslResult.setTipologiaAdsl(row.getCell(getCellIndexByTestMode(R.integer.result_tipologia_adsl_business,
                R.integer.result_tipologia_adsl_consumer)).getStringCellValue());
        adslResult.setAdslNumber(adslNumber);
        adslResult.setResultOfferId(offerResult.getResultOfferId());
        adslResultDAO.insert(adslResult);
    }

    private String buildServiceCode(Row row, Bundle adslBundle) {
        List<Integer> idsList = new ArrayList<>();

        String lineaSoloDati = row.getCell(getCellIndexByTestMode(R.integer.linea_solo_dati_adsl_business,
                R.integer.linea_solo_dati_adsl_consumer)).getStringCellValue();
        if (!lineaSoloDati.isEmpty()) {
            List<Services> servicesList = ServicesDAOImpl.getServicesByFollowingParams(campagnaId, ServicesDAOImpl.TipoOpzioni.LINEA_SOLO_DATI_LNA);
            if (!servicesList.isEmpty()) {
                idsList.add(AbstractJarUtil.defineLineaSoloDati(servicesList, adslBundle));
            }
        }

        String ipAggiuntivi = String.valueOf(getIntFromCell(row.getCell(getCellIndexByTestMode(R.integer.ip_aggiuntivi_adsl_business,
                R.integer.ip_aggiuntivi_adsl_consumer))));
        if (!ipAggiuntivi.isEmpty()) {
            idsList.add(ServicesDAOImpl.getServicesByDesc(campagnaId, ipAggiuntivi).getServiceId());
        }
//        idsList.add(ServicesDAOImpl.getServicesByDesc(campagnaId, ServicesDAOImpl.TipoOpzioni.ROUTER).getServiceId());
        return TextUtils.join(",", idsList);
    }

    private String buildServiceCodeTest(Row row, Bundle adslBundle) {
        List<Integer> idsList = new ArrayList<>();

        String lineaSoloDati = row.getCell(getCellIndexByTestMode(R.integer.linea_solo_dati_adsl_business,
                R.integer.linea_solo_dati_adsl_consumer)).getStringCellValue();
        if (!lineaSoloDati.isEmpty()) {
            List<Services> servicesList = ServicesDAOImpl.getServicesByFollowingParams(campagnaId, ServicesDAOImpl.TipoOpzioni.LINEA_SOLO_DATI_LNA);
            if (!servicesList.isEmpty()) {
                idsList.add(AbstractJarUtil.defineLineaSoloDati(servicesList, adslBundle));
            }
        }

        String ipAggiuntivi = String.valueOf(getIntFromCell(row.getCell(getCellIndexByTestMode(R.integer.ip_aggiuntivi_adsl_business,
                R.integer.ip_aggiuntivi_adsl_consumer))));
        if (!ipAggiuntivi.isEmpty()) {
            idsList.add(ServicesDAOImpl.getServicesByDesc(campagnaId, ipAggiuntivi).getServiceId());
        }
        idsList.add(ServicesDAOImpl.getServicesByDesc(campagnaId, ServicesDAOImpl.TipoOpzioni.ROUTER).getServiceId());
        return TextUtils.join(",", idsList);
    }

    private void checkAdslObject(Offer offer) {
        if (internetOffer == null) {
            internetOffer = new InternetOffer();
            internetOfferDAO.insert(internetOffer);
            offer.setInternetOfferId(internetOffer.getInternetOfferId());
            offerDAO.update(offer);
        }
    }

    public OnParsingErrorListener getOnParsingErrorListener() {
        return onParsingErrorListener;
    }

    public void setOnParsingErrorListener(OnParsingErrorListener onParsingErrorListener) {
        this.onParsingErrorListener = onParsingErrorListener;
    }
}
