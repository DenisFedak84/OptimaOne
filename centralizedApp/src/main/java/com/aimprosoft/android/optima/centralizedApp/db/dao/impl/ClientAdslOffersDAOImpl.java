package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.ClientAdslOffers;

import java.util.List;

public class ClientAdslOffersDAOImpl extends AbstractDAOImpl<ClientAdslOffers> {
    public ClientAdslOffersDAOImpl() {
        super(ClientAdslOffers.class);
    }

    public ClientAdslOffers getClientAdslOfferById(int id) {
        try {
            List<ClientAdslOffers> clientAdslOfferses = getBaseDAO().queryForEq(ClientAdslOffers.CLIENT_ADSL_OFFER_ID, id);
            if (clientAdslOfferses.size() != 0) {
                return clientAdslOfferses.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new ClientAdslOffers();
    }
}
