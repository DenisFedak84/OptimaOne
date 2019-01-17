package com.aimprosoft.android.optima.centralizedApp.service;


import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.configuratoreEngineDAO.LineTypeDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.LineTypesDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.IdCampganaUtils;

import java.util.ArrayList;
import java.util.List;

public class LineTypeSpinnerService extends AbstractService<String, List> {

    private Offer offer;
    private String lineType;

    public LineTypeSpinnerService(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    public LineTypeSpinnerService(Activity activity, OnTaskCompleted onTaskCompleted, Offer offer, String lineType) {
        super(activity, onTaskCompleted);
        this.offer = offer;
        this.lineType = lineType;
    }

    @Override
    protected List doStuff(String... strings) {
        List lineTypeList = new ArrayList();
        if (offer != null && !TextUtils.isEmpty(lineType)) {
            try {
                int idCampagna = IdCampganaUtils.getOfferIdCampagnaByServiceType(IdCampganaUtils.ServiceType.VOCE, offer.getConfiguratorType(), offer.getOldTlcOfferId() != 0);
                lineTypeList = LineTypeDAOImpl.getLineTypeDescsByType(idCampagna, lineType);
                lineTypeList.add(0, "");
            } catch (Exception e) {
                Log.e(TAG, "error while executing LineTypeSpinnerService", e);
            }
        }
        return lineTypeList;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }
}


