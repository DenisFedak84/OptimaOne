package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;

import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.CentralizedCostUtil;
import com.optima.pricing.domain.Pricing;

public class GetJarInputParamsAsJsonService extends AbstractService<Offer, String> {

    public GetJarInputParamsAsJsonService(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected String doStuff(Offer... offers) {
        Pricing pricing = new CentralizedCostUtil().buildPricing(offers[0]);
        return new BuildJarInputParamsJsonUtil().getJson(pricing, offers[0]);
    }
}
