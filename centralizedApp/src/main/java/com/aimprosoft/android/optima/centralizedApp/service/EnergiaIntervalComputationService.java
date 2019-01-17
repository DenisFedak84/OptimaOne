package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;

import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.util.EnergiaConsumptionAlgoritm;

import java.util.Map;

public class EnergiaIntervalComputationService extends AbstractService<Object, Map<String, Object>> {

    public EnergiaIntervalComputationService(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected Map<String, Object> doStuff(Object... param) {
        EnergyOfferDetails energyOfferdetail = (EnergyOfferDetails) param[0];
        int tipoContatore = (int) param[1];
//        List tableContentList = (List) param[0];
//        int tipoContatore = (int) param[1];
        return new EnergiaConsumptionAlgoritm(energyOfferdetail, tipoContatore).getConsumptionMap();
    }
}
