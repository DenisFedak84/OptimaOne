package com.aimprosoft.android.optima.centralizedApp.util.SDCUtils;

import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasDetailOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasSDCDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TownDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasDetailOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasSDC;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Town;
import com.aimprosoft.android.optima.centralizedApp.service.GetOffersForJsonService;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.AbstractJarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SDCGasReportImpl implements SDCReport<GasDetailOffers> {

    public SDCGasReportImpl() {
    }

    @Override
    public List<Map<String, Object>> getSingleOfferSDCDataList(Offer offer, GasDetailOffers gasDetailsOffer) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<GasSDC> gasSDCList = new GasSDCDAOImpl().getGasSDCByDetailsId(gasDetailsOffer.getGasDetailsOfferId());
        Town town = new TownDAOImpl().getTownById(gasDetailsOffer.getTownId());
        if (gasSDCList != null && !gasSDCList.isEmpty()) {
            Map<String, Object> objectMap;
            for (GasSDC gasSDC : gasSDCList) {
                objectMap = new HashMap<>();
                objectMap.put(GasSDC.CONSUMO_ANNUO, gasSDC.getConsumoAnnuo());
                objectMap.put(GasSDC.STIMA_SPESA_RIF, gasSDC.getStimaSpesaRif());
                objectMap.put(GasSDC.COSTO_TOT_SEN_IMPG, gasSDC.getCostoTotSenImpG());
                objectMap.put(GasSDC.DATA_INIZIO, AbstractJarUtil.changeSDCDateFormat(gasSDC.getDataInizio(),
                        AbstractJarUtil.formatterFullDateWithSlashAndTimeDetails,
                        AbstractJarUtil.formatterFullDateWithSlash));
                objectMap.put(GasSDC.DATA_FINE, AbstractJarUtil.changeSDCDateFormat(gasSDC.getDataFine(),
                        AbstractJarUtil.formatterFullDateWithSlashAndTimeDetails,
                        AbstractJarUtil.formatterFullDateWithSlash));
                objectMap.put(GasDetailOffers.OFFER_COST, gasDetailsOffer.getOfferCost());
                objectMap.put(GasDetailOffers.OFFER_COSTIVA, gasDetailsOffer.getOfferCostIVA());
                objectMap.put(MONTHLY_CONSUMPTION, gasDetailsOffer.getYearlyConsumption());
                objectMap.put(TOWN, town != null ? town.getTariffZone() : "");
                resultList.add(objectMap);
            }
        }
        return resultList;
    }

    @Override
    public Map<String, Object> getFullSDCDataMap(Offer offer, List<String> emails) {

        Map<String, Object> resultMap = null;
        if (offer != null && offer.getGasOfferId() != null) {
            List<GasDetailOffers> gasOfferDetailsList = new GasDetailOffersDAOImpl().getGasOfferDetailsByGasOfferId(offer.getGasOfferId());
            if (!gasOfferDetailsList.isEmpty()) {
                resultMap = new HashMap<>();
                List<List<Map<String, Object>>> mainScdDataList = new ArrayList<>();
                List<Map<String, Object>> sdcDataList;
                for (GasDetailOffers gasOfferDetails : gasOfferDetailsList) {
                    sdcDataList = getSingleOfferSDCDataList(offer, gasOfferDetails);
                    if (sdcDataList != null && !sdcDataList.isEmpty()) {
                        mainScdDataList.add(sdcDataList);
                    }
                }
                resultMap.put(GetOffersForJsonService.SERVICE_ID, Constants.GAS_SERVICE_ID);
                resultMap.put(SDC, mainScdDataList);
                resultMap.put(EMAILS, emails);
            }
        }
        return resultMap;
    }
}
