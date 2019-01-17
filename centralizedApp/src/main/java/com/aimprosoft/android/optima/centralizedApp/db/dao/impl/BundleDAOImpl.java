package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.Bundle;
import com.j256.ormlite.stmt.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class BundleDAOImpl extends AbstractDAOImpl<Bundle> {
    public BundleDAOImpl() {
        super(Bundle.class);
    }

    public List<Bundle> getBundleByType(int bundleType) {
        try {
            return getBaseDAO().queryForEq(Bundle.BUNDLE_TYPE, bundleType);
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new ArrayList<>();
    }

    public List<Bundle> getBundleByTypeAndConfiguratorType(int bundleType, String configuratorType) {
        try {
            QueryBuilder<Bundle, Integer> q = new BundleDAOImpl().getBaseDAO().queryBuilder();
            q.where().eq(Bundle.BUNDLE_TYPE, bundleType).and().eq(Bundle.SYSTEM, configuratorType);
            q.orderBy(Bundle.BUNDLE_ID, true);
            return q.query();
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new ArrayList<>();
    }

    public Bundle getBundleById(int bundleId) {
        try {
            List<Bundle> bundles = getBaseDAO().queryForEq(Bundle.BUNDLE_ID, bundleId);
            if (bundles.size() != 0) {
                return bundles.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new Bundle();
    }


    public double getBundleCostByBundleId(int bundleId) {
        try {
            List<Bundle> bundles = getBaseDAO().queryForEq(Bundle.BUNDLE_ID, bundleId);
            if (bundles.size() != 0) {
                return bundles.get(0).getBundleCost();
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return 0;
    }

    public String getBundleDescByBundleId(int bundleId) {
        try {
            List<Bundle> bundles = getBaseDAO().queryForEq(Bundle.BUNDLE_ID, bundleId);
            if (bundles.size() != 0) {
                return bundles.get(0).getBundleDesc();
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return "";
    }

    public Bundle getBundleBySpecialParams(String bundleDesc, int bundleType, String configuratorType) {
        try {
            Bundle bundle = getBaseDAO().queryBuilder().where()
                    .eq(Bundle.BUNDLE_TYPE, bundleType).and()
                    .eq(Bundle.SYSTEM, configuratorType).and()
                    .eq(Bundle.BUNDLE_DESC, bundleDesc).queryForFirst();
            if(bundle!=null) {
                return bundle;
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new Bundle();
    }

    public Bundle getBundleBySpecialParams(String bundleDesc, int bundleType) {
        try {
            Bundle bundle = getBaseDAO().queryBuilder().where()
                    .eq(Bundle.BUNDLE_TYPE, bundleType).and()
                    .like(Bundle.BUNDLE_DESC, bundleDesc.trim()).queryForFirst();
            if(bundle!=null) {
                return bundle;
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new Bundle();
    }
}
