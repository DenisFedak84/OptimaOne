package com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.questionarioEnergy.impl;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.HoursOfReferenceDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.SplitNumberDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.YearOfReferenceDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.questionarioEnergy.QuestionarioValidation;
import com.optima.consumi.domain.Consume;

public class CondizionatoreRiscaldamento implements QuestionarioValidation {

    private final String CONDIZIONATORE_RISCALDAMENTO = "condizionatore_riscaldamento";
    private EnergyOfferDetails energyOfferDetails;

    public CondizionatoreRiscaldamento(EnergyOfferDetails energyOfferDetails) {
        this.energyOfferDetails = energyOfferDetails;
    }

    public CondizionatoreRiscaldamento() {
    }

    @Override
    public Consume.DatiEnergia.InfoQuestionario.Elettrodimestici.InfoElettrodomestico getInfoElettrodomestico() {

        YearOfReferenceDAOImpl yearOfReferenceDAO = new YearOfReferenceDAOImpl();
        SplitNumberDAOImpl splitNumberDAO = new SplitNumberDAOImpl();
        HoursOfReferenceDAOImpl hoursOfReferenceDAO = new HoursOfReferenceDAOImpl();

        Consume.DatiEnergia.InfoQuestionario.Elettrodimestici.InfoElettrodomestico infoElettrodomestico = new Consume.DatiEnergia.InfoQuestionario.Elettrodimestici.InfoElettrodomestico();
        infoElettrodomestico.setCodiceElettrodomestico(CONDIZIONATORE_RISCALDAMENTO);
        infoElettrodomestico.setAnnoAcquisto(yearOfReferenceDAO.getYearOfReferenceDescById(energyOfferDetails.getAirConditionerPurchaseYear()));
        infoElettrodomestico.setNumSplit(splitNumberDAO.getSplitNumberValueById(energyOfferDetails.getAirConditionerSplitId()));
        infoElettrodomestico.setNumUtilizzo(Integer.valueOf(hoursOfReferenceDAO.getHoursOfReferenceDescById(energyOfferDetails.getAirConditionerWinterHoursId())));
        infoElettrodomestico.setDimensione(null);
        infoElettrodomestico.setPotenza(null);
        infoElettrodomestico.setCe(null);
        return infoElettrodomestico;
    }

    @Override
    public boolean validationElettrodomestico() {
        return energyOfferDetails.getAirConditionerWinterHoursId() != 0 &&
                energyOfferDetails.getAirConditionerSplitId() != 0;
    }
}
