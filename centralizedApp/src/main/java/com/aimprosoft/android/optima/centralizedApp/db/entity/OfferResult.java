package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "OfferResult")
public class OfferResult extends BaseEntity {

    public static final String OFFER_ID = "offerId";
    public static final String RESULT_OFFER_ID = "resultOfferId";
    public static final String CANONE_MENSILE = "canoneMensile";
    public static final String CANONE_MENSILE_CON_IVA = "canoneMensileConIva";
    public static final String CANONE_MENSILE_IVA_ESCLUSA = "canoneMensileIvaEsclusa";
    public static final String CONTO_RELAX = "contoRelax";
    public static final String COSTO_ATTIVAZIONE = "costoAttivazione";
    public static final String CANONE_DEVICE = "canoneDevice";

    public OfferResult() {
    }

    @DatabaseField(generatedId = true, columnName = RESULT_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int resultOfferId;

    @DatabaseField(columnName = OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int offerId;

    @DatabaseField(columnName = CANONE_MENSILE, canBeNull = true, dataType = DataType.DOUBLE)
    private double canoneMensile;

    @DatabaseField(columnName = CANONE_MENSILE_CON_IVA, canBeNull = true, dataType = DataType.DOUBLE)
    private double canoneMensileIvaEsclusa;

    @DatabaseField(columnName = CANONE_MENSILE_IVA_ESCLUSA, canBeNull = true, dataType = DataType.DOUBLE)
    private double canoneMensileConIva;

    @DatabaseField(columnName = CONTO_RELAX, canBeNull = true, dataType = DataType.DOUBLE)
    private double contoRelax;

    @DatabaseField(columnName = COSTO_ATTIVAZIONE, canBeNull = true, dataType = DataType.DOUBLE)
    private double costoAttivazione;

    @DatabaseField(columnName = CANONE_DEVICE, canBeNull = true, dataType = DataType.DOUBLE)
    private double canoneDevice;

    public int getResultOfferId() {
        return resultOfferId;
    }

    public void setResultOfferId(int resultOfferId) {
        this.resultOfferId = resultOfferId;
    }

    public double getCanoneDevice() {
        return canoneDevice;
    }

    public void setCanoneDevice(double canoneDevice) {
        this.canoneDevice = canoneDevice;
    }

    public double getCostoAttivazione() {
        return costoAttivazione;
    }

    public void setCostoAttivazione(double costoAttivazione) {
        this.costoAttivazione = costoAttivazione;
    }

    public double getContoRelax() {
        return contoRelax;
    }

    public void setContoRelax(double contoRelax) {
        this.contoRelax = contoRelax;
    }

    public double getCanoneMensileConIva() {
        return canoneMensileConIva;
    }

    public void setCanoneMensileConIva(double canoneMensileConIva) {
        this.canoneMensileConIva = canoneMensileConIva;
    }

    public double getCanoneMensileIvaEsclusa() {
        return canoneMensileIvaEsclusa;
    }

    public void setCanoneMensileIvaEsclusa(double canoneMensileIvaEsclusa) {
        this.canoneMensileIvaEsclusa = canoneMensileIvaEsclusa;
    }

    public double getCanoneMensile() {
        return canoneMensile;
    }

    public void setCanoneMensile(double canoneMensile) {
        this.canoneMensile = canoneMensile;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }
}
