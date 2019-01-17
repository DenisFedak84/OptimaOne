package com.aimprosoft.android.optima.centralizedApp.util.autotest.parser.impl;

import android.text.TextUtils;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.configuratoreEngineDAO.LineTypeDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.configuratoreEngineDAO.ServicesDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.BundleDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.CarriersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.NetworksDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TLCOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TlcDetailsOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.VoceResultDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WlrOfferDetailsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WlrOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.OfferResult;
import com.aimprosoft.android.optima.centralizedApp.db.entity.TLCOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.TlcDetailsOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.VoceResult;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WlrOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WlrOffers;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.listener.OnParsingErrorListener;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.parser.AbstractTestParser;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.IdCampganaUtils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;

public class VoceTestParser extends AbstractTestParser {

    private TLCOffer tlcOffer;
    private WlrOffers wlrOffer;
    private OfferDAOImpl offerDAO = new OfferDAOImpl();
    private WlrOffersDAOImpl wlrOffersDAO = new WlrOffersDAOImpl();
    private TLCOfferDAOImpl tlcOfferDAO = new TLCOfferDAOImpl();
    private TlcDetailsOfferDAOImpl tlcDetailsOfferDAO = new TlcDetailsOfferDAOImpl();
    private WlrOfferDetailsDAOImpl wlrOfferDetailsDAO = new WlrOfferDetailsDAOImpl();
    private BundleDAOImpl bundleDAO = new BundleDAOImpl();
    private NetworksDAOImpl networksDAO = new NetworksDAOImpl();
    private CarriersDAOImpl carriersDAO = new CarriersDAOImpl();
    private VoceResultDAOImpl voceResultDAO = new VoceResultDAOImpl();
    private OnParsingErrorListener onParsingErrorListener;

    private final String NUMERAZIONE_AGGIUNTIVA = "Numerazione aggiuntiva";

    private final int LOCAL_BUNDLE = 1;
    private final int LOCAL_BUNDLE_VOIP = 4;
    private final int MOBILE_BUNDLE = 2;
    private final int MOBILE_BUNDLE_VOIP = 5;
    private final int DEFAULT_NUM_LINES = 1;
    private int campagnaId;

    public VoceTestParser() {
    }

    @Override
    public void parse(List<Row> rowList, Offer offer, OfferResult offerResult) {
        defineTestMode(offer);
        campagnaId = IdCampganaUtils.getOfferIdCampagnaByServiceType(IdCampganaUtils.ServiceType.VOCE,
                offer.getConfiguratorType(), offer.getIsOldTlcOfferUsed());
        String voceNumber = null;
        try {
            for (Row row : rowList) {
                voceNumber = row.getCell(getCellIndexByTestMode(
                        R.integer.id_voce_business, R.integer.id_voce_consumer))
                        .getStringCellValue();
                initializeOfferResult(offerResult, row);
                createVoceOfferDetails(row, voceNumber, offer, offerResult);
            }
        } catch (Exception e) {
            if (onParsingErrorListener != null) {
                onParsingErrorListener.onParsingError(MyApplication.getContext()
                        .getString(R.string.voce_parsing_error_message, voceNumber, e.getMessage()));
            }
        }
    }

    private void createVoceOfferDetails(Row row, String voceNumber, Offer offer, OfferResult offerResult) {
        if (voceNumber.isEmpty()) {
            listener.onParsingComplete(voceNumber);
            return;
        }

        String rete = row.getCell(getCellIndexByTestMode(
                R.integer.offerta_configurata_business, R.integer.rete_consumer))
                .getStringCellValue();

        createVoceResult(row, offerResult, voceNumber);
        createVoceDetails(rete, row, offer, voceNumber);
    }

    private void createVoceDetails(String voceType, Row row, Offer offer, String voceNumber) {
        switch (voceType.toUpperCase()) {
            case Constants.WLR:
                createWlrOfferDetails(row, offer, voceNumber);
                break;
            case Constants.CPS:
                createCpsOfferDetails(row, offer, voceNumber);
                break;
            case Constants.VOIP:
                createVoipOfferDetails(row, offer, voceNumber);
                break;
        }
    }

    private void createVoipOfferDetails(Row row, Offer offer, String voceNumber) {
        checkWlrObject(offer, row, true);
        WlrOfferDetails wlrOfferDetail = new WlrOfferDetails();

        wlrOfferDetail.setNumLines(1);
        wlrOfferDetail.setRete(Constants.VOIP);

        wlrOfferDetail.setServicesId("");
        wlrOfferDetail.setWlrOfferId(wlrOffer.getWlrOfferId());
        wlrOfferDetailsDAO.insert(wlrOfferDetail);
        listener.onParsingComplete(voceNumber);
    }

    private void createVoceResult(Row row, OfferResult offerResult, String voceNumber) {
        VoceResult voceResult = new VoceResult();
        voceResult.setPrezzo(getDoubleFromCell(row.getCell(getCellIndexByTestMode(R.integer.result_prezzo_voce_business,
                R.integer.result_prezzo_voce_consumer))));
        voceResult.setPrezzoConIva(getDoubleFromCell(row.getCell(getCellIndexByTestMode(R.integer.result_prezzo_con_iva_voce_business,
                R.integer.result_prezzo_con_iva_voce_consumer))));
        Cell minutiVersoFissoCell = row.getCell(getCellIndexByTestMode(R.integer.result_minuti_verso_fissi_voce_business,
                R.integer.result_minuti_verso_fissi_voce_consumer));
        voceResult.setMinutiVersoFisso(minutiVersoFissoCell.getCellType() == Cell.CELL_TYPE_NUMERIC ?
                String.valueOf(minutiVersoFissoCell.getNumericCellValue()) : minutiVersoFissoCell.getStringCellValue());
        Cell minutiVersoMobileCell = row.getCell(getCellIndexByTestMode(R.integer.result_minuti_verso_mobile_voce_business,
                R.integer.result_minuti_verso_mobile_voce_consumer));
        voceResult.setMinutiVersoMobile(minutiVersoMobileCell.getCellType() == Cell.CELL_TYPE_NUMERIC ?
                String.valueOf(minutiVersoMobileCell.getNumericCellValue()) :
                minutiVersoMobileCell.getStringCellValue());
        voceResult.setVocePerc(Double.valueOf(decimalFormat.format(getDoubleFromCell(row.getCell(getCellIndexByTestMode(R.integer.result_perc_voce_business,
                R.integer.result_perc_voce_consumer))) * 100)));
        voceResult.setVoceNumber(voceNumber);
        voceResult.setResultOfferId(offerResult.getResultOfferId());
        voceResultDAO.insert(voceResult);
    }

    private void createCpsOfferDetails(Row row, Offer offer, String voceNumber) {
        checkCpsObject(offer);
        TlcDetailsOffer tlcDetailsOffer = new TlcDetailsOffer();
        tlcDetailsOffer.setTlcOfferId(tlcOffer.getTlcOfferId());
        tlcDetailsOffer.setLocalBundleId(getBundleId(row,
                getCellIndexByTestMode(R.integer.pachetto_vs_fissi_business, R.integer.pachetto_vs_fissi_consumer),
                LOCAL_BUNDLE));
        tlcDetailsOffer.setMobileBundleId(getBundleId(row,
                getCellIndexByTestMode(R.integer.pachetto_vs_mobile_business, R.integer.pachetto_vs_mobile_consumer),
                MOBILE_BUNDLE));
        tlcDetailsOfferDAO.insert(tlcDetailsOffer);
        listener.onParsingComplete(voceNumber);
    }

    private int getBundleId(Row row, int cellIndex, int bundleType) {
        Cell bundleCell = row.getCell(cellIndex);
        String bundleDesc = bundleCell.getCellType() == Cell.CELL_TYPE_STRING ?
                bundleCell.getStringCellValue() : String.valueOf(getIntFromCell(bundleCell));
        int bundleId;
        if (bundleType == LOCAL_BUNDLE || bundleType == LOCAL_BUNDLE_VOIP) {
            bundleId = bundleDAO.getBundleBySpecialParams(bundleDesc, bundleType).getBundleId();
        } else {
            bundleId = bundleDAO.getBundleBySpecialParams(bundleDesc, bundleType,
                    getSystem()).getBundleId();
        }
        return bundleId;
    }

    private void checkCpsObject(Offer offer) {
        if (tlcOffer == null) {
            tlcOffer = new TLCOffer();
            tlcOfferDAO.insert(tlcOffer);
            offer.setTlcOfferId(tlcOffer.getTlcOfferId());
            offerDAO.update(offer);
        }
    }

    private void createWlrOfferDetails(Row row, Offer offer, String voceNumber) {
        checkWlrObject(offer, row, false);
        WlrOfferDetails wlrOfferDetail = new WlrOfferDetails();

        int carrierId = carriersDAO.getCarrierByDescAndConfiguratorType(row
                        .getCell(getCellIndexByTestMode(R.integer.operatore_business, R.integer.operatore_consumer))
                        .getStringCellValue(),
                getSystem()).getCarrierId();
        wlrOfferDetail.setCarrierId(carrierId);
        wlrOfferDetail.setNetworkId(networksDAO.getNetworksByDesc(row
                        .getCell(getCellIndexByTestMode(R.integer.rete_business, R.integer.rete_consumer))
                        .getStringCellValue(),
                carrierId).getNetworkId());

        int numLines = getIntFromCell(row
                .getCell(getCellIndexByTestMode(R.integer.num_linee_business, R.integer.num_linee_consumer)));
        wlrOfferDetail.setNumLines(numLines != 0 ? numLines : DEFAULT_NUM_LINES);

        int lineTypeId = LineTypeDAOImpl.getLineTypeByDesc(campagnaId, (row
                .getCell(getCellIndexByTestMode(R.integer.tipologia_linea_business,
                        R.integer.tipologia_linea_consumer))
                .getStringCellValue())).getLineId();
        wlrOfferDetail.setLineId(lineTypeId);

        wlrOfferDetail.setServicesId(getServicesIdsString(row, lineTypeId, wlrOfferDetail));


        wlrOfferDetail.setWlrOfferId(wlrOffer.getWlrOfferId());
        wlrOfferDetailsDAO.insert(wlrOfferDetail);
        listener.onParsingComplete(voceNumber);
    }

    private void createWlrOfferDetailsTest(Row row, Offer offer, String voceNumber) {
        checkWlrObject(offer, row, false);
        WlrOfferDetails wlrOfferDetail = new WlrOfferDetails();

        String reteDesc = row.getCell(getCellIndexByTestMode(R.integer.rete_business, R.integer.rete_consumer))
                .getStringCellValue();

        if (reteDesc.equalsIgnoreCase(Constants.VOIP)) {
            wlrOfferDetail.setRete(Constants.VOIP);
            wlrOfferDetail.setNumLines(DEFAULT_NUM_LINES);
        } else {
            wlrOfferDetail.setRete(Constants.WLR);
            int carrierId = carriersDAO.getCarrierByDescAndConfiguratorType(row
                            .getCell(getCellIndexByTestMode(R.integer.operatore_business, R.integer.operatore_consumer))
                            .getStringCellValue(),
                    getSystem()).getCarrierId();
            wlrOfferDetail.setCarrierId(carrierId);
            wlrOfferDetail.setNetworkId(networksDAO.getNetworksByDesc(reteDesc,
                    carrierId).getNetworkId());

            int numLines = getIntFromCell(row
                    .getCell(getCellIndexByTestMode(R.integer.num_linee_business, R.integer.num_linee_consumer)));
            wlrOfferDetail.setNumLines(numLines != 0 ? numLines : DEFAULT_NUM_LINES);

            int lineTypeId = LineTypeDAOImpl.getLineTypeByDesc(campagnaId, (row
                    .getCell(getCellIndexByTestMode(R.integer.tipologia_linea_business,
                            R.integer.tipologia_linea_consumer))
                    .getStringCellValue())).getLineId();
            wlrOfferDetail.setLineId(lineTypeId);

            wlrOfferDetail.setServicesId(getServicesIdsString(row, lineTypeId, wlrOfferDetail));
        }
        wlrOfferDetail.setWlrOfferId(wlrOffer.getWlrOfferId());
        wlrOfferDetailsDAO.insert(wlrOfferDetail);
        listener.onParsingComplete(voceNumber);
    }

    private String getServicesIdsString(Row row, int lineTypeId, WlrOfferDetails wlrOfferDetails) {
        List<Integer> idsList = new ArrayList<>();
        checkForServicePresent(row, idsList,
                getCellIndexByTestMode(R.integer.servizi_aggiuntivi_1_business,
                        R.integer.servizi_aggiuntivi_1_consumer), lineTypeId, wlrOfferDetails);
        checkForServicePresent(row, idsList,
                getCellIndexByTestMode(R.integer.servizi_aggiuntivi_2_business,
                        R.integer.servizi_aggiuntivi_2_consumer), lineTypeId, wlrOfferDetails);
        checkForServicePresent(row, idsList,
                getCellIndexByTestMode(R.integer.servizi_aggiuntivi_3_business,
                        R.integer.servizi_aggiuntivi_3_consumer), lineTypeId, wlrOfferDetails);
        checkForServicePresent(row, idsList,
                getCellIndexByTestMode(R.integer.servizi_aggiuntivi_4_business,
                        R.integer.servizi_aggiuntivi_4_consumer), lineTypeId, wlrOfferDetails);
        if (isBusinessMode) {
            checkForServicePresent(row, idsList, resources.getInteger(R.integer.numero_canali_business),
                    lineTypeId, wlrOfferDetails);
        }

        return TextUtils.join(",", idsList);
    }

    private void checkForServicePresent(Row row, List<Integer> idsList, int cellId, int lineTypeId, WlrOfferDetails wlrOfferDetails) {
        Cell currentCell = row.getCell(cellId);
        String serviceDesc = currentCell.getCellType() == Cell.CELL_TYPE_STRING ?
                currentCell.getStringCellValue() : String.valueOf(getIntFromCell(currentCell));
        if (!serviceDesc.isEmpty() && !serviceDesc.equals("0.0") && !serviceDesc.equals("-1")) {
            int id = ServicesDAOImpl.getServicesByDescAndAdditionalParams(campagnaId, String.valueOf(lineTypeId), serviceDesc)
                    .getServiceId();
            idsList.add(id);
            checkForNumerazioneAggiuntivi(row, serviceDesc, wlrOfferDetails);
        }
    }

    private void checkForNumerazioneAggiuntivi(Row row, String serviceDesc, WlrOfferDetails wlrOfferDetails) {
        if (serviceDesc.equals(NUMERAZIONE_AGGIUNTIVA)) {
            int numerazioneAggiuntivi = isBusinessMode ? getIntFromCell(row.
                    getCell(resources.getInteger(R.integer.quantita_numerazioni_aggiuntive_business))) : 1;
            wlrOfferDetails.setServiceAddictionNumber(numerazioneAggiuntivi != 0 ? numerazioneAggiuntivi : null);
        }
    }

    private void checkWlrObject(Offer offer, Row row, boolean isVoip) {
        if (wlrOffer == null) {
            wlrOffer = new WlrOffers();
            wlrOffer.setLocalBundleId(getBundleId(row,
                    getCellIndexByTestMode(R.integer.pachetto_vs_fissi_business, R.integer.pachetto_vs_fissi_consumer),
                    isVoip ? LOCAL_BUNDLE_VOIP : LOCAL_BUNDLE));
            wlrOffer.setMobileBundleId(getBundleId(row,
                    getCellIndexByTestMode(R.integer.pachetto_vs_mobile_business, R.integer.pachetto_vs_mobile_consumer),
                    isVoip ? MOBILE_BUNDLE_VOIP : MOBILE_BUNDLE));
            wlrOffer.setBundleCost(0.00);
            wlrOffer.setBundleCostIVA(0.00);
            wlrOffer.setOfferCost(0.00);
            wlrOffer.setOfferCostIVA(0.00);
            wlrOffersDAO.insert(wlrOffer);
            offer.setWlrOfferId(wlrOffer.getWlrOfferId());
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
