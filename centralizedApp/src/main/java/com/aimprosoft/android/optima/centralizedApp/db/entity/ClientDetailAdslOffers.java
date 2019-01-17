package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ClientDetailAdslOffers")
public class ClientDetailAdslOffers extends BaseEntity {

    public static final String CLIENT_DETAILS_ADSL_OFFER_ID = "clientDetailsAdslOfferId";
    public static final String CLIENT_ADSL_OFFER_ID = "clientAdslOfferId";
    public static final String LINE = "line";
    public static final String ADSL_TYPE = "adslType";
    public static final String IP = "ip";
    public static final String DATA_LINE = "dataLine";
    public static final String COST = "cost";
    public static final String COSTIVA = "costIva";
    public static final String CLIENT_SIN = "clientSin";
    public static final String VERSION = "version";
    public static final String DISPLAY = "display";

    public ClientDetailAdslOffers() {
    }

    public ClientDetailAdslOffers(int clientDetailsAdslOfferId, int clientAdslOfferId, String line, int adslType,
                                  int ip, int dataLine, double cost, double costIva, int clientSin, String version, String display) {
        this.clientDetailsAdslOfferId = clientDetailsAdslOfferId;
        this.clientAdslOfferId = clientAdslOfferId;
        this.line = line;
        this.adslType = adslType;
        this.ip = ip;
        this.dataLine = dataLine;
        this.cost = cost;
        this.costIva = costIva;
        this.clientSin = clientSin;
        this.version = version;
        this.display = display;
    }

    @DatabaseField(id = true, columnName = CLIENT_DETAILS_ADSL_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int clientDetailsAdslOfferId;
    @DatabaseField(columnName = CLIENT_ADSL_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private  int clientAdslOfferId;
    @DatabaseField(columnName = LINE, canBeNull = false , dataType = DataType.STRING)
    private String line;
    @DatabaseField(columnName = ADSL_TYPE, canBeNull = false, dataType = DataType.INTEGER)
    private int adslType;
    @DatabaseField(columnName = IP, canBeNull = false, dataType = DataType.INTEGER)
    private int ip;
    @DatabaseField(columnName = DATA_LINE, canBeNull = false, dataType = DataType.INTEGER)
    private int dataLine;
    @DatabaseField(columnName = COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double cost;
    @DatabaseField(columnName = COSTIVA, canBeNull = false, dataType = DataType.DOUBLE)
    private double costIva;
    @DatabaseField(columnName = CLIENT_SIN, canBeNull = false, dataType = DataType.INTEGER)
    private int clientSin;
    @DatabaseField(columnName = VERSION, canBeNull = false, dataType = DataType.STRING)
    private String version;
    @DatabaseField(columnName = DISPLAY, canBeNull = false, dataType = DataType.STRING)
    private String display;

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

    public int getClientSin() {
        return clientSin;
    }

    public void setClientSin(int clientSin) {
        this.clientSin = clientSin;
    }

    public int getClientDetailsAdslOfferId() {
        return clientDetailsAdslOfferId;
    }

    public void setClientDetailsAdslOfferId(int clientDetailsAdslOfferId) {
        this.clientDetailsAdslOfferId = clientDetailsAdslOfferId;
    }

    public int getClientAdslOfferId() {
        return clientAdslOfferId;
    }

    public void setClientAdslOfferId(int clientAdslOfferId) {
        this.clientAdslOfferId = clientAdslOfferId;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public int getAdslType() {
        return adslType;
    }

    public void setAdslType(int adslType) {
        this.adslType = adslType;
    }

    public int getIp() {
        return ip;
    }

    public void setIp(int ip) {
        this.ip = ip;
    }

    public int getDataLine() {
        return dataLine;
    }

    public void setDataLine(int dataLine) {
        this.dataLine = dataLine;
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
