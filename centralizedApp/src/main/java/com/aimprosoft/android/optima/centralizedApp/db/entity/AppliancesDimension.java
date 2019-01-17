package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "AppliancesDimension")
public class AppliancesDimension extends BaseEntity {

    public static final String REFRIGERATOR_DIMENSION_ID = "refrigeratorDimensionId";
    public static final String REFRIGERATOR_DIMENSION_DESC = "refrigeratorDimensionDesc";
    public static final String REFRIGERATOR_DIMENSION_VALUE = "refrigeratorDimensionValue";

    public AppliancesDimension() {
    }

    public AppliancesDimension(int refrigeratorDimensionId, String refrigeratorDimensionDesc, int refrigeratorDimensionValue) {
        this.refrigeratorDimensionId = refrigeratorDimensionId;
        this.refrigeratorDimensionDesc = refrigeratorDimensionDesc;
        this.refrigeratorDimensionValue = refrigeratorDimensionValue;
    }

    @DatabaseField(columnName = REFRIGERATOR_DIMENSION_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int refrigeratorDimensionId;

    @DatabaseField(columnName = REFRIGERATOR_DIMENSION_DESC, canBeNull = false, dataType = DataType.STRING)
    private String refrigeratorDimensionDesc;

    @DatabaseField(columnName = REFRIGERATOR_DIMENSION_VALUE, canBeNull = false, dataType = DataType.INTEGER)
    private int refrigeratorDimensionValue;

    public int getRefrigeratorDimensionId() {
        return refrigeratorDimensionId;
    }

    public void setRefrigeratorDimensionId(int refrigeratorDimensionId) {
        this.refrigeratorDimensionId = refrigeratorDimensionId;
    }

    public String getRefrigeratorDimensionDesc() {
        return refrigeratorDimensionDesc;
    }

    public void setRefrigeratorDimensionDesc(String refrigeratorDimensionDesc) {
        this.refrigeratorDimensionDesc = refrigeratorDimensionDesc;
    }

    public String toString() {
        return refrigeratorDimensionDesc;
    }

    public int getRefrigeratorDimensionValue() {
        return refrigeratorDimensionValue;
    }

    public void setRefrigeratorDimensionValue(int refrigeratorDimensionValue) {
        this.refrigeratorDimensionValue = refrigeratorDimensionValue;
    }
}
