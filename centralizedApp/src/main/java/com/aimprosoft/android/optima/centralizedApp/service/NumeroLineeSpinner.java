package com.aimprosoft.android.optima.centralizedApp.service;


import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.LineNumbersDAOImpl;

import java.util.ArrayList;
import java.util.List;

public class NumeroLineeSpinner extends AbstractService<Void, List> {

    public NumeroLineeSpinner() {
    }

    public NumeroLineeSpinner(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List doStuff(Void... voids) {
        try {
            List numeroLinees = new LineNumbersDAOImpl().getAllRows();
            numeroLinees.add(0, "");
            return numeroLinees;
        } catch (Exception e) {
            Log.e(TAG, "error while executing NumeroLineeSpinner", e);
        }
        return new ArrayList<>();
    }
}


