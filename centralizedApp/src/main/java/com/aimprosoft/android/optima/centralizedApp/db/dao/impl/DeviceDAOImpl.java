package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.Device;
import com.j256.ormlite.dao.RawRowMapper;

import java.util.List;

public class DeviceDAOImpl extends AbstractDAOImpl<Device> {
    public DeviceDAOImpl() {
        super(Device.class);
    }

    public List<String> getDeviceNameList() {
        String query = "SELECT nameDevice FROM Device INNER JOIN DeviceCost on Device.codeDevice = " +
                "DeviceCost.codeDevice ORDER BY sortOrder ASC";
        List<String> result = null;
        try {
            result = getBaseDAO().queryRaw(query, new RawRowMapper<String>() {
                @Override
                public String mapRow(String[] columnNames, String[] resultColumns) {
                    return String.valueOf(resultColumns[0]);
                }
            }).getResults();
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return result;
    }


    public Device getDeviceByDesc(String deviceDesc) {
        try {
            Device device = getBaseDAO().queryBuilder().where()
                    .like(Device.NAME_DEVICE, deviceDesc.replaceAll("\'", "''")).queryForFirst();
            if (device != null) {
                return device;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row");
        }
        return null;
    }
}
