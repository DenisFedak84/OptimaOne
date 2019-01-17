package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasProfiliUtilizzoDAOImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClasseDiPrelievoService extends AbstractService<Map<String, Integer>, List> {

    public ClasseDiPrelievoService() {
    }

    public ClasseDiPrelievoService(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List doStuff(Map<String, Integer>... maps) {
        HashMap<String, Integer> param = (HashMap<String, Integer>) maps[0];
        return new GasProfiliUtilizzoDAOImpl().getClassePrelievoGasFilteredList(param.get("townId"), param.get("categoriaUso"));
    }
}
