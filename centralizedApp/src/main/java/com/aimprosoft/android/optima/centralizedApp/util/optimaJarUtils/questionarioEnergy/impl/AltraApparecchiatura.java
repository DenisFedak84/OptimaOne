package com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.questionarioEnergy.impl;

import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.questionarioEnergy.QuestionarioValidation;
import com.optima.consumi.domain.Consume;

public class AltraApparecchiatura implements QuestionarioValidation{

    private final String ALTRA_APPARECCHIATURA = "altra_apparecchiatura";
    private EnergyOfferDetails energyOfferDetails;

    public AltraApparecchiatura(EnergyOfferDetails energyOfferDetails) {
        this.energyOfferDetails = energyOfferDetails;
    }

    public AltraApparecchiatura() {
    }

    @Override
    public Consume.DatiEnergia.InfoQuestionario.Elettrodimestici.InfoElettrodomestico getInfoElettrodomestico() {

        Consume.DatiEnergia.InfoQuestionario.Elettrodimestici.InfoElettrodomestico infoElettrodomestico = new Consume.DatiEnergia.InfoQuestionario.Elettrodimestici.InfoElettrodomestico();
        infoElettrodomestico.setCodiceElettrodomestico(ALTRA_APPARECCHIATURA);
        infoElettrodomestico.setAnnoAcquisto(null);
        infoElettrodomestico.setNumSplit(null);
        infoElettrodomestico.setNumUtilizzo(energyOfferDetails.getOtherDeviceWeeklyHourUse());
        infoElettrodomestico.setDimensione(null);
        infoElettrodomestico.setPotenza(energyOfferDetails.getOtherDeviceWatt());
        infoElettrodomestico.setCe(null);
        return infoElettrodomestico;
    }

    @Override
    public boolean validationElettrodomestico() {
        return energyOfferDetails.getOtherDeviceWeeklyHourUse() != 0 && energyOfferDetails.getOtherDeviceWatt() != 0;
    }
}
