package com.aimprosoft.android.optima.centralizedApp.util.autotest.model;

public class VoceTestDetails extends AbstractTestModel {
    private String prezzoVoce;
    private boolean prezzoVoceStatus;
    private String prezzoVoceConIva;
    private boolean prezzoVoceConIvaStatus;
    private String vocePerc;
    private boolean vocePercStatus;
    private String voceNumber;

    public VoceTestDetails() {
    }

    public boolean getTotalResult() {
        return prezzoVoceStatus && prezzoVoceConIvaStatus && vocePercStatus;
    }

    public String getVoceNumber() {
        return voceNumber;
    }

    public void setVoceNumber(String voceNumber) {
        this.voceNumber = voceNumber;
    }

    public String getPrezzoVoce() {
        return prezzoVoce;
    }

    public void setPrezzoVoce(String prezzoVoce) {
        this.prezzoVoce = prezzoVoce;
    }

    public boolean isPrezzoVoceStatus() {
        return prezzoVoceStatus;
    }

    public void setPrezzoVoceStatus(boolean prezzoVoceStatus) {
        this.prezzoVoceStatus = prezzoVoceStatus;
    }

    public String getPrezzoVoceConIva() {
        return prezzoVoceConIva;
    }

    public void setPrezzoVoceConIva(String prezzoVoceConIva) {
        this.prezzoVoceConIva = prezzoVoceConIva;
    }

    public boolean isPrezzoVoceConIvaStatus() {
        return prezzoVoceConIvaStatus;
    }

    public void setPrezzoVoceConIvaStatus(boolean prezzoVoceConIvaStatus) {
        this.prezzoVoceConIvaStatus = prezzoVoceConIvaStatus;
    }

    public String getVocePerc() {
        return vocePerc;
    }

    public void setVocePerc(String vocePerc) {
        this.vocePerc = vocePerc;
    }

    public boolean isVocePercStatus() {
        return vocePercStatus;
    }

    public void setVocePercStatus(boolean vocePercStatus) {
        this.vocePercStatus = vocePercStatus;
    }
}
