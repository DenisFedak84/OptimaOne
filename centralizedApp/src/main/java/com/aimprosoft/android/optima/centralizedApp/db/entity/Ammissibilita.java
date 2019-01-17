package com.aimprosoft.android.optima.centralizedApp.db.entity;

public class Ammissibilita extends BaseEntity {
    private int verifica_amm;
    private String motivazione;

    public Ammissibilita() {
    }

    public Ammissibilita(int verifica_amm, String motivazione) {
        this.verifica_amm = verifica_amm;
        this.motivazione = motivazione;
    }

    public int getVerifica_amm() {
        return verifica_amm;
    }

    public void setVerifica_amm(int verifica_amm) {
        this.verifica_amm = verifica_amm;
    }

    public String getMotivazione() {
        return motivazione;
    }

    public void setMotivazione(String motivazione) {
        this.motivazione = motivazione;
    }
}
