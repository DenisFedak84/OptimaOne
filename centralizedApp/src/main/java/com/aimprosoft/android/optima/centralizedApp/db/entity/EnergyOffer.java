package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;

@DatabaseTable(tableName = "EnergyOffer")
public class EnergyOffer extends BaseEntity {

    public static final String ENERGY_OFFER_ID = "energyOfferId";
    public static final String YEARLY_KWH = "yearlyKwh";
    public static final String YEARLY_CONSUMPTION = "yearly_consumption";
    public static final String OFFER_COST = "offerCost";
    public static final String OFFER_COST_IVA = "offerCostIva";
    public static final String QUESTIONARIO_USING = "questionarioUsing";

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = ENERGY_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int energyOfferId;
    @DatabaseField(columnName = YEARLY_KWH, canBeNull = false, dataType = DataType.INTEGER)
    private int yearlyKwh;
    @DatabaseField(columnName = YEARLY_CONSUMPTION, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "0")
    private int yearlyConsumption;
    @DatabaseField(columnName = OFFER_COST, canBeNull = true, dataType = DataType.BIG_DECIMAL)
    private BigDecimal offerCost;
    @DatabaseField(columnName = OFFER_COST_IVA, canBeNull = true, dataType = DataType.BIG_DECIMAL)
    private BigDecimal offerCostIVA;
    @DatabaseField(columnName = QUESTIONARIO_USING, canBeNull = false, dataType = DataType.BOOLEAN)
    private boolean questionarioUsing;

    public boolean isQuestionarioUsed() {
        return questionarioUsing;
    }

    public void setQuestionarioUsing(boolean questionarioUsing) {
        this.questionarioUsing = questionarioUsing;
    }

    public BigDecimal getOfferCostIVA() {
        return offerCostIVA;
    }

    public void setOfferCostIVA(BigDecimal offerCostIVA) {
        this.offerCostIVA = offerCostIVA;
    }

    public BigDecimal getOfferCost() {
        return offerCost;
    }

    public void setOfferCost(BigDecimal offerCost) {
        this.offerCost = offerCost;
    }

    public int getYearlyConsumption() {
        return yearlyConsumption;
    }

    public void setYearlyConsumption(int yearlyConsumption) {
        this.yearlyConsumption = yearlyConsumption;
    }

    public int getYearlyKwh() {
        return yearlyKwh;
    }

    public void setYearlyKwh(int yearlyKwh) {
        this.yearlyKwh = yearlyKwh;
    }

    public int getEnergyOfferId() {
        return energyOfferId;
    }

    public void setEnergyOfferId(int energyOfferId) {
        this.energyOfferId = energyOfferId;
    }
}
