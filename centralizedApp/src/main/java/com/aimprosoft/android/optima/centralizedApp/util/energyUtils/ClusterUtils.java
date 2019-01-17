package com.aimprosoft.android.optima.centralizedApp.util.energyUtils;

import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDateInterval;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClusterUtils extends BaseEnergyUtils {

    private double p1, p2, p3, k1, k2;

    private int monthFromMayToAugust = 0;
    private int monthEcxeptMonthsFromMayToAugust = 0;

    private String cluster;

    public ClusterUtils() {
    }

    public String getCluster(List<EnergyOfferDateInterval> energyOfferDateIntervals, int tipoContatore) {
        defineMonthGroupSize(energyOfferDateIntervals);
        return energyOfferDateIntervals.size() != 0 ? computerCluster(energyOfferDateIntervals, tipoContatore) : "M";
    }

    private String computerCluster(List<EnergyOfferDateInterval> energyOfferDateIntervals, int tipoContatore) {
        return monthFromMayToAugust >= 2 && monthEcxeptMonthsFromMayToAugust >= 2 ?
                getConsumoAnnuoIfMay_AugustPresented(energyOfferDateIntervals, tipoContatore) :
                getConsumoAnnuoIfMay_AugustAbsent(energyOfferDateIntervals, tipoContatore);
    }


    private String getConsumoAnnuoIfMay_AugustPresented(List<EnergyOfferDateInterval> energyOfferDateIntervals, int tipoContatore) {
        k1 = 0;
        k2 = 0;
        p1 = 0;
        p2 = 0;
        p3 = 0;
//      monthQueryPart = "";
        Set<String> monthsSet = new HashSet<>();

        for (EnergyOfferDateInterval energyOfferDateInterval : energyOfferDateIntervals) {
            double kwh1 = (double) (energyOfferDateInterval.getKwh1() != Constants.NO_VALUE ? energyOfferDateInterval.getKwh1() : 0);
            double kwh2 = (double) (energyOfferDateInterval.getKwh2() != Constants.NO_VALUE ? energyOfferDateInterval.getKwh2() : 0);
            double kwh3 = (double) (energyOfferDateInterval.getKwh3() != Constants.NO_VALUE ? energyOfferDateInterval.getKwh3() : 0);
            double currentIntervalKwhSum = (kwh1 + kwh2 + kwh3);
            if (isMonthBelongToFirstGroup(convertDatetoString(energyOfferDateInterval.getDateTo()))) {
                k1 += currentIntervalKwhSum;
            } else {
                k2 += currentIntervalKwhSum;
            }

//            for (int i = 0; i < intervalList.size(); i += 5) {
//            double kwh1 = Double.valueOf(intervalList.get(i + 2).toString());
//            double kwh2 = Double.valueOf(intervalList.get(i + 3).toString());
//            double kwh3 = Double.valueOf(intervalList.get(i + 4).toString());
//            double currentIntervalKwhSum = kwh1 + kwh2 + kwh3;
//            if (checkForMonthExist(String.valueOf(intervalList.get(i + 1)))) {
//                k1 += currentIntervalKwhSum;
//            } else {
//                k2 += currentIntervalKwhSum;
//            }
//            monthsSet.add("SUM(rc_perc_mese" + getMonthNumber(energyOfferDateInterval.getDateTo()) + ") + ");
//            p1 += kwh1;
//            p2 += kwh2;
//            p3 += kwh3;
        }

        double K = (k1 / monthFromMayToAugust) / (k2 / monthEcxeptMonthsFromMayToAugust);
        if (K > 0.2 && K < 1) cluster = getConsumoAnnuoIfMay_AugustAbsent(energyOfferDateIntervals, tipoContatore);
        if (K > 1) cluster = "E";
        if (K < 0.2) cluster = "NE";
//        for (String monthColumn : monthsSet) {
//            monthQueryPart += monthColumn;
//        }
        return cluster;
    }

    private String getConsumoAnnuoIfMay_AugustAbsent(List<EnergyOfferDateInterval> energyOfferDateIntervals, int tipoContatore) {
        final int PP = 77;

        p1 = 0;
        p2 = 0;
        p3 = 0;

//        monthQueryPart = "";
//        Set<String> monthsSet = new HashSet<>();
        for (EnergyOfferDateInterval energyOfferDateInterval : energyOfferDateIntervals) {
//            monthsSet.add(getMonthQueryPart(energyOfferDateInterval.getDateTo()));
            p1 += (double) (energyOfferDateInterval.getKwh1() != Constants.NO_VALUE ? energyOfferDateInterval.getKwh1() : 0);
            p2 += (double) (energyOfferDateInterval.getKwh2() != Constants.NO_VALUE ? energyOfferDateInterval.getKwh2() : 0);
            p3 += (double) (energyOfferDateInterval.getKwh3() != Constants.NO_VALUE ? energyOfferDateInterval.getKwh3() : 0);
        }
        double P = (p1 + p2) / (p1 + p2 + p3);
        double deltaP = P * 100 - PP;
        cluster = defineCluster(deltaP, tipoContatore);
//        for (String monthColumn : monthsSet) {
//            monthQueryPart += monthColumn;
//        }
        return cluster;
    }

    private void defineMonthGroupSize(List<EnergyOfferDateInterval> energyOfferDateIntervals) {
        for (EnergyOfferDateInterval energyOfferDateInterval : energyOfferDateIntervals) {
            if (isMonthBelongToFirstGroup(convertDatetoString(energyOfferDateInterval.getDateTo()))) {
                monthFromMayToAugust++;
            } else {
                monthEcxeptMonthsFromMayToAugust++;
            }
        }
    }

    private String defineCluster(double deltaP, int tipoContatore) {
        return tipoContatore == 1 ? "M" : getClusterByDeltaP(deltaP);
    }

    private String getClusterByDeltaP(double deltaP) {
        String cluster = "";
        if (deltaP >= 10) {
            cluster = "P++";
        }
        if (deltaP < 10 && deltaP >= 7.5) {
            cluster = "P+";
        }
        if (deltaP < 7.5 && deltaP >= 5) {
            cluster = "P";
        }
        if (deltaP < 5 && deltaP >= 2.5) {
            cluster = "M+";
        }
        if (deltaP < 2.5 && deltaP >= -2.5) {
            cluster = "M";
        }
        if (deltaP < -2.5 && deltaP >= -5) {
            cluster = "M-";
        }
        if (deltaP < -5 && deltaP >= -7.5) {
            cluster = "OP";
        }
        if (deltaP < -7.5 && deltaP >= -10) {
            cluster = "OP+";
        }
        if (deltaP < -10) {
            cluster = "OP++";
        }
        return cluster;
    }
}
