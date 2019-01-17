package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.HoursOfReferenceDAOImpl;

import java.util.ArrayList;
import java.util.List;

public class OreDiUtilizzoSpinner extends AbstractService<String, List> {

    public OreDiUtilizzoSpinner() {
    }

    public OreDiUtilizzoSpinner(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List doStuff(String... str) {
        try {
            List hoursOfReferences = new HoursOfReferenceDAOImpl().getYearOfReferenceFilteredByApplianceCode(str[0]);
            hoursOfReferences.add(0, "");
            return hoursOfReferences;
        } catch (Exception e) {
            Log.e(e.getClass().getName(), "", e);
        }
        return new ArrayList<>();
    }
}
