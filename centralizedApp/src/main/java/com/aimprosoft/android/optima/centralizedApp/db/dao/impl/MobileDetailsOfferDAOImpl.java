package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.InternetDetailOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.MobileDetailsOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.MobileOffer;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.util.ArrayList;
import java.util.List;

public class MobileDetailsOfferDAOImpl extends AbstractDAOImpl<MobileDetailsOffer> {
    public MobileDetailsOfferDAOImpl() {
        super(MobileDetailsOffer.class);
    }

    public List<MobileDetailsOffer> getMobileOfferDetailsByMobileOfferId(int id) {
        try {
            List<MobileDetailsOffer> list = getBaseDAO().queryBuilder().where().eq(MobileDetailsOffer.MOBILE_OFFER_ID, id).query();
            if (list.size() != 0) {
                return list;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new ArrayList<>();
    }

    public int deleteMobileDetailslByMobileOfferId(int id) {
        try {
            DeleteBuilder<MobileDetailsOffer, Integer> offerDetailsIntegerDeleteBuilder =
                    getBaseDAO().deleteBuilder();
            offerDetailsIntegerDeleteBuilder.where().eq(MobileDetailsOffer.MOBILE_OFFER_ID, id);
            return offerDetailsIntegerDeleteBuilder.delete();
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return 0;
    }


//    public MobileOffer getMobileOfferById(int id) {
//        try {
//            List<MobileOffer> list = getBaseDAO().queryBuilder().where().eq(MobileOffer.MOBILE_OFFER_ID, id).query();
//            if (list.size() != 0) {
//                return list.get(0);
//            }
//        } catch (Throwable e) {
//            Log.e(TAG, "can't find row", e);
//        }
//        return null;
//    }
//
//    public Integer getMobileOfferId(MobileOffer mobileOffer) {
//        try {
//            return getBaseDAO().extractId(mobileOffer);
//        } catch (Throwable e) {
//            Log.e(TAG, "can't extract id", e);
//        }
//        return null;
//    }
}
