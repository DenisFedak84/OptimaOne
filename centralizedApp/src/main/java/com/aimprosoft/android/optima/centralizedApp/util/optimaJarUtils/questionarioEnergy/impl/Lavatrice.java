package com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.questionarioEnergy.impl;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyClassDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WeeklyWashingDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.YearOfReferenceDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.questionarioEnergy.QuestionarioValidation;
import com.optima.consumi.domain.Consume;

public class Lavatrice implements QuestionarioValidation {

    private final String LAVATRICE = "lavatrice";
    private EnergyOfferDetails energyOfferDetails;

    public Lavatrice(EnergyOfferDetails energyOfferDetails) {
        this.energyOfferDetails = energyOfferDetails;
    }

    public Lavatrice() {
    }

    @Override
    public Consume.DatiEnergia.InfoQuestionario.Elettrodimestici.InfoElettrodomestico getInfoElettrodomestico() {
        YearOfReferenceDAOImpl yearOfReferenceDAO = new YearOfReferenceDAOImpl();
        EnergyClassDAOImpl energyClassDAO = new EnergyClassDAOImpl();

        Consume.DatiEnergia.InfoQuestionario.Elettrodimestici.InfoElettrodomestico infoElettrodomestico = new Consume.DatiEnergia.InfoQuestionario.Elettrodimestici.InfoElettrodomestico();
        infoElettrodomestico.setCodiceElettrodomestico(LAVATRICE);
        infoElettrodomestico.setAnnoAcquisto(yearOfReferenceDAO.getYearOfReferenceDescById(energyOfferDetails.getWashingMachinePurchaseYear()));
        infoElettrodomestico.setNumSplit(null);
        infoElettrodomestico.setNumUtilizzo(new WeeklyWashingDAOImpl().getWeeklyWashingValueById(energyOfferDetails.getWashingMachineWeeklyUse()));
        infoElettrodomestico.setDimensione(null);
        infoElettrodomestico.setPotenza(null);
        infoElettrodomestico.setCe(energyClassDAO.getEnergyClassDescById(energyOfferDetails.getWashingMachineEnergyClass()));
        return infoElettrodomestico;
    }

    @Override
    public boolean validationElettrodomestico() {
        return energyOfferDetails.getWashingMachineEnergyClass() != 0;
    }
}
