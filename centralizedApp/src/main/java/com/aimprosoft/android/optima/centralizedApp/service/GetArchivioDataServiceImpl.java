package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;

import java.util.List;

public class GetArchivioDataServiceImpl extends AbstractService<Void, List<Object[]>> {

    public GetArchivioDataServiceImpl(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List<Object[]> doStuff(Void... params) {
        return new OfferDAOImpl().getArchivioList();
    }
}
