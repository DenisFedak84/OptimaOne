package com.aimprosoft.android.optima.centralizedApp.util.autotest.model;

public class ADSLTestDetails extends AbstractTestModel {
    private String prezzoAdsl;
    private boolean prezzoAdslStatus;
    private String prezzoAdslConIva;
    private boolean prezzoAdslConIvaStatus;
    private String adslPerc;
    private boolean adslPercStatus;
    private String adslNumber;

    public ADSLTestDetails() {
    }

    public boolean getTotalResult() {
        return prezzoAdslStatus && prezzoAdslConIvaStatus && adslPercStatus;
    }

    public String getAdslNumber() {
        return adslNumber;
    }

    public void setAdslNumber(String adslNumber) {
        this.adslNumber = adslNumber;
    }

    public String getPrezzoAdsl() {
        return prezzoAdsl;
    }

    public void setPrezzoAdsl(String prezzoAdsl) {
        this.prezzoAdsl = prezzoAdsl;
    }

    public boolean isPrezzoAdslStatus() {
        return prezzoAdslStatus;
    }

    public void setPrezzoAdslStatus(boolean prezzoAdslStatus) {
        this.prezzoAdslStatus = prezzoAdslStatus;
    }

    public String getPrezzoAdslConIva() {
        return prezzoAdslConIva;
    }

    public void setPrezzoAdslConIva(String prezzoAdslConIva) {
        this.prezzoAdslConIva = prezzoAdslConIva;
    }

    public boolean isPrezzoAdslConIvaStatus() {
        return prezzoAdslConIvaStatus;
    }

    public void setPrezzoAdslConIvaStatus(boolean prezzoAdslConIvaStatus) {
        this.prezzoAdslConIvaStatus = prezzoAdslConIvaStatus;
    }

    public String getAdslPerc() {
        return adslPerc;
    }

    public void setAdslPerc(String adslPerc) {
        this.adslPerc = adslPerc;
    }

    public boolean isAdslPercStatus() {
        return adslPercStatus;
    }

    public void setAdslPercStatus(boolean adslPercStatus) {
        this.adslPercStatus = adslPercStatus;
    }
}
