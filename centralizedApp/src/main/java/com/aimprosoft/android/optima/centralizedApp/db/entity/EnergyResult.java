package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "EnergyResult")
public class EnergyResult extends BaseEntity {

    public static final String ENERGY_OFFER_ID = "energyOfferId";
    public static final String RESULT_OFFER_ID = "resultOfferId";
    public static final String POD_NUMBER = "podNumber";
    public static final String POTENZA = "potenza";
    public static final String TAGLIA_MESE = "tagliaMese";
    public static final String PREZZO = "prezzo";
    public static final String PREZZO_CON_IVA = "prezzoConIva";
    public static final String SFORAMENTO = "sforamento";
    public static final String MANCATO_CONSUMO = "mancatoConsumo";
    public static final String CODICE_TARIFFA = "codiceTariffa";
    public static final String OPZIONE_TARIFFARIA = "opzioneTariffaria";
    public static final String ENERGY_PERC = "energyPerc";

    public EnergyResult() {
    }

    @DatabaseField(generatedId = true, columnName = ENERGY_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int energyOfferId;

    @DatabaseField(columnName = RESULT_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int resultOfferId;

    @DatabaseField(columnName = POD_NUMBER, canBeNull = true, dataType = DataType.STRING)
    private String podNumber;

    @DatabaseField(columnName = POTENZA, canBeNull = true, dataType = DataType.DOUBLE)
    private double potenza;

    @DatabaseField(columnName = TAGLIA_MESE, canBeNull = true, dataType = DataType.DOUBLE)
    private double tagliaMese;

    @DatabaseField(columnName =  PREZZO, canBeNull = true, dataType = DataType.DOUBLE)
    private double prezzo;

    @DatabaseField(columnName =  PREZZO_CON_IVA, canBeNull = true, dataType = DataType.DOUBLE)
    private double prezzoConIva;

    @DatabaseField(columnName =  SFORAMENTO, canBeNull = true, dataType = DataType.DOUBLE)
    private double sforamento;

    @DatabaseField(columnName =  MANCATO_CONSUMO, canBeNull = true, dataType = DataType.DOUBLE)
    private double mancatoConsumo;

    @DatabaseField(columnName =  CODICE_TARIFFA, canBeNull = true, dataType = DataType.STRING)
    private String codiceTariffa;

    @DatabaseField(columnName =  OPZIONE_TARIFFARIA, canBeNull = true, dataType = DataType.STRING)
    private String opzioneTariffaria;

    @DatabaseField(columnName =  ENERGY_PERC, canBeNull = true, dataType = DataType.DOUBLE)
    private double energyPerc;

    public int getResultOfferId() {
        return resultOfferId;
    }

    public void setResultOfferId(int resultOfferId) {
        this.resultOfferId = resultOfferId;
    }

    public String getPodNumber() {
        return podNumber;
    }

    public void setPodNumber(String podNumber) {
        this.podNumber = podNumber;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getEnergyOfferId() {
        return energyOfferId;
    }

    public void setEnergyOfferId(int energyOfferId) {
        this.energyOfferId = energyOfferId;
    }

    public double getPotenza() {
        return potenza;
    }

    public void setPotenza(double potenza) {
        this.potenza = potenza;
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

    public String getOpzioneTariffaria() {
        return opzioneTariffaria;
    }

    public void setOpzioneTariffaria(String opzioneTariffaria) {
        this.opzioneTariffaria = opzioneTariffaria;
    }

    public double getEnergyPerc() {
        return energyPerc;
    }

    public void setEnergyPerc(double energyPerc) {
        this.energyPerc = energyPerc;
    }
}
