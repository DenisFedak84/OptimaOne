package com.aimprosoft.android.optima.centralizedApp.service;


import android.app.Activity;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.BundleDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Bundle;

import java.util.List;

public class AdslSpinner extends AbstractService<Void, List<Bundle>> {

    public AdslSpinner() {
    }

    public AdslSpinner(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List<Bundle> doStuff(Void... voids) {
        List bundleList = new BundleDAOImpl().getBundleByType(3);
        bundleList.add(0, "");
        return bundleList;
    }
}


