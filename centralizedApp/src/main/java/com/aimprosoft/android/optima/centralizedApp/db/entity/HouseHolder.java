package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "HouseHolders")
public class HouseHolder extends BaseEntity {

    public static final String HOUSE_HOLDER_ID = "houseHolderId";
    public static final String HOUSE_HOLDER_DESC = "houseHolderDesc";
    public static final String HOUSE_HOLDER_VALUE = "houseHolderValue";

    public HouseHolder() {
    }

    public HouseHolder(int houseHolderId, String houseHolderDesc, int houseHolderValue) {
        this.houseHolderId = houseHolderId;
        this.houseHolderDesc = houseHolderDesc;
        this.houseHolderValue = houseHolderValue;
    }

    @DatabaseField(columnName = HOUSE_HOLDER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int houseHolderId;

    @DatabaseField(columnName = HOUSE_HOLDER_DESC, canBeNull = false, dataType = DataType.STRING)
    private String houseHolderDesc;

    @DatabaseField(columnName = HOUSE_HOLDER_VALUE, canBeNull = false, dataType = DataType.INTEGER)
    private int houseHolderValue;

    public int getHouseHolderId() {
        return houseHolderId;
    }

    public void setHouseHolderId(int houseHolderId) {
        this.houseHolderId = houseHolderId;
    }

    public String getHouseHolderDesc() {
        return houseHolderDesc;
    }

    public void setHouseHolderDesc(String houseHolderDesc) {
        this.houseHolderDesc = houseHolderDesc;
    }

    public int getHouseHolderValue() {
        return houseHolderValue;
    }

    public void setHouseHolderValue(int houseHolderValue) {
        this.houseHolderValue = houseHolderValue;
    }

    @Override
    public String toString() {
        return houseHolderDesc + "";
    }
}
