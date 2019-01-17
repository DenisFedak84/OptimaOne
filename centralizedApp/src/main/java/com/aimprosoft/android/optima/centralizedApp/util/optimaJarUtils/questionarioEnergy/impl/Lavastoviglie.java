package com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.questionarioEnergy.impl;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyClassDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WeeklyWashingDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.YearOfReferenceDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.questionarioEnergy.QuestionarioValidation;
import com.optima.consumi.domain.Consume;

public class Lavastoviglie implements QuestionarioValidation {

    private final String LAVASTOVIGLIE = "lavastoviglie";
    private EnergyOfferDetails energyOfferDetails;

    public Lavastoviglie(EnergyOfferDetails energyOfferDetails) {
        this.energyOfferDetails = energyOfferDetails;
    }

    public Lavastoviglie() {
    }

    @Override
    public Consume.DatiEnergia.InfoQuestionario.Elettrodimestici.InfoElettrodomestico getInfoElettrodomestico() {
        YearOfReferenceDAOImpl yearOfReferenceDAO = new YearOfReferenceDAOImpl();
        EnergyClassDAOImpl energyClassDAO = new EnergyClassDAOImpl();

        Consume.DatiEnergia.InfoQuestionario.Elettrodimestici.InfoElettrodomestico infoElettrodomestico = new Consume.DatiEnergia.InfoQuestionario.Elettrodimestici.InfoElettrodomestico();
        infoElettrodomestico.setCodiceElettrodomestico(LAVASTOVIGLIE);
        infoElettrodomestico.setAnnoAcquisto(yearOfReferenceDAO.getYearOfReferenceDescById(energyOfferDetails.getDishwasherPurchaseYear()));
        infoElettrodomestico.setNumSplit(null);
        infoElettrodomestico.setNumUtilizzo(new WeeklyWashingDAOImpl().getWeeklyWashingValueById(energyOfferDetails.getDishwasherWeeklyUse()));
        infoElettrodomestico.setDimensione(null);
        infoElettrodomestico.setPotenza(null);
        infoElettrodomestico.setCe(energyClassDAO.getEnergyClassDescById(energyOfferDetails.getDishwasherEnergyClass()));
        return infoElettrodomestico;
    }


    @Override
    public boolean validationElettrodomestico() {
        return energyOfferDetails.getDishwasherEnergyClass() != 0;
    }
}
