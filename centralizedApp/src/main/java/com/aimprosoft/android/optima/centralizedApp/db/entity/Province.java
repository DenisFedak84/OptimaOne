package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Province")
public class Province extends BaseEntity {

    public static final String PROVINCE_ID = "provinceId";
    public static final String PROVINCE_DESC = "provinceDesc";

    public Province() {
    }

    public Province(int provinceId, String provinceDesc) {
        this.provinceId = provinceId;
        this.provinceDesc = provinceDesc;
    }

    @DatabaseField(columnName = PROVINCE_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int provinceId;

    @DatabaseField(columnName = PROVINCE_DESC, canBeNull = false, dataType = DataType.STRING)
    private String provinceDesc;


    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceDesc() {
        return provinceDesc;
    }

    public void setProvinceDesc(String provinceDesc) {
        this.provinceDesc = provinceDesc;
    }
}
