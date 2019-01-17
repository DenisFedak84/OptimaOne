package com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.questionarioEnergy.impl;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyClassDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.YearOfReferenceDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.questionarioEnergy.QuestionarioValidation;
import com.optima.consumi.domain.Consume;

public class Frigorifero implements QuestionarioValidation{

    private final String FRIGORIFERO = "frigorifero";
    private EnergyOfferDetails energyOfferDetails;

    public Frigorifero(EnergyOfferDetails energyOfferDetails) {
        this.energyOfferDetails = energyOfferDetails;
    }

    public Frigorifero() {
    }

    @Override
    public Consume.DatiEnergia.InfoQuestionario.Elettrodimestici.InfoElettrodomestico getInfoElettrodomestico() {

        YearOfReferenceDAOImpl yearOfReferenceDAO = new YearOfReferenceDAOImpl();
        EnergyClassDAOImpl energyClassDAO = new EnergyClassDAOImpl();

        Consume.DatiEnergia.InfoQuestionario.Elettrodimestici.InfoElettrodomestico infoElettrodomestico = new Consume.DatiEnergia.InfoQuestionario.Elettrodimestici.InfoElettrodomestico();
        infoElettrodomestico.setCodiceElettrodomestico(FRIGORIFERO);
        infoElettrodomestico.setAnnoAcquisto(yearOfReferenceDAO.getYearOfReferenceDescById(energyOfferDetails.getRefrigeratorPurchaseYear()));
        infoElettrodomestico.setNumSplit(null);
        infoElettrodomestico.setNumUtilizzo(null);
        infoElettrodomestico.setDimensione(energyOfferDetails.getRefrigeratorWeeklyUse());
        infoElettrodomestico.setPotenza(null);
        infoElettrodomestico.setCe(energyClassDAO.getEnergyClassDescById(energyOfferDetails.getRefrigeratorEnergyClass()));
        return infoElettrodomestico;
    }

    @Override
    public boolean validationElettrodomestico() {
        return energyOfferDetails.getRefrigeratorEnergyClass() != 0;
    }
}
