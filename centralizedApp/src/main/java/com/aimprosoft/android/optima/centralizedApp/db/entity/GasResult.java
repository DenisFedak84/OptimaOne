package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "GasResult")
public class GasResult extends BaseEntity {

    public static final String GAS_OFFER_ID = "gasOfferId";
    public static final String RESULT_OFFER_ID = "resultOfferId";
    public static final String PDR_NUMBER = "pdrNumber";
    public static final String TAGLIA_MESE = "tagliaMese";
    public static final String PREZZO = "prezzo";
    public static final String PREZZO_CON_IVA = "prezzoConIva";
    public static final String SFORAMENTO = "sforamento";
    public static final String MANCATO_CONSUMO = "mancatoConsumo";
    public static final String CODICE_TARIFFA = "codiceTariffa";
    public static final String GAS_PERC = "gasPerc";
    public static final String COMUNE = "comune";

    public GasResult() {
    }

    @DatabaseField(generatedId = true, columnName = GAS_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int gasOfferId;

    @DatabaseField(columnName = RESULT_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int resultOfferId;

    @DatabaseField(columnName = PDR_NUMBER, canBeNull = true, dataType = DataType.STRING)
    private String pdrNumber;

    @DatabaseField(columnName = TAGLIA_MESE, canBeNull = true, dataType = DataType.DOUBLE)
    private double tagliaMese;

    @DatabaseField(columnName = PREZZO_CON_IVA, canBeNull = true, dataType = DataType.DOUBLE)
    private double prezzoConIva;

    @DatabaseField(columnName = PREZZO, canBeNull = true, dataType = DataType.DOUBLE)
    private double prezzo;

    @DatabaseField(columnName = SFORAMENTO, canBeNull = true, dataType = DataType.DOUBLE)
    private double sforamento;

    @DatabaseField(columnName = MANCATO_CONSUMO, canBeNull = true, dataType = DataType.DOUBLE)
    private double mancatoConsumo;

    @DatabaseField(columnName = CODICE_TARIFFA, canBeNull = true, dataType = DataType.STRING)
    private String codiceTariffa;

    @DatabaseField(columnName = GAS_PERC, canBeNull = true, dataType = DataType.DOUBLE)
    private double gasPerc;

    @DatabaseField(columnName = COMUNE, canBeNull = true, dataType = DataType.STRING)
    private String comune;

    public int getResultOfferId() {
        return resultOfferId;
    }

    public void setResultOfferId(int resultOfferId) {
        this.resultOfferId = resultOfferId;
    }

    public String getPdrNumber() {
        return pdrNumber;
    }

    public void setPdrNumber(String pdrNumber) {
        this.pdrNumber = pdrNumber;
    }

    public int getGasOfferId() {
        return gasOfferId;
    }

    public void setGasOfferId(int gasOfferId) {
        this.gasOfferId = gasOfferId;
    }

    public double getTagliaMese() {
        return tagliaMese;
    }

    public void setTagliaMese(double tagliaMese) {
        this.tagliaMese = tagliaMese;
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

    public double getSforamento() {
        return sforamento;
    }

    public void setSforamento(double sforamento) {
        this.sforamento = sforamento;
    }

    public double getMancatoConsumo() {
        return mancatoConsumo;
    }

    public void setMancatoConsumo(double mancatoConsumo) {
        this.mancatoConsumo = mancatoConsumo;
    }

    public String getCodiceTariffa() {
        return codiceTariffa;
    }

    public void setCodiceTariffa(String codiceTariffa) {
        this.codiceTariffa = codiceTariffa;
    }

    public double getGasPerc() {
        return gasPerc;
    }

    public void setGasPerc(double gasPerc) {
        this.gasPerc = gasPerc;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }
}
