package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.HouseHolderDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.HouseHolder;

import java.util.ArrayList;
import java.util.List;

public class AbitantiDellaCasaSpinner extends AbstractService<Void, List> {

    public AbitantiDellaCasaSpinner() {
    }

    public AbitantiDellaCasaSpinner(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List doStuff(Void... voids) {
        HouseHolderDAOImpl houseHolderDAO = new HouseHolderDAOImpl();
        try {
            List list = houseHolderDAO.getAllRows();
            list.add(0, "");
            return list;
        } catch (Exception e) {
            Log.e(e.getClass().getName(), "", e);
        }
        return new ArrayList<HouseHolder>();
    }
}
