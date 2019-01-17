package com.aimprosoft.android.optima.centralizedApp.db.entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseEntity implements Serializable, Cloneable {

    private static final DateFormat dateFormat;
    static {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    public Date convertDate(String dateStr) {
        try {
            return dateFormat.parse(dateStr);
        } catch (Throwable e) {
            return null;
        }
    }
}
