package com.aimprosoft.android.optima.centralizedApp.service;


import com.aimprosoft.android.optima.centralizedApp.activity.optilife.BaseActivity;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.BundleDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Bundle;

import java.util.List;

public class VersoMobileSpinner extends AbstractService<String, List<Bundle>> {
    BaseActivity baseActivity;

    public VersoMobileSpinner(BaseActivity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
        baseActivity = activity;
    }

    @Override
    protected List<Bundle> doStuff(String... strings) {
        return new BundleDAOImpl().getBundleByTypeAndConfiguratorType(2, strings[0]);
    }
}


