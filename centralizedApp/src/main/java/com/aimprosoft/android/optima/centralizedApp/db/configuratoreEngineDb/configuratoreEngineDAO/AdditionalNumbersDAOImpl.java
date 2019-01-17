package com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.configuratoreEngineDAO;

import android.database.Cursor;

import com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.ConfiguratoreEngineDBHelper;
import com.aimprosoft.android.optima.centralizedApp.db.entity.AdditionalNumbers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.LineTypes;

import java.util.List;

public class AdditionalNumbersDAOImpl extends ConfiguratoreEngineDBHelper {

    public static List<AdditionalNumbers> getAdditionalNumbersByCampagnaId(int campagnaId){
        String query = "SELECT * FROM cpi_NumeriAggiuntivi WHERE cna_idCampagna = " + campagnaId;
        Cursor cursor = executeCursor(query);
        List<AdditionalNumbers> list = AdditionalNumbers.getListFromCursor(cursor);
        cursor.close();
        return list;
    }
}
