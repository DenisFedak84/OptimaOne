package com.aimprosoft.android.optima.centralizedApp.util.autotest.model;

public class GasTestDetails extends AbstractTestModel {
    private String tagliaMeseGas;
    private boolean tagliaMeseGasStatus;
    private String prezzoGas;
    private boolean prezzoGasStatus;
    private String prezzoGasConIva;
    private boolean prezzoGasConIvaStatus;
    private String sforamento;
    private boolean sforamentoStatus;
    private String mancatoConsumo;
    private boolean mancatoConsumoStatus;
    private String codiceTariffa;
    private boolean codiceTariffaStatus;
    private String gasPerc;
    private boolean gasPercStatus;
    private String pdrNumber;

    public GasTestDetails() {
    }

    public boolean getTotalResult() {
        return tagliaMeseGasStatus && prezzoGasStatus && prezzoGasConIvaStatus &&
                sforamentoStatus && mancatoConsumoStatus && codiceTariffaStatus &&
                gasPercStatus;
    }

    public String getPdrNumber() {
        return pdrNumber;
    }

    public void setPdrNumber(String pdrNumber) {
        this.pdrNumber = pdrNumber;
    }

    public String getTagliaMeseGas() {
        return tagliaMeseGas;
    }

    public void setTagliaMeseGas(String tagliaMeseGas) {
        this.tagliaMeseGas = tagliaMeseGas;
    }

    public boolean isTagliaMeseGasStatus() {
        return tagliaMeseGasStatus;
    }

    public void setTagliaMeseGasStatus(boolean tagliaMeseGasStatus) {
        this.tagliaMeseGasStatus = tagliaMeseGasStatus;
    }

    public String getPrezzoGas() {
        return prezzoGas;
    }

    public void setPrezzoGas(String prezzoGas) {
        this.prezzoGas = prezzoGas;
    }

    public boolean isPrezzoGasStatus() {
        return prezzoGasStatus;
    }

    public void setPrezzoGasStatus(boolean prezzoGasStatus) {
        this.prezzoGasStatus = prezzoGasStatus;
    }

    public String getPrezzoGasConIva() {
        return prezzoGasConIva;
    }

    public void setPrezzoGasConIva(String prezzoGasConIva) {
        this.prezzoGasConIva = prezzoGasConIva;
    }

    public boolean isPrezzoGasConIvaStatus() {
        return prezzoGasConIvaStatus;
    }

    public void setPrezzoGasConIvaStatus(boolean prezzoGasConIvaStatus) {
        this.prezzoGasConIvaStatus = prezzoGasConIvaStatus;
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

    public String getGasPerc() {
        return gasPerc;
    }

    public void setGasPerc(String gasPerc) {
        this.gasPerc = gasPerc;
    }

    public boolean isGasPercStatus() {
        return gasPercStatus;
    }

    public void setGasPercStatus(boolean gasPercStatus) {
        this.gasPercStatus = gasPercStatus;
    }

}
