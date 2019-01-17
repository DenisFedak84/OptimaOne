package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOfferDateInterval;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.util.ArrayList;
import java.util.List;

public class GasOfferDateIntervalDAOImpl extends AbstractDAOImpl<GasOfferDateInterval> {
    public GasOfferDateIntervalDAOImpl() {
        super(GasOfferDateInterval.class);
    }

    public List<GasOfferDateInterval> getOfferDateIntervalByGasDetailsId(int id) {
        try {
            List<GasOfferDateInterval> list = getBaseDAO().queryBuilder().where().eq(GasOfferDateInterval.GAS_DETAIL_OFFER_ID, id).query();
            if (list.size() != 0) {
                return list;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new ArrayList<>();
    }

    public int deleteGasDateIntervallByDetailId(int id) {
        try {
            DeleteBuilder<GasOfferDateInterval, Integer> gasOfferDateIntervalIntegerDeleteBuilder =
                    getBaseDAO().deleteBuilder();
            gasOfferDateIntervalIntegerDeleteBuilder.where().eq(GasOfferDateInterval.GAS_DETAIL_OFFER_ID, id);
            gasOfferDateIntervalIntegerDeleteBuilder.delete();
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return 0;
    }

    public int deleteGasDateIntervallById(int id) {
        try {
            DeleteBuilder<GasOfferDateInterval, Integer> gasOfferDateIntervalIntegerDeleteBuilder =
                    getBaseDAO().deleteBuilder();
            gasOfferDateIntervalIntegerDeleteBuilder.where().eq(GasOfferDateInterval.GAS_DETAIL_OFFER_INTERVAL_ID, id);
            gasOfferDateIntervalIntegerDeleteBuilder.delete();
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return 0;
    }
}
