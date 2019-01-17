package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.AbstractJarUtil;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@DatabaseTable(tableName = "Offer")
public class Offer extends BaseEntity {

    public static final String OFFER_ID = "offerId";
    public static final String NAME = "name";
    public static final String NOME = "nome";
    public static final String SURNAME = "surname";
    public static final String BIRTH_DATE = "birthDate";
    public static final String BIRTH_DATE_TOWN_ID = "birthDateTownId";
    public static final String CREATION_DATE = "creationDate";
    public static final String TOWN_ID = "townId";
    public static final String SEX = "sex";
    public static final String POD = "pod";
    public static final String CAP = "cap";
    public static final String MESI = "mesi";
    public static final String GAS_HEATING_TYPE = "gasHeatingType";
    public static final String CLIMATIC_ZONE = "climaticZone";
    public static final String ENERGY_OFFER_ID = "energyOfferId";
    public static final String GAS_OFFER_ID = "gasOfferId";
    public static final String TLC_OFFER_ID = "tlcOfferId";
    public static final String OLD_TLC_OFFER_ID = "oldTlcOfferId";
    public static final String IS_OLD_TLC_OFFER_USED = "isOldTlcOfferUsed";
    public static final String INTERNET_OFFER_ID = "internetOfferId";
    public static final String MOBILE_OFFER_ID = "mobileOfferId";
    public static final String WLR_OFFER_ID = "wlrOfferId";
    public static final String OFFER_COST = "offerCost";
    public static final String OFFER_COST_IVA = "offerCostIVA";
    public static final String OFFER_COST_EXTRA = "offerCostExtra";
    public static final String OPTILIFE_CODE = "optilifeCode";
    public static final String NI = "ni";
    public static final String THERMALINSULATION = "thermalinsulation";
    public static final String UNDERFLOOR_HEATING = "underfloorHeating";
    public static final String DOUBLE_GLAZING = "doubleGlazing";
    public static final String BOILER_TYPE = "boilerType";
    public static final String PIVA = "piva";
    public static final String MAIN_SERVICE_CODE = "mainServiceCode";
    public static final String OFFERTE1_SERVICE_CODE = "offerte1ServiceCode";
    public static final String OFFERTE2_SERVICE_CODE = "offerte2ServiceCode";
    public static final String OFFERTE3_SERVICE_CODE = "offerte3ServiceCode";
    public static final String OFFERTE1_COST = "offerte1Cost";
    public static final String OFFERTE2_COST = "offerte2Cost";
    public static final String OFFERTE3_COST = "offerte3Cost";
    public static final String MAIL = "mail";
    public static final String CODICE_FISCALE = "codiceFiscale";
    public static final String IS_LOCAL_ALG = "isLocalAlg";
    public static final String INDIRIZZO_DI_FORNITURA = "indirizzoDiFornitura";
    public static final String AGREEMENT_ID = "agreementId";
    public static final String CONTO_RELAX = "conto_relax";
    public static final String SUPERFICIE = "superficie";
    public static final String RESTORATION = "restoration";
    public static final String FLAT_TYPE = "flatType";
    public static final String GAS_YEAR_OF_REFERENCE = "gasYearOfReference";
    public static final String ENERGY_CLASS = "energyClass";
    public static final String FLAT_DIMENSION_ID = "flatDimensionId";
    public static final String NUMERO_PERSONE = "numeroPersone";
    public static final String HOUSE_HOLDER_ID = "houseHolderId";
    public static final String CONFIGURATOR_TYPE = "configurator_type";
    public static final String CLIENT_TYPE_ID = "clientTypeId";
    public static final String CENTRO_COMMERCIALE = "centroCommerciale";
    public static final String PRICING_OFFER_ID = "pricingOfferId";
    public static final String CARTACEO = "cartaceo";
    public static final String IS_PDF_SENDING_REQUIRED = "isPdfSendingRequired";

    public Offer() {
    }

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = OFFER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int offerId;

    @DatabaseField(columnName = NOME, canBeNull = true, dataType = DataType.STRING)
    private String nome;

    @DatabaseField(columnName = NAME, canBeNull = true, dataType = DataType.STRING)
    private String name;

    @DatabaseField(columnName = NUMERO_PERSONE, canBeNull = false, dataType = DataType.INTEGER)
    private int numeroPersone;

    @DatabaseField(columnName = FLAT_TYPE, canBeNull = false, dataType = DataType.INTEGER)
    private int flatType;

    @DatabaseField(columnName = ENERGY_CLASS, canBeNull = false, dataType = DataType.INTEGER)
    private int energyClass;

    @DatabaseField(columnName = GAS_YEAR_OF_REFERENCE, canBeNull = false, dataType = DataType.INTEGER)
    private int gasYearOfReference;

    @DatabaseField(columnName = SURNAME, canBeNull = false, dataType = DataType.STRING)
    private String surname;

    @DatabaseField(columnName = POD, canBeNull = false, dataType = DataType.STRING, defaultValue = "")
    private String pod;

    @DatabaseField(columnName = CAP, canBeNull = false, dataType = DataType.STRING)
    private String cap;

    @DatabaseField(columnName = SEX, canBeNull = false, dataType = DataType.STRING)
    private String sex;

    @DatabaseField(columnName = SUPERFICIE, canBeNull = false, dataType = DataType.INTEGER)
    private int superficie;

    @DatabaseField(columnName = HOUSE_HOLDER_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int houseHolderId;

    @DatabaseField(columnName = RESTORATION, canBeNull = false, dataType = DataType.INTEGER)
    private int restoration;

    @DatabaseField(columnName = CREATION_DATE, canBeNull = true, dataType = DataType.STRING)
    private String creationDate;

    @DatabaseField(columnName = TOWN_ID, canBeNull = true, dataType = DataType.INTEGER)
    private int townId;

    @DatabaseField(columnName = BIRTH_DATE, canBeNull = false, dataType = DataType.STRING)
    private String birthDate;

    @DatabaseField(columnName = BIRTH_DATE_TOWN_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int birthDateTownId;

    @DatabaseField(columnName = CLIMATIC_ZONE, canBeNull = false, dataType = DataType.STRING)
    private String climaticZone;
//
//    @DatabaseField(columnName = ENERGY_CLASS, canBeNull = true, dataType = DataType.INTEGER)
//    private int energyClass;
//
//    @DatabaseField(columnName = GAS_YEAR_OF_REFERENCE, canBeNull = true, dataType = DataType.INTEGER)
//    private int gasYearOfReference;
//
//    @DatabaseField(columnName = CLIENT_TYPE_ID, canBeNull = true, dataType = DataType.INTEGER)
//    private int clientTypeId;

    @DatabaseField(columnName = ENERGY_OFFER_ID, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer energyOfferId;

    @DatabaseField(columnName = GAS_OFFER_ID, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer gasOfferId;

    @DatabaseField(columnName = TLC_OFFER_ID, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer tlcOfferId;

    @DatabaseField(columnName = OLD_TLC_OFFER_ID, canBeNull = true, dataType = DataType.INTEGER_OBJ, defaultValue = "0")
    private Integer oldTlcOfferId;

    @DatabaseField(columnName = IS_OLD_TLC_OFFER_USED, canBeNull = true, dataType = DataType.BOOLEAN_OBJ, defaultValue = "false")
    private Boolean isOldTlcOfferUsed;

    @DatabaseField(columnName = INTERNET_OFFER_ID, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer internetOfferId;

    @DatabaseField(columnName = FLAT_DIMENSION_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int flatDimensionId;

    @DatabaseField(columnName = GAS_HEATING_TYPE, canBeNull = false, dataType = DataType.INTEGER)
    private int gasHeatingType;

    @DatabaseField(columnName = MOBILE_OFFER_ID, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer mobileOfferId;

    @DatabaseField(columnName = WLR_OFFER_ID, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer wlrOfferId;

    @DatabaseField(columnName = OFFER_COST, canBeNull = true, dataType = DataType.BIG_DECIMAL)
    private BigDecimal offerCost;

    @DatabaseField(columnName = OFFER_COST_EXTRA, canBeNull = true, dataType = DataType.BIG_DECIMAL)
    private BigDecimal offerCostExtra;

    @DatabaseField(columnName = OFFER_COST_IVA, canBeNull = true, dataType = DataType.BIG_DECIMAL)
    private BigDecimal offerCostIVA;

    @DatabaseField(columnName = OPTILIFE_CODE, canBeNull = true, dataType = DataType.STRING)
    private String optilifeCode;

    @DatabaseField(columnName = NI, canBeNull = true, dataType = DataType.STRING)
    private String ni;

    @DatabaseField(columnName = PIVA, canBeNull = false, dataType = DataType.STRING)
    private String piva;

    @DatabaseField(columnName = MAIN_SERVICE_CODE, canBeNull = false, dataType = DataType.STRING)
    private String mainServiceCode;

    @DatabaseField(columnName = OFFERTE1_SERVICE_CODE, canBeNull = false, dataType = DataType.STRING)
    private String offerte1ServiceCode;

    @DatabaseField(columnName = OFFERTE2_SERVICE_CODE, canBeNull = false, dataType = DataType.STRING)
    private String offerte2ServiceCode;

    @DatabaseField(columnName = OFFERTE3_SERVICE_CODE, canBeNull = false, dataType = DataType.STRING)
    private String offerte3ServiceCode;

    @DatabaseField(columnName = OFFERTE1_COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double offerte1Cost;

    @DatabaseField(columnName = OFFERTE2_COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double offerte2Cost;

    @DatabaseField(columnName = OFFERTE3_COST, canBeNull = false, dataType = DataType.DOUBLE)
    private double offerte3Cost;

    @DatabaseField(columnName = THERMALINSULATION, canBeNull = false, dataType = DataType.INTEGER)
    private int thermalinsulation;

    @DatabaseField(columnName = UNDERFLOOR_HEATING, canBeNull = false, dataType = DataType.INTEGER)
    private int underfloorHeating;

    @DatabaseField(columnName = DOUBLE_GLAZING, canBeNull = false, dataType = DataType.INTEGER)
    private int doubleGlazing;

    @DatabaseField(columnName = BOILER_TYPE, canBeNull = false, dataType = DataType.INTEGER)
    private int boilerType;

    @DatabaseField(columnName = MAIL, canBeNull = false, dataType = DataType.STRING)
    private String mail;

    @DatabaseField(columnName = AGREEMENT_ID, canBeNull = true, dataType = DataType.INTEGER_OBJ)
    private Integer agreementId;

    @DatabaseField(columnName = CONTO_RELAX, canBeNull = true, dataType = DataType.DOUBLE)
    private double conto_relax;

    @DatabaseField(columnName = IS_LOCAL_ALG, canBeNull = false, dataType = DataType.BOOLEAN, defaultValue = "false")
    private boolean isLocalArg;

    @DatabaseField(columnName = CONFIGURATOR_TYPE, canBeNull = false, dataType = DataType.INTEGER)
    private int configurator_type;

    @DatabaseField(columnName = INDIRIZZO_DI_FORNITURA, canBeNull = true, dataType = DataType.STRING)
    private String indirizzoDiFornitura;

    @DatabaseField(columnName = CODICE_FISCALE, canBeNull = false, dataType = DataType.STRING)
    private String codiceFiscale;

    @DatabaseField(columnName = MESI, canBeNull = false, dataType = DataType.INTEGER)
    private int mesi;

    @DatabaseField(columnName = CLIENT_TYPE_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int clientTypeId;

    @DatabaseField(columnName = CENTRO_COMMERCIALE, canBeNull = false, dataType = DataType.BOOLEAN, defaultValue = "false")
    private boolean centroCommerciale;

    @DatabaseField(columnName = PRICING_OFFER_ID, canBeNull = false, dataType = DataType.STRING)
    private String pricingOfferId;

    @DatabaseField(columnName = CARTACEO, canBeNull = false, dataType = DataType.BOOLEAN, defaultValue = "false")
    private boolean cartaceo;

    @DatabaseField(columnName = IS_PDF_SENDING_REQUIRED, canBeNull = false, dataType = DataType.BOOLEAN)
    private boolean isPdfSendingRequired;

    public boolean isPdfSendingRequired() {
        return isPdfSendingRequired;
    }

    public void setPdfSendingRequired(boolean isPdfSendingRequired) {
        this.isPdfSendingRequired = isPdfSendingRequired;
    }

    public boolean isCartaceo() {
        return cartaceo;
    }

    public void setCartaceo(boolean cartaceo) {
        this.cartaceo = cartaceo;
    }

    public String getPricingOfferId() {
        return pricingOfferId;
    }

    public void setPricingOfferId(String pricingOfferId) {
        this.pricingOfferId = pricingOfferId;
    }

    public boolean isCentroCommerciale() {
        return centroCommerciale;
    }

    public void setCentroCommerciale(boolean centroCommerciale) {
        this.centroCommerciale = centroCommerciale;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getClientTypeId() {
        return clientTypeId;
    }

    public void setClientTypeId(int clientTypeId) {
        this.clientTypeId = clientTypeId;
    }

    public int getHouseHolderId() {
        return houseHolderId;
    }

    public void setHouseHolderId(int houseHolderId) {
        this.houseHolderId = houseHolderId;
    }

    public int getFlatDimensionId() {
        return flatDimensionId;
    }

    public int getConfiguratorType() {
        return configurator_type;
    }

    public void setConfigurator_type(int configurator_type) {
        this.configurator_type = configurator_type;
    }

    public void setFlatDimensionId(int flatDimensionId) {
        this.flatDimensionId = flatDimensionId;
    }

    public int getGasHeatingType() {
        return gasHeatingType;
    }

    public void setGasHeatingType(int gasHeatingType) {
        this.gasHeatingType = gasHeatingType;
    }

    public int getMesi() {
        return mesi;
    }

    public void setMesi(int mesi) {
        this.mesi = mesi;
    }

    public int getGasYearOfReference() {
        return gasYearOfReference;
    }

    public void setGasYearOfReference(int gasYearOfReference) {
        this.gasYearOfReference = gasYearOfReference;
    }

    public int getThermalinsulation() {
        return thermalinsulation;
    }

    public int getNumeroPersone() {
        return numeroPersone;
    }

    public void setNumeroPersone(int numeroPersone) {
        this.numeroPersone = numeroPersone;
    }

    public int getFlatType() {
        return flatType;
    }

    public void setFlatType(int flatType) {
        this.flatType = flatType;
    }

    public int getEnergyClass() {
        return energyClass;
    }

    public void setEnergyClass(int energyClass) {
        this.energyClass = energyClass;
    }

    public Boolean getIsOldTlcOfferUsed() {
        return isOldTlcOfferUsed;
    }

    public void setIsOldTlcOfferUsed(Boolean isOldTlcOfferUsed) {
        this.isOldTlcOfferUsed = isOldTlcOfferUsed;
    }

    public void setThermalinsulation(int thermalinsulation) {
        this.thermalinsulation = thermalinsulation;
    }

    public int getUnderfloorHeating() {
        return underfloorHeating;
    }

    public void setUnderfloorHeating(int underfloorHeating) {
        this.underfloorHeating = underfloorHeating;
    }

    public int getDoubleGlazing() {
        return doubleGlazing;
    }

    public void setDoubleGlazing(int doubleGlazing) {
        this.doubleGlazing = doubleGlazing;
    }

    public int getBoilerType() {
        return boilerType;
    }

    public void setBoilerType(int boilerType) {
        this.boilerType = boilerType;
    }

    public int getSuperficie() {
        return superficie;
    }

    public int getRestoration() {
        return restoration;
    }

    public void setRestoration(int restoration) {
        this.restoration = restoration;
    }

    public void setSuperficie(int superficie) {
        this.superficie = superficie;
    }

    public String getClimaticZone() {
        return climaticZone;
    }

    public void setClimaticZone(String climaticZone) {
        this.climaticZone = climaticZone;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getIndirizzoDiFornitura() {
        return indirizzoDiFornitura;
    }

    public void setIndirizzoDiFornitura(String indirizzoDiFornitura) {
        this.indirizzoDiFornitura = indirizzoDiFornitura;
    }

    public boolean isLocalArg() {
        return isLocalArg;
    }

    public void setLocalArg(boolean isLocalArg) {
        this.isLocalArg = isLocalArg;
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSurname() {
        return surname;
    }

    public int getBirthDateTownId() {
        return birthDateTownId;
    }

    public void setBirthDateTownId(int birthDateTownId) {
        this.birthDateTownId = birthDateTownId;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public double getConto_relax() {
        return conto_relax;
    }

    public void setConto_relax(double conto_relax) {
        this.conto_relax = conto_relax;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(Integer agreementId) {
        this.agreementId = agreementId;
    }

    public Boolean getOldTlcOfferUsed() {
        return isOldTlcOfferUsed;
    }

    public void setOldTlcOfferUsed(Boolean oldTlcOfferUsed) {
        isOldTlcOfferUsed = oldTlcOfferUsed;
    }

    public Integer getOldTlcOfferId() {
        return oldTlcOfferId;
    }

    public void setOldTlcOfferId(Integer oldTlcOfferId) {
        this.oldTlcOfferId = oldTlcOfferId;
    }

    public Integer getWlrOfferId() {
        return wlrOfferId;
    }

    public void setWlrOfferId(Integer wlrOfferId) {
        this.wlrOfferId = wlrOfferId;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public double getOfferte3Cost() {
        return offerte3Cost;
    }

    public void setOfferte3Cost(double offerte3Cost) {
        this.offerte3Cost = offerte3Cost;
    }

    public double getOfferte2Cost() {
        return offerte2Cost;
    }

    public void setOfferte2Cost(double offerte2Cost) {
        this.offerte2Cost = offerte2Cost;
    }

    public double getOfferte1Cost() {
        return offerte1Cost;
    }

    public void setOfferte1Cost(double offerte1Cost) {
        this.offerte1Cost = offerte1Cost;
    }

    public String getOfferte3ServiceCode() {
        return offerte3ServiceCode;
    }

    public void setOfferte3ServiceCode(String offerte3ServiceCode) {
        this.offerte3ServiceCode = offerte3ServiceCode;
    }

    public String getOfferte2ServiceCode() {
        return offerte2ServiceCode;
    }

    public void setOfferte2ServiceCode(String offerte2ServiceCode) {
        this.offerte2ServiceCode = offerte2ServiceCode;
    }

    public String getOfferte1ServiceCode() {
        return offerte1ServiceCode;
    }

    public void setOfferte1ServiceCode(String offerte1ServiceCode) {
        this.offerte1ServiceCode = offerte1ServiceCode;
    }

    public String getMainServiceCode() {
        return mainServiceCode;
    }

    public void setMainServiceCode(String mainServiceCode) {
        this.mainServiceCode = mainServiceCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getTownId() {
        return townId;
    }

    public void setTownId(int townId) {
        this.townId = townId;
    }

    public Integer getEnergyOfferId() {
        return energyOfferId;
    }

    public void setEnergyOfferId(Integer energyOfferId) {
        this.energyOfferId = energyOfferId;
    }

    public Integer getGasOfferId() {
        return gasOfferId;
    }

    public void setGasOfferId(Integer gasOfferId) {
        this.gasOfferId = gasOfferId;
    }

    public Integer getTlcOfferId() {
        return tlcOfferId;
    }

    public void setTlcOfferId(Integer tlcOfferId) {
        this.tlcOfferId = tlcOfferId;
    }

    public Integer getInternetOfferId() {
        return internetOfferId;
    }

    public void setInternetOfferId(Integer internetOfferId) {
        this.internetOfferId = internetOfferId;
    }

    public Integer getMobileOfferId() {
        return mobileOfferId;
    }

    public void setMobileOfferId(Integer mobileOfferId) {
        this.mobileOfferId = mobileOfferId;
    }

    public BigDecimal getOfferCost() {
        return offerCost;
    }

    public void setOfferCost(BigDecimal offerCost) {
        this.offerCost = offerCost;
    }

    public BigDecimal getOfferCostExtra() {
        return offerCostExtra;
    }

    public void setOfferCostExtra(BigDecimal offerCostExtra) {
        this.offerCostExtra = offerCostExtra;
    }

    public BigDecimal getOfferCostIVA() {
        return offerCostIVA;
    }

    public void setOfferCostIVA(BigDecimal offerCostIVA) {
        this.offerCostIVA = offerCostIVA;
    }

    public String getOptilifeCode() {
        return optilifeCode;
    }

    public void setOptilifeCode(String optilifeCode) {
        this.optilifeCode = optilifeCode;
    }

    public String getNi() {
        return ni;
    }

    public void setNi(String ni) {
        this.ni = ni;
    }

    public String getPiva() {
        return piva;
    }

    public void setPiva(String piva) {
        this.piva = piva;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.UK);
        decimalFormatSymbols.setDecimalSeparator('.');
        String pattern = "#.##";
        return (configurator_type== Constants.CONSUMER_CONFIGURATOR ? ("Codice fiscale: " + codiceFiscale) : ("Piva: " + piva)) + ",  Canone mensile: " + new DecimalFormat(pattern, decimalFormatSymbols).format(offerCost) + ", Costo attivazione: " +
                AbstractJarUtil.getFormattedActivationCost(this);
    }
}
