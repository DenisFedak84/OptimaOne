package com.aimprosoft.android.optima.centralizedApp.db.entity;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class MobileBundle extends BaseEntity {
    private float idProdotto;
    private String descrizioneProdotto;
    private String tipoprodotto;
    private String pianoTariffario;
    private String idOpzione;
    private float idPianoTariffario;
    private float idOpzione1;
    private float min;
    private float sms;
    private float giga;
    private float idCampagna;
    private float costo;
    private float costoIva;
    private float costoAttivazione;
    private String dataInizioValidita;
    private String dataFineValidita;
    private int pacchettoPromo;
    private int id;

    public MobileBundle() {
    }

    public float getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(float idProdotto) {
        this.idProdotto = idProdotto;
    }

    public String getDescrizioneProdotto() {
        return descrizioneProdotto;
    }

    public void setDescrizioneProdotto(String descrizioneProdotto) {
        this.descrizioneProdotto = descrizioneProdotto;
    }

    public String getTipoprodotto() {
        return tipoprodotto;
    }

    public void setTipoprodotto(String tipoprodotto) {
        this.tipoprodotto = tipoprodotto;
    }

    public String getPianoTariffario() {
        return pianoTariffario;
    }

    public void setPianoTariffario(String pianoTariffario) {
        this.pianoTariffario = pianoTariffario;
    }

    public String getIdOpzione() {
        return idOpzione;
    }

    public void setIdOpzione(String idOpzione) {
        this.idOpzione = idOpzione;
    }

    public float getIdPianoTariffario() {
        return idPianoTariffario;
    }

    public void setIdPianoTariffario(float idPianoTariffario) {
        this.idPianoTariffario = idPianoTariffario;
    }

    public float getIdOpzione1() {
        return idOpzione1;
    }

    public void setIdOpzione1(float idOpzione1) {
        this.idOpzione1 = idOpzione1;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getSms() {
        return sms;
    }

    public void setSms(float sms) {
        this.sms = sms;
    }

    public float getGiga() {
        return giga;
    }

    public void setGiga(float giga) {
        this.giga = giga;
    }

    public float getIdCampagna() {
        return idCampagna;
    }

    public void setIdCampagna(float idCampagna) {
        this.idCampagna = idCampagna;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public float getCostoIva() {
        return costoIva;
    }

    public void setCostoIva(float costoIva) {
        this.costoIva = costoIva;
    }

    public float getCostoAttivazione() {
        return costoAttivazione;
    }

    public void setCostoAttivazione(float costoAttivazione) {
        this.costoAttivazione = costoAttivazione;
    }

    public String getDataInizioValidita() {
        return dataInizioValidita;
    }

    public void setDataInizioValidita(String dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }

    public String getDataFineValidita() {
        return dataFineValidita;
    }

    public void setDataFineValidita(String dataFineValidita) {
        this.dataFineValidita = dataFineValidita;
    }

    public int getPacchettoPromo() {
        return pacchettoPromo;
    }

    public void setPacchettoPromo(int pacchettoPromo) {
        this.pacchettoPromo = pacchettoPromo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static List<MobileBundle> getListFromCursor(Cursor cursor) {
        List<MobileBundle> list = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(parseMobileBundle(cursor));
            }
        }
        return list;
    }

    private static MobileBundle parseMobileBundle(Cursor cursor) {
        MobileBundle mobileBundle = null;
        try {
            mobileBundle = new MobileBundle();
            mobileBundle.setId(cursor.getInt(0));
            mobileBundle.setIdProdotto(cursor.getFloat(1));
            mobileBundle.setDescrizioneProdotto(cursor.getString(2));
            mobileBundle.setIdPianoTariffario(cursor.getFloat(2));
            mobileBundle.setTipoprodotto(cursor.getString(3));
            mobileBundle.setPianoTariffario(cursor.getString(4));
            mobileBundle.setIdOpzione(cursor.getString(5));
            mobileBundle.setIdOpzione1(cursor.getFloat(5));
            mobileBundle.setPacchettoPromo(cursor.getInt(6));
            mobileBundle.setMin(cursor.getFloat(7));
            mobileBundle.setSms(cursor.getFloat(8));
            mobileBundle.setGiga(cursor.getFloat(9));
            mobileBundle.setIdCampagna(cursor.getFloat(10));
            mobileBundle.setCosto(cursor.getFloat(11));
            mobileBundle.setCostoIva(cursor.getFloat(12));
            mobileBundle.setCostoAttivazione(cursor.getFloat(13));
            mobileBundle.setDataInizioValidita(cursor.getString(14));
            mobileBundle.setDataFineValidita(cursor.getString(15));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mobileBundle;
    }

    public static MobileBundle getMobileBundleFromCursor(Cursor cursor) {
        MobileBundle mobileBundle;
        if (cursor != null && cursor.moveToNext()) {
            mobileBundle = parseMobileBundle(cursor);
        } else {
            mobileBundle = new MobileBundle();
        }
        return mobileBundle;
    }

    @Override
    public String toString() {
        return descrizioneProdotto;
    }
}
