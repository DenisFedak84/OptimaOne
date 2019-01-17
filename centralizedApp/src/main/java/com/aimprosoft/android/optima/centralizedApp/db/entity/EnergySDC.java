package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "EnergySDC")
public class EnergySDC extends BaseEntity {

    public static final String SDC_ID = "sdcId";
    public static final String CONSUMO_ANNUO = "consumoAnnuo";
    public static final String ENERGY_DETAILS_OFFER_ID = "energyDetailsOfferId";
    public static final String PREZZO_BIORARIO_NO_PUNTA = "prezzoBiorarioNoPunta";
    public static final String PREZZO_BIORARIO_PUNTA = "prezzoBiorarioPunta";
    public static final String PREZZO_MONORARIO = "prezzoMonorario";
    public static final String DATA_FINE = "dataFine";
    public static final String DATA_INIZIO = "dataInizio";
    public static final String COSTO_TOT_SEN_IMPE = "costoTotSenImpE";

    public EnergySDC() {
    }

    public EnergySDC(int energyDetailsOfferId, double consumoAnnuo, double prezzoBiorarioPunta, double prezzoBiorarioNoPunta, double prezzoMonorario, String dataInizio, String dataFine) {
        this.energyDetailsOfferId = energyDetailsOfferId;
        this.consumoAnnuo = consumoAnnuo;
        this.prezzoBiorarioPunta = prezzoBiorarioPunta;
        this.prezzoBiorarioNoPunta = prezzoBiorarioNoPunta;
        this.prezzoMonorario = prezzoMonorario;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = SDC_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int sdcId;
    @DatabaseField(columnName = ENERGY_DETAILS_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int energyDetailsOfferId;
    @DatabaseField(columnName = CONSUMO_ANNUO, canBeNull = false, dataType = DataType.DOUBLE)
    private double consumoAnnuo;
    @DatabaseField(columnName = PREZZO_BIORARIO_PUNTA, canBeNull = false, dataType = DataType.DOUBLE)
    private double prezzoBiorarioPunta;
    @DatabaseField(columnName = PREZZO_BIORARIO_NO_PUNTA, canBeNull = false, dataType = DataType.DOUBLE)
    private double prezzoBiorarioNoPunta;
    @DatabaseField(columnName = PREZZO_MONORARIO, canBeNull = false, dataType = DataType.DOUBLE)
    private double prezzoMonorario;
    @DatabaseField(columnName = DATA_INIZIO, canBeNull = false, dataType = DataType.STRING)
    private String dataInizio;
    @DatabaseField(columnName = DATA_FINE, canBeNull = false, dataType = DataType.STRING)
    private String dataFine;
    @DatabaseField(columnName = COSTO_TOT_SEN_IMPE, canBeNull = false, dataType = DataType.DOUBLE)
    private double costoTotSenImpE;

    public double getCostoTotSenImpE() {
        return costoTotSenImpE;
    }

    public void setCostoTotSenImpE(double costoTotSenImpE) {
        this.costoTotSenImpE = costoTotSenImpE;
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

    public double getPrezzoBiorarioPunta() {
        return prezzoBiorarioPunta;
    }

    public void setPrezzoBiorarioPunta(double prezzoBiorarioPunta) {
        this.prezzoBiorarioPunta = prezzoBiorarioPunta;
    }

    public int getEnergyDetailsOfferId() {
        return energyDetailsOfferId;
    }

    public void setEnergyDetailsOfferId(int energyDetailsOfferId) {
        this.energyDetailsOfferId = energyDetailsOfferId;
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

    public double getPrezzoBiorarioNoPunta() {
        return prezzoBiorarioNoPunta;
    }

    public void setPrezzoBiorarioNoPunta(double prezzoBiorarioNoPunta) {
        this.prezzoBiorarioNoPunta = prezzoBiorarioNoPunta;
    }

    public double getPrezzoMonorario() {
        return prezzoMonorario;
    }

    public void setPrezzoMonorario(double prezzoMonorario) {
        this.prezzoMonorario = prezzoMonorario;
    }
}
