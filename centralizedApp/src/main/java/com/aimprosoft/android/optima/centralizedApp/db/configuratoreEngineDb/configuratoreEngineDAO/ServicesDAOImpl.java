package com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.configuratoreEngineDAO;

import android.app.Service;
import android.database.Cursor;

import com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.ConfiguratoreEngineDBHelper;
import com.aimprosoft.android.optima.centralizedApp.db.entity.LineTypes;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Services;
import com.aimprosoft.android.optima.centralizedApp.db.entity.TipoOpzioni;

import java.util.List;

public class ServicesDAOImpl extends ConfiguratoreEngineDBHelper {

    public final static String IP_AGGIUNTIVI_ID = "1";
    public final static String SERVIZI_AGGIUNTIVI_ID = "2";
    public final static String ROUTER_ID = "3";
    public final static String LINEA_SOLO_DATI_LNA_ID = "4";
    public final static String NUMERO_CANALI_ID = "5";

    public enum TipoOpzioni {
        IP_AGGIUNTIVI(IP_AGGIUNTIVI_ID),
        SERVIZI_AGGIUNTIVI(SERVIZI_AGGIUNTIVI_ID),
        ROUTER(ROUTER_ID),
        LINEA_SOLO_DATI_LNA(LINEA_SOLO_DATI_LNA_ID),
        NUMERO_CANALI(NUMERO_CANALI_ID);

        private String ctoId;

        TipoOpzioni(String ctoId) {
            this.ctoId = ctoId;
        }

        public String getCtoId() {
            return ctoId;
        }

        public void setCtoId(String ctoId) {
            this.ctoId = ctoId;
        }
    }

    public static List<Services> getServicesByFollowingParams(int campagnaId, String lineType) {
        String query = "SELECT * FROM cpi_opzioni WHERE cop_tipoLinea = '" + lineType +
                "' AND cop_idCampagna = " + campagnaId + " AND cop_vendita = 1 order by cop_id";
        Cursor cursor = executeCursor(query);
        List<Services> list = Services.getListFromCursor(cursor);
        cursor.close();
        return list;
    }

    public static List<Services> getServicesByFollowingParams(int campagnaId, String lineType, TipoOpzioni tipoOpzioni) {
        String query = "SELECT * FROM cpi_opzioni WHERE cop_tipoLinea = '" + lineType +
                "' AND cop_idCampagna = " + campagnaId + " AND cop_tipoOpzione = " + tipoOpzioni.getCtoId()
                + " AND cop_vendita = 1";
        Cursor cursor = executeCursor(query);
        List<Services> list = Services.getListFromCursor(cursor);
        cursor.close();
        return list;
    }

    public static List<Services> getServicesByFollowingParams(int campagnaId, TipoOpzioni tipoOpzioni) {
        String query = "SELECT * FROM cpi_opzioni WHERE cop_idCampagna = " + campagnaId + " AND cop_tipoOpzione = " + tipoOpzioni.getCtoId()
                + " AND cop_vendita = 1";
        Cursor cursor = executeCursor(query);
        List<Services> list = Services.getListFromCursor(cursor);
        cursor.close();
        return list;
    }

    public static List<Services> getServicesByIdRange(String ids) {
        String query = "SELECT * FROM cpi_opzioni WHERE cop_id in (" + ids + ") AND cop_vendita = 1;";
        Cursor cursor = executeCursor(query);
        List<Services> list = Services.getListFromCursor(cursor);
        cursor.close();
        return list;
    }

    public static Services getServicesByDesc(int campagnaId, String desc) {
        String query = "SELECT * FROM cpi_opzioni WHERE cop_idCampagna = " + campagnaId + " AND cop_desc = '" + desc + "' AND cop_vendita = 1;";
        Cursor cursor = executeCursor(query);
        Services service = Services.getServicesFromCursor(cursor);
        cursor.close();
        return service;
    }

    public static Services getServicesByDesc(int campagnaId, TipoOpzioni tipoOpzioni) {
        String query = "SELECT * FROM cpi_opzioni WHERE cop_idCampagna = " + campagnaId + " AND cop_tipoOpzione = '"
                + tipoOpzioni.getCtoId() + "' AND cop_vendita = 1;";
        Cursor cursor = executeCursor(query);
        Services service = Services.getServicesFromCursor(cursor);
        cursor.close();
        return service;
    }

    public static Services getServicesByDescAndAdditionalParams(int campagnaId, String lineType, String desc) {
        String query = "SELECT * FROM cpi_opzioni WHERE cop_idCampagna = " + campagnaId + " AND cop_desc = '" + desc +
                "' AND cop_tipoLinea = " + lineType + " AND cop_vendita = 1";
        Cursor cursor = executeCursor(query);
        Services service = Services.getServicesFromCursor(cursor);
        cursor.close();
        return service;
    }
}
