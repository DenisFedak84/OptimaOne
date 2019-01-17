package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.Clients;

import java.util.ArrayList;
import java.util.List;

public class ClientsDAOImpl extends AbstractDAOImpl<Clients> {

    public ClientsDAOImpl() {
        super(Clients.class);
    }

    public Clients getClientsByClientId(int clientId) {
        try {
            List<Clients> clientsList = getBaseDAO().queryForEq(Clients.CLIENT_ID, clientId);
            if (clientsList.size() != 0) {
                return clientsList.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new Clients();
    }

    public List<Clients> getClientsListWhereNameStartWith(String columnaName, String pattern) {
        try {
            List<Clients> clientsList = getBaseDAO().queryBuilder().where().like(columnaName, pattern+"%").query();
            if (clientsList.size() != 0) {
                return clientsList;
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new ArrayList<>();
    }
}
