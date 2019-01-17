package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ActivationCost")
public class ActivationCost extends BaseEntity {

    public static final String ID_SERVICE = "idService";
    public static final String NAME_SERVICE = "nameService";
    public static final String ACTIVATION_COST = "activationCost";
    public static final String CLIENT_TYPE = "clientType";
    public static final String VERSION = "version";
    public static final String DISPLAY = "display";

    public ActivationCost(int idService, String nameService, double activationCost, Integer clientType, String version, String display) {
        this.idService = idService;
        this.nameService = nameService;
        this.activationCost = activationCost;
        this.clientType = clientType;
        this.version = version;
        this.display = display;
    }

    public ActivationCost() {
    }

    @DatabaseField(columnName = ID_SERVICE, canBeNull = false, dataType = DataType.INTEGER)
    private int idService;

    @DatabaseField(columnName = NAME_SERVICE, canBeNull = false, dataType = DataType.STRING)
    private String nameService;

    @DatabaseField(columnName = ACTIVATION_COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double activationCost;

    @DatabaseField(columnName = CLIENT_TYPE, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer clientType;

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

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public double getActivationCost() {
        return activationCost;
    }

    public void setActivationCost(double activationCost) {
        this.activationCost = activationCost;
    }
}
