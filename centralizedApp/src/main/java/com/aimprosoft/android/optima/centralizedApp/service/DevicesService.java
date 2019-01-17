package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.DeviceDAOImpl;

import java.util.ArrayList;
import java.util.List;

public class DevicesService extends AbstractService<Void, List<String>> {

    public DevicesService(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List<String> doStuff(Void... voids) {
        List<String> deviceList = new DeviceDAOImpl().getDeviceNameList();
        if (!deviceList.isEmpty()) {
            deviceList.add(0, "");
            return deviceList;
        }
        return new ArrayList<>();
    }
}
