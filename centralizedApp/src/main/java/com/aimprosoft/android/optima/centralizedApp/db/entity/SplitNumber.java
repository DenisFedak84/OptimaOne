package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "SplitNumber")
public class SplitNumber extends BaseEntity {

    public static final String SPLIT_NUMBER_ID = "splitNumberId";
    public static final String SPLIT_NUMBER_VALUE = "splitNumberValue";

    public SplitNumber() {
    }

    public SplitNumber(int splitNumberId, int splitNumberValue) {
        this.splitNumberId = splitNumberId;
        this.splitNumberValue = splitNumberValue;
    }

    @DatabaseField(columnName = SPLIT_NUMBER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int splitNumberId;

    @DatabaseField(columnName = SPLIT_NUMBER_VALUE, canBeNull = false, dataType = DataType.INTEGER)
    private int splitNumberValue;

    public int getSplitNumberId() {
        return splitNumberId;
    }

    public void setSplitNumberId(int splitNumberId) {
        this.splitNumberId = splitNumberId;
    }

    public int getSplitNumberValue() {
        return splitNumberValue;
    }

    public void setSplitNumberValue(int splitNumberValue) {
        this.splitNumberValue = splitNumberValue;
    }

    @Override
    public String toString() {
        return splitNumberValue + "";
    }
}
