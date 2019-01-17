package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.ClientDetailEnergyOffers;

import java.util.ArrayList;
import java.util.List;

public class ClientDetailEnergyOffersDAOImpl extends AbstractDAOImpl<ClientDetailEnergyOffers> {


    public ClientDetailEnergyOffersDAOImpl() {
        super(ClientDetailEnergyOffers.class);
    }

    public List<ClientDetailEnergyOffers> getClientDetailEnergyOffersById(int id) {
        try {
            List<ClientDetailEnergyOffers> detailEnergyOfferses = getBaseDAO().queryForEq(ClientDetailEnergyOffers.CLIENT_ENERGY_OFFER_ID, id);
            if (detailEnergyOfferses.size() != 0) {
                return detailEnergyOfferses;
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new ArrayList<>();
    }
}
