package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.GasUtilizationProfileClass;

import java.util.List;

public class GasUtilizationProfileClassDAOImpl extends AbstractDAOImpl<GasUtilizationProfileClass> {
    public GasUtilizationProfileClassDAOImpl() {
        super(GasUtilizationProfileClass.class);
    }

    public String getClassCode(String validationMap) {

        String query = "SELECT GasUtilizationProfileClass.classCode FROM GasUtilizationProfileClass " +
                "WHERE (GasUtilizationProfileClass.validationMap = '" + validationMap + "');";

        String result = null;
        try {
            result = getBaseDAO().queryBuilder().where().eq(GasUtilizationProfileClass.VALIDATION_MAP, validationMap).queryForFirst().getClassCode();
        } catch (Throwable e) {
            Log.e(TAG, "can't make query", e);
        }
        return result;
    }

    public boolean getIsClassCode(String validationMap) {
        List<GasUtilizationProfileClass> list;
        try {
            list = getBaseDAO().queryBuilder().where().eq(GasUtilizationProfileClass.VALIDATION_MAP, validationMap).query();
            if (list.size() != 0) {
                return true;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't make query", e);
        }
        return false;
    }
}
