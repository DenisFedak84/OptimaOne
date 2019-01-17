package com.aimprosoft.android.optima.centralizedApp.util.autotest.model;

public class MainOfferDetails extends AbstractTestModel {
    private String canoneMensile;
    private boolean canoneMensileStatus;
    private String canoneMensileConIVA;
    private boolean canoneMensileConIVAStatus;
    private String contoRelax;
    private boolean contoRelaxStatus;
    private String costoAttivazione;
    private boolean costoAttivazioneStatus;

    public MainOfferDetails() {
    }

    public String getCanoneMensile() {
        return canoneMensile;
    }

    public void setCanoneMensile(String canoneMensile) {
        this.canoneMensile = canoneMensile;
    }

    public boolean isCanoneMensileStatus() {
        return canoneMensileStatus;
    }

    public void setCanoneMensileStatus(boolean canoneMensileStatus) {
        this.canoneMensileStatus = canoneMensileStatus;
    }

    public String getCanoneMensileConIVA() {
        return canoneMensileConIVA;
    }

    public void setCanoneMensileConIVA(String canoneMensileConIVA) {
        this.canoneMensileConIVA = canoneMensileConIVA;
    }

    public boolean isCanoneMensileConIVAStatus() {
        return canoneMensileConIVAStatus;
    }

    public void setCanoneMensileConIVAStatus(boolean canoneMensileConIVAStatus) {
        this.canoneMensileConIVAStatus = canoneMensileConIVAStatus;
    }

    public String getContoRelax() {
        return contoRelax;
    }

    public void setContoRelax(String contoRelax) {
        this.contoRelax = contoRelax;
    }

    public boolean isContoRelaxStatus() {
        return contoRelaxStatus;
    }

    public void setContoRelaxStatus(boolean contoRelaxStatus) {
        this.contoRelaxStatus = contoRelaxStatus;
    }

    public String getCostoAttivazione() {
        return costoAttivazione;
    }

    public void setCostoAttivazione(String costoAttivazione) {
        this.costoAttivazione = costoAttivazione;
    }

    public boolean isCostoAttivazioneStatus() {
        return costoAttivazioneStatus;
    }

    public void setCostoAttivazioneStatus(boolean costoAttivazioneStatus) {
        this.costoAttivazioneStatus = costoAttivazioneStatus;
    }

    @Override
    public boolean getTotalResult() {
        return canoneMensileStatus && canoneMensileConIVAStatus && costoAttivazioneStatus &&
                costoAttivazioneStatus;
    }
}
