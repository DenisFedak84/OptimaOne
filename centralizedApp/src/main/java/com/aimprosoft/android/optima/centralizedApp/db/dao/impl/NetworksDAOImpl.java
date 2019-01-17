package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.Networks;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NetworksDAOImpl extends AbstractDAOImpl<Networks> {
    public NetworksDAOImpl() {
        super(Networks.class);
    }

    public Networks getNetworkById(int networkId) {
        try {
            List<Networks> networksList = getBaseDAO().queryForEq(Networks.NETWORK_ID, networkId);
            if (networksList.size() != 0) {
                return networksList.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new Networks();
    }

    public List<Networks> getNetworksByOperatorId(int id) {
        try {
            List list = getBaseDAO().queryBuilder().where().eq(Networks.CARRIER_ID, id).query();
            if (list.size() != 0) {
                return list;
            }
        } catch (SQLException e) {
            Log.e("Networks ", "can't get info from base", e);
        }
        return new ArrayList();
    }

    public Networks getNetworksByDesc(String desc, int carrierId) {
        try {
            Networks networks = getBaseDAO().queryBuilder().where().eq(Networks.NETWORK_DESC, desc)
                    .and().eq(Networks.CARRIER_ID, carrierId).queryForFirst();
            if (networks != null) {
                return networks;
            }
        } catch (SQLException e) {
            Log.e("Networks ", "can't get info from base", e);
        }
        return null;
    }
}
