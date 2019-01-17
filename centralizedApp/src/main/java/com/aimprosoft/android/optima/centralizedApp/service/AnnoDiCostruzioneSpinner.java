package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasYearOfReferenceDAOImpl;

import java.util.ArrayList;
import java.util.List;

public class AnnoDiCostruzioneSpinner extends AbstractService<Void, List> {

    public AnnoDiCostruzioneSpinner() {
    }

    public AnnoDiCostruzioneSpinner(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List doStuff(Void... voids) {
        try {
            List list = new GasYearOfReferenceDAOImpl().getAllRows();
            list.add(0, "");
            return list;
        } catch (Exception e) {
            Log.e(e.getClass().getName(), "", e);
        }
        return new ArrayList<>();
    }

}
