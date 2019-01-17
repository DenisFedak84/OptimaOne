package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;

@DatabaseTable(tableName = "GasDetailOffers")
public class GasDetailOffers extends BaseEntity {

    public static final String GAS_DETAILS_OFFER_ID = "gasDetailsOfferId";
    public static final String GAS_OFFER_ID = "gasOfferId";
    public static final String IS_EXISTING_CLIENT_OFFER = "isExistingClientOffer";
    public static final String PDR = "pdr";
    public static final String USER_TYPE_ID = "userTypeId";
    public static final String DAY_CLASS_ID = "dayClassId";
    public static final String TOWN_ID = "townId";
    public static final String YEARLY_SMC = "yearlySmc";
    public static final String PROFILO_DI_UTILIZZO = "profiloDiUtilizzo";
    public static final String YEARLY_CONSUMPTION = "yearlyConsumption";
    public static final String FISCAL_CLASS = "fiscalClass";
    public static final String TIPO_CONTRATTO = "tipoContratto";
    public static final String OFFER_COST = "offerCost";
    public static final String OFFER_COSTIVA = "offerCostIVA";
    public static final String COST_VERSION = "costVersion";
    public static final String PMC = "pmc";
    public static final String PS = "ps";
    public static final String CODICE_PMC_PS = "codicePmcPs";
    public static final String COMPUTED_YEARLY_CONSUMPTION = "computed_yearly_consumption";
    public static final String PERCENTAGE = "percentage";
    public static final String VALIDATION_MAP = "validationMap";

    public GasDetailOffers() {
    }

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = GAS_DETAILS_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int gasDetailsOfferId;
    @DatabaseField(columnName = GAS_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int gasOfferId;
    @DatabaseField(columnName = IS_EXISTING_CLIENT_OFFER, canBeNull = false, dataType = DataType.BOOLEAN, defaultValue = "false")
    private boolean isExistingClientOffer;
    @DatabaseField(columnName = USER_TYPE_ID, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "0")
    private int userTypeId;
    @DatabaseField(columnName = PDR, canBeNull = false, dataType = DataType.STRING, defaultValue = " ")
    private String pdr;
    @DatabaseField(columnName = DAY_CLASS_ID, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "0")
    private int dayClassId;
    @DatabaseField(columnName = PROFILO_DI_UTILIZZO, canBeNull = true, dataType = DataType.STRING)
    private String profiloDiUtilizzo;
    @DatabaseField(columnName = TOWN_ID, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "0")
    private int townId;
    @DatabaseField(columnName = YEARLY_SMC, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "0")
    private int yearlySmc;
    @DatabaseField(columnName = YEARLY_CONSUMPTION, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "0")
    private int yearlyConsumption;
    @DatabaseField(columnName = FISCAL_CLASS, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "0")
    private int fiscalClass;
    @DatabaseField(columnName = TIPO_CONTRATTO, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "0")
    private int tipoContratto;
    @DatabaseField(columnName = OFFER_COST, canBeNull = true, dataType = DataType.BIG_DECIMAL, defaultValue = "0")
    private BigDecimal offerCost;
    @DatabaseField(columnName = OFFER_COSTIVA, canBeNull = true, dataType = DataType.BIG_DECIMAL, defaultValue = "0")
    private BigDecimal offerCostIVA;
    @DatabaseField(columnName = COST_VERSION, canBeNull = true, dataType = DataType.STRING, defaultValue = "0")
    private String costVersion;
    @DatabaseField(columnName = PMC, canBeNull = true, dataType = DataType.STRING, defaultValue = "0")
    private String pmc;
    @DatabaseField(columnName = PS, canBeNull = true, dataType = DataType.STRING, defaultValue = "0")
    private String ps;
    @DatabaseField(columnName = CODICE_PMC_PS, canBeNull = true, dataType = DataType.STRING, defaultValue = "0")
    private String codicePmcPS;
    @DatabaseField(columnName = COMPUTED_YEARLY_CONSUMPTION, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "0")
    private int computedYearlyConsumption;
    @DatabaseField(columnName = PERCENTAGE, canBeNull = true, dataType = DataType.FLOAT, defaultValue = "0")
    private float percentage;
    @DatabaseField(columnName = VALIDATION_MAP, canBeNull = false, dataType = DataType.STRING, defaultValue = "")
    private String validationMap;

    public int getTipoContratto() {
        return tipoContratto;
    }

    public void setTipoContratto(int tipoContratto) {
        this.tipoContratto = tipoContratto;
    }

    public String getProfiloDiUtilizzo() {
        return profiloDiUtilizzo;
    }

    public void setProfiloDiUtilizzo(String profiloDiUtilizzo) {
        this.profiloDiUtilizzo = profiloDiUtilizzo;
    }

    public String getValidationMap() {
        return validationMap;
    }

    public void setValidationMap(String validationMap) {
        this.validationMap = validationMap;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public int getComputedYearlyConsumption() {
        return computedYearlyConsumption;
    }

    public void setComputedYearlyConsumption(int computedYearlyConsumption) {
        this.computedYearlyConsumption = computedYearlyConsumption;
    }

    public String getCodicePmcPS() {
        return codicePmcPS;
    }

    public void setCodicePmcPS(String codicePmcPS) {
        this.codicePmcPS = codicePmcPS;
    }

    public String getPmc() {
        return pmc;
    }

    public void setPmc(String pmc) {
        this.pmc = pmc;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public String getCostVersion() {
        return costVersion;
    }

    public void setCostVersion(String costVersion) {
        this.costVersion = costVersion;
    }

    public int getTownId() {
        return townId;
    }

    public void setTownId(int townId) {
        this.townId = townId;
    }

    public int getGasDetailsOfferId() {
        return gasDetailsOfferId;
    }

    public void setGasDetailsOfferId(int gasDetailsOfferId) {
        this.gasDetailsOfferId = gasDetailsOfferId;
    }

    public int getGasOfferId() {
        return gasOfferId;
    }

    public void setGasOfferId(int gasOfferId) {
        this.gasOfferId = gasOfferId;
    }

    public boolean isExistingClientOffer() {
        return isExistingClientOffer;
    }

    public void setExistingClientOffer(boolean existingClientOffer) {
        isExistingClientOffer = existingClientOffer;
    }

    public int getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getPdr() {
        return pdr;
    }

    public void setPdr(String pdr) {
        this.pdr = pdr;
    }

    public int getDayClassId() {
        return dayClassId;
    }

    public void setDayClassId(int dayClassId) {
        this.dayClassId = dayClassId;
    }

    public int getYearlySmc() {
        return yearlySmc;
    }

    public void setYearlySmc(int yearlySmc) {
        this.yearlySmc = yearlySmc;
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
