package com.aimprosoft.android.optima.centralizedApp.util.energyUtils;

import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.Eng_tRipartizioniConsumiDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDateInterval;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstimatedConsumptionUtils extends BaseEnergyUtils {
    private int intervalsCount;

    public EstimatedConsumptionUtils() {
    }

    private Map<Integer, Integer> absentMonthNumberMap = new HashMap<>();
    private Map<Integer, Integer> presentMonthNumberMap = new HashMap<>();
    private Map<Integer, Double> monthConsumptionMap = new HashMap<>();

    private double Coi_Sum;

    public Map<Integer, Double> getMonthConsumptionMap(List<EnergyOfferDateInterval> energyOfferDateIntervals, int annoKwh, String cluster, boolean isSpecificComputationUsed) {
        intervalsCount = energyOfferDateIntervals.size();
        initializeAbsentMonthMap();
        defineAnsentMonthAndConsumption(energyOfferDateIntervals, annoKwh, isSpecificComputationUsed);
        return buildMonthConsumptionMap(isSpecificComputationUsed ? "M" : cluster);
    }

    private Map<Integer, Double> buildMonthConsumptionMap(String cluster) {
        Eng_tRipartizioniConsumiDAOImpl eng_tRipartizioniConsumiDAO = new Eng_tRipartizioniConsumiDAOImpl();

        double insertedMOnthPerc = getInsertedMonthPerc(cluster) / 100;
        double perc;
        String queryPart;

        for (int absentMonthNumber : absentMonthNumberMap.keySet()) {
            queryPart = getMonthQueryPart(absentMonthNumber);
            perc = eng_tRipartizioniConsumiDAO.getInsertedMonthPerc(queryPart.substring(0, queryPart.length() - 2), cluster);
            double absentConsumption = (perc / 100) * (Coi_Sum / insertedMOnthPerc);
            monthConsumptionMap.put(absentMonthNumber, absentConsumption);
        }
        return monthConsumptionMap;
    }

    private void initializeAbsentMonthMap() {
        for (int i = 1; i < 13; i++) {
            absentMonthNumberMap.put(i, i);
            presentMonthNumberMap.put(i, i);
        }
    }

    private void defineAnsentMonthAndConsumption(List<EnergyOfferDateInterval> energyOfferDateIntervals, int annoKwh, boolean isSpecificComputationUsed) {
        Coi_Sum = energyOfferDateIntervals.size() != 0 ? 0 : annoKwh;
        for (EnergyOfferDateInterval interval : energyOfferDateIntervals) {
            double currentIntervalconsumption = (interval.getKwh1() != Constants.NO_VALUE ? interval.getKwh1() : 0)
                    + (interval.getKwh2() != Constants.NO_VALUE ? interval.getKwh2() : 0)
                    + (interval.getKwh3() != Constants.NO_VALUE ? interval.getKwh3() : 0);
            monthConsumptionMap.put(removeMonthFromMap(interval), currentIntervalconsumption);
            Coi_Sum += currentIntervalconsumption;
        }
        if (isSpecificComputationUsed) {
            Coi_Sum = annoKwh;
        }
        presentMonthNumberMap.keySet().removeAll(absentMonthNumberMap.keySet());
    }

    private int removeMonthFromMap(EnergyOfferDateInterval interval) {
        calendar.setTime(interval.getDateFrom());
        int month = calendar.get(Calendar.MONTH) + 1;
        absentMonthNumberMap.remove(month);
        return month;
    }

    private double getInsertedMonthPerc(String cluster) {
        double result;
        Eng_tRipartizioniConsumiDAOImpl eng_tRipartizioniConsumiDAO = new Eng_tRipartizioniConsumiDAOImpl();
        if (intervalsCount != 0) {
            String queryPart = "";
            for (int monthNumber : presentMonthNumberMap.keySet()) {
                queryPart = queryPart + getMonthQueryPart(monthNumber);
            }
            result = eng_tRipartizioniConsumiDAO.getInsertedMonthPerc(queryPart.substring(0, queryPart.length() - 2), cluster);
        } else {
            result = eng_tRipartizioniConsumiDAO.getMonthSumTotalValue(cluster);
        }
        return result;
    }

    private String getMonthQueryPart(int monthNumber) {
        return "rc_perc_mese" + monthNumber + " + ";
    }
}
