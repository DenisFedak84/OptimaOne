package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ClientsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Clients;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientsDataService extends AbstractService<Object, List<Clients>> {

    public ClientsDataService(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List<Clients> doStuff(Object... param) {
        try {
            List<Clients> clientses = new ClientsDAOImpl().getAllRows();
            if (clientses.size() != 0)
                return clientses;
        } catch (SQLException e) {
            Log.e(e.getClass().getName(), "can't make query", e);
        }
        return new ArrayList<>();
    }

}
