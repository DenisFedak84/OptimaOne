package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.ClientEnergyOffers;

import java.util.List;

public class ClientEnergyOffersDAOImpl extends AbstractDAOImpl<ClientEnergyOffers> {
    public ClientEnergyOffersDAOImpl() {
        super(ClientEnergyOffers.class);
    }

    public ClientEnergyOffers getClientEnergyOfferByClientId(int id) {
        try {
            List<ClientEnergyOffers> clientEnergyOffersList = getBaseDAO().queryForEq(ClientEnergyOffers.CLIENT_ID, id);
            if (clientEnergyOffersList.size() != 0) {
                return clientEnergyOffersList.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new ClientEnergyOffers();
    }
}
