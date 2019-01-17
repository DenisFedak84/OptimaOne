package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ClientDetailGasOffers")
public class ClientDetailGasOffers extends BaseEntity {

    public static final String CLIENT_DETAIL_GAS_OFFERS = "clientDetailGasOffers";
    public static final String CLIENT_GAS_OFFER_ID = "clientGasOfferId";
    public static final String PDR = "pdr";
    public static final String FISCAL_CLASS = "fiscalClass";
    public static final String SMC = "smc";
    public static final String COST = "cost";
    public static final String COSTIVA = "costIva";
    public static final String RELAX = "relax";
    public static final String VERSION = "version";
    public static final String DISPLAY = "display";
    public static final String PMC = "pmc";
    public static final String PS = "ps";
    public static final String CODICE_PMC_PS = "codicePmcPs";

    public ClientDetailGasOffers() {
    }

    public ClientDetailGasOffers(int clientDetailGasOffers, int clientGasOfferId, String pdr, int fiscalClass,
                                 int smc, double cost, double costIva, int relax, String version, String display) {
        this.clientDetailGasOffers = clientDetailGasOffers;
        this.clientGasOfferId = clientGasOfferId;
        this.pdr = pdr;
        this.fiscalClass = fiscalClass;
        this.smc = smc;
        this.cost = cost;
        this.costIva = costIva;
        this.relax = relax;
        this.version = version;
        this.display = display;
    }

    @DatabaseField(id = true, columnName = CLIENT_DETAIL_GAS_OFFERS, canBeNull = false, dataType = DataType.INTEGER)
    private int clientDetailGasOffers;
    @DatabaseField(columnName = CLIENT_GAS_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int clientGasOfferId;
    @DatabaseField(columnName = PDR, canBeNull = false, dataType = DataType.STRING)
    private String pdr;
    @DatabaseField(columnName = FISCAL_CLASS, canBeNull = false, dataType = DataType.INTEGER)
    private int fiscalClass;
    @DatabaseField(columnName = SMC, canBeNull = false, dataType = DataType.INTEGER)
    private int smc;
    @DatabaseField(columnName = COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double cost;
    @DatabaseField(columnName = COSTIVA, canBeNull = false, dataType = DataType.DOUBLE)
    private double costIva;
    @DatabaseField(columnName = RELAX, canBeNull = false, dataType = DataType.INTEGER)
    private int relax;
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

    public int getClientDetailGasOffers() {
        return clientDetailGasOffers;
    }

    public void setClientDetailGasOffers(int clientDetailGasOffers) {
        this.clientDetailGasOffers = clientDetailGasOffers;
    }

    public int getClientGasOfferId() {
        return clientGasOfferId;
    }

    public void setClientGasOfferId(int clientGasOfferId) {
        this.clientGasOfferId = clientGasOfferId;
    }

    public String getPdr() {
        return pdr;
    }

    public void setPdr(String pdr) {
        this.pdr = pdr;
    }

    public int getFiscalClass() {
        return fiscalClass;
    }

    public void setFiscalClass(int fiscalClass) {
        this.fiscalClass = fiscalClass;
    }

    public int getSmc() {
        return smc;
    }

    public void setSmc(int smc) {
        this.smc = smc;
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

    public int getRelax() {
        return relax;
    }

    public void setRelax(int relax) {
        this.relax = relax;
    }
}
