package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.SplitNumber;

import java.util.List;

public class SplitNumberDAOImpl extends AbstractDAOImpl<SplitNumber> {
    public SplitNumberDAOImpl() {
        super(SplitNumber.class);
    }

    public int getSplitNumberValueById(int id) {
        try {
            List<SplitNumber> splitNumbers = getBaseDAO().queryBuilder().where().eq(SplitNumber.SPLIT_NUMBER_ID, id).query();
            if (splitNumbers.size() > 0) {
                SplitNumber splitNumber = splitNumbers.get(0);
                return splitNumber.getSplitNumberValue();
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find exiting town", e);
        }
        return 0;
    }
}
