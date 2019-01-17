package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.AppliancesDimension;

import java.util.List;

public class AppliancesDimensionDAOImpl extends AbstractDAOImpl<AppliancesDimension> {
    public AppliancesDimensionDAOImpl() {
        super(AppliancesDimension.class);
    }

    public String getRefrigeratorDimensionDescById(int id) {
        try {
            List<AppliancesDimension> list = getBaseDAO().queryBuilder().where().eq(AppliancesDimension.REFRIGERATOR_DIMENSION_ID, id).query();
            if (list.size() != 0) {
                return list.get(0).getRefrigeratorDimensionDesc();
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return "";
    }

}
