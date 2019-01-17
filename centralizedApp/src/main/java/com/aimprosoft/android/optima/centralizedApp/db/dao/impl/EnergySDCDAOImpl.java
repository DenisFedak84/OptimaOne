package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergySDC;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class EnergySDCDAOImpl extends AbstractDAOImpl<EnergySDC> {
    public EnergySDCDAOImpl() {
        super(EnergySDC.class);
    }

    public List<EnergySDC> getEnergySDCByDetailsId(int id) {
        List<EnergySDC> list;
        try {
            QueryBuilder<EnergySDC, Integer> q = getBaseDAO().queryBuilder();
            q.where().eq(EnergySDC.ENERGY_DETAILS_OFFER_ID, id);
            q.orderBy(EnergySDC.CONSUMO_ANNUO, true);
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

    public int deleteEnergySDCByDetailsId(int id) {
        int deletedRows;
        try {
            DeleteBuilder<EnergySDC, Integer> energySDCIntegerDeleteBuilder =
                    getBaseDAO().deleteBuilder();
            energySDCIntegerDeleteBuilder.where().eq(EnergySDC.ENERGY_DETAILS_OFFER_ID, id);
            deletedRows = energySDCIntegerDeleteBuilder.delete();
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
            deletedRows = 0;
        }
        return deletedRows;
    }
}
