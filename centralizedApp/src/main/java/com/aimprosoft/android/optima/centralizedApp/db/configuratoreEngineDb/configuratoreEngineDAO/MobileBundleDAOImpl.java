package com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.configuratoreEngineDAO;

import android.database.Cursor;

import com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.ConfiguratoreEngineDBHelper;
import com.aimprosoft.android.optima.centralizedApp.db.entity.LineTypes;
import com.aimprosoft.android.optima.centralizedApp.db.entity.MobileBundle;
import com.aimprosoft.android.optima.centralizedApp.db.entity.TipoOpzioni;

import java.util.List;

public class MobileBundleDAOImpl extends ConfiguratoreEngineDBHelper {

    public final static String TIPO_PRODOTTO_SOLO_DATI = "SoloDati";
    public final static String TIPO_PRODOTTO_MINUTI_SMS_GIGA = "MinutiSMSGIGA";

    public enum TipoProdotto {
        MINUTI_SMS_GIGA(TIPO_PRODOTTO_MINUTI_SMS_GIGA),
        SOLO_DATI(TIPO_PRODOTTO_SOLO_DATI);

        private String desc;

        TipoProdotto(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }

    public static List<MobileBundle> getMobileBundleList(int campagnaId, int pachettoPromo, TipoProdotto tipoProdotto) {
        String query = "SELECT * FROM cpi_Mobile WHERE cmo_Tipoprodotto = '" + tipoProdotto.getDesc() +
                "' AND cmo_IdCampagna = " + campagnaId + " AND cmo_descrizione = " + pachettoPromo;
        Cursor cursor = executeCursor(query);
        List<MobileBundle> list = MobileBundle.getListFromCursor(cursor);
        cursor.close();
        return list;
    }

    public static List<MobileBundle> getMobileBundleList(int campagnaId, int pachettoPromo) {
        String query = "SELECT * FROM cpi_Mobile WHERE cmo_IdCampagna = " + campagnaId +
                " AND cmo_descrizione = " + pachettoPromo;
        Cursor cursor = executeCursor(query);
        List<MobileBundle> list = MobileBundle.getListFromCursor(cursor);
        cursor.close();
        return list;
    }

    public static MobileBundle getMobileBundle(int id) {
        String query = "SELECT * FROM cpi_Mobile WHERE cmo_Id = " + id;
        Cursor cursor = executeCursor(query);
        MobileBundle bundle = MobileBundle.getMobileBundleFromCursor(cursor);
        cursor.close();
        return bundle;
    }
}
