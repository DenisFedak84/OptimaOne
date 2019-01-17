package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.WeeklyWashing;

import java.util.List;

public class WeeklyWashingDAOImpl extends AbstractDAOImpl<WeeklyWashing> {
    public WeeklyWashingDAOImpl() {
        super(WeeklyWashing.class);
    }

    public int getWeeklyWashingValueById(int id) {
        try {
            List<WeeklyWashing> weeklyWashings = getBaseDAO().queryBuilder().where().eq(WeeklyWashing.WEEKLY_WASHING_ID, id).query();
            if (weeklyWashings.size() > 0) {
                WeeklyWashing weeklyWashing = weeklyWashings.get(0);
                return weeklyWashing.getWeeklyWashingValue();
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find exiting town", e);
        }
        return 1;
    }

    public String getWeeklyWashingDescById(int id) {
        try {
            List<WeeklyWashing> list = getBaseDAO().queryBuilder().where().eq(WeeklyWashing.WEEKLY_WASHING_ID, id).query();
            if (list.size() != 0) {
                return list.get(0).getWeeklyWashingDesc();
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return "";
    }
}
