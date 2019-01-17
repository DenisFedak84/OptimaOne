package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.db.entity.TlcDetailsOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WlrOfferDetails;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WlrOfferDetailsDAOImpl extends AbstractDAOImpl<WlrOfferDetails> {
    public WlrOfferDetailsDAOImpl() {
        super(WlrOfferDetails.class);
    }

    public void deleteWlrOfferDetailsByWlrOfferId(int id) {
        try {
            DeleteBuilder<WlrOfferDetails, Integer> wlrOfferDetailsIntegerDeleteBuilder =
                    getBaseDAO().deleteBuilder();
            wlrOfferDetailsIntegerDeleteBuilder.where().eq(WlrOfferDetails.WLR_OFFER_DETT_ID, id);
            wlrOfferDetailsIntegerDeleteBuilder.delete();
        } catch (SQLException e) {
            Log.e(TAG, "can't find row", e);
        }
    }

    public void deleteWlrOfferDetailsByMainWlrOfferId(int id) {
        try {
            DeleteBuilder<WlrOfferDetails, Integer> wlrOfferDetailsIntegerDeleteBuilder =
                    getBaseDAO().deleteBuilder();
            wlrOfferDetailsIntegerDeleteBuilder.where().eq(WlrOfferDetails.WLR_OFFER_ID, id);
            wlrOfferDetailsIntegerDeleteBuilder.delete();
        } catch (SQLException e) {
            Log.e(TAG, "can't find row", e);
        }
    }

    public void deleteWlrOfferDetailsByMainWlrOfferIdAndLineeName(int id, String wlrName) {
        try {
            DeleteBuilder<WlrOfferDetails, Integer> wlrOfferDetailsIntegerDeleteBuilder =
                    getBaseDAO().deleteBuilder();
            wlrOfferDetailsIntegerDeleteBuilder.where().eq(WlrOfferDetails.WLR_OFFER_ID, id)
                    .and().eq(WlrOfferDetails.WLR_NAME, wlrName);
            wlrOfferDetailsIntegerDeleteBuilder.delete();
        } catch (SQLException e) {
            Log.e(TAG, "can't find row", e);
        }
    }


    public boolean isExistingOffersUsed(int mainOfferId) {
        boolean result = false;
        try {
            List<WlrOfferDetails> list = getBaseDAO().queryBuilder().where().eq(WlrOfferDetails.WLR_OFFER_ID, mainOfferId).and().eq(WlrOfferDetails.IS_EXISTING_CLIENT_OFFER, true).query();
            if (list.size() != 0) result = true;
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return result;
    }

    public boolean isVoipLinesUsedForOfferId(int mainOfferId) {
        boolean result = false;
        try {
            if (mainOfferId != 0) {
                List<WlrOfferDetails> list = getBaseDAO().queryBuilder().where().eq(WlrOfferDetails.WLR_OFFER_ID, mainOfferId).and()
                        .eq(WlrOfferDetails.RETE, Constants.VOIP).query();
                if (list.size() != 0) result = true;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return result;
    }

    public boolean isServicesUsedByClient(int mainOfferId) {
        boolean result = false;
        try {
            List<WlrOfferDetails> list = getBaseDAO().queryBuilder().where().eq(WlrOfferDetails.WLR_OFFER_ID, mainOfferId).and().notIn(WlrOfferDetails.SERVICES_ID, "").query();
            if (list.size() != 0) result = true;
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return result;
    }


    public List<WlrOfferDetails> getWlrOfferDetailWithServicesUsed(int mainOfferId) {
        try {
            List<WlrOfferDetails> list = getBaseDAO().queryBuilder().where().eq(WlrOfferDetails.WLR_OFFER_ID, mainOfferId).and().notIn(WlrOfferDetails.SERVICES_ID, "").query();
            if (list.size() != 0) return list;
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new ArrayList<>();
    }

    public List<WlrOfferDetails> getNewClientDetailsByOfferId(int id, boolean isParent) {
        try {
            List<WlrOfferDetails> list = getBaseDAO().queryBuilder().where().eq(WlrOfferDetails.WLR_OFFER_ID, id)
                    .and().eq(TlcDetailsOffer.IS_EXISTING_CLIENT_OFFER, false).and().eq(WlrOfferDetails.PARENT, isParent).query();
            if (list.size() != 0) {
                return list;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new ArrayList<>();
    }

    public List<WlrOfferDetails> getOldClientDetailsByOfferId(int id, boolean isParent) {
        try {
            List<WlrOfferDetails> list = getBaseDAO().queryBuilder().where().eq(WlrOfferDetails.WLR_OFFER_ID, id)
                    .and().eq(TlcDetailsOffer.IS_EXISTING_CLIENT_OFFER, true).and().eq(WlrOfferDetails.PARENT, isParent).query();
            if (list.size() != 0) {
                return list;
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new ArrayList<>();
    }

    public List<WlrOfferDetails> getWlrOfferDetailsByWlrOfferId(int id, boolean isParent) {
        try {
            List<WlrOfferDetails> offerteList = getBaseDAO().queryBuilder().where().eq(WlrOfferDetails.WLR_OFFER_ID, id)
                    .and().eq(WlrOfferDetails.PARENT, isParent).query();
            if (offerteList.size() != 0) {
                return offerteList;
            }
        } catch (SQLException e) {
            Log.e(TAG, "can't find row", e);
        }
        return new ArrayList<>();
    }

    public WlrOfferDetails getWlrOfferDetailsById(int id) {
        try {
            List<WlrOfferDetails> list = getBaseDAO().queryBuilder().where().eq(WlrOfferDetails.WLR_OFFER_DETT_ID, id).query();
            if (list.size() != 0) {
                return list.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row", e);
        }
        return new WlrOfferDetails();
    }

    public List<WlrOfferDetails> getWlrOfferDetailsByPhoneNumber(int parentWlrOfferId, String phoneNumber) {
        try {
            List<WlrOfferDetails> offerteList = getBaseDAO().queryBuilder().where().eq(WlrOfferDetails.WLR_NAME, phoneNumber)
                    .and().eq(WlrOfferDetails.WLR_OFFER_ID, parentWlrOfferId).query();
            if (offerteList.size() != 0) {
                return offerteList;
            }
        } catch (SQLException e) {
            Log.e(TAG, "can't find row", e);
        }
        return new ArrayList<>();
    }
}
