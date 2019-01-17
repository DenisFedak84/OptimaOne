package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.DeviceResult;

import java.util.List;

public class DeviceResultDAOImpl extends AbstractDAOImpl<DeviceResult> {

    public DeviceResultDAOImpl() {
        super(DeviceResult.class);
    }

    public List<DeviceResult> getDeviceResultListByResultOfferId(int id) {
        try {
            List<DeviceResult> deviceResultList = getBaseDAO().queryBuilder().where()
                    .eq(DeviceResult.RESULT_OFFER_ID, id).query();
            if (!deviceResultList.isEmpty()) {
                return deviceResultList;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return null;
    }
}
