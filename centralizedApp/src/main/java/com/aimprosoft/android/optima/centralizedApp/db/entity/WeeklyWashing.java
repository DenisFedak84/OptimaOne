package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "WeeklyWashing")
public class WeeklyWashing extends BaseEntity {

    public static final String WEEKLY_WASHING_ID = "weeklyWashingId";
    public static final String WEEKLY_WASHING_DESC = "weeklyWashingDesc";
    public static final String WEEKLY_WASHING_VALUE = "weeklyWashingValue";

    public WeeklyWashing() {
    }

    public WeeklyWashing(int weeklyWashingId, String weeklyWashingDesc, int weeklyWashingValue) {
        this.weeklyWashingId = weeklyWashingId;
        this.weeklyWashingDesc = weeklyWashingDesc;
        this.weeklyWashingValue = weeklyWashingValue;
    }

    @DatabaseField(columnName = WEEKLY_WASHING_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int weeklyWashingId;

    @DatabaseField(columnName = WEEKLY_WASHING_DESC, canBeNull = false, dataType = DataType.STRING)
    private String weeklyWashingDesc;

    @DatabaseField(columnName = WEEKLY_WASHING_VALUE, canBeNull = false, dataType = DataType.INTEGER)
    private int weeklyWashingValue;

    public String getWeeklyWashingDesc() {
        return weeklyWashingDesc;
    }

    public void setWeeklyWashingDesc(String weeklyWashingDesc) {
        this.weeklyWashingDesc = weeklyWashingDesc;
    }

    public int getWeeklyWashingId() {
        return weeklyWashingId;
    }

    public void setWeeklyWashingId(int weeklyWashingId) {
        this.weeklyWashingId = weeklyWashingId;
    }

    public int getWeeklyWashingValue() {
        return weeklyWashingValue;
    }

    public void setWeeklyWashingValue(int weeklyWashingValue) {
        this.weeklyWashingValue = weeklyWashingValue;
    }

    @Override
    public String toString() {
        return weeklyWashingDesc;
    }
}
