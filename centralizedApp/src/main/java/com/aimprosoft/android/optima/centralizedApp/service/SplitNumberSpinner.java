package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.SplitNumberDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.SplitNumber;

import java.util.ArrayList;
import java.util.List;

public class SplitNumberSpinner extends AbstractService<Void, List> {

    public SplitNumberSpinner(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    public SplitNumberSpinner() {
    }

    @Override
    protected List doStuff(Void... voids) {
        SplitNumberDAOImpl splitNumberDAO = new SplitNumberDAOImpl();
        try {
            List list = splitNumberDAO.getAllRows();
            list.add(0, "");
            return list;
        } catch (Exception e) {
            Log.e(e.getClass().getName(), "e", e);

        }
        return new ArrayList<SplitNumber>();
    }
}
