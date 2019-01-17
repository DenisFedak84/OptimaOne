package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.ClientDetailGasOffers;

import java.util.ArrayList;
import java.util.List;

public class ClientDetailGasOffersDAOImpl extends AbstractDAOImpl<ClientDetailGasOffers> {
    public ClientDetailGasOffersDAOImpl() {
        super(ClientDetailGasOffers.class);
    }


    public List<ClientDetailGasOffers> getClientDetailGasOffersById(int id) {
        try {
            List<ClientDetailGasOffers> detailGasOfferses = getBaseDAO().queryForEq(ClientDetailGasOffers.CLIENT_GAS_OFFER_ID, id);
            if (detailGasOfferses.size() != 0) {
                return detailGasOfferses;
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new ArrayList<>();
    }
}
