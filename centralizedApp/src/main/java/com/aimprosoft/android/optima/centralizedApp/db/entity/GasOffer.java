package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;

@DatabaseTable(tableName = "GasOffer")
public class GasOffer extends BaseEntity {

    public static final String GAS_OFFER_ID = "gasOfferId";
    public static final String YEARLY_CONSUMPTION = "yearlyConsumption";
    public static final String OFFER_COST = "offerCost";
    public static final String OFFER_COST_IVA = "offerCostIVA";
    public static final String QUESTIONARIO_USING = "questionarioUsing";

    public GasOffer() {
    }

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = GAS_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int gasOfferId;

    @DatabaseField(columnName = YEARLY_CONSUMPTION, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "0")
    private int yearlyConsumption;

    @DatabaseField(columnName = OFFER_COST, canBeNull = true, dataType = DataType.BIG_DECIMAL, defaultValue = "0")
    private BigDecimal offerCost;

    @DatabaseField(columnName = OFFER_COST_IVA, canBeNull = true, dataType = DataType.BIG_DECIMAL, defaultValue = "0")
    private BigDecimal offerCostIVA;

    @DatabaseField(columnName = QUESTIONARIO_USING, canBeNull = false, dataType = DataType.BOOLEAN)
    private boolean questionarioUsing;

    public boolean isQuestionarioUsing() {
        return questionarioUsing;
    }

    public void setQuestionarioUsing(boolean questionarioUsing) {
        this.questionarioUsing = questionarioUsing;
    }

    public int getGasOfferId() {
        return gasOfferId;
    }

    public void setGasOfferId(int gasOfferId) {
        this.gasOfferId = gasOfferId;
    }

    public int getYearlyConsumption() {
        return yearlyConsumption;
    }

    public void setYearlyConsumption(int yearlyConsumption) {
        this.yearlyConsumption = yearlyConsumption;
    }

    public BigDecimal getOfferCost() {
        return offerCost;
    }

    public void setOfferCost(BigDecimal offerCost) {
        this.offerCost = offerCost;
    }

    public BigDecimal getOfferCostIVA() {
        return offerCostIVA;
    }

    public void setOfferCostIVA(BigDecimal offerCostIVA) {
        this.offerCostIVA = offerCostIVA;
    }
}
