package com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.configuratoreEngineDAO;

import android.database.Cursor;

import com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.ConfiguratoreEngineDBHelper;
import com.aimprosoft.android.optima.centralizedApp.db.entity.LineTypes;
import com.aimprosoft.android.optima.centralizedApp.db.entity.TipoOpzioni;

import java.util.List;

public class TipoOpzioniDAOImpl extends ConfiguratoreEngineDBHelper {

    public static TipoOpzioni getTipoOpzioniById(ServicesDAOImpl.TipoOpzioni opzioni){
        String query = "SELECT * FROM cpi_tipoOpzioni WHERE cto_id = " + opzioni.getCtoId();
        Cursor cursor = executeCursor(query);
        TipoOpzioni tipoOpzioni = TipoOpzioni.getTipoOpzioneFromCursor(cursor);

        return tipoOpzioni;
    }
}
