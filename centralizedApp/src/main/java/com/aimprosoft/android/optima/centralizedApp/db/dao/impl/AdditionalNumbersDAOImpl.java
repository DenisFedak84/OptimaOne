package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.AdditionalNumbers;

import java.util.ArrayList;
import java.util.List;

public class AdditionalNumbersDAOImpl extends AbstractDAOImpl<AdditionalNumbers> {
    public AdditionalNumbersDAOImpl() {
        super(AdditionalNumbers.class);
    }

    public AdditionalNumbers getAdditionalNumbersById(int id) {
        try {
            List<AdditionalNumbers> additionalNumberses = getBaseDAO().queryForEq(AdditionalNumbers.ID_ADDITIONAL_NUMBER, id);
            if (additionalNumberses.size() != 0) {
                return additionalNumberses.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new AdditionalNumbers();
    }

    public List<AdditionalNumbers> getAdditionalNumbersByConfiguratorType(String configuratorType) {
        try {
            List<AdditionalNumbers> additionalNumberses = getBaseDAO().queryForEq(AdditionalNumbers.SYSTEM, configuratorType);
            if (additionalNumberses.size() != 0) {
                return additionalNumberses;
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new ArrayList<>();
    }
}
