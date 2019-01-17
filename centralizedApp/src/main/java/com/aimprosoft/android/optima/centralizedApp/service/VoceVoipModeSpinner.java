package com.aimprosoft.android.optima.centralizedApp.service;


import com.aimprosoft.android.optima.centralizedApp.activity.optilife.BaseActivity;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.BundleDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Bundle;

import java.util.ArrayList;
import java.util.List;

public class VoceVoipModeSpinner extends AbstractService<String, List<Bundle>> {
    private String configuratorType;
    private int bundleType;

    public VoceVoipModeSpinner(BaseActivity activity, String configuratorType, int bundleType,  OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
        this.configuratorType = configuratorType;
        this.bundleType = bundleType;
    }

    @Override
    protected List<Bundle> doStuff(String... strings) {
        BundleDAOImpl bundleDAO = new BundleDAOImpl();
        return bundleDAO.getBundleByTypeAndConfiguratorType(bundleType, configuratorType);
    }
}


