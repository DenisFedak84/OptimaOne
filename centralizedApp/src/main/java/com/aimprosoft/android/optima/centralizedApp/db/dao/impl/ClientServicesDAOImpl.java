package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.ClientServices;

import java.util.List;

public class ClientServicesDAOImpl extends AbstractDAOImpl<ClientServices> {
    public ClientServicesDAOImpl() {
        super(ClientServices.class);
    }

    public ClientServices getClientServicesByClientId(int id) {
        try {
            List<ClientServices> clientServiceses = getBaseDAO().queryForEq(ClientServices.CLIENT_ID, id);
            if (clientServiceses.size() != 0) {
                return clientServiceses.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new ClientServices();
    }
}
