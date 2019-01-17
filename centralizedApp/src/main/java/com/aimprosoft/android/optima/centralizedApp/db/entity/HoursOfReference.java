package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "HoursOfReference")
public class HoursOfReference extends BaseEntity {

    public static final String HOURS_ID = "hoursId";
    public static final String HOURS_VALUE = "hoursValue";
    public static final String CODE_APPLIANCE = "codeAppliance";

    public HoursOfReference() {
    }

    public HoursOfReference(int hoursId, String hoursValue, String codeAppliance) {
        this.hoursId = hoursId;
        this.hoursValue = hoursValue;
        this.codeAppliance = codeAppliance;
    }

    @DatabaseField(columnName = HOURS_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int hoursId;

    @DatabaseField(columnName = HOURS_VALUE, canBeNull = false, dataType = DataType.STRING)
    private String hoursValue;

    @DatabaseField(columnName = CODE_APPLIANCE, canBeNull = false, dataType = DataType.STRING)
    private String codeAppliance;

    public int getHoursId() {
        return hoursId;
    }

    public void setHoursId(int hoursId) {
        this.hoursId = hoursId;
    }

    public String getCodeAppliance() {
        return codeAppliance;
    }

    public void setCodeAppliance(String codeAppliance) {
        this.codeAppliance = codeAppliance;
    }

    public String getHoursValue() {
        return hoursValue;
    }

    public void setHoursValue(String hoursValue) {
        this.hoursValue = hoursValue;
    }

    public String toString() {
        return hoursValue + "";
    }
}
