package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.BoilerTypesDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.BoilerTypes;

import java.util.ArrayList;
import java.util.List;

public class TipologiaDiCaldaiaSpinner extends AbstractService<String, List<BoilerTypes>> {

    public TipologiaDiCaldaiaSpinner() {
    }

    public TipologiaDiCaldaiaSpinner(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List<BoilerTypes> doStuff(String... strings) {
        try {
//            List<Restoration> list = new RestorationDAOImpl().getAllRows();
//            list.add(0, "");
            return new BoilerTypesDAOImpl().getAllRows();
        } catch (Exception e) {
            Log.e(e.getClass().getName(), "", e);
        }
        return new ArrayList<BoilerTypes>();

    }
}
