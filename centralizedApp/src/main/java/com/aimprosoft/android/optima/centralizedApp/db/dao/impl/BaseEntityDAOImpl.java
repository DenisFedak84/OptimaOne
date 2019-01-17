package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;


import com.aimprosoft.android.optima.centralizedApp.db.entity.BaseEntity;

public class BaseEntityDAOImpl<T extends BaseEntity> extends AbstractDAOImpl<T> {
    public BaseEntityDAOImpl(Class<T> entity) {
        super(entity);
    }

    public void deleteAll(){
//        try {
//
//
//        } catch (SQLException e) {
//            Log.e(TAG, "can't delete row", e);
//        }
    }
    public void deleteEmailAdressByDesc(String desc) {
//        try {
//
//        } catch (SQLException e) {
//            Log.e(TAG, "can't delete row", e);
//        }
    }
}
