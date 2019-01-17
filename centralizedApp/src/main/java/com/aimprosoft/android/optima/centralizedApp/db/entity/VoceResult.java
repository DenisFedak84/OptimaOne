package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "VoceResult")
public class VoceResult extends BaseEntity {

    public static final String VOCE_OFFER_ID = "voceOfferId";
    public static final String RESULT_OFFER_ID = "resultOfferId";
    public static final String MINUTI_VERSO_FISSO = "minutiVersoFisso";
    public static final String MINUTI_VERSO_MOBILE = "minutiVersoMobile";
    public static final String PREZZO = "prezzo";
    public static final String PREZZO_CON_IVA = "prezzoConIva";
    public static final String VOCE_PERC = "vocePerc";
    public static final String VOCE_NUMBER = "voceNumber";

    public VoceResult() {
    }

    @DatabaseField(generatedId = true, columnName = VOCE_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int voceOfferId;

    @DatabaseField(columnName = RESULT_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int resultOfferId;

    @DatabaseField(columnName = MINUTI_VERSO_FISSO, canBeNull = true, dataType = DataType.STRING)
    private String minutiVersoFisso;

    @DatabaseField(columnName = MINUTI_VERSO_MOBILE, canBeNull = true, dataType = DataType.STRING)
    private String minutiVersoMobile;

    @DatabaseField(columnName = VOCE_NUMBER, canBeNull = true, dataType = DataType.STRING)
    private String voceNumber;

    @DatabaseField(columnName = PREZZO_CON_IVA, canBeNull = true, dataType = DataType.DOUBLE)
    private double prezzoConIva;

    @DatabaseField(columnName = PREZZO, canBeNull = true, dataType = DataType.DOUBLE)
    private double prezzo;

    @DatabaseField(columnName = VOCE_PERC, canBeNull = true, dataType = DataType.DOUBLE)
    private double vocePerc;

    public int getResultOfferId() {
        return resultOfferId;
    }

    public void setResultOfferId(int resultOfferId) {
        this.resultOfferId = resultOfferId;
    }

    public String getVoceNumber() {
        return voceNumber;
    }

    public void setVoceNumber(String voceNumber) {
        this.voceNumber = voceNumber;
    }

    public int getVoceOfferId() {
        return voceOfferId;
    }

    public void setVoceOfferId(int voceOfferId) {
        this.voceOfferId = voceOfferId;
    }

    public String getMinutiVersoFisso() {
        return minutiVersoFisso;
    }

    public void setMinutiVersoFisso(String minutiVersoFisso) {
        this.minutiVersoFisso = minutiVersoFisso;
    }

    public String getMinutiVersoMobile() {
        return minutiVersoMobile;
    }

    public void setMinutiVersoMobile(String minutiVersoMobile) {
        this.minutiVersoMobile = minutiVersoMobile;
    }

    public double getPrezzoConIva() {
        return prezzoConIva;
    }

    public void setPrezzoConIva(double prezzoConIva) {
        this.prezzoConIva = prezzoConIva;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public double getVocePerc() {
        return vocePerc;
    }

    public void setVocePerc(double vocePerc) {
        this.vocePerc = vocePerc;
    }
}
