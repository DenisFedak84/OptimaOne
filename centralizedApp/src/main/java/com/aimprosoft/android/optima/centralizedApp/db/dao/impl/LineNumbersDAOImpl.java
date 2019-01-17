package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.LineNumbers;

import java.util.List;

public class LineNumbersDAOImpl extends AbstractDAOImpl<LineNumbers> {

    public LineNumbersDAOImpl() {
        super(LineNumbers.class);
    }

    public LineNumbers getLineNumbersById(int id) {
        try {
            List<LineNumbers> lineNumbers = getBaseDAO().queryForEq(LineNumbers.LINE_NUMBER_ID, id);
            if (lineNumbers.size() != 0) {
                return lineNumbers.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "don't find row");
        }
        return new LineNumbers();
    }
}
