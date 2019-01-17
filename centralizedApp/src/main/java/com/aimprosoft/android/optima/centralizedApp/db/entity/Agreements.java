package com.aimprosoft.android.optima.centralizedApp.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Agreements")
public class Agreements extends BaseEntity{

    public static final String AGREEMENT_ID = "agreementId";
    public static final String AGREEMENT_DESC = "agreementDesc";
    public static final String DISCOUNT_PERC = "discountPerc";

    public Agreements() {
    }

    public Agreements(int agreementId, String agreementDesc, int discountPerc) {

        this.agreementId = agreementId;
        this.agreementDesc = agreementDesc;
        this.discountPerc = discountPerc;
    }

    @DatabaseField(id = true, columnName = AGREEMENT_ID, canBeNull = false, dataType = DataType.INTEGER)
    private int agreementId;
    @DatabaseField(columnName = AGREEMENT_DESC, canBeNull = false, dataType = DataType.STRING)
    private String agreementDesc;
    @DatabaseField(columnName = DISCOUNT_PERC, canBeNull = false, dataType = DataType.INTEGER)
    private int discountPerc;

    public int getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(int agreementId) {
        this.agreementId = agreementId;
    }

    public String getAgreementDesc() {
        return agreementDesc;
    }

    public void setAgreementDesc(String agreementDesc) {
        this.agreementDesc = agreementDesc;
    }

    public int getDiscountPerc() {
        return discountPerc;
    }

    public void setDiscountPerc(int discountPerc) {
        this.discountPerc = discountPerc;
    }

    @Override
    public String toString() {
        return getAgreementDesc();
    }
}
