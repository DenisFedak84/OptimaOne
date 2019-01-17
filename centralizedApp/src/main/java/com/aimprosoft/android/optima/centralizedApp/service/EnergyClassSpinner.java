package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyClassDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyClass;

import java.util.ArrayList;
import java.util.List;

public class EnergyClassSpinner extends AbstractService<Void, List> {

    public EnergyClassSpinner() {
    }

    public EnergyClassSpinner(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List doStuff(Void... voids) {

        EnergyClassDAOImpl energyClassDAO = new EnergyClassDAOImpl();
        try {
            List list = energyClassDAO.getAllRows();
            list.add(0, "");
            return list;
        } catch (Exception e) {
            Log.e(e.getClass().getName(), "", e);
        }
        return new ArrayList<EnergyClass>();
    }
}
