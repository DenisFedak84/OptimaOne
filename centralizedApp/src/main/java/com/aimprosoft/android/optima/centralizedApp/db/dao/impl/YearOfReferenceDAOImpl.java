package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.YearOfReference;

import java.util.ArrayList;
import java.util.List;

public class YearOfReferenceDAOImpl extends AbstractDAOImpl<YearOfReference> {
    public YearOfReferenceDAOImpl() {
        super(YearOfReference.class);
    }

    public int getEnergyClassIdByYearOfReferenceId(int id) {
        try {
            List<YearOfReference> yearOfReferences = getBaseDAO().queryBuilder().where().eq(YearOfReference.YEAR_OF_REFERENCE_ID, id).query();
            if (yearOfReferences.size() > 0) {
                YearOfReference yearOfReference = yearOfReferences.get(0);
                return yearOfReference.getEnergyClass();
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find exiting town", e);
        }
        return 0;
    }

    public double getCoefficicentYearOfReference(int id,String code) {
        try {
            List<YearOfReference> yearOfReferences = getBaseDAO().queryBuilder().where().eq(YearOfReference.YEAR_OF_REFERENCE_ID, id).and().eq(YearOfReference.CODE_APPLIANCE, code).query();
            if (yearOfReferences.size() > 0) {
                YearOfReference yearOfReference = yearOfReferences.get(0);
                return yearOfReference.getCoefficient();
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find exiting town", e);
        }
        return 0.0;
    }

    public List<YearOfReference> getYearOfReferenceFilteredByApplianceCode(String code) {
        try {
            List<YearOfReference> yearOfReferences = getBaseDAO().queryBuilder().where().eq(YearOfReference.CODE_APPLIANCE, code).query();
            if (yearOfReferences.size() > 0) {
                return yearOfReferences;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find exiting town", e);
        }
        return new ArrayList<>();
    }

    public int getYearOfReferenceDescById(int id) {
        try {
            List<YearOfReference> yearOfReferences = getBaseDAO().queryBuilder().where().eq(YearOfReference.YEAR_OF_REFERENCE_ID, id).query();
            if (yearOfReferences.size() > 0) {
                return yearOfReferences.get(0).getYearOfReferenceValue();
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find exiting YearOfReference", e);
        }
        return 0;
    }
}
