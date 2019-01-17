package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "AssicurazioneOffer")
public class AssicurazioneOffer extends BaseEntity {

    public static final String ASSICURAZIONE_ID = "assicurazioneId";
    public static final String OFFER_ID = "offerId";
    public static final String ASSICURAZIONE_COST = "assicurazioneCost";
    public static final String ASSICURAZIONE_DATE = "assicurazioneDate";
    public static final String COSTO_GESTIONE_INTEGRATA = "costoGestioneIntegrata";
    public static final String COSTO_GESTIONE_INTEGRATA_IVA = "costoGestioneIntegrataIva";
    public static final String TARGA = "targa";

    public AssicurazioneOffer() {
    }

    @DatabaseField(generatedId = true, columnName = ASSICURAZIONE_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int assicurazioneId;
    @DatabaseField(columnName = OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int offerId;
    @DatabaseField(columnName = ASSICURAZIONE_COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double assicurazioneCost;
    @DatabaseField(columnName = ASSICURAZIONE_DATE, dataType = DataType.DATE)
    private Date assicurazioneDate;
    @DatabaseField(columnName = COSTO_GESTIONE_INTEGRATA, canBeNull = false, dataType = DataType.DOUBLE)
    private double costoGestioneIntegrata;
    @DatabaseField(columnName = TARGA, canBeNull = false, dataType = DataType.STRING)
    private String targa;
    @DatabaseField(columnName = COSTO_GESTIONE_INTEGRATA_IVA, canBeNull = false, dataType = DataType.DOUBLE)
    private double costoGestioneIntegrataIva;

    public int getAssicurazioneId() {
        return assicurazioneId;
    }

    public void setAssicurazioneId(int assicurazioneId) {
        this.assicurazioneId = assicurazioneId;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public double getAssicurazioneCost() {
        return assicurazioneCost;
    }

    public void setAssicurazioneCost(double assicurazioneCost) {
        this.assicurazioneCost = assicurazioneCost;
    }

    public Date getAssicurazioneDate() {
        return assicurazioneDate;
    }

    public void setAssicurazioneDate(Date assicurazioneDate) {
        this.assicurazioneDate = assicurazioneDate;
    }

    public double getCostoGestioneIntegrata() {
        return costoGestioneIntegrata;
    }

    public void setCostoGestioneIntegrata(double costoGestioneIntegrata) {
        this.costoGestioneIntegrata = costoGestioneIntegrata;
    }

    public double getCostoGestioneIntegrataIva() {
        return costoGestioneIntegrataIva;
    }

    public void setCostoGestioneIntegrataIva(double costoGestioneIntegrataIva) {
        this.costoGestioneIntegrataIva = costoGestioneIntegrataIva;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }
}
