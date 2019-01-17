package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.ClientDetailTlcOffers;

import java.util.ArrayList;
import java.util.List;

public class ClientDetailsTlcOffersDAOImpl extends AbstractDAOImpl<ClientDetailTlcOffers> {
    public ClientDetailsTlcOffersDAOImpl() {
        super(ClientDetailTlcOffers.class);
    }


    public List<ClientDetailTlcOffers> getClientDetailTlcOffersById(int id) {
        try {
            List<ClientDetailTlcOffers> clientDetailTlcOfferses = getBaseDAO().queryForEq(ClientDetailTlcOffers.CLIENT_TLC_OFFERS_ID, id);
            if (clientDetailTlcOfferses.size() != 0) {
                return clientDetailTlcOfferses;
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new ArrayList<>();
    }

    public ClientDetailTlcOffers getClientDetailTlcOffersByLine(String line) {
        try {
            List<ClientDetailTlcOffers> clientDetailTlcOfferses = getBaseDAO().queryForEq(ClientDetailTlcOffers.LINE, line);
            if (clientDetailTlcOfferses.size() != 0) {
                return clientDetailTlcOfferses.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new ClientDetailTlcOffers();
    }
}
