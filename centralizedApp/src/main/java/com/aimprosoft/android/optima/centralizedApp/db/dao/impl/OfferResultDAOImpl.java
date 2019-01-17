package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.OfferResult;

public class OfferResultDAOImpl extends AbstractDAOImpl<OfferResult> {

    public OfferResultDAOImpl() {
        super(OfferResult.class);
    }

    public OfferResult getOfferResultByOfferId(int offerId) {
        try {
            OfferResult offerResult = getBaseDAO().queryBuilder().where()
                    .eq(OfferResult.OFFER_ID, offerId).queryForFirst();
            if (offerResult != null) {
                return offerResult;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return null;
    }
}
