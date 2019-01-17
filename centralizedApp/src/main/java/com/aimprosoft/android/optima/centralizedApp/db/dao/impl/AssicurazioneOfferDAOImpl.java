package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;


import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.AssicurazioneOffer;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssicurazioneOfferDAOImpl extends AbstractDAOImpl<AssicurazioneOffer> {
    public AssicurazioneOfferDAOImpl() {
        super(AssicurazioneOffer.class);
    }

    public void deleteAssicurazioneOfferById(int id) {
        try {
            DeleteBuilder<AssicurazioneOffer, Integer> assicurazioneOfferIntegerDeleteBuilder =
                    getBaseDAO().deleteBuilder();
            assicurazioneOfferIntegerDeleteBuilder.where().eq(AssicurazioneOffer.ASSICURAZIONE_ID, id);
            assicurazioneOfferIntegerDeleteBuilder.delete();
        } catch (SQLException e) {
            Log.e(getClass().getSimpleName(), "can't delete row", e);
        }
    }

    public List<AssicurazioneOffer> getAssicurazioneByOfferId(int offerId) {
        try {
            List<AssicurazioneOffer> list = getBaseDAO().queryBuilder().where().eq(AssicurazioneOffer.OFFER_ID, offerId).query();
            if (list.size() != 0) {
                return list;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new ArrayList<>();
    }

    public AssicurazioneOffer getAssicurazioneById(int id) {
        try {
            AssicurazioneOffer assicurazioneOffer = getBaseDAO().queryBuilder().where().eq(AssicurazioneOffer.ASSICURAZIONE_ID, id).queryForFirst();
            if (assicurazioneOffer != null) {
                return assicurazioneOffer;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new AssicurazioneOffer();
    }

    public void deleteAssicurazioneByOfferId(int offerId) {
        try {
            DeleteBuilder<AssicurazioneOffer, Integer> assicurazioneOfferIntegerDeleteBuilder =
                    getBaseDAO().deleteBuilder();
            assicurazioneOfferIntegerDeleteBuilder.where().eq(AssicurazioneOffer.OFFER_ID, offerId);
            assicurazioneOfferIntegerDeleteBuilder.delete();
        } catch (SQLException e) {
            Log.e(TAG, "can't delete row", e);
        }
    }

}
