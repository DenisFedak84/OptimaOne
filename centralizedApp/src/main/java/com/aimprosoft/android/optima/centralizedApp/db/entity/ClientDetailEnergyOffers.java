package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ClientDetailEnergyOffers")
public class ClientDetailEnergyOffers extends BaseEntity {

    public static final String CLIENT_DETAIL_ENERGY_OFFERS = "clientDetailEnergyOffers";
    public static final String CLIENT_ENERGY_OFFER_ID = "clientEnergyOfferId";
    public static final String POD = "pod";
    public static final String CONSUMPTION_CLASS_TYPE = "consumptionClassType";
    public static final String ENERGY_METER = "energyMeter";
    public static final String KWH = "kwh";
    public static final String COST = "cost";
    public static final String COSTIVA = "costIva";
    public static final String VERSION = "version";
    public static final String DISPLAY = "display";
    public static final String PMC = "pmc";
    public static final String PS = "ps";
    public static final String CODICE_PMC_PS = "codicePmcPs";
    public static final String POTENZA_REAL = "potenzaReal";

    public ClientDetailEnergyOffers() {
    }

    @DatabaseField(id = true, columnName = CLIENT_DETAIL_ENERGY_OFFERS, canBeNull = false, dataType = DataType.INTEGER)
    private int clientDetailEnergyOffers;
    @DatabaseField(columnName = CLIENT_ENERGY_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int clientEnergyOfferId;
    @DatabaseField(columnName = POD, canBeNull = false, dataType = DataType.STRING)
    private String pod;
    @DatabaseField(columnName = CONSUMPTION_CLASS_TYPE, canBeNull = false, dataType = DataType.INTEGER)
    private int consumptionClassType;
    @DatabaseField(columnName = ENERGY_METER, canBeNull = false, dataType = DataType.INTEGER)
    private int energyMeter;
    @DatabaseField(columnName = KWH, canBeNull = false, dataType = DataType.INTEGER)
    private int kwh;
    @DatabaseField(columnName = COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double cost;
    @DatabaseField(columnName = COSTIVA, canBeNull = false, dataType = DataType.DOUBLE)
    private double costIva;
    @DatabaseField(columnName = VERSION, canBeNull = false, dataType = DataType.STRING)
    private String version;
    @DatabaseField(columnName = DISPLAY, canBeNull = false, dataType = DataType.STRING)
    private String display;
    @DatabaseField(columnName = PMC, canBeNull = true, dataType = DataType.STRING, defaultValue = "0")
    private String pmc;
    @DatabaseField(columnName = PS, canBeNull = true, dataType = DataType.STRING, defaultValue = "0")
    private String ps;
    @DatabaseField(columnName = CODICE_PMC_PS, canBeNull = true, dataType = DataType.STRING, defaultValue = "0")
    private String codicePmcPS;
    @DatabaseField(columnName = POTENZA_REAL, canBeNull = false, dataType = DataType.INTEGER)
    private int potenzaReal;

    public int getPotenzaReal() {
        return potenzaReal;
    }

    public void setPotenzaReal(int potenzaReal) {
        this.potenzaReal = potenzaReal;
    }

    public String getPmc() {
        return pmc;
    }

    public void setPmc(String pmc) {
        this.pmc = pmc;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public String getCodicePmcPS() {
        return codicePmcPS;
    }

    public void setCodicePmcPS(String codicePmcPS) {
        this.codicePmcPS = codicePmcPS;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public int getConsumptionClassType() {
        return consumptionClassType;
    }

    public void setConsumptionClassType(int consumptionClassType) {
        this.consumptionClassType = consumptionClassType;
    }

    public int getClientDetailEnergyOffers() {
        return clientDetailEnergyOffers;
    }

    public void setClientDetailEnergyOffers(int clientDetailEnergyOffers) {
        this.clientDetailEnergyOffers = clientDetailEnergyOffers;
    }

    public int getClientEnergyOfferId() {
        return clientEnergyOfferId;
    }

    public void setClientEnergyOfferId(int clientEnergyOfferId) {
        this.clientEnergyOfferId = clientEnergyOfferId;
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    public int getEnergyMeter() {
        return energyMeter;
    }

    public void setEnergyMeter(int energyMeter) {
        this.energyMeter = energyMeter;
    }

    public int getKwh() {
        return kwh;
    }

    public void setKwh(int kwh) {
        this.kwh = kwh;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCostIva() {
        return costIva;
    }

    public void setCostIva(double costIva) {
        this.costIva = costIva;
    }
}
