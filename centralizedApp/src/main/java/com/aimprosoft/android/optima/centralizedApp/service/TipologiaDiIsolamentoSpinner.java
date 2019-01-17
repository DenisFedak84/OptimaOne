package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ThermalInsulationDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.ThermalInsulation;

import java.util.ArrayList;
import java.util.List;

public class TipologiaDiIsolamentoSpinner extends AbstractService<String, List<ThermalInsulation>> {

    public TipologiaDiIsolamentoSpinner() {
    }

    public TipologiaDiIsolamentoSpinner(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List<ThermalInsulation> doStuff(String... strings){
        try {
//            List<Restoration> list = new RestorationDAOImpl().getAllRows();
//            list.add(0, "");
            return new ThermalInsulationDAOImpl().getAllRows();
        } catch (Exception e) {
            Log.e(e.getClass().getName(), "", e);
        }
        return new ArrayList<ThermalInsulation>();

    }
}
