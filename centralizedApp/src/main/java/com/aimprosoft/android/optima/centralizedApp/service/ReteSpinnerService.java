package com.aimprosoft.android.optima.centralizedApp.service;


import android.app.Activity;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.NetworksDAOImpl;

import java.util.List;

public class ReteSpinnerService extends AbstractService<Integer, List> {

    public ReteSpinnerService() {
    }

    public ReteSpinnerService(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List doStuff(Integer... integers) {
        List networksList = new NetworksDAOImpl().getNetworksByOperatorId(integers[0]);
        networksList.add(0, "");
        return networksList;
    }
}


