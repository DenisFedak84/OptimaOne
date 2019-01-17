package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ADSLResult")
public class ADSLResult extends BaseEntity {

    public static final String ADSL_OFFER_ID = "adslOfferId";
    public static final String RESULT_OFFER_ID = "resultOfferId";
    public static final String TIPOLOGIA_ADSL = "tipologiaAdsl";
    public static final String PREZZO = "prezzo";
    public static final String PREZZO_CON_IVA = "prezzoConIva";
    public static final String ADSL_PERC = "adslPerc";
    public static final String ADSL_NUMBER = "adslNumber";

    public ADSLResult() {
    }

    @DatabaseField(generatedId = true, columnName = ADSL_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int adslOfferId;

    @DatabaseField(columnName = RESULT_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int resultOfferId;

    @DatabaseField(columnName = TIPOLOGIA_ADSL, canBeNull = true, dataType = DataType.STRING)
    private String tipologiaAdsl;

    @DatabaseField(columnName = PREZZO_CON_IVA, canBeNull = true, dataType = DataType.DOUBLE)
    private double prezzoConIva;

    @DatabaseField(columnName = PREZZO, canBeNull = true, dataType = DataType.DOUBLE)
    private double prezzo;

    @DatabaseField(columnName = ADSL_PERC, canBeNull = true, dataType = DataType.DOUBLE)
    private double adslPerc;

    @DatabaseField(columnName = ADSL_NUMBER, canBeNull = true, dataType = DataType.STRING)
    private String adslNumber;

    public int getAdslOfferId() {
        return adslOfferId;
    }

    public int getResultOfferId() {
        return resultOfferId;
    }

    public void setResultOfferId(int resultOfferId) {
        this.resultOfferId = resultOfferId;
    }

    public void setAdslOfferId(int adslOfferId) {
        this.adslOfferId = adslOfferId;
    }

    public String getTipologiaAdsl() {
        return tipologiaAdsl;
    }

    public void setTipologiaAdsl(String tipologiaAdsl) {
        this.tipologiaAdsl = tipologiaAdsl;
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

    public double getAdslPerc() {
        return adslPerc;
    }

    public void setAdslPerc(double adslPerc) {
        this.adslPerc = adslPerc;
    }

    public String getAdslNumber() {
        return adslNumber;
    }

    public void setAdslNumber(String adslNumber) {
        this.adslNumber = adslNumber;
    }
}
