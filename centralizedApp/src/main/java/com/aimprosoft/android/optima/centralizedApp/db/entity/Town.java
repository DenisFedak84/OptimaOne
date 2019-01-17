package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Towns")
public class Town extends BaseEntity {

    public static final String TOWN_ID = "townId";
    public static final String TOWN_DESC = "townDesc";
    public static final String NATIONAL_CODE = "nationalCode";
    public static final String PROVINCE_DESC = "provinceDesc";
    public static final String CLIMATIC_ZONE = "climaticZone";
    public static final String TARIFF_ZONE = "tariffZone";
    public static final String GG = "gg";
    public static final String TOWN_COEFFICIENT = "coefficient";
    public static final String TOWN_DESC_CENTRALIZED = "townDescCentralized";

    public Town() {
    }

    @DatabaseField(columnName = TOWN_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int townId;
    @DatabaseField(columnName = TOWN_DESC, canBeNull = false, dataType = DataType.STRING)
    private String townDesc;
    @DatabaseField(columnName = NATIONAL_CODE, canBeNull = false, dataType = DataType.STRING)
    private String nationalCode;
    @DatabaseField(columnName = PROVINCE_DESC, canBeNull = false, dataType = DataType.STRING)
    private String provinceDesc;
    @DatabaseField(columnName = CLIMATIC_ZONE, canBeNull = false, dataType = DataType.STRING)
    private String climaticZone;
    @DatabaseField(columnName = TARIFF_ZONE, canBeNull = false, dataType = DataType.STRING)
    private String tariffZone;
    @DatabaseField(columnName = GG, canBeNull = false, dataType = DataType.INTEGER)
    private int gg;
    @DatabaseField(columnName = TOWN_COEFFICIENT, canBeNull = false, dataType = DataType.DOUBLE)
    private double сoefficient;
    @DatabaseField(columnName = TOWN_DESC_CENTRALIZED, canBeNull = false, dataType = DataType.STRING)
    private String townDescCentralized;

    public String getTownDescCentralized() {
        return townDescCentralized;
    }

    public void setTownDescCentralized(String townDescCentralized) {
        this.townDescCentralized = townDescCentralized;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public double getСoefficient() {
        return сoefficient;
    }

    public void setСoefficient(double сoefficient) {
        this.сoefficient = сoefficient;
    }

    public int getTownId() {
        return townId;
    }

    public void setTownId(int townId) {
        this.townId = townId;
    }

    public String getTownDesc() {
        return townDesc;
    }

    public void setTownDesc(String townDesc) {
        this.townDesc = townDesc;
    }

    public String getClimaticZone() {
        return climaticZone;
    }

    public void setClimaticZone(String climaticZone) {
        this.climaticZone = climaticZone;
    }

    public String getTariffZone() {
        return tariffZone;
    }

    public void setTariffZone(String tariffZone) {
        this.tariffZone = tariffZone;
    }

    public int getGg() {
        return gg;
    }

    public void setGg(int gg) {
        this.gg = gg;
    }

    public String getProvinceDesc() {
        return provinceDesc;
    }

    public void setProvinceDesc(String provinceDesc) {
        this.provinceDesc = provinceDesc;
    }

    @Override
    public String toString() {
        return getTownDesc() + "(" + getProvinceDesc() + ")";
    }
}
