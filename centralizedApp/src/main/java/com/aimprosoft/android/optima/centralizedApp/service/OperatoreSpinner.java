package com.aimprosoft.android.optima.centralizedApp.service;


import android.app.Activity;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.CarriersDAOImpl;

import java.util.List;

public class OperatoreSpinner extends AbstractService<String, List> {

    public OperatoreSpinner() {
    }

    public OperatoreSpinner(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List doStuff(String... strings) {
        List carriersList = new CarriersDAOImpl().getCarrierByConfiguratorType(strings[0]);
        carriersList.add(0, "");
        return carriersList;
    }
}


