package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Bundle")
public class Bundle extends BaseEntity {

    public static final String BUNDLE_ID = "bundleId";
    public static final String BUNDLE_DESC = "bundleDesc";
    public static final String BUNDLE_MINUTES = "bundleMinutes";
    public static final String BUNDLE_COST = "bundleCost";
    public static final String BUNDLE_COST_IVA = "bundleCostIVA";
    public static final String BUNDLE_TYPE = "bundleType";
    public static final String ACTIVATION_COST = "activationCost";
    public static final String RELAX = "relax";
    public static final String VERSION = "version";
    public static final String DISPLAY = "display";
    public static final String CODE = "code";
    public static final String SYSTEM = "system";

    public Bundle() {
    }

    @DatabaseField(columnName = BUNDLE_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int bundleId;
    @DatabaseField(columnName = BUNDLE_DESC, canBeNull = false, dataType = DataType.STRING)
    private String bundleDesc;
    @DatabaseField(columnName = BUNDLE_MINUTES, dataType = DataType.INTEGER_OBJ)
    private Integer bundleMinutes;
    @DatabaseField(columnName = BUNDLE_COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double bundleCost;
    @DatabaseField(columnName = BUNDLE_COST_IVA, canBeNull = false, dataType = DataType.DOUBLE)
    private double bundleCostIVA;
    @DatabaseField(columnName = BUNDLE_TYPE, canBeNull = false, dataType = DataType.INTEGER)
    private int bundleType;
    @DatabaseField(columnName = ACTIVATION_COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double activationCost;
    @DatabaseField(columnName = RELAX, canBeNull = false, dataType = DataType.INTEGER)
    private int relax;
    @DatabaseField(columnName = VERSION, canBeNull = false, dataType = DataType.STRING)
    private String version;
    @DatabaseField(columnName = DISPLAY, canBeNull = false, dataType = DataType.STRING)
    private String display;
    @DatabaseField(columnName = CODE, canBeNull = false, dataType = DataType.STRING)
    private String code;
    @DatabaseField(columnName = SYSTEM, canBeNull = true, dataType = DataType.STRING)
    private String system;

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public double getActivationCost() {
        return activationCost;
    }

    public void setActivationCost(double activationCost) {
        this.activationCost = activationCost;
    }

    public int getRelax() {
        return relax;
    }

    public void setRelax(int relax) {
        this.relax = relax;
    }

    public int getBundleId() {
        return bundleId;
    }

    public void setBundleId(int bundleId) {
        this.bundleId = bundleId;
    }

    public String getBundleDesc() {
        return bundleDesc;
    }

    public void setBundleDesc(String bundleDesc) {
        this.bundleDesc = bundleDesc;
    }

    public Integer getBundleMinutes() {
        return bundleMinutes;
    }

    public void setBundleMinutes(Integer bundleMinutes) {
        this.bundleMinutes = bundleMinutes;
    }

    public double getBundleCost() {
        return bundleCost;
    }

    public void setBundleCost(double bundleCost) {
        this.bundleCost = bundleCost;
    }

    public int getBundleType() {
        return bundleType;
    }

    public void setBundleType(int bundleType) {
        this.bundleType = bundleType;
    }

    public double getBundleCostIVA() {
        return bundleCostIVA;
    }

    public void setBundleCostIVA(double bundleCostIVA) {
        this.bundleCostIVA = bundleCostIVA;
    }

    @Override
    public String toString() {
        return bundleDesc;
    }

}
