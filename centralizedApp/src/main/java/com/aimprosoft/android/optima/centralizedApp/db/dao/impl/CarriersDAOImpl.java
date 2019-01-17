package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.Carriers;

import java.util.ArrayList;
import java.util.List;

public class CarriersDAOImpl extends AbstractDAOImpl<Carriers> {
    public CarriersDAOImpl() {
        super(Carriers.class);
    }

    public Carriers getCarrierById(int id) {
        try {
            List<Carriers> carrierses = getBaseDAO().queryForEq(Carriers.CARRIER_ID, id);
            if (carrierses.size() != 0) {
                return carrierses.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new Carriers();
    }

    public List<Carriers> getCarrierByConfiguratorType(String configuratorType) {
        try {
            List<Carriers> carrierses = getBaseDAO().queryForEq(Carriers.SYSTEM, configuratorType);
            if (carrierses.size() != 0) {
                return carrierses;
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new ArrayList<>();
    }

    public Carriers getCarrierByDescAndConfiguratorType(String desc, String configuratorType) {
        try {
            Carriers carriers = getBaseDAO().queryBuilder().where().eq(Carriers.SYSTEM, configuratorType).and()
                    .eq(Carriers.CARRIER_DESC, desc).queryForFirst();
            if (carriers != null) {
                return carriers;
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return null;
    }
}
