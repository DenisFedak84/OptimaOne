package com.aimprosoft.android.optima.centralizedApp.util.energyUtils;

import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.Eng_tRipartizioniConsumiDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDateInterval;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ComputedConsumptionByClusterUtils extends BaseEnergyUtils {

    private double SumKwh;
    private String monthQueryPart;

    public ComputedConsumptionByClusterUtils() {
    }

    public double computeConsumoAnnuo(List<EnergyOfferDateInterval> energyOfferDateIntervals, String cluster) {
        defineMonthQueryPart(energyOfferDateIntervals);
        double consumoTotal = SumKwh / (new Eng_tRipartizioniConsumiDAOImpl().getMonthSumValue(monthQueryPart, cluster) / 100);
        double conF1 = consumoTotal * new Eng_tRipartizioniConsumiDAOImpl().getMonthSumTotalValueAccrodingFascia(cluster, 1) / 100;
        double conF2 = consumoTotal * new Eng_tRipartizioniConsumiDAOImpl().getMonthSumTotalValueAccrodingFascia(cluster, 2) / 100;
        double conF3 = consumoTotal * new Eng_tRipartizioniConsumiDAOImpl().getMonthSumTotalValueAccrodingFascia(cluster, 3) / 100;
        return (conF1 + conF2 + conF3);
    }

    private void defineMonthQueryPart(List<EnergyOfferDateInterval> energyOfferDateIntervals) {
        Set<String> monthsSet = new HashSet<>();
        SumKwh = 0;
        for (EnergyOfferDateInterval energyOfferDateInterval : energyOfferDateIntervals) {
            double kwh1 = (double) (energyOfferDateInterval.getKwh1() != Constants.NO_VALUE ? energyOfferDateInterval.getKwh1() : 0);
            double kwh2 = (double) (energyOfferDateInterval.getKwh2() != Constants.NO_VALUE ? energyOfferDateInterval.getKwh2() : 0);
            double kwh3 = (double) (energyOfferDateInterval.getKwh3() != Constants.NO_VALUE ? energyOfferDateInterval.getKwh3() : 0);
            SumKwh = (kwh1 + kwh2 + kwh3);

            monthsSet.add("SUM(rc_perc_mese" + getMonthNumber(energyOfferDateInterval.getDateTo()) + ") + ");
        }
        for (String monthColumn : monthsSet) {
            monthQueryPart += monthColumn;
        }
    }

}
