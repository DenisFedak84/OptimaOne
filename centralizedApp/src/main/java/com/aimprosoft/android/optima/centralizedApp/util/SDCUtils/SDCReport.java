package com.aimprosoft.android.optima.centralizedApp.util.SDCUtils;

import com.aimprosoft.android.optima.centralizedApp.db.entity.BaseEntity;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;

import java.util.List;
import java.util.Map;

public interface SDCReport<T extends BaseEntity> {

    String SDC = "sdc";
    String EMAILS = "emails";
    String MONTHLY_CONSUMPTION = "monthlyConsumption";
    String TOWN = "town";
    String POTENZA = "potenza";

    List<Map<String, Object>> getSingleOfferSDCDataList(Offer offer, T serviceOfferDetails);

    Map<String, Object> getFullSDCDataMap(Offer offer, List<String> emails);
}
