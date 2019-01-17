package com.aimprosoft.android.optima.centralizedApp.service;


import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.AdditionalNumbersDAOImpl;

import java.util.ArrayList;
import java.util.List;

public class SceltaNumerazioniAggiuntiveSpinner extends AbstractService<Void, List> {

    public SceltaNumerazioniAggiuntiveSpinner() {
    }

    public SceltaNumerazioniAggiuntiveSpinner(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List doStuff(Void... voids) {
        try {
            List additionalNumberses = new AdditionalNumbersDAOImpl().getAllRows();
            additionalNumberses.add(0, "");
            return additionalNumberses;
        } catch (Exception e) {
            Log.e(TAG, "error while executing SceltaNumerazioniAggiuntiveSpinner");
        }
        return new ArrayList<>();
    }
}


