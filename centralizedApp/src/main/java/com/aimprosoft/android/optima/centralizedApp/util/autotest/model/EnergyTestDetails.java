package com.aimprosoft.android.optima.centralizedApp.util.autotest.model;

public class EnergyTestDetails extends AbstractTestModel {
    private String potenza;
    private boolean potenzaStatus;
    private String tagliaMeseEE;
    private boolean tagliaMeseEEStatus;
    private String prezzoEE;
    private boolean prezzoEEStatus;
    private String prezzoEEConIva;
    private boolean prezzoEEConIvaStatus;
    private String sforamento;
    private boolean sforamentoStatus;
    private String mancatoConsumo;
    private boolean mancatoConsumoStatus;
    private String codiceTariffa;
    private boolean codiceTariffaStatus;
    private String energyPerc;
    private boolean energyPercStatus;
    private String podNumber;

    public EnergyTestDetails() {
    }

    public boolean getTotalResult() {
        return potenzaStatus && tagliaMeseEEStatus && prezzoEEStatus && prezzoEEConIvaStatus &&
                sforamentoStatus && mancatoConsumoStatus && codiceTariffaStatus &&
                energyPercStatus;
    }

    public String getPodNumber() {
        return podNumber;
    }

    public void setPodNumber(String podNumber) {
        this.podNumber = podNumber;
    }

    public String getPotenza() {
        return potenza;
    }

    public void setPotenza(String potenza) {
        this.potenza = potenza;
    }

    public boolean isPotenzaStatus() {
        return potenzaStatus;
    }

    public void setPotenzaStatus(boolean potenzaStatus) {
        this.potenzaStatus = potenzaStatus;
    }

    public String getTagliaMeseEE() {
        return tagliaMeseEE;
    }

    public void setTagliaMeseEE(String tagliaMeseEE) {
        this.tagliaMeseEE = tagliaMeseEE;
    }

    public boolean isTagliaMeseEEStatus() {
        return tagliaMeseEEStatus;
    }

    public void setTagliaMeseEEStatus(boolean tagliaMeseEEStatus) {
        this.tagliaMeseEEStatus = tagliaMeseEEStatus;
    }

    public String getPrezzoEE() {
        return prezzoEE;
    }

    public void setPrezzoEE(String prezzoEE) {
        this.prezzoEE = prezzoEE;
    }

    public boolean isPrezzoEEStatus() {
        return prezzoEEStatus;
    }

    public void setPrezzoEEStatus(boolean prezzoEEStatus) {
        this.prezzoEEStatus = prezzoEEStatus;
    }

    public String getPrezzoEEConIva() {
        return prezzoEEConIva;
    }

    public void setPrezzoEEConIva(String prezzoEEConIva) {
        this.prezzoEEConIva = prezzoEEConIva;
    }

    public boolean isPrezzoEEConIvaStatus() {
        return prezzoEEConIvaStatus;
    }

    public void setPrezzoEEConIvaStatus(boolean prezzoEEConIvaStatus) {
        this.prezzoEEConIvaStatus = prezzoEEConIvaStatus;
    }

    public String getSforamento() {
        return sforamento;
    }

    public void setSforamento(String sforamento) {
        this.sforamento = sforamento;
    }

    public boolean isSforamentoStatus() {
        return sforamentoStatus;
    }

    public void setSforamentoStatus(boolean sforamentoStatus) {
        this.sforamentoStatus = sforamentoStatus;
    }

    public String getMancatoConsumo() {
        return mancatoConsumo;
    }

    public void setMancatoConsumo(String mancatoConsumo) {
        this.mancatoConsumo = mancatoConsumo;
    }

    public boolean isMancatoConsumoStatus() {
        return mancatoConsumoStatus;
    }

    public void setMancatoConsumoStatus(boolean mancatoConsumoStatus) {
        this.mancatoConsumoStatus = mancatoConsumoStatus;
    }

    public String getCodiceTariffa() {
        return codiceTariffa;
    }

    public void setCodiceTariffa(String codiceTariffa) {
        this.codiceTariffa = codiceTariffa;
    }

    public boolean isCodiceTariffaStatus() {
        return codiceTariffaStatus;
    }

    public void setCodiceTariffaStatus(boolean codiceTariffaStatus) {
        this.codiceTariffaStatus = codiceTariffaStatus;
    }

    public String getEnergyPerc() {
        return energyPerc;
    }

    public void setEnergyPerc(String energyPerc) {
        this.energyPerc = energyPerc;
    }

    public boolean isEnergyPercStatus() {
        return energyPercStatus;
    }

    public void setEnergyPercStatus(boolean energyPercStatus) {
        this.energyPercStatus = energyPercStatus;
    }
}
