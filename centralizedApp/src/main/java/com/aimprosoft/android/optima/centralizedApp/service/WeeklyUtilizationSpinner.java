package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WeeklyUtilizationDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WeeklyUtilization;

import java.util.ArrayList;
import java.util.List;

public class WeeklyUtilizationSpinner extends AbstractService<Void, List> {

    public WeeklyUtilizationSpinner() {
    }

    public WeeklyUtilizationSpinner(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List doStuff(Void... voids){
        WeeklyUtilizationDAOImpl weeklyUtilizationDAO = new WeeklyUtilizationDAOImpl();
        try {
            List list = weeklyUtilizationDAO.getAllRows();
            list.add(0, "");
            return list;
        } catch (Exception e) {
            Log.e(e.getClass().getName(), "", e);
        }
        return new ArrayList<WeeklyUtilization>();
    }
}
