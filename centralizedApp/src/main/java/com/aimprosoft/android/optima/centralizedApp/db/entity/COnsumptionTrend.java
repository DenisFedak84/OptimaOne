package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;
import java.util.Date;

@DatabaseTable(tableName = "COnsumptionTrend")
public class COnsumptionTrend extends BaseEntity {

    public static final String DATE = "date";
    public static final String CODE_ZONE = "codeZone";
    public static final String PERC_VALUE = "percValue";

    public COnsumptionTrend(String date, String codeZone, BigDecimal percValue) {
        this.date = convertDate(date);
        this.codeZone = codeZone;
        this.percValue = percValue;
    }

    public COnsumptionTrend() {
    }

    @DatabaseField(columnName = DATE, dataType = DataType.DATE)
    private Date date;


    @DatabaseField(columnName = CODE_ZONE, canBeNull = false, dataType = DataType.STRING)
    private String codeZone;

    @DatabaseField(columnName = PERC_VALUE, canBeNull = false, dataType = DataType.BIG_DECIMAL)
    private BigDecimal percValue;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCodeZone() {
        return codeZone;
    }

    public void setCodeZone(String codeZone) {
        this.codeZone = codeZone;
    }

    public BigDecimal getPercValue() {
        return percValue;
    }

    public void setPercValue(BigDecimal percValue) {
        this.percValue = percValue;
    }

}
