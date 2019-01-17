package com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.configuratoreEngineDAO;

import android.database.Cursor;

import com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.ConfiguratoreEngineDBHelper;
import com.aimprosoft.android.optima.centralizedApp.db.entity.LineTypes;

import java.util.List;

public class LineTypeDAOImpl extends ConfiguratoreEngineDBHelper {

    public final static String LINEA_RTG_DESC = "RTG";
    public final static String LINEA_ISDN_DESC = "ISDN";

    public enum LineaType {
        LINEA_RTG(LINEA_RTG_DESC),
        LINEA_ISDN(LINEA_ISDN_DESC);

        private String lineaDesc;

        LineaType(String lineaDesc) {
            this.lineaDesc = lineaDesc;
        }

        public String getLineaDesc() {
            return lineaDesc;
        }
    }

    public static List getLineTypeDescsByType(int campagnaId, String lineType) {
        String query = "SELECT * FROM cpi_tipilinee WHERE ctl_desc = '" + lineType +
                "' AND ctl_idCampagna = " + campagnaId;
        Cursor cursor = executeCursor(query);
        List<LineTypes> list = LineTypes.getListFromCursor(cursor);
        cursor.close();
        return list;
    }

    public static LineTypes getLineTypeByIdAndCampagnaId(int campagnaId, int id) {
        String query = "SELECT * FROM cpi_tipilinee WHERE ctl_id = '" + id +
                "' AND ctl_idCampagna = " + campagnaId;
        Cursor cursor = executeCursor(query);
        LineTypes lineTypes = LineTypes.getLineTypeFromCursor(cursor);
        cursor.close();
        return lineTypes;
    }

    public static LineTypes getLineTypeById(int id) {
        String query = "SELECT * FROM cpi_tipilinee WHERE ctl_id = " + id;
        Cursor cursor = executeCursor(query);
        LineTypes lineTypes = LineTypes.getLineTypeFromCursor(cursor);
        cursor.close();
        return lineTypes;
    }

    public static LineTypes getLineTypeByDesc(int campagnaId, String det) {
        String query = "SELECT * FROM cpi_tipilinee WHERE ctl_det = '" + det +
                "' AND ctl_idCampagna = " + campagnaId;
        Cursor cursor = executeCursor(query);
        LineTypes lineTypes = LineTypes.getLineTypeFromCursor(cursor);
        cursor.close();
        return lineTypes;
    }
}
