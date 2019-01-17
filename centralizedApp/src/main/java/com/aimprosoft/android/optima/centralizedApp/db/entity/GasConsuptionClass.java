package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;
import java.util.Date;

@DatabaseTable(tableName = "GasConsuptionClass")
public class GasConsuptionClass extends BaseEntity {

    public static final String CONSUMPTION_CLASS_ID = "consumptionClassId";
    public static final String YEARLY_CONSUMPTION = "yearlyConsumption";
    public static final String YEARLY_RANGE_FROM = "yearlyRangeFrom";
    public static final String YEARLY_RANGE_TO = "yearlyRangeTo";
    public static final String TARIFF_ZONE = "tariffZone";
    public static final String FISCAL_CLASS = "fiscalClass";
    public static final String MONTLY_COST = "montlyCost";
    public static final String MONTLY_COST_IVA = "montlyCostIVA";
    public static final String VERSION = "version";
    public static final String DISPLAY = "display";
    public static final String DATE_REF = "dateRef";

    public GasConsuptionClass(int consumptionClassId, int yearlyConsumption, int yearlyRangeFrom,
                              Integer yearlyRangeTo, String tariffZone, int fiscalClass,
                              BigDecimal montlyCost, BigDecimal montlyCostIVA, String version,
                              String display, String dateRef) {
        this.consumptionClassId = consumptionClassId;
        this.yearlyConsumption = yearlyConsumption;
        this.yearlyRangeFrom = yearlyRangeFrom;
        this.yearlyRangeTo = yearlyRangeTo;
        this.tariffZone = tariffZone;
        this.fiscalClass = fiscalClass;
        this.montlyCost = montlyCost;
        this.montlyCostIVA = montlyCostIVA;
        this.version = version;
        this.display = display;
        this.dateRef = convertDate(dateRef);
    }

    public GasConsuptionClass() {
    }

    @DatabaseField(columnName = CONSUMPTION_CLASS_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int consumptionClassId;

    @DatabaseField(columnName = YEARLY_CONSUMPTION, canBeNull = false, dataType = DataType.INTEGER)
    private int yearlyConsumption;

    @DatabaseField(columnName = YEARLY_RANGE_FROM, canBeNull = false, dataType = DataType.INTEGER)
    private int yearlyRangeFrom;

    @DatabaseField(columnName = YEARLY_RANGE_TO, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer yearlyRangeTo;

    @DatabaseField(columnName = TARIFF_ZONE, canBeNull = false, dataType = DataType.STRING)
    private String tariffZone;

    @DatabaseField(columnName = FISCAL_CLASS, canBeNull = false, dataType = DataType.INTEGER)
    private int fiscalClass;

    @DatabaseField(columnName = MONTLY_COST, canBeNull = false, dataType = DataType.BIG_DECIMAL)
    private BigDecimal montlyCost;

    @DatabaseField(columnName = MONTLY_COST_IVA, canBeNull = false, dataType = DataType.BIG_DECIMAL)
    private BigDecimal montlyCostIVA;

    @DatabaseField(columnName = VERSION, canBeNull = false, dataType = DataType.STRING)
    private String version;

    @DatabaseField(columnName = DISPLAY, canBeNull = false, dataType = DataType.STRING)
    private String display;

    @DatabaseField(columnName = DATE_REF, canBeNull = false, dataType = DataType.DATE)
    private Date dateRef;

    public Date getDateRef() {
        return dateRef;
    }

    public void setDateRef(Date dateRef) {
        this.dateRef = dateRef;
    }

    public void setYearlyRangeTo(Integer yearlyRangeTo) {
        this.yearlyRangeTo = yearlyRangeTo;
    }

    public BigDecimal getMontlyCostIVA() {
        return montlyCostIVA;
    }

    public void setMontlyCostIVA(BigDecimal montlyCostIVA) {
        this.montlyCostIVA = montlyCostIVA;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public int getConsumptionClassId() {
        return consumptionClassId;
    }

    public void setConsumptionClassId(int consumptionClassId) {
        this.consumptionClassId = consumptionClassId;
    }

    public int getYearlyConsumption() {
        return yearlyConsumption;
    }

    public void setYearlyConsumption(int yearlyConsumption) {
        this.yearlyConsumption = yearlyConsumption;
    }

    public int getFiscalClass() {
        return fiscalClass;
    }

    public void setFiscalClass(int fiscalClass) {
        this.fiscalClass = fiscalClass;
    }

    public int getYearlyRangeFrom() {
        return yearlyRangeFrom;
    }

    public void setYearlyRangeFrom(int yearlyRangeFrom) {
        this.yearlyRangeFrom = yearlyRangeFrom;
    }

    public int getYearlyRangeTo() {
        return yearlyRangeTo;
    }

    public void setYearlyRangeTo(int yearlyRangeTo) {
        this.yearlyRangeTo = yearlyRangeTo;
    }

    public BigDecimal getMontlyCost() {
        return montlyCost;
    }

    public void setMontlyCost(BigDecimal montlyCost) {
        this.montlyCost = montlyCost;
    }

    public String getTariffZone() {
        return tariffZone;
    }

    public void setTariffZone(String tariffZone) {
        this.tariffZone = tariffZone;
    }

    public BigDecimal getMmontlyCostIVA() {
        return montlyCostIVA;
    }

    public void setMmontlyCostIVA(BigDecimal mmontlyCostIVA) {
        this.montlyCostIVA = mmontlyCostIVA;
    }
}
