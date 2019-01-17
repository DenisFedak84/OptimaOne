package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.ClientDetailAdslOffers;

import java.util.ArrayList;
import java.util.List;

public class ClientDetailAdslOffersDAOImpl extends AbstractDAOImpl<ClientDetailAdslOffers> {
    public ClientDetailAdslOffersDAOImpl() {
        super(ClientDetailAdslOffers.class);
    }

    public List<ClientDetailAdslOffers> getClientDetailAdslOffersById(int id) {
        try {
            List<ClientDetailAdslOffers> clientDetailAdslOfferses = getBaseDAO().queryForEq(ClientDetailAdslOffers.CLIENT_ADSL_OFFER_ID, id);
            if (clientDetailAdslOfferses.size() != 0) {
                return clientDetailAdslOfferses;
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new ArrayList<>();
    }
}
