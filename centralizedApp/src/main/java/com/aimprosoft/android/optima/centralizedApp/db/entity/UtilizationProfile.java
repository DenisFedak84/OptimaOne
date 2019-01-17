package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "UtilizationProfile")
public class UtilizationProfile extends BaseEntity {

    public static final String ENERGY_METER = "energyMeter";
    public static final String FLAT_TYPE = "flatType";
    public static final String CLIMATIC_ZONE = "climaticZone";
    public static final String CODE_ZONE = "codeZone";

    public UtilizationProfile() {
    }

    public UtilizationProfile(int energyMeter, int flatType, String climaticZone, String codeZone) {
        this.energyMeter = energyMeter;
        this.flatType = flatType;
        this.climaticZone = climaticZone;
        this.codeZone = codeZone;
    }

    @DatabaseField(columnName = ENERGY_METER, canBeNull = false, dataType = DataType.INTEGER)
    private int energyMeter;

    @DatabaseField(columnName = FLAT_TYPE, canBeNull = false, dataType = DataType.INTEGER)
    private int flatType;


    @DatabaseField(columnName = CLIMATIC_ZONE, canBeNull = false, dataType = DataType.STRING)
    private String climaticZone;

    @DatabaseField(columnName = CODE_ZONE, canBeNull = false, dataType = DataType.STRING)
    private String codeZone;

    public int getEnergyMeter() {
        return energyMeter;
    }

    public void setEnergyMeter(int energyMeter) {
        this.energyMeter = energyMeter;
    }

    public int getFlatType() {
        return flatType;
    }

    public void setFlatType(int flatType) {
        this.flatType = flatType;
    }

    public String getClimaticZone() {
        return climaticZone;
    }

    public void setClimaticZone(String climaticZone) {
        this.climaticZone = climaticZone;
    }

    public String getCodeZone() {
        return codeZone;
    }

    public void setCodeZone(String codeZone) {
        this.codeZone = codeZone;
    }
}
