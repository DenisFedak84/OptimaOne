package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "HouseMontlyUse")
public class HouseMontlyUse extends BaseEntity {

    public static final String HOUSE_MONTLY_USE_ID = "houseMontlyUseId";
    public static final String HOUSE_MONTLY_USE_DESC = "houseMontlyUseDesc";
    public static final String HOUSE_MONTLY_USE_VALUE = "houseMontlyUseValue";

    public HouseMontlyUse() {
    }

    public HouseMontlyUse(int houseMontlyUseId, String houseMontlyUseDesc, int houseMontlyUseValue) {
        this.houseMontlyUseId = houseMontlyUseId;
        this.houseMontlyUseDesc = houseMontlyUseDesc;
        this.houseMontlyUseValue = houseMontlyUseValue;
    }

    @DatabaseField(generatedId = true, columnName = HOUSE_MONTLY_USE_ID, canBeNull = false, dataType = DataType.INTEGER)
    int houseMontlyUseId;
    @DatabaseField(columnName = HOUSE_MONTLY_USE_DESC, canBeNull = false, dataType = DataType.STRING)
    String houseMontlyUseDesc;
    @DatabaseField(columnName = HOUSE_MONTLY_USE_VALUE, canBeNull = false, dataType = DataType.INTEGER)
    int houseMontlyUseValue;

    public int getHouseMontlyUseId() {
        return houseMontlyUseId;
    }

    public void setHouseMontlyUseId(int houseMontlyUseId) {
        this.houseMontlyUseId = houseMontlyUseId;
    }

    public String getHouseMontlyUseDesc() {
        return houseMontlyUseDesc;
    }

    public void setHouseMontlyUseDesc(String houseMontlyUseDesc) {
        this.houseMontlyUseDesc = houseMontlyUseDesc;
    }

    public int getHouseMontlyUseValue() {
        return houseMontlyUseValue;
    }

    public void setHouseMontlyUseValue(int houseMontlyUseValue) {
        this.houseMontlyUseValue = houseMontlyUseValue;
    }

    @Override
    public String toString() {
        return houseMontlyUseDesc;
    }
}
