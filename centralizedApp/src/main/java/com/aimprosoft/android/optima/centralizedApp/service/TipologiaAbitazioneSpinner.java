package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.FlatTypeDAOImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipologiaAbitazioneSpinner extends AbstractService<Void, List> {

    public TipologiaAbitazioneSpinner() {
    }

    public TipologiaAbitazioneSpinner(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }


    @Override
    protected List doStuff(Void... voids){
        try {
            List list = new FlatTypeDAOImpl().getAllRows();
            list.add(0, "");
            return list;
        } catch (SQLException e) {
            Log.e(e.getClass().getName(), "", e);
        }
        return new ArrayList();
    }
}
