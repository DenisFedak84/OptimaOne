package com.aimprosoft.android.optima.centralizedApp.service;


import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.BundleDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Bundle;

import java.util.ArrayList;
import java.util.List;

public class VersoFissoSpinner extends AbstractService<String, List<Bundle>> {

    public VersoFissoSpinner() {
    }

    public VersoFissoSpinner(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List<Bundle> doStuff(String... strings) {
        BundleDAOImpl bundleDAO = new BundleDAOImpl();
        try {
            return bundleDAO.getBundleByTypeAndConfiguratorType(1, strings[0]);
        } catch (Exception e) {
            Log.e(TAG, "");
        }
        return new ArrayList<>();
    }
}


