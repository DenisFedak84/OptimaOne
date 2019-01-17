package com.aimprosoft.android.optima.centralizedApp.util.energyUtils;

import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.CoeffTransportDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GroupOfPotenzaDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDateInterval;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GroupOfPotenza;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnergyPSUtils extends BaseEnergyUtils {
    private Map<String, Double> testMap = new HashMap<>();
    private double Coi_Sum, Poi_Sum, Co, Po, numerator_r, denominator_r_first_element, denominator_r_second_element, denominator_r_result;
    private int N;
    private final double ALPHA = 0.5;

    public EnergyPSUtils() {
    }

    public int getPS(List<EnergyOfferDateInterval> energyOfferDateIntervals, Map<Integer, Double> monthConsumptionMap, int tariffaId) {
        computeCoAndPo(energyOfferDateIntervals);
        double r = getR(energyOfferDateIntervals);
        ////////////////////
        testMap.put("r", r);
        testMap.put("Co", Co);
        testMap.put("Po", Po);
        int PS = (int) Math.round(Math.abs(r) >= ALPHA ? computationPS1(r, monthConsumptionMap) : computationPS2(tariffaId));
        int roundedPotenzaStimata = (PS != 0 ? PS : 1);
        MyApplication.set("PS", roundedPotenzaStimata);
        MyApplication.set("TestMap", testMap);
        return roundedPotenzaStimata;
        ////////////////
//        return Math.abs(r) >= ALPHA ? computationPS1(r, monthConsumptionMap) : computationPS2(tariffaId);
    }

    private double computationPS1(double r, Map<Integer, Double> monthConsumptionMap) {
        double b = (Math.sqrt(denominator_r_second_element) / Math.sqrt(denominator_r_first_element)) * r;
        double a = Po - (b * Co);
        //////
        testMap.put("a", a);
        testMap.put("b", b);
        //////

        double absentConsumption = getAbsentMonthConsumption(monthConsumptionMap) - Coi_Sum;
        double POT = absentConsumption != 0 ? (absentConsumption * b + a * N) : 0;

        return (POT + Poi_Sum) / (12);
    }

    private double getAbsentMonthConsumption(Map<Integer, Double> monthConsumptionMap) {
        Double consumptionSum = 0.0;
        for (Integer key : monthConsumptionMap.keySet()) {
            consumptionSum += monthConsumptionMap.get(key);
        }
        return consumptionSum;
    }

    private double computationPS2(int tariffaId) {
        GroupOfPotenza groupOfPotenza = new GroupOfPotenzaDAOImpl().getGroupOfPotenzaByValue(Po);
        BigDecimal coeffTransporto = new CoeffTransportDAOImpl().getCoeffTransportoByPotenza(tariffaId);
        double result = ((0.0219111774 - 0.0000001594) * Co) + groupOfPotenza.getCoefficientePotenza() +
                groupOfPotenza.getCoefficientePotenzaConsumi() * Co + coeffTransporto.doubleValue();
        ////
        testMap.put("m", result);
        ////
        return 1 / result;
    }

    private double getR(List<EnergyOfferDateInterval> energyOfferDateIntervals) {
        numerator_r = 0;
        denominator_r_first_element = 0;
        denominator_r_second_element = 0;
        denominator_r_result = 0;
        for (EnergyOfferDateInterval interval : energyOfferDateIntervals) {
            double kwhSum = (interval.getKwh1() != Constants.NO_VALUE ? interval.getKwh1() : 0)
                    + (interval.getKwh2() != Constants.NO_VALUE ? interval.getKwh2() : 0)
                    + (interval.getKwh3() != Constants.NO_VALUE ? interval.getKwh3() : 0);
            numerator_r += ((kwhSum - Co) * (interval.getPotenzaImpegnata() - Po));
            denominator_r_first_element += Math.pow(kwhSum - Co, 2);
            denominator_r_second_element += Math.pow(interval.getPotenzaImpegnata() - Po, 2);
        }
        numerator_r = numerator_r / N;
        denominator_r_first_element = denominator_r_first_element / N;
        denominator_r_second_element = denominator_r_second_element / N;
        denominator_r_result = Math.sqrt(denominator_r_first_element * denominator_r_second_element);
        return numerator_r / denominator_r_result;
    }

    private void computeCoAndPo(List<EnergyOfferDateInterval> energyOfferDateIntervals) {
        Coi_Sum = 0;
        Poi_Sum = 0;
        Po = 0;
        Co = 0;
        N = energyOfferDateIntervals.size();

        for (EnergyOfferDateInterval interval : energyOfferDateIntervals) {
            double currentIntervalconsumption = (interval.getKwh1() != Constants.NO_VALUE ? interval.getKwh1() : 0)
                    + (interval.getKwh2() != Constants.NO_VALUE ? interval.getKwh2() : 0)
                    + (interval.getKwh3() != Constants.NO_VALUE ? interval.getKwh3() : 0);
            Coi_Sum += currentIntervalconsumption;
            Poi_Sum += interval.getPotenzaImpegnata();
        }
        Co = Coi_Sum / N;
        Po = Poi_Sum / N;
    }
}
