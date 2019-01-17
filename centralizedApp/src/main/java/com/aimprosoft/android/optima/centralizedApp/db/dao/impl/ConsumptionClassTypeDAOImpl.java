package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;


import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.ConsumptionClassType;

import java.sql.SQLException;
import java.util.List;

public class ConsumptionClassTypeDAOImpl extends AbstractDAOImpl<ConsumptionClassType> {
    public ConsumptionClassTypeDAOImpl() {
        super(ConsumptionClassType.class);
    }

    public String getTensioneDescById(int tensioneId) {
        try {
            List<ConsumptionClassType> consumptionClassTypes = getBaseDAO().queryBuilder().where().eq(ConsumptionClassType.CONSUMPTION_CLASS_TYPE_ID, tensioneId).query();
            if(consumptionClassTypes.size()!=0) {
                ConsumptionClassType consumptionClassType = consumptionClassTypes.get(0);
                return consumptionClassType.getConsumptionClassTypeDesc();
            }
        } catch (SQLException e) {
            Log.e(TAG, "can't get codice categoria", e);
        }
        return "";
    }
}
