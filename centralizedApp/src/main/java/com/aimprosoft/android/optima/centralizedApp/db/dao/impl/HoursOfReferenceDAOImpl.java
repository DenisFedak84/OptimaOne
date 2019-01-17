package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.HoursOfReference;

import java.util.ArrayList;
import java.util.List;

public class HoursOfReferenceDAOImpl extends AbstractDAOImpl<HoursOfReference> {
    public HoursOfReferenceDAOImpl() {
        super(HoursOfReference.class);
    }

    public List<HoursOfReference> getYearOfReferenceFilteredByApplianceCode(String code) {
        try {
            List<HoursOfReference> hoursOfReferences = getBaseDAO().queryBuilder().where().eq(HoursOfReference.CODE_APPLIANCE, code).query();
            if (hoursOfReferences.size() > 0) {
                return hoursOfReferences;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find exiting HoursOfReference", e);
        }
        return new ArrayList<>();
    }

    public String getHoursOfReferenceDescById(int id) {
        try {
            List<HoursOfReference> list = getBaseDAO().queryBuilder().where().eq(HoursOfReference.HOURS_ID, id).query();
            if (list.size() != 0) {
                return list.get(0).getHoursValue();
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return "";
    }

    public int getHourseOfReferenceById(int id) {
        try {
            List<HoursOfReference> hoursOfReferences = getBaseDAO().queryBuilder().where().eq(HoursOfReference.HOURS_ID, id).query();
            if (hoursOfReferences.size() > 0) {
                HoursOfReference hoursOfReference = hoursOfReferences.get(0);
                return hoursOfReference.getHoursId();
            }
        } catch (Throwable e) {
            Log.e(TAG, "", e);
        }
        return 0;
    }

}
