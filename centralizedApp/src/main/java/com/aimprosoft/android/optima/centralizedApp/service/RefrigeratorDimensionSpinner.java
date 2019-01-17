package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.AppliancesDimensionDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.AppliancesDimension;

import java.util.ArrayList;
import java.util.List;

public class RefrigeratorDimensionSpinner extends AbstractService<Void, List> {

    public RefrigeratorDimensionSpinner() {
    }

    public RefrigeratorDimensionSpinner(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List doStuff(Void... voids){

        AppliancesDimensionDAOImpl refrigeratorDimensionDAO = new AppliancesDimensionDAOImpl();
        try {
            List list = refrigeratorDimensionDAO.getAllRows();
            list.add(0, "");
            return list;
        } catch (Exception e) {
            Log.e(e.getClass().getName(), "", e);
        }
        return new ArrayList<AppliancesDimension>();
    }
}
