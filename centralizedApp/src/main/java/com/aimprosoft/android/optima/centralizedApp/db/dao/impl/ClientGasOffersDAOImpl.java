package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.ClientGasOffers;

import java.util.List;

public class ClientGasOffersDAOImpl extends AbstractDAOImpl<ClientGasOffers> {
    public ClientGasOffersDAOImpl() {
        super(ClientGasOffers.class);
    }


    public ClientGasOffers getClientGasOfferById(int id) {
        try {
            List<ClientGasOffers> clientGasOfferses = getBaseDAO().queryForEq(ClientGasOffers.CLIENT_GAS_OFFER_ID, id);
            if (clientGasOfferses.size() != 0) {
                return clientGasOfferses.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new ClientGasOffers();
    }
}
