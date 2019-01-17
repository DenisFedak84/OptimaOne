package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.LineTypes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LineTypesDAOImpl extends AbstractDAOImpl<LineTypes> {

    public LineTypesDAOImpl() {
        super(LineTypes.class);
    }

    public List getLineTypesListByLineAndConfiguratorType(String lineType, String configuratorType) {
        try {
            List list = getBaseDAO().queryBuilder().where().eq(LineTypes.LINE_DESC, lineType).
                    and().eq(LineTypes.SYSTEM, configuratorType).query();
            if (list.size() != 0) {
                return list;
            }
        } catch (SQLException e) {
            Log.e("LineType", "can't get info from base", e);
        }
        return new ArrayList();
    }

    public LineTypes getLineTypeByLineAndConfiguratorType(String lineDesc, String configuratorType) {
        try {
            LineTypes lineTypes = getBaseDAO().queryBuilder().where().like(LineTypes.LINE_DET, lineDesc.trim()).
                    and().eq(LineTypes.SYSTEM, configuratorType).queryForFirst();
            if (lineTypes!=null) {
                return lineTypes;
            }
        } catch (SQLException e) {
            Log.e("LineType", "can't get info from base", e);
        }
        return new LineTypes();
    }

    public LineTypes getLineTypesById(int id) {
        try {
            List<LineTypes> lineTypeses = getBaseDAO().queryForEq(LineTypes.LINE_ID, id);
            if (lineTypeses.size() != 0) {
                return lineTypeses.get(0);
            }
        } catch (Throwable e) {
            Log.e(TAG, "can't find row");
        }
        return new LineTypes();
    }
}
