package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyClass;

import java.util.List;

public class EnergyClassDAOImpl extends AbstractDAOImpl<EnergyClass> {
    public EnergyClassDAOImpl() {
        super(EnergyClass.class);
    }

    public String getEnergyClassDescById(int id) {
        try {
            List<EnergyClass> list = getBaseDAO().queryBuilder().where().eq(EnergyClass.ENERGY_CLASS_ID, id).query();
            if (list.size() != 0) {
                return list.get(0).getEnergyClassValue();
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return "";
    }
}
