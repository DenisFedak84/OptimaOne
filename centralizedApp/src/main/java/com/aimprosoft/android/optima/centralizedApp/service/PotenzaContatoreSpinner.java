package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyMeterDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyMeter;

import java.util.ArrayList;
import java.util.List;

public class PotenzaContatoreSpinner extends AbstractService<Object, List<EnergyMeter>> {

    public PotenzaContatoreSpinner() {
    }

    public PotenzaContatoreSpinner(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List<EnergyMeter> doStuff(Object... objects) {
        EnergyMeterDAOImpl energyMeterDAO = new EnergyMeterDAOImpl();
        String configuratorType = (String) objects[0];
        Integer contractType = (Integer) objects[1];
        try {
            List<EnergyMeter> list = energyMeterDAO.getEnergyMeterListForBT(configuratorType, contractType);
            EnergyMeter energyMeter = new EnergyMeter();
            energyMeter.setEnergyMeterValue("");
            list.add(0, energyMeter);
            return list;
        } catch (Exception e) {
            Log.e(TAG, "error while executing PotenzaContatoreSpinner", e);
        }
        return new ArrayList<>();
    }
}
