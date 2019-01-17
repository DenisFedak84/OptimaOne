package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "WeeklyUtilizationSpinner")
public class WeeklyUtilization extends BaseEntity {

    public static final String WEEKLY_UTILIZATION_ID = "weeklyUtilizationId";
    public static final String WEEKLY_UTILIZATION_DESC = "weeklyUtilizationDesc";
    public static final String WEEKLY_UTILIZATION_VALUE = "weeklyUtilizationValue";

    public WeeklyUtilization() {
    }

    public WeeklyUtilization(int weeklyUtilizationId, String weeklyUtilizationDesc, int weeklyUtilizationValue) {
        this.weeklyUtilizationId = weeklyUtilizationId;
        this.weeklyUtilizationDesc = weeklyUtilizationDesc;
        this.weeklyUtilizationValue = weeklyUtilizationValue;
    }

    @DatabaseField(columnName = WEEKLY_UTILIZATION_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int weeklyUtilizationId;

    @DatabaseField(columnName = WEEKLY_UTILIZATION_DESC, canBeNull = false, dataType = DataType.STRING)
    private String weeklyUtilizationDesc;

    @DatabaseField(columnName = WEEKLY_UTILIZATION_VALUE, canBeNull = false, dataType = DataType.INTEGER)
    private int weeklyUtilizationValue;

    public int getWeeklyUtilizationId() {
        return weeklyUtilizationId;
    }

    public void setWeeklyUtilizationId(int weeklyUtilizationId) {
        this.weeklyUtilizationId = weeklyUtilizationId;
    }

    public String getWeeklyUtilizationDesc() {
        return weeklyUtilizationDesc;
    }

    public void setWeeklyUtilizationDesc(String weeklyUtilizationDesc) {
        this.weeklyUtilizationDesc = weeklyUtilizationDesc;
    }

    public int getWeeklyUtilizationValue() {
        return weeklyUtilizationValue;
    }

    public void setWeeklyUtilizationValue(int weeklyUtilizationValue) {
        this.weeklyUtilizationValue = weeklyUtilizationValue;
    }

    @Override
    public String toString() {
        return weeklyUtilizationDesc;
    }
}
