package com.aimprosoft.android.optima.centralizedApp.db.entity;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class TipoOpzioni extends BaseEntity {

    private String ctoId;
    private String ctoDesc;
    private String ctoCodice;

    public TipoOpzioni(String ctoId, String ctoDesc, String ctoCodice) {
        this.ctoId = ctoId;
        this.ctoDesc = ctoDesc;
        this.ctoCodice = ctoCodice;
    }

    public TipoOpzioni() {
    }

    public String getCtoId() {
        return ctoId;
    }

    public void setCtoId(String ctoId) {
        this.ctoId = ctoId;
    }

    public String getCtoDesc() {
        return ctoDesc;
    }

    public void setCtoDesc(String ctoDesc) {
        this.ctoDesc = ctoDesc;
    }

    public String getCtoCodice() {
        return ctoCodice;
    }

    public void setCtoCodice(String ctoCodice) {
        this.ctoCodice = ctoCodice;
    }

    /**
     * For using with dbLite.db and ConfiguratoreEngine jar
     **/
    public static List<TipoOpzioni> getListFromCursor(Cursor cursor) {
        List<TipoOpzioni> list = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(parseTipoOpzione(cursor));
            }
        }
        return list;
    }

    private static TipoOpzioni parseTipoOpzione(Cursor cursor) {
        TipoOpzioni tipoOpzioni = null;
        try {
            tipoOpzioni = new TipoOpzioni();
            tipoOpzioni.setCtoId(cursor.getString(0));
            tipoOpzioni.setCtoDesc(cursor.getString(1));
            tipoOpzioni.setCtoCodice(cursor.getString(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tipoOpzioni;
    }

    public static TipoOpzioni getTipoOpzioneFromCursor(Cursor cursor) {
        TipoOpzioni tipoOpzioni;
        if (cursor != null && cursor.moveToNext()) {
            tipoOpzioni = parseTipoOpzione(cursor);
        } else {
            tipoOpzioni = new TipoOpzioni();
        }
        return tipoOpzioni;
    }
}
