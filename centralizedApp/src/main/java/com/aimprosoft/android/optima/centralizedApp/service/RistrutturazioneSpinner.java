package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.RestorationDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Restoration;

import java.util.ArrayList;
import java.util.List;

public class RistrutturazioneSpinner extends AbstractService<String, List<Restoration>> {

    public RistrutturazioneSpinner() {
    }

    public RistrutturazioneSpinner(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List<Restoration> doStuff(String... strings){
        try {
//            List<Restoration> list = new RestorationDAOImpl().getAllRows();
//            list.add(0, "");
            return new RestorationDAOImpl().getAllRows();
        } catch (Exception e) {
            Log.e(e.getClass().getName(), "", e);
        }
        return new ArrayList<Restoration>();

    }
}
