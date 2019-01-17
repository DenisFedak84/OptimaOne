package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;

@DatabaseTable(tableName = "EnergyOfferDetails")
public class EnergyOfferDetails extends BaseEntity {

    public static final String ENERGY_DETAIL_OFFER_ID = "energyDetailOfferId";
    public static final String ENERGY_OFFER_ID = "energyOfferId";
    public static final String IS_EXISTING_CLIENT_OFFER = "isExistingClientOffer";
    public static final String POD = "pod";
    public static final String REMOTE_SERVICE_STATUS = "remoteServiceStatus";
    public static final String ENERGY_METER = "energyMeter";
    public static final String CONSUMPTION_CLASS_TYPE_ID = "consumptionClassTypeId";
    public static final String CLIENT_TYPE = "clientType";
    public static final String YEARLY_KWH = "yearlyKwh";
    public static final String YEARLY_KWH2 = "yearlyKwh2";
    public static final String YEARLY_KWH3 = "yearlyKwh3";
    public static final String YEARLY_CONSUMPTION = "yearly_consumption";
    public static final String OFFER_COST = "offerCost";
    public static final String OFFER_COST_IVA = "offerCostIva";
    public static final String TENSIONE = "tensione";
    public static final String TARIFFA_ID = "tariffaId";
    public static final String COST_VERSION = "costVersion";
    public static final String PMC = "pmc";
    public static final String PS = "ps";
    public static final String CONTRACT_TYPE = "contractType";
    public static final String POTENZA_STIMATA = "potenzaStimata";
    public static final String CODICE_PMC_PS = "codicePmcPs";
    public static final String COMPUTED_YEARLY_CONSUMPTION = "computed_yearly_consumption";
    public static final String PERCENTAGE = "percentage";
    public static final String WATER_HEATER_PURCHASE_YEAR = "waterHeaterPurchaseYear";
    public static final String WATER_HEATER_HOURS = "waterHeaterHours";
    public static final String AIR_CONDITIONER_SPLIT_ID = "airConditionerSplitId";
    public static final String AIR_CONDITIONER_HOURS_ID = "airConditionerHoursId";
    public static final String AIR_CONDITIONER_PURCHASE_YEAR = "airConditionerPurchaseYear";
    public static final String AIR_CONDITIONER_WINTER_HOURS_ID = "airConditionerWinterHoursId";
    public static final String ASCIUGATRICE_OVEN_WEEKLY_USE = "asciugatriceOvenWeeklyUse";
    public static final String ASCIUGATRICE_OVEN_ENERGY_CLASS = "asciugatriceOvenEnergyClass";
    public static final String ASCIUGATRICE_OVEN_PURCHASE_YEAR = "asciugatriceOvenPurchaseYear";
    public static final String OVEN_WEEKLY_USE = "ovenWeeklyUse";
    public static final String OVEN_ENERGY_CLASS = "ovenEnergyClass";
    public static final String OVEN_PURCHASE_YEAR = "ovenPurchaseYear";
    public static final String WASHING_MACHINE_WEEKLY_USE = "washingMachineWeeklyUse";
    public static final String WASHING_MACHINE_ENERGY_CLASS = "washingMachineEnergyClass";
    public static final String WASHING_MACHINE_PURCHASE_YEAR = "washingMachinePurchaseYear";
    public static final String DISHWASHER_WEEKLY_USE = "dishwasherWeeklyUse";
    public static final String DISHWASHER_ENERGY_CLASS = "dishwasherEnergyClass";
    public static final String DISHWASHER_PURCHASE_YEAR = "dishwasherPurchaseYear";
    public static final String REFRIGERATOR_WEEKLY_USE = "refrigeratorWeeklyUse";
    public static final String REFRIGERATOR_ENERGY_CLASS = "refrigeratorEnergyClass";
    public static final String REFRIGERATOR_PURCHASE_YEAR = "refrigeratorPurchaseYear";
    public static final String OTHER_DEVICE_WATT = "otherDeviceWatt";
    public static final String OTHER_DEVICE_WEEKLY_HOUR_USE = "otherDeviceWeeklyHourUse";
    public static final String LIGHTING_TYPE = "lightingType";
    public static final String ENERGY_METER2 = "energyMeter2";
    public final static String SUM_ATTIVA = "sumAttiva";
    public final static String COUNT_MESE = "countMese";
    public final static String COUNT_POD = "countPod";
    public static final String QUESTIONARIO_USING = "questionarioUsing";
    public static final String QUESTIONARIO_YEARLY_KWH = "questionarioYearlyKwh";
    public static final String POTENZA_REAL = "potenzaReal";
    public static final String WEB_SERVICE_RESULT = "webServiceResult";

    public EnergyOfferDetails() {
    }

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = ENERGY_DETAIL_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int energyDetailOfferId;
    @DatabaseField(columnName = ENERGY_OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int energyOfferId;
    @DatabaseField(columnName = IS_EXISTING_CLIENT_OFFER, canBeNull = false, dataType = DataType.BOOLEAN, defaultValue = "false")
    private boolean isExistingClientOffer;
    @DatabaseField(columnName = REMOTE_SERVICE_STATUS, canBeNull = false, dataType = DataType.BOOLEAN, defaultValue = "false")
    private boolean remoteServiceStatus;
    @DatabaseField(columnName = POD, canBeNull = false, dataType = DataType.STRING, defaultValue = " ")
    private String pod;
    @DatabaseField(columnName = ENERGY_METER, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "0")
    private int energyMeter;
    @DatabaseField(columnName = CONSUMPTION_CLASS_TYPE_ID, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "0")
    private int consumptioClassTypeId;
    @DatabaseField(columnName = CLIENT_TYPE, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "0")
    private int clientType;
    @DatabaseField(columnName = YEARLY_KWH, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer yearlyKwh;
    @DatabaseField(columnName = YEARLY_KWH2, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer yearlyKwh2;
    @DatabaseField(columnName = YEARLY_KWH3, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer yearlyKwh3;
    @DatabaseField(columnName = YEARLY_CONSUMPTION, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "0")
    private int yearlyConsumption;
    @DatabaseField(columnName = TENSIONE, canBeNull = true, dataType = DataType.INTEGER)
    private int tensione;
    @DatabaseField(columnName = OFFER_COST, canBeNull = true, dataType = DataType.BIG_DECIMAL, defaultValue = "0")
    private BigDecimal offerCost;
    @DatabaseField(columnName = OFFER_COST_IVA, canBeNull = true, dataType = DataType.BIG_DECIMAL, defaultValue = "0")
    private BigDecimal offerCostIVA;
    @DatabaseField(columnName = COST_VERSION, canBeNull = true, dataType = DataType.STRING, defaultValue = "0")
    private String costVersion;
    @DatabaseField(columnName = TARIFFA_ID, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "0")
    private int tariffaId;
    @DatabaseField(columnName = PMC, canBeNull = true, dataType = DataType.STRING, defaultValue = "0")
    private String pmc;
    @DatabaseField(columnName = PS, canBeNull = true, dataType = DataType.STRING, defaultValue = "0")
    private String ps;
    @DatabaseField(columnName = POTENZA_STIMATA, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "0")
    private int potenzaStimata;
    @DatabaseField(columnName = CODICE_PMC_PS, canBeNull = true, dataType = DataType.STRING, defaultValue = "0")
    private String codicePmcPS;
    @DatabaseField(columnName = COMPUTED_YEARLY_CONSUMPTION, canBeNull = false, dataType = DataType.INTEGER, defaultValue = "0")
    private int computedYearlyConsumption;
    @DatabaseField(columnName = PERCENTAGE, canBeNull = true, dataType = DataType.FLOAT, defaultValue = "0")
    private float percentage;
    @DatabaseField(columnName = CONTRACT_TYPE, canBeNull = true, dataType = DataType.INTEGER_OBJ, defaultValue = "0")
    private Integer tipologiaContratto;
    @DatabaseField(columnName = WATER_HEATER_HOURS, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer waterHeaterHours;
    @DatabaseField(columnName = WATER_HEATER_PURCHASE_YEAR, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer waterHeaterPurchaseYear;
    @DatabaseField(columnName = AIR_CONDITIONER_SPLIT_ID, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer airConditionerSplitId;
    @DatabaseField(columnName = AIR_CONDITIONER_HOURS_ID, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer airConditionerHoursId;
    @DatabaseField(columnName = AIR_CONDITIONER_PURCHASE_YEAR, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer airConditionerPurchaseYear;
    @DatabaseField(columnName = AIR_CONDITIONER_WINTER_HOURS_ID, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer airConditionerWinterHoursId;
    @DatabaseField(columnName = OVEN_WEEKLY_USE, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer ovenWeeklyUse;
    @DatabaseField(columnName = OVEN_ENERGY_CLASS, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer ovenEnergyClass;
    @DatabaseField(columnName = OVEN_PURCHASE_YEAR, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer ovenPurchaseYear;
    @DatabaseField(columnName = ASCIUGATRICE_OVEN_WEEKLY_USE, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer asciugatriceOvenWeeklyUse;
    @DatabaseField(columnName = ASCIUGATRICE_OVEN_ENERGY_CLASS, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer asciugatriceOvenEnergyClass;
    @DatabaseField(columnName = ASCIUGATRICE_OVEN_PURCHASE_YEAR, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer asciugatriceOvenPurchaseYear;
    @DatabaseField(columnName = WASHING_MACHINE_WEEKLY_USE, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer washingMachineWeeklyUse;
    @DatabaseField(columnName = WASHING_MACHINE_ENERGY_CLASS, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer washingMachineEnergyClass;
    @DatabaseField(columnName = WASHING_MACHINE_PURCHASE_YEAR, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer washingMachinePurchaseYear;
    @DatabaseField(columnName = DISHWASHER_WEEKLY_USE, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer dishwasherWeeklyUse;
    @DatabaseField(columnName = DISHWASHER_ENERGY_CLASS, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer dishwasherEnergyClass;
    @DatabaseField(columnName = DISHWASHER_PURCHASE_YEAR, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer dishwasherPurchaseYear;
    @DatabaseField(columnName = REFRIGERATOR_WEEKLY_USE, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer refrigeratorWeeklyUse;
    @DatabaseField(columnName = REFRIGERATOR_ENERGY_CLASS, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer refrigeratorEnergyClass;
    @DatabaseField(columnName = REFRIGERATOR_PURCHASE_YEAR, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer refrigeratorPurchaseYear;
    @DatabaseField(columnName = OTHER_DEVICE_WATT, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer otherDeviceWatt;
    @DatabaseField(columnName = OTHER_DEVICE_WEEKLY_HOUR_USE, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer otherDeviceWeeklyHourUse;
    @DatabaseField(columnName = LIGHTING_TYPE, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer lightingType;
    @DatabaseField(columnName = SUM_ATTIVA, canBeNull = true, dataType = DataType.DOUBLE_OBJ, defaultValue = "0")
    private Double sumAttiva;
    @DatabaseField(columnName = COUNT_MESE, canBeNull = true, dataType = DataType.INTEGER_OBJ, defaultValue = "0")
    private Integer countMese;
    @DatabaseField(columnName = COUNT_POD, canBeNull = true, dataType = DataType.INTEGER_OBJ, defaultValue = "0")
    private Integer countPod;
    @DatabaseField(columnName = QUESTIONARIO_YEARLY_KWH, canBeNull = false, dataType = DataType.INTEGER)
    private int questionarioYearlyKwh;
    @DatabaseField(columnName = QUESTIONARIO_USING, canBeNull = false, dataType = DataType.BOOLEAN)
    private boolean questionarioUsing;
    @DatabaseField(columnName = ENERGY_METER2, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer energyMeter2;
    @DatabaseField(columnName = POTENZA_REAL, canBeNull = false, dataType = DataType.INTEGER)
    private int potenzaReal;
    @DatabaseField(columnName = WEB_SERVICE_RESULT, canBeNull = true, dataType = DataType.STRING)
    private String webServiceResult;


    public String getWebServiceResult() {
        return webServiceResult;
    }

    public void setWebServiceResult(String webServiceResult) {
        this.webServiceResult = webServiceResult;
    }

    public boolean isRemoteServiceStatus() {
        return remoteServiceStatus;
    }

    public void setRemoteServiceStatus(boolean remoteServiceStatus) {
        this.remoteServiceStatus = remoteServiceStatus;
    }

    public int getPotenzaReal() {
        return potenzaReal;
    }

    public void setPotenzaReal(int potenzaReal) {
        this.potenzaReal = potenzaReal;
    }

    public Integer getWaterHeaterHours() {
        return waterHeaterHours;
    }

    public void setWaterHeaterHours(Integer waterHeaterHours) {
        this.waterHeaterHours = waterHeaterHours;
    }

    public Integer getWaterHeaterPurchaseYear() {
        return waterHeaterPurchaseYear;
    }

    public void setWaterHeaterPurchaseYear(Integer waterHeaterPurchaseYear) {
        this.waterHeaterPurchaseYear = waterHeaterPurchaseYear;
    }

    public Integer getAirConditionerSplitId() {
        return airConditionerSplitId;
    }

    public void setAirConditionerSplitId(Integer airConditionerSplitId) {
        this.airConditionerSplitId = airConditionerSplitId;
    }

    public Integer getAirConditionerHoursId() {
        return airConditionerHoursId;
    }

    public void setAirConditionerHoursId(Integer airConditionerHoursId) {
        this.airConditionerHoursId = airConditionerHoursId;
    }

    public Integer getAirConditionerPurchaseYear() {
        return airConditionerPurchaseYear;
    }

    public void setAirConditionerPurchaseYear(Integer airConditionerPurchaseYear) {
        this.airConditionerPurchaseYear = airConditionerPurchaseYear;
    }

    public Integer getAirConditionerWinterHoursId() {
        return airConditionerWinterHoursId;
    }

    public void setAirConditionerWinterHoursId(Integer airConditionerWinterHoursId) {
        this.airConditionerWinterHoursId = airConditionerWinterHoursId;
    }

    public Integer getOvenWeeklyUse() {
        return ovenWeeklyUse;
    }

    public void setOvenWeeklyUse(Integer ovenWeeklyUse) {
        this.ovenWeeklyUse = ovenWeeklyUse;
    }

    public Integer getOvenEnergyClass() {
        return ovenEnergyClass;
    }

    public void setOvenEnergyClass(Integer ovenEnergyClass) {
        this.ovenEnergyClass = ovenEnergyClass;
    }

    public Integer getOvenPurchaseYear() {
        return ovenPurchaseYear;
    }

    public void setOvenPurchaseYear(Integer ovenPurchaseYear) {
        this.ovenPurchaseYear = ovenPurchaseYear;
    }

    public Integer getAsciugatriceOvenWeeklyUse() {
        return asciugatriceOvenWeeklyUse;
    }

    public void setAsciugatriceOvenWeeklyUse(Integer asciugatriceOvenWeeklyUse) {
        this.asciugatriceOvenWeeklyUse = asciugatriceOvenWeeklyUse;
    }

    public Integer getAsciugatriceOvenEnergyClass() {
        return asciugatriceOvenEnergyClass;
    }

    public void setAsciugatriceOvenEnergyClass(Integer asciugatriceOvenEnergyClass) {
        this.asciugatriceOvenEnergyClass = asciugatriceOvenEnergyClass;
    }

    public Integer getAsciugatriceOvenPurchaseYear() {
        return asciugatriceOvenPurchaseYear;
    }

    public void setAsciugatriceOvenPurchaseYear(Integer asciugatriceOvenPurchaseYear) {
        this.asciugatriceOvenPurchaseYear = asciugatriceOvenPurchaseYear;
    }

    public Integer getWashingMachineWeeklyUse() {
        return washingMachineWeeklyUse;
    }

    public void setWashingMachineWeeklyUse(Integer washingMachineWeeklyUse) {
        this.washingMachineWeeklyUse = washingMachineWeeklyUse;
    }

    public Integer getWashingMachineEnergyClass() {
        return washingMachineEnergyClass;
    }

    public void setWashingMachineEnergyClass(Integer washingMachineEnergyClass) {
        this.washingMachineEnergyClass = washingMachineEnergyClass;
    }

    public Integer getWashingMachinePurchaseYear() {
        return washingMachinePurchaseYear;
    }

    public void setWashingMachinePurchaseYear(Integer washingMachinePurchaseYear) {
        this.washingMachinePurchaseYear = washingMachinePurchaseYear;
    }

    public Integer getDishwasherWeeklyUse() {
        return dishwasherWeeklyUse;
    }

    public void setDishwasherWeeklyUse(Integer dishwasherWeeklyUse) {
        this.dishwasherWeeklyUse = dishwasherWeeklyUse;
    }

    public Integer getDishwasherEnergyClass() {
        return dishwasherEnergyClass;
    }

    public void setDishwasherEnergyClass(Integer dishwasherEnergyClass) {
        this.dishwasherEnergyClass = dishwasherEnergyClass;
    }

    public Integer getDishwasherPurchaseYear() {
        return dishwasherPurchaseYear;
    }

    public void setDishwasherPurchaseYear(Integer dishwasherPurchaseYear) {
        this.dishwasherPurchaseYear = dishwasherPurchaseYear;
    }

    public Integer getRefrigeratorWeeklyUse() {
        return refrigeratorWeeklyUse;
    }

    public void setRefrigeratorWeeklyUse(Integer refrigeratorWeeklyUse) {
        this.refrigeratorWeeklyUse = refrigeratorWeeklyUse;
    }

    public Integer getRefrigeratorEnergyClass() {
        return refrigeratorEnergyClass;
    }

    public void setRefrigeratorEnergyClass(Integer refrigeratorEnergyClass) {
        this.refrigeratorEnergyClass = refrigeratorEnergyClass;
    }

    public Integer getRefrigeratorPurchaseYear() {
        return refrigeratorPurchaseYear;
    }

    public void setRefrigeratorPurchaseYear(Integer refrigeratorPurchaseYear) {
        this.refrigeratorPurchaseYear = refrigeratorPurchaseYear;
    }

    public Integer getOtherDeviceWatt() {
        return otherDeviceWatt;
    }

    public void setOtherDeviceWatt(Integer otherDeviceWatt) {
        this.otherDeviceWatt = otherDeviceWatt;
    }

    public Integer getOtherDeviceWeeklyHourUse() {
        return otherDeviceWeeklyHourUse;
    }

    public void setOtherDeviceWeeklyHourUse(Integer otherDeviceWeeklyHourUse) {
        this.otherDeviceWeeklyHourUse = otherDeviceWeeklyHourUse;
    }

    public Integer getLightingType() {
        return lightingType;
    }

    public void setLightingType(Integer lightingType) {
        this.lightingType = lightingType;
    }

    public Double getSumAttiva() {
        return sumAttiva;
    }

    public void setSumAttiva(Double sumAttiva) {
        this.sumAttiva = sumAttiva;
    }

    public Integer getCountMese() {
        return countMese;
    }

    public void setCountMese(Integer countMese) {
        this.countMese = countMese;
    }

    public Integer getCountPod() {
        return countPod;
    }

    public void setCountPod(Integer countPod) {
        this.countPod = countPod;
    }

    public int getQuestionarioYearlyKwh() {
        return questionarioYearlyKwh;
    }

    public void setQuestionarioYearlyKwh(int questionarioYearlyKwh) {
        this.questionarioYearlyKwh = questionarioYearlyKwh;
    }

    public boolean isQuestionarioUsing() {
        return questionarioUsing;
    }

    public void setQuestionarioUsing(boolean questionarioUsing) {
        this.questionarioUsing = questionarioUsing;
    }

    public Integer getEnergyMeter2() {
        return energyMeter2;
    }

    public void setEnergyMeter2(Integer energyMeter2) {
        this.energyMeter2 = energyMeter2;
    }

    public Integer getTipologiaContratto() {
        return tipologiaContratto;
    }

    public void setTipologiaContratto(Integer tipologiaContratto) {
        this.tipologiaContratto = tipologiaContratto;
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

    public int getPotenzaStimata() {
        return potenzaStimata;
    }

    public void setPotenzaStimata(int potenzaStimata) {
        this.potenzaStimata = potenzaStimata;
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

    public int getTariffaId() {
        return tariffaId;
    }

    public void setTariffaId(int tariffaId) {
        this.tariffaId = tariffaId;
    }

    public String getCostVersion() {
        return costVersion;
    }

    public void setCostVersion(String costVersion) {
        this.costVersion = costVersion;
    }

    public boolean isExistingClientOffer() {
        return isExistingClientOffer;
    }

    public void setExistingClientOffer(boolean existingClientOffer) {
        isExistingClientOffer = existingClientOffer;
    }

    public int getEnergyDetailOfferId() {
        return energyDetailOfferId;
    }

    public void setEnergyDetailOfferId(int energyDetailOfferId) {
        this.energyDetailOfferId = energyDetailOfferId;
    }

    public int getEnergyOfferId() {
        return energyOfferId;
    }

    public void setEnergyOfferId(int energyOfferId) {
        this.energyOfferId = energyOfferId;
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    public int getEnergyMeter() {
        return energyMeter;
    }

    public void setEnergyMeter(int energyMeter) {
        this.energyMeter = energyMeter;
    }

    public int getConsumptioClassTypeId() {
        return consumptioClassTypeId;
    }

    public void setConsumptioClassTypeId(int consumptioClassTypeId) {
        this.consumptioClassTypeId = consumptioClassTypeId;
    }

    public int getClientType() {
        return clientType;
    }

    public void setClientType(int clientType) {
        this.clientType = clientType;
    }

    public Integer getYearlyKwh() {
        return yearlyKwh;
    }

    public void setYearlyKwh(Integer yearlyKwh) {
        this.yearlyKwh = yearlyKwh;
    }

    public Integer getYearlyKwh2() {
        return yearlyKwh2;
    }

    public void setYearlyKwh2(Integer yearlyKwh2) {
        this.yearlyKwh2 = yearlyKwh2;
    }

    public Integer getYearlyKwh3() {
        return yearlyKwh3;
    }

    public void setYearlyKwh3(Integer yearlyKwh3) {
        this.yearlyKwh3 = yearlyKwh3;
    }

    public int getYearlyConsumption() {
        return yearlyConsumption;
    }

    public void setYearlyConsumption(int yearlyConsumption) {
        this.yearlyConsumption = yearlyConsumption;
    }

    public int getTensione() {
        return tensione;
    }

    public void setTensione(int tensione) {
        this.tensione = tensione;
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
