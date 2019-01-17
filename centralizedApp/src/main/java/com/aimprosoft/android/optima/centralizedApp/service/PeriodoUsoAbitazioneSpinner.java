package com.aimprosoft.android.optima.centralizedApp.service;


import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.HouseMontlyUseDAOImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeriodoUsoAbitazioneSpinner extends AbstractService<Void, List> {

    public PeriodoUsoAbitazioneSpinner() {
    }

    public PeriodoUsoAbitazioneSpinner(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List doStuff(Void... voids){
        try {
            List list = new HouseMontlyUseDAOImpl().getAllRows();
            list.add(0, "");
            return list;
        } catch (SQLException e) {
            Log.e(e.getClass().getName(), "", e);
        }
        return new ArrayList();
    }
}
