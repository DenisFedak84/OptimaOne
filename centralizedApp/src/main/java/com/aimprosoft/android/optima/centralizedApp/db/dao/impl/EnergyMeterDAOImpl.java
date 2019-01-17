package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyMeter;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.util.ArrayList;
import java.util.List;

public class EnergyMeterDAOImpl extends AbstractDAOImpl<EnergyMeter> {
    public EnergyMeterDAOImpl() {
        super(EnergyMeter.class);
    }

    public String getEnergyMeterDescById(int id) {
        try {
            List<EnergyMeter> list = getBaseDAO().queryBuilder().where().eq(EnergyMeter.ENERGY_METER_ID, id).query();
            if (list.size() != 0) {
                return list.get(0).getEnergyMeterValue();

            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return "";
    }

    public EnergyMeter getEnergiMeterById(int id) {
        try {
            List<EnergyMeter> list = getBaseDAO().queryBuilder().where().eq(EnergyMeter.ENERGY_METER_ID, id).query();
            if (list.size() != 0) {
                return list.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row");
        }
        return new EnergyMeter();
    }

    public List<EnergyMeter> getEnergyMeterListForBT(String configuratorType, Integer contractType) {
        try {
            List<EnergyMeter> list;
            if (configuratorType.equals(Constants.BUSINESS_CONFIGURATOR_FLAG)) {
                list = getBaseDAO().queryBuilder().where().in(EnergyMeter.ENERGY_METER_ID, new Object[]{1, 2, 3, 4, 5}).and().eq(EnergyMeter.SYSTEM, configuratorType).query();
            } else {
                list = getBaseDAO().queryBuilder().where().eq(EnergyMeter.CONTRACT_TYPE, contractType).and().eq(EnergyMeter.SYSTEM, configuratorType).query();
            }
            if (list.size() != 0) {
                return list;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row");
        }
        return new ArrayList<>();
    }

    public EnergyMeter getEnergiMeterByTariffOption(String tariffOption) {
        try {
            List<EnergyMeter> list = getBaseDAO().queryBuilder().where().eq(EnergyMeter.TARIFF_OPTION, tariffOption).query();
            if (list.size() != 0) {
                return list.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row");
        }
        return new EnergyMeter();
    }

    public EnergyMeter getEnergiMeterByTariffOptionAndProjectType(String tariffOption, String system) {
        try {
            List<EnergyMeter> list = getBaseDAO().queryBuilder().where().eq(EnergyMeter.TARIFF_OPTION, tariffOption)
                    .and().eq(EnergyMeter.SYSTEM, system).query();
            if (list.size() != 0) {
                return list.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row");
        }
        return new EnergyMeter();
    }

    public EnergyMeter getEnergiMeterByFollowingParams(String tariffOption, String potenza, int tariffaId, String system) {
        String perc = MyApplication.getContext().getString(R.string.perc);
        try {
            Where<EnergyMeter, Integer> whereStatement = getBaseDAO().queryBuilder().where().eq(EnergyMeter.SYSTEM, system);

            if (tariffaId!=1) {
                whereStatement.and().eq(EnergyMeter.TARIFF_OPTION, tariffOption);
            }

            if (system.equalsIgnoreCase(Constants.BUSINESS_CONFIGURATOR_FLAG)) {
                whereStatement.and().isNull(EnergyMeter.CONTRACT_TYPE);
            } else {
                whereStatement.and().eq(EnergyMeter.CONTRACT_TYPE, tariffaId);
            }
            List<EnergyMeter> list = whereStatement.and().like(EnergyMeter.ENERGY_METER_VALUE, perc + potenza + perc).query();
//                    getBaseDAO().queryBuilder().where().eq(EnergyMeter.TARIFF_OPTION, tariffOption)
//                    .and().eq(EnergyMeter.SYSTEM, system)
//                    .and().like(EnergyMeter.ENERGY_METER_VALUE, perc + potenza + perc).query();
            if (list.size() != 0) {
                return list.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row");
        }
        return new EnergyMeter();
    }
}
