package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDateInterval;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EnergyOfferDateIntervalDAOImpl extends AbstractDAOImpl<EnergyOfferDateInterval> {
    public EnergyOfferDateIntervalDAOImpl() {
        super(EnergyOfferDateInterval.class);
    }

    public List<EnergyOfferDateInterval> getOfferDateIntervalByDetailsId(int id) {
        try {
            List<EnergyOfferDateInterval> list = getBaseDAO().queryBuilder().where().eq(EnergyOfferDateInterval.ID_ENERGY_DETAIL_OFFER, id).query();
            if (list.size() != 0) {
                return list;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new ArrayList<>();
    }

    public int deleteEnergyDateIntervallById(int id) {
        try {
            DeleteBuilder<EnergyOfferDateInterval, Integer> energyOfferDateIntervalIntegerDeleteBuilder =
                    getBaseDAO().deleteBuilder();
            energyOfferDateIntervalIntegerDeleteBuilder.where().eq(EnergyOfferDateInterval.ENERGY_OFFER_DATE_INTERVAL_ID, id);
            return energyOfferDateIntervalIntegerDeleteBuilder.delete();
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return 0;
    }

    public int deleteEnergyDateIntervallByDetailsId(int id) {
        try {
            DeleteBuilder<EnergyOfferDateInterval, Integer> energyOfferDateIntervalIntegerDeleteBuilder =
                    getBaseDAO().deleteBuilder();
            energyOfferDateIntervalIntegerDeleteBuilder.where().eq(EnergyOfferDateInterval.ID_ENERGY_DETAIL_OFFER, id);
            return energyOfferDateIntervalIntegerDeleteBuilder.delete();
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return 0;
    }

    public boolean isMonthAlreadyExists(int energyOfferDetailId, Date date) {
        boolean result = false;
        try {
            List<EnergyOfferDateInterval> list = getBaseDAO().queryBuilder().where().eq(EnergyOfferDateInterval.ID_ENERGY_DETAIL_OFFER, energyOfferDetailId).and()
                    .eq(EnergyOfferDateInterval.DATE_TO, date).query();
            if (list.size() != 0) {
                result = true;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return result;
    }

    public String[] getMinAndMaxPotenzaImpegnataOfPod(int energyOfferDetailId) {
        String[] result = null;
        try {
            result = getBaseDAO().queryRaw("SELECT MIN(potenzaImpegnata), MAX(potenzaImpegnata) FROM EnergyOfferDateInterval WHERE" +
                    " idEnergyDetailOffer = " + energyOfferDetailId).getFirstResult();
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return result;
    }

    public Integer getPotenzaImpegnataByDetailsIdAndMonth(int energyOfferDetailId, String mm) {
        String query = "SELECT potenzaImpegnata FROM EnergyOfferDateInterval WHERE " +
                "strftime('%m', dateFrom) = '" + mm + "' AND idEnergyDetailOffer = " + energyOfferDetailId;
        Integer result = 0;
        try {
            result = getBaseDAO().queryRaw(query, new RawRowMapper<Integer>() {
                @Override
                public Integer mapRow(String[] columnNames, String[] resultColumns) {
                    return Integer.valueOf(resultColumns[0]);
                }
            }).getFirstResult();
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return result;
    }
}
