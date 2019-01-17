package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WeeklyWashingDAOImpl;

import java.util.ArrayList;
import java.util.List;

public class WeeklyWashingSpinner extends AbstractService<Void, List> {

    public WeeklyWashingSpinner() {
    }

    public WeeklyWashingSpinner(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List doStuff(Void... voids){
        WeeklyWashingDAOImpl weeklyWashingDAO = new WeeklyWashingDAOImpl();
        try {
            List list = weeklyWashingDAO.getAllRows();
            list.add(0, "");
            return list;
        } catch (Exception e) {
            Log.e(e.getClass().getName(), "", e);
        }
        return new ArrayList<>();
    }
}
