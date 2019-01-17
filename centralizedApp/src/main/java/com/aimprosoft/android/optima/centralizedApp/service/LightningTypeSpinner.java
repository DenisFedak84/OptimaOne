package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.LightingTypesDAOImpl;

import java.util.ArrayList;
import java.util.List;

public class LightningTypeSpinner extends AbstractService<Void, List> {

    public LightningTypeSpinner() {
    }

    public LightningTypeSpinner(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List doStuff(Void... voids) {
        try {
            List list = new LightingTypesDAOImpl().getAllRows();
            list.add(0, "");
            return list;
        } catch (Exception e) {
            Log.e(e.getClass().getName(), "", e);
        }
        return new ArrayList<>();
    }

}
