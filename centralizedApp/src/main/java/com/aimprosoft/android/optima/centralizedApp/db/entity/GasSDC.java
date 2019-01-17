package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "GasSDC")
public class GasSDC extends BaseEntity {

    public static final String SDC_ID = "sdcId";
    public static final String GAS_DETAILS_OFFER_ID = "gasDetailsOfferId";
    public static final String CONSUMO_ANNUO = "consumoAnnuo";
    public static final String STIMA_SPESA_RIF = "stimaSpesaRif";
    public static final String DATA_FINE = "dataFine";
    public static final String DATA_INIZIO = "dataInizio";
    public static final String COSTO_TOT_SEN_IMPG = "costoTotSenImpG";

    public GasSDC() {
    }

    public GasSDC(int gasDetailsOfferId, double consumoAnnuo, double stimaSpesaRif, String dataInizio, String dataFine) {
        this.gasDetailsOfferId = gasDetailsOfferId;
        this.consumoAnnuo = consumoAnnuo;
        this.stimaSpesaRif = stimaSpesaRif;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = SDC_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int sdcId;
    @DatabaseField(columnName = GAS_DETAILS_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int gasDetailsOfferId;
    @DatabaseField(columnName = CONSUMO_ANNUO, canBeNull = false, dataType = DataType.DOUBLE)
    private double consumoAnnuo;
    @DatabaseField(columnName = STIMA_SPESA_RIF, canBeNull = false, dataType = DataType.DOUBLE)
    private double stimaSpesaRif;
    @DatabaseField(columnName = DATA_INIZIO, canBeNull = false, dataType = DataType.STRING)
    private String dataInizio;
    @DatabaseField(columnName = DATA_FINE, canBeNull = false, dataType = DataType.STRING)
    private String dataFine;
    @DatabaseField(columnName = COSTO_TOT_SEN_IMPG, canBeNull = false, dataType = DataType.DOUBLE)
    private double costoTotSenImpG;

    public double getCostoTotSenImpG() {
        return costoTotSenImpG;
    }

    public void setCostoTotSenImpG(double costoTotSenImpG) {
        this.costoTotSenImpG = costoTotSenImpG;
    }

    public String getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(String dataInizio) {
        this.dataInizio = dataInizio;
    }

    public String getDataFine() {
        return dataFine;
    }

    public void setDataFine(String dataFine) {
        this.dataFine = dataFine;
    }

    public int getGasDetailsOfferId() {
        return gasDetailsOfferId;
    }

    public void setGasDetailsOfferId(int gasDetailsOfferId) {
        this.gasDetailsOfferId = gasDetailsOfferId;
    }

    public int getSdcId() {
        return sdcId;
    }

    public void setSdcId(int sdcId) {
        this.sdcId = sdcId;
    }

    public double getConsumoAnnuo() {
        return consumoAnnuo;
    }

    public void setConsumoAnnuo(double consumoAnnuo) {
        this.consumoAnnuo = consumoAnnuo;
    }

    public double getStimaSpesaRif() {
        return stimaSpesaRif;
    }

    public void setStimaSpesaRif(double stimaSpesaRif) {
        this.stimaSpesaRif = stimaSpesaRif;
    }
}

