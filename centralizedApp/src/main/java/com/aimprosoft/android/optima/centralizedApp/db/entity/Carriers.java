package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Carriers")
public class Carriers extends BaseEntity {

    public static final String CARRIER_ID = "carrierId";
    public static final String CARRIER_DESC = "carrierDesc";
    public static final String SYSTEM = "system";
    public static final String CODE = "code";

    public Carriers() {
    }

    public Carriers(int carrierId, String carrierDesc, String system, String code) {
        this.carrierId = carrierId;
        this.carrierDesc = carrierDesc;
        this.system = system;
        this.code = code;
    }

    @DatabaseField(id = true, columnName = CARRIER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int carrierId;

    @DatabaseField(columnName = CARRIER_DESC, canBeNull = false, dataType = DataType.STRING)
    private String carrierDesc;

    @DatabaseField(columnName = SYSTEM, canBeNull = true, dataType = DataType.STRING)
    private String system;

    @DatabaseField(columnName = CODE, canBeNull = true, dataType = DataType.STRING)
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public int getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(int carrierId) {
        this.carrierId = carrierId;
    }

    public String getCarrierDesc() {
        return carrierDesc;
    }

    public void setCarrierDesc(String carrierDesc) {
        this.carrierDesc = carrierDesc;
    }

    @Override
    public String toString() {
        return carrierDesc;
    }
}
