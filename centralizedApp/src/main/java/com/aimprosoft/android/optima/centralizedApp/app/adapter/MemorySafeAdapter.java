package com.aimprosoft.android.optima.centralizedApp.app.adapter;

import com.aimprosoft.android.optima.centralizedApp.db.entity.BaseEntity;

import java.util.List;

public interface MemorySafeAdapter<T extends BaseEntity> {
    void clearAdapter();

    void notifyDataSetUpdated(List<T> list);
}

