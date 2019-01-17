package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;

import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.CentralizedCostUtil;

import java.util.Map;

public class TotalCostJarService extends AbstractService<Offer, Map<String, Object>> {

    public TotalCostJarService(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    public TotalCostJarService(OnTaskCompleted onTaskCompleted) {
        super(onTaskCompleted);
    }

    @Override
    protected Map<String, Object> doStuff(Offer... offers) {
       return new CentralizedCostUtil().getTotalPrice(offers[0]);
    }
}
