package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "GasUtilizationProfileClass")
public class GasUtilizationProfileClass extends BaseEntity {

    public static final String CLASS_ID = "classId";
    public static final String VALIDATION_MAP = "validationMap";
    public static final String CLASS_CODE = "classCode";

    public GasUtilizationProfileClass(int classId, String validationMap, String classCode) {
        this.classId = classId;
        this.validationMap = validationMap;
        this.classCode = classCode;
    }

    public GasUtilizationProfileClass() {
    }

    @DatabaseField(columnName = CLASS_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int classId;

    @DatabaseField(columnName = VALIDATION_MAP, canBeNull = false, dataType = DataType.STRING)
    private String validationMap;

    @DatabaseField(columnName = CLASS_CODE, canBeNull = false, dataType = DataType.STRING)
    private String classCode;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getValidationMap() {
        return validationMap;
    }

    public void setValidationMap(String validationMap) {
        this.validationMap = validationMap;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }
}
