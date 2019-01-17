package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TariffaDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Tariffa;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class TariffaService extends AbstractService<Void, List<? extends Object>> {


    public TariffaService() {
    }

    public TariffaService(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List<? extends Object> doStuff(Void... voids) {
        try {
            List<Tariffa> tariffa = new TariffaDAOImpl().getAllRows();
            tariffa.add(0, new Tariffa(0, ""));
            return tariffa;
        } catch (SQLException e) {
            Log.e(e.getClass().getName(), "can't make query", e);
        }
        return new ArrayList<>();
    }
}
