package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.ClientTlcOffers;

import java.util.List;

public class ClientTlcOffersDAOImpl extends AbstractDAOImpl<ClientTlcOffers> {
    public ClientTlcOffersDAOImpl() {
        super(ClientTlcOffers.class);
    }

    public ClientTlcOffers getClientTlcOfferById(int id) {
        try {
            List<ClientTlcOffers> clientTlcOfferses = getBaseDAO().queryForEq(ClientTlcOffers.CLIENT_TLC_OFFER_ID, id);
            if (clientTlcOfferses.size() != 0) {
                return clientTlcOfferses.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new ClientTlcOffers();
    }
}
