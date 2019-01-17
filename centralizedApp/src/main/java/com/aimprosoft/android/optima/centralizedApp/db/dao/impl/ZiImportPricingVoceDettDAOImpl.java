package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.ZiImportPricingVoceDett;

import java.util.List;

public class ZiImportPricingVoceDettDAOImpl extends AbstractDAOImpl<ZiImportPricingVoceDett> {
    public ZiImportPricingVoceDettDAOImpl() {
        super(ZiImportPricingVoceDett.class);
    }

    public List<ZiImportPricingVoceDett> getZiImportPricingVoceDettByLine(String line) {
        try {
            List<ZiImportPricingVoceDett> ziImportPricingVoceDetts = getBaseDAO().queryForEq(ZiImportPricingVoceDett.DIALER_PADRE, line);
            if (!ziImportPricingVoceDetts.isEmpty()) {
                return ziImportPricingVoceDetts;
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return null;
    }
}
