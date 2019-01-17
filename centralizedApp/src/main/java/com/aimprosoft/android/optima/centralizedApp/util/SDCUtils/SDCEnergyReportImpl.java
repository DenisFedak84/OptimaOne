package com.aimprosoft.android.optima.centralizedApp.util.SDCUtils;

import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDetailsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergySDCDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergySDC;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.service.GetOffersForJsonService;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.AbstractJarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SDCEnergyReportImpl implements SDCReport<EnergyOfferDetails> {

    public SDCEnergyReportImpl() {
    }

    @Override
    public List<Map<String, Object>> getSingleOfferSDCDataList(Offer offer, EnergyOfferDetails energyOfferDetails) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<EnergySDC> energySDCList = new EnergySDCDAOImpl().getEnergySDCByDetailsId(energyOfferDetails.getEnergyDetailOfferId());
        if (energySDCList != null && !energySDCList.isEmpty()) {
            Map<String, Object> objectMap;
            for (EnergySDC energySDC : energySDCList) {
                objectMap = new HashMap<>();
                objectMap.put(EnergySDC.CONSUMO_ANNUO, energySDC.getConsumoAnnuo());
                objectMap.put(EnergySDC.PREZZO_MONORARIO, energySDC.getPrezzoMonorario());
                objectMap.put(EnergySDC.PREZZO_BIORARIO_PUNTA, energySDC.getPrezzoBiorarioPunta());
                objectMap.put(EnergySDC.PREZZO_BIORARIO_NO_PUNTA, energySDC.getPrezzoBiorarioNoPunta());
                objectMap.put(EnergySDC.COSTO_TOT_SEN_IMPE, energySDC.getCostoTotSenImpE());
                objectMap.put(EnergySDC.DATA_INIZIO, AbstractJarUtil.changeSDCDateFormat(energySDC.getDataInizio(),
                        AbstractJarUtil.formatterFullDateWithSlashAndTimeDetails,
                        AbstractJarUtil.formatterFullDateWithSlash));
                objectMap.put(EnergySDC.DATA_FINE, AbstractJarUtil.changeSDCDateFormat(energySDC.getDataFine(),
                        AbstractJarUtil.formatterFullDateWithSlashAndTimeDetails,
                        AbstractJarUtil.formatterFullDateWithSlash));
                objectMap.put(EnergyOfferDetails.OFFER_COST, energyOfferDetails.getOfferCost());
                objectMap.put(EnergyOfferDetails.OFFER_COST_IVA, energyOfferDetails.getOfferCostIVA());
                objectMap.put(MONTHLY_CONSUMPTION, energyOfferDetails.getYearlyConsumption());
                objectMap.put(POTENZA, (AbstractJarUtil.checkForSpecialPotenza(offer, energyOfferDetails))
                        ? Constants.SPECIAL_POTENZA_VALUE : String.valueOf(AbstractJarUtil.getPotenzaValue(offer, energyOfferDetails)));
                objectMap.put(EnergyOfferDetails.CLIENT_TYPE,
                        offer.getClientTypeId() == Constants.RESIDENZIALE ? Constants.RESIDENZIALE_CODE : Constants.NON_RESIDENZIALE_CODE);
                resultList.add(objectMap);
            }
        }
        return resultList;
    }

    @Override
    public Map<String, Object> getFullSDCDataMap(Offer offer, List<String> emails) {
        Map<String, Object> resultMap = null;
        if (offer != null && offer.getEnergyOfferId() != null) {
            List<EnergyOfferDetails> energyOfferDetailsList = new EnergyOfferDetailsDAOImpl().getEnergyOfferDetailsByEnergyOfferId(offer.getEnergyOfferId());
            if (!energyOfferDetailsList.isEmpty()) {
                resultMap = new HashMap<>();
                List<List<Map<String, Object>>> mainScdDataList = new ArrayList<>();
                List<Map<String, Object>> sdcDataList;
                for (EnergyOfferDetails energyOfferDetails : energyOfferDetailsList) {
                    sdcDataList = getSingleOfferSDCDataList(offer, energyOfferDetails);
                    if (sdcDataList != null && !sdcDataList.isEmpty()) {
                        mainScdDataList.add(sdcDataList);
                    }
                }
                resultMap.put(GetOffersForJsonService.SERVICE_ID, Constants.ENERGY_SERVICE_ID);
                resultMap.put(SDC, mainScdDataList);
                resultMap.put(EMAILS, emails);
            }
        }
        return resultMap;
    }
}
