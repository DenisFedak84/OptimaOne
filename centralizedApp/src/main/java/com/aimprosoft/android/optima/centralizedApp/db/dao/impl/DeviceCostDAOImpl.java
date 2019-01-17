package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.DeviceCost;

public class DeviceCostDAOImpl extends AbstractDAOImpl<DeviceCost> {
    public DeviceCostDAOImpl() {
        super(DeviceCost.class);
    }

    public DeviceCost getDeviceCostByCode(String deviceCode) {
        try {
            DeviceCost deviceCost = getBaseDAO().queryBuilder().where()
                    .eq(DeviceCost.CODE_DEVICE, deviceCode).queryForFirst();
            if (deviceCost != null) {
                return deviceCost;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row");
        }
        return null;
    }
}
