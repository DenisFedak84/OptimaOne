package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.WeeklyUtilization;

import java.util.List;

public class WeeklyUtilizationDAOImpl extends AbstractDAOImpl<WeeklyUtilization> {
    public WeeklyUtilizationDAOImpl() {
        super(WeeklyUtilization.class);
    }

    public int getWeeklyUtilizationValueById(int id) {
        try {
            List<WeeklyUtilization> weeklyUtilizations = getBaseDAO().queryBuilder().where().eq(WeeklyUtilization.WEEKLY_UTILIZATION_ID, id).query();
            if (weeklyUtilizations.size() > 0) {
                WeeklyUtilization weeklyUtilization = weeklyUtilizations.get(0);
                return weeklyUtilization.getWeeklyUtilizationValue();
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find exiting town", e);
        }
        return 0;
    }


    public String getWeeklyUtilizationDescById(int id) {
        try {
            List<WeeklyUtilization> list = getBaseDAO().queryBuilder().where().eq(WeeklyUtilization.WEEKLY_UTILIZATION_ID, id).query();
            if (list.size() != 0) {
                return list.get(0).getWeeklyUtilizationDesc();
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return "";
    }
}
