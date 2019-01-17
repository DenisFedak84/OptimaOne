package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.YearOfReferenceDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.YearOfReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnnoDiAcquistoSpinner extends AbstractService<String, List> {

    public AnnoDiAcquistoSpinner() {
    }

    public AnnoDiAcquistoSpinner(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List doStuff(String... code) {
        YearOfReferenceDAOImpl yearOfReferenceDAO = new YearOfReferenceDAOImpl();
        try {
            List list = yearOfReferenceDAO.getYearOfReferenceFilteredByApplianceCode(code[0]);
            Collections.reverse(list);
            list.add(0, "");
            return list;
        } catch (Exception e) {
            Log.e(e.getClass().getName(), "", e);
        }
        return new ArrayList<YearOfReference>();
    }
}
