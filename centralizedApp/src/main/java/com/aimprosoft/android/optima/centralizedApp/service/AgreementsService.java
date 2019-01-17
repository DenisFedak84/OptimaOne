package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.AgreementsDAOImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AgreementsService extends AbstractService<Void, List> {

    public AgreementsService() {
    }

    public AgreementsService(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List doStuff(Void... voids) {
        try {
            List agreementses = new AgreementsDAOImpl().getAllRows();
            agreementses.add(0, "");
            return agreementses;
        } catch (SQLException e) {
            Log.e(e.getClass().getName(), "can't make query", e);
        }
        return new ArrayList();
    }
}
