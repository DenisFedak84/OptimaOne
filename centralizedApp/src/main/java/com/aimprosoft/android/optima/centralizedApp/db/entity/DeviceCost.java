package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "DeviceCost")
public class DeviceCost extends BaseEntity {

    public static final String ID_DEVICE_COST = "idDeviceCost";
    public static final String CODE_DEVICE = "codeDevice";
    public static final String ID_CAMPAGNA = "idCampagna";
    public static final String COST = "cost";
    public static final String COST_IVA = "costIva";
    public static final String ACTIVATION_COST = "activationCost";
    public static final String ACTIVATION_COSTO_EXTRA = "activationCostoExtra";
    public static final String VERSION = "version";
    public static final String DISPLAY = "display";
    public static final String PRIORITY = "priority";
    public static final String SORT_ORDER = "sortOrder";

    public DeviceCost() {
    }

    public DeviceCost(int idDeviceCost, String codeDevice, int idCampagna, double cost,
                      double costIva, double activationCost, double activationCostoExtra,
                      int priority, String display, String version, int sortOrder) {
        this.idDeviceCost = idDeviceCost;
        this.codeDevice = codeDevice;
        this.idCampagna = idCampagna;
        this.cost = cost;
        this.costIva = costIva;
        this.activationCost = activationCost;
        this.activationCostoExtra = activationCostoExtra;
        this.priority = priority;
        this.display = display;
        this.version = version;
        this.sortOrder = sortOrder;
    }

    @DatabaseField(generatedId = true, columnName = ID_DEVICE_COST, canBeNull = false, dataType = DataType.INTEGER)
    private int idDeviceCost;
    @DatabaseField(columnName = CODE_DEVICE, canBeNull = false, dataType = DataType.STRING)
    private String codeDevice;
    @DatabaseField(columnName = ID_CAMPAGNA, canBeNull = false, dataType = DataType.INTEGER)
    private int idCampagna;
    @DatabaseField(columnName = COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double cost;
    @DatabaseField(columnName = COST_IVA, canBeNull = false, dataType = DataType.DOUBLE)
    private double costIva;
    @DatabaseField(columnName = ACTIVATION_COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double activationCost;
    @DatabaseField(columnName = ACTIVATION_COSTO_EXTRA, canBeNull = false, dataType = DataType.DOUBLE)
    private double activationCostoExtra;
    @DatabaseField(columnName = PRIORITY, canBeNull = false, dataType = DataType.INTEGER)
    private int priority;
    @DatabaseField(columnName = DISPLAY, canBeNull = false, dataType = DataType.STRING)
    private String display;
    @DatabaseField(columnName = VERSION, canBeNull = false, dataType = DataType.STRING)
    private String version;
    @DatabaseField(columnName = SORT_ORDER, canBeNull = false, dataType = DataType.INTEGER)
    private int sortOrder;

    public int getIdDeviceCost() {
        return idDeviceCost;
    }

    public void setIdDeviceCost(int idDeviceCost) {
        this.idDeviceCost = idDeviceCost;
    }

    public String getCodeDevice() {
        return codeDevice;
    }

    public void setCodeDevice(String codeDevice) {
        this.codeDevice = codeDevice;
    }

    public int getIdCampagna() {
        return idCampagna;
    }

    public void setIdCampagna(int idCampagna) {
        this.idCampagna = idCampagna;
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

    public double getActivationCost() {
        return activationCost;
    }

    public void setActivationCost(double activationCost) {
        this.activationCost = activationCost;
    }

    public double getActivationCostoExtra() {
        return activationCostoExtra;
    }

    public void setActivationCostoExtra(double activationCostoExtra) {
        this.activationCostoExtra = activationCostoExtra;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
