package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.GasSDC;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class GasSDCDAOImpl extends AbstractDAOImpl<GasSDC> {
    public GasSDCDAOImpl() {
        super(GasSDC.class);
    }

    public List<GasSDC> getGasSDCByDetailsId(int id) {
        List<GasSDC> list;
        try {
            QueryBuilder<GasSDC, Integer> q = getBaseDAO().queryBuilder();
            q.where().eq(GasSDC.GAS_DETAILS_OFFER_ID, id);
            q.orderBy(GasSDC.CONSUMO_ANNUO, true);
            list = q.query();
            if (!list.isEmpty()) {
                return list;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
            list = new ArrayList<>();
        }
        return list;
    }

    public int deleteGasSDCByDetailsId(int id) {
        int deletedRows;
        try {
            DeleteBuilder<GasSDC, Integer> gasSDCIntegerDeleteBuilder =
                    getBaseDAO().deleteBuilder();
            gasSDCIntegerDeleteBuilder.where().eq(GasSDC.GAS_DETAILS_OFFER_ID, id);
            deletedRows = gasSDCIntegerDeleteBuilder.delete();
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
            deletedRows = 0;
        }
        return deletedRows;
    }
}
