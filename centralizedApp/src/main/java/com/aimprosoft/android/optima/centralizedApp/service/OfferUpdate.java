package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;

import java.util.List;

public class OfferUpdate extends AbstractService<List, Boolean> {

    public OfferUpdate() {
    }

    public OfferUpdate(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected Boolean doStuff(List... lists) {
        List offers = lists[0];
        return new OfferDAOImpl().update((Offer) offers.get(0));
    }
}
