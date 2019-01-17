package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;
import java.util.Date;

@DatabaseTable(tableName = "ConsuptionClass")
public class ConsuptionClass extends BaseEntity {

    public static final String CONSUMPTION_CLASS_ID = "consumptionClassId";
    public static final String CONSUMPTION_CLASS_TYPE = "consumptionClassType";
    public static final String YEARLY_CONSUMPTION = "yearlyConsumption";
    public static final String YEARLY_RANGE_FROM = "yearlyRangeFrom";
    public static final String YEARLY_RANGE_TO = "yearlyRangeTo";
    public static final String MONTLY_COST = "montlyCost";
    public static final String MONTLY_COST_2 = "montlyCost2";
    public static final String MONTLY_COST_3 = "montlyCost3";
    public static final String MONTLY_COST_IVA = "montlyCostIVA";
    public static final String MONTLY_COST_IVA_2 = "montlyCostIVA2";
    public static final String MONTLY_COST_IVA_3 = "montlyCostIVA3";
    public static final String ENERGY_METER = "energy_meter";
    public static final String CLIENT_TYPE = "clientType";
    public static final String VERSION = "version";
    public static final String DISPLAY = "display";
    public static final String DATE_REF = "dateRef";

    public ConsuptionClass() {
    }

    public ConsuptionClass(int consumptionClassId, int consumptionClassType, int yearlyConsumption,
                           int yearlyRangeFrom, Integer yearlyRangeTo, BigDecimal montlyCost,
                           BigDecimal montlyCostIVA, BigDecimal montlyCost2, BigDecimal montlyCostIVA2,
                           BigDecimal montlyCost3, BigDecimal montlyCostIVA3, int energyMeter,
                           Integer clientType, String version, String display, String dateRef) {
        this.consumptionClassId = consumptionClassId;
        this.consumptionClassType = consumptionClassType;
        this.yearlyConsumption = yearlyConsumption;
        this.yearlyRangeFrom = yearlyRangeFrom;
        this.yearlyRangeTo = yearlyRangeTo;
        this.montlyCost = montlyCost;
        this.montlyCostIVA = montlyCostIVA;
        this.montlyCost2 = montlyCost2;
        this.montlyCostIVA2 = montlyCostIVA2;
        this.montlyCost3 = montlyCost3;
        this.montlyCostIVA3 = montlyCostIVA3;
        this.energyMeter = energyMeter;
        this.clientType = clientType;
        this.version = version;
        this.display = display;
        this.dateRef = convertDate(dateRef);
    }

    @DatabaseField(id = true, columnName = CONSUMPTION_CLASS_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int consumptionClassId;
    @DatabaseField(columnName = CONSUMPTION_CLASS_TYPE, canBeNull = false, dataType = DataType.INTEGER)
    private int consumptionClassType;
    @DatabaseField(columnName = YEARLY_CONSUMPTION, canBeNull = false, dataType = DataType.INTEGER)
    private int yearlyConsumption;
    @DatabaseField(columnName = YEARLY_RANGE_FROM, canBeNull = false, dataType = DataType.INTEGER)
    private int yearlyRangeFrom;
    @DatabaseField(columnName = YEARLY_RANGE_TO, dataType = DataType.INTEGER_OBJ)
    private Integer yearlyRangeTo;
    @DatabaseField(columnName = MONTLY_COST, canBeNull = false, dataType = DataType.BIG_DECIMAL)
    private BigDecimal montlyCost;
    @DatabaseField(columnName = MONTLY_COST_IVA, canBeNull = false, dataType = DataType.BIG_DECIMAL)
    private BigDecimal montlyCostIVA;
    @DatabaseField(columnName = MONTLY_COST_2, canBeNull = false, dataType = DataType.BIG_DECIMAL)
    private BigDecimal montlyCost2;
    @DatabaseField(columnName = MONTLY_COST_IVA_2, canBeNull = false, dataType = DataType.BIG_DECIMAL)
    private BigDecimal montlyCostIVA2;
    @DatabaseField(columnName = MONTLY_COST_3, canBeNull = false, dataType = DataType.BIG_DECIMAL)
    private BigDecimal montlyCost3;
    @DatabaseField(columnName = MONTLY_COST_IVA_3, canBeNull = false, dataType = DataType.BIG_DECIMAL)
    private BigDecimal montlyCostIVA3;
    @DatabaseField(columnName = ENERGY_METER, dataType = DataType.INTEGER)
    private int energyMeter;
    @DatabaseField(columnName = CLIENT_TYPE, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer clientType;
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

    public int getConsumptionClassType() {
        return consumptionClassType;
    }

    public void setConsumptionClassType(int consumptionClassType) {
        this.consumptionClassType = consumptionClassType;
    }

    public int getYearlyConsumption() {
        return yearlyConsumption;
    }

    public void setYearlyConsumption(int yearlyConsumption) {
        this.yearlyConsumption = yearlyConsumption;
    }

    public int getYearlyRangeFrom() {
        return yearlyRangeFrom;
    }

    public void setYearlyRangeFrom(int yearlyRangeFrom) {
        this.yearlyRangeFrom = yearlyRangeFrom;
    }

    public Integer getYearlyRangeTo() {
        return yearlyRangeTo;
    }

    public void setYearlyRangeTo(Integer yearlyRangeTo) {
        this.yearlyRangeTo = yearlyRangeTo;
    }

    public int getEnergyMeter() {
        return energyMeter;
    }

    public void setEnergyMeter(int energyMeter) {
        this.energyMeter = energyMeter;
    }

    public BigDecimal getMontlyCost() {
        return montlyCost;
    }

    public void setMontlyCost(BigDecimal montlyCost) {
        this.montlyCost = montlyCost;
    }

    public BigDecimal getMontlyCost2() {
        return montlyCost2;
    }

    public void setMontlyCost2(BigDecimal montlyCost2) {
        this.montlyCost2 = montlyCost2;
    }

    public BigDecimal getMontlyCost3() {
        return montlyCost3;
    }

    public void setMontlyCost3(BigDecimal montlyCost3) {
        this.montlyCost3 = montlyCost3;
    }

    public BigDecimal getMontlyCostIVA() {
        return montlyCostIVA;
    }

    public void setMontlyCostIVA(BigDecimal montlyCostIVA) {
        this.montlyCostIVA = montlyCostIVA;
    }

    public BigDecimal getMontlyCostIVA2() {
        return montlyCostIVA2;
    }

    public void setMontlyCostIVA2(BigDecimal montlyCostIVA2) {
        this.montlyCostIVA2 = montlyCostIVA2;
    }

    public BigDecimal getMontlyCostIVA3() {
        return montlyCostIVA3;
    }

    public void setMontlyCostIVA3(BigDecimal montlyCostIVA3) {
        this.montlyCostIVA3 = montlyCostIVA3;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }
}
