package com.aimprosoft.android.optima.centralizedApp.db.entity;

import android.database.Cursor;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = "Services")
public class Services extends BaseEntity {

    public static final String SERVICE_ID = "serviceId";
    public static final String SERVICE_DESC = "serviceDesc";
    public static final String LINE_TYPE = "lineType";
    public static final String SERVICE_TYPE = "serviceType";
    public static final String FIELD_TYPE = "fieldType";
    public static final String FIELD_NAME = "fieldName";
    public static final String FIELD_GROUP = "fieldGroup";
    public static final String DISPLAY = "display";
    public static final String ACTIVATION_COST = "activationCost";
    public static final String ACTIVATION_COST_IVA = "activationCostIva";
    public static final String COST = "cost";
    public static final String COST_IVA = "costIva";
    public static final String RELAX = "relax";
    public static final String VERSION = "version";
    public static final String SYSTEM = "system";
    public static final String CODE = "code";

    public Services() {
    }

    public Services(int serviceId, String serviceDesc, String lineType, String serviceType,
                    String fieldType, String fieldName, Integer fieldGroup, int display,
                    double activationCost, double activationCostIva, double cost,
                    double costIVA, int relax, String version, String system, String code) {
        this.serviceId = serviceId;
        this.serviceDesc = serviceDesc;
        this.lineType = lineType;
        this.serviceType = serviceType;
        this.fieldType = fieldType;
        this.fieldName = fieldName;
        this.fieldGroup = fieldGroup;
        this.display = display;
        this.activationCost = activationCost;
        this.activationCostIva = activationCostIva;
        this.cost = cost;
        this.costIVA = costIVA;
        this.relax = relax;
        this.version = version;
        this.system = system;
        this.code = code;
    }

    @DatabaseField(id = true, columnName = SERVICE_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int serviceId;
    @DatabaseField(columnName = SERVICE_DESC, canBeNull = false, dataType = DataType.STRING)
    private String serviceDesc;
    @DatabaseField(columnName = LINE_TYPE, canBeNull = true, dataType = DataType.STRING)
    private String lineType;
    @DatabaseField(columnName = SERVICE_TYPE, canBeNull = true, dataType = DataType.STRING)
    private String serviceType;
    @DatabaseField(columnName = FIELD_TYPE, canBeNull = false, dataType = DataType.STRING)
    private String fieldType;
    @DatabaseField(columnName = FIELD_NAME, canBeNull = false, dataType = DataType.STRING)
    private String fieldName;
    @DatabaseField(columnName = FIELD_GROUP, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer fieldGroup;
    @DatabaseField(columnName = DISPLAY, canBeNull = false, dataType = DataType.INTEGER)
    private int display;
    @DatabaseField(columnName = ACTIVATION_COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double activationCost;
    @DatabaseField(columnName = ACTIVATION_COST_IVA, canBeNull = false, dataType = DataType.DOUBLE)
    private double activationCostIva;
    @DatabaseField(columnName = COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double cost;
    @DatabaseField(columnName = COST_IVA, canBeNull = false, dataType = DataType.DOUBLE)
    private double costIVA;
    @DatabaseField(columnName = RELAX, canBeNull = false, dataType = DataType.INTEGER)
    private int relax;
    @DatabaseField(columnName = VERSION, canBeNull = false, dataType = DataType.STRING)
    private String version;
    @DatabaseField(columnName = SYSTEM, canBeNull = true, dataType = DataType.STRING)
    private String system;
    @DatabaseField(columnName = CODE, canBeNull = true, dataType = DataType.STRING)
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public double getActivationCostIva() {
        return activationCostIva;
    }

    public void setActivationCostIva(double activationCostIva) {
        this.activationCostIva = activationCostIva;
    }

    public int getRelax() {
        return relax;
    }

    public void setRelax(int relax) {
        this.relax = relax;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Integer getFieldGroup() {
        return fieldGroup;
    }

    public void setFieldGroup(Integer fieldGroup) {
        this.fieldGroup = fieldGroup;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public double getActivationCost() {
        return activationCost;
    }

    public void setActivationCost(double activationCost) {
        this.activationCost = activationCost;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCostIVA() {
        return costIVA;
    }

    public void setCostIVA(double costIVA) {
        this.costIVA = costIVA;
    }

    @Override
    public String toString() {
        return getServiceDesc();
    }

    /**
     * For using with dbLite.db and ConfiguratoreEngine jar
     **/
    public static List<Services> getListFromCursor(Cursor cursor) {
        List<Services> servicesTypesList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                servicesTypesList.add(parseServices(cursor));
            }
        }
        return servicesTypesList;
    }

    private static Services parseServices(Cursor cursor) {
        Services services = new Services();
        services.setServiceId(cursor.getInt(0));
        services.setCode(cursor.getString(1));
        services.setServiceDesc(cursor.getString(2));
        services.setLineType(cursor.getString(3));
        services.setFieldName(cursor.getString(5));
        services.setCost(cursor.getDouble(6));
        services.setCostIVA(cursor.getDouble(7));
        services.setActivationCost(cursor.getDouble(8));
        return services;
    }

    public static Services getServicesFromCursor(Cursor cursor) {
        Services services;
        if (cursor != null && cursor.moveToNext()) {
            services = parseServices(cursor);
        } else {
            services = new Services();
        }
        return services;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Services services = (Services) o;

        if (serviceId != services.serviceId) return false;
        if (display != services.display) return false;
        if (Double.compare(services.activationCost, activationCost) != 0) return false;
        if (Double.compare(services.activationCostIva, activationCostIva) != 0) return false;
        if (Double.compare(services.cost, cost) != 0) return false;
        if (Double.compare(services.costIVA, costIVA) != 0) return false;
        if (relax != services.relax) return false;
        if (serviceDesc != null ? !serviceDesc.equals(services.serviceDesc) : services.serviceDesc != null)
            return false;
        if (lineType != null ? !lineType.equals(services.lineType) : services.lineType != null)
            return false;
        if (serviceType != null ? !serviceType.equals(services.serviceType) : services.serviceType != null)
            return false;
        if (fieldType != null ? !fieldType.equals(services.fieldType) : services.fieldType != null)
            return false;
        if (fieldName != null ? !fieldName.equals(services.fieldName) : services.fieldName != null)
            return false;
        if (fieldGroup != null ? !fieldGroup.equals(services.fieldGroup) : services.fieldGroup != null)
            return false;
        if (version != null ? !version.equals(services.version) : services.version != null)
            return false;
        if (system != null ? !system.equals(services.system) : services.system != null)
            return false;
        return code != null ? code.equals(services.code) : services.code == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = serviceId;
        result = 31 * result + (serviceDesc != null ? serviceDesc.hashCode() : 0);
        result = 31 * result + (lineType != null ? lineType.hashCode() : 0);
        result = 31 * result + (serviceType != null ? serviceType.hashCode() : 0);
        result = 31 * result + (fieldType != null ? fieldType.hashCode() : 0);
        result = 31 * result + (fieldName != null ? fieldName.hashCode() : 0);
        result = 31 * result + (fieldGroup != null ? fieldGroup.hashCode() : 0);
        result = 31 * result + display;
        temp = Double.doubleToLongBits(activationCost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(activationCostIva);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(cost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(costIVA);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + relax;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (system != null ? system.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }
}
