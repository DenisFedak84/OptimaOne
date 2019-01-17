package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.ThermalInsulation;

import java.util.List;

public class ThermalInsulationDAOImpl extends AbstractDAOImpl<ThermalInsulation> {
    public ThermalInsulationDAOImpl() {
        super(ThermalInsulation.class);
    }

    public String getThermalInsulationDescById(int id) {
        try {
            List<ThermalInsulation> list = getBaseDAO().queryBuilder().where().eq(ThermalInsulation.ID_THERMAL_INSULATION, id).query();
            if (list.size() != 0) {
                return list.get(0).getDescThermalInsulation();
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return "";
    }

}
