package com.aimprosoft.android.optima.centralizedApp.util;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.CoeffTransportDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDateIntervalDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.Eng_tRipartizioniConsumiDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GroupOfPotenzaDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDateInterval;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GroupOfPotenza;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EnergiaConsumptionAlgoritm extends BaseAlgoritm {
    private Map<String, Double> testMap = new HashMap<>();
    private Map<Integer, Integer> absentMonthNumberMap = new HashMap<>();
    private Map<Integer, Integer> presentMonthNumberMap = new HashMap<>();
    private Map<Integer, Double> monthConsumptionMap = new HashMap<>();

    public static final String YEARLY_CONSUMPTION = "yearlyConsumption";
    public static final String YEARLY_CONSUMPTION_BTA6_MT = "yearlyConsumptionBta6Mt";
    public static final String MONTH_CONSUMPTION_MAP = "month_consumption_map";

    private String monthQueryPart;
    private String cluster;
    private int monthFromMayToAugust = 0;
    private int monthEcxeptMonthsFromMayToAugust = 0;
    public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private List<EnergyOfferDateInterval> energyOfferDateIntervals;

    private EnergyOfferDetails energyOfferDetails;
    private int tipoContatore;
    private int PP = 77;
    private int N;
    private final double ALPHA = 0.5;

    private double p1;
    private double p2;
    private double p3;
    private double k1;
    private double k2;

    private double Co = 0;
    private double Po = 0;
    private double Coi_Sum = 0;
    private double Poi_Sum = 0;
    private double POT = 0;
    private double numerator_r = 0;
    private double denominator_r_first_element = 0;
    private double denominator_r_second_element = 0;
    private double denominator_r_result = 0;

    private Calendar calendar = Calendar.getInstance();

    public EnergiaConsumptionAlgoritm(EnergyOfferDetails energyOfferDetails, int tipoContatore) {
        this.energyOfferDetails = energyOfferDetails;
        this.tipoContatore = tipoContatore;
        energyOfferDateIntervals = new EnergyOfferDateIntervalDAOImpl().getOfferDateIntervalByDetailsId(energyOfferDetails.getEnergyDetailOfferId());
        N = energyOfferDateIntervals.size();
    }

    public Map<String, Object> getConsumptionMap() {
        defineMonthGroupSize();
        double yearlyConsumption = monthFromMayToAugust >= 2 && monthEcxeptMonthsFromMayToAugust >= 2 ?
                getConsumoAnnuoIfMay_AugustPresented() :
                getConsumoAnnuoIfMay_AugustAbsent();
        double PS = energyOfferDetails.getTensione() != 1 ? getPS() : 0.00;
        testMap.put("PS", PS);
        MyApplication.set("TestMap", testMap);
        Map<String, Object> map = new HashMap<>();
        map.put(YEARLY_CONSUMPTION, yearlyConsumption);
        map.put(YEARLY_CONSUMPTION_BTA6_MT, PS);
        map.put(MONTH_CONSUMPTION_MAP, monthConsumptionMap);
        return map;
    }


    private double getConsumoAnnuoIfMay_AugustPresented() {
        k1 = 0;
        k2 = 0;
        p1 = 0;
        p2 = 0;
        p3 = 0;
        monthQueryPart = "";
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
            monthsSet.add("SUM(rc_perc_mese" + getMonthNumber(energyOfferDateInterval.getDateTo()) + ") + ");
            p1 += kwh1;
            p2 += kwh2;
            p3 += kwh3;
        }

        double K = (k1 / monthFromMayToAugust) / (k2 / monthEcxeptMonthsFromMayToAugust);
        if (K > 0.2 && K < 1) return getConsumoAnnuoIfMay_AugustAbsent();
        if (K > 1) cluster = "E";
        if (K < 0.2) cluster = "NE";
        for (String monthColumn : monthsSet) {
            monthQueryPart += monthColumn;
        }
        return computeConsumoAnnuo();
    }

    private double getConsumoAnnuoIfMay_AugustAbsent() {
        p1 = 0;
        p2 = 0;
        p3 = 0;

        monthQueryPart = "";
        Set<String> monthsSet = new HashSet<>();
        for (EnergyOfferDateInterval energyOfferDateInterval : energyOfferDateIntervals) {
            monthsSet.add(getMonthQueryPart(energyOfferDateInterval.getDateTo()));
            p1 += (double) (energyOfferDateInterval.getKwh1() != Constants.NO_VALUE ? energyOfferDateInterval.getKwh1() : 0);
            p2 += (double) (energyOfferDateInterval.getKwh2() != Constants.NO_VALUE ? energyOfferDateInterval.getKwh2() : 0);
            p3 += (double) (energyOfferDateInterval.getKwh3() != Constants.NO_VALUE ? energyOfferDateInterval.getKwh3() : 0);
        }
        double P = (p1 + p2) / (p1 + p2 + p3);
        double deltaP = P * 100 - PP;
        setCluster(deltaP);
        for (String monthColumn : monthsSet) {
            monthQueryPart += monthColumn;
        }
        return computeConsumoAnnuo();
    }

    private String getMonthQueryPart(Date date) {
        return "SUM(rc_perc_mese" + getMonthNumber(date) + ") + ";
    }

    private String getMonthQueryPart(int monthNumber) {
        return "rc_perc_mese" + monthNumber + " + ";
    }

//    private String getMonthQueryPart(Date date) {
//        return "SUM(rc_perc_mese" + getMonthNumber(convertDatetoString(date)) + ") + ";
//    }

    private double computeConsumoAnnuo() {
        double consumoTotal = (p1 + p2 + p3) / (new Eng_tRipartizioniConsumiDAOImpl().getMonthSumValue(monthQueryPart, cluster) / 100);
        double conF1 = consumoTotal * new Eng_tRipartizioniConsumiDAOImpl().getMonthSumTotalValueAccrodingFascia(cluster, 1) / 100;
        double conF2 = consumoTotal * new Eng_tRipartizioniConsumiDAOImpl().getMonthSumTotalValueAccrodingFascia(cluster, 2) / 100;
        double conF3 = consumoTotal * new Eng_tRipartizioniConsumiDAOImpl().getMonthSumTotalValueAccrodingFascia(cluster, 3) / 100;
        return (conF1 + conF2 + conF3);
    }

    private void setCluster(double deltaP) {
        if (tipoContatore == 1) {
            cluster = "M";
        } else {
            if (deltaP >= 10) {
                cluster = "P++";
                return;
            }
            if (deltaP < 10 && deltaP >= 7.5) {
                cluster = "P+";
                return;
            }
            if (deltaP < 7.5 && deltaP >= 5) {
                cluster = "P";
                return;
            }
            if (deltaP < 5 && deltaP >= 2.5) {
                cluster = "M+";
                return;
            }
            if (deltaP < 2.5 && deltaP >= -2.5) {
                cluster = "M";
                return;
            }
            if (deltaP < -2.5 && deltaP >= -5) {
                cluster = "M-";
                return;
            }
            if (deltaP < -5 && deltaP >= -7.5) {
                cluster = "OP";
                return;
            }
            if (deltaP < -7.5 && deltaP >= -10) {
                cluster = "OP+";
                return;
            }
            if (deltaP < -10) {
                cluster = "OP++";
                return;
            }
        }
    }

    private void defineMonthGroupSize() {
        for (EnergyOfferDateInterval energyOfferDateInterval : energyOfferDateIntervals) {
            if (isMonthBelongToFirstGroup(convertDatetoString(energyOfferDateInterval.getDateTo()))) {
                monthFromMayToAugust++;
            } else {
                monthEcxeptMonthsFromMayToAugust++;
            }
        }
    }

    private boolean isMonthBelongToFirstGroup(String date) {
        boolean result = false;
        try {
            calendar.setTime(simpleDateFormat.parse(date));
            int monthNumber = calendar.get(Calendar.MONTH) + 1;
            result = (monthNumber > 4 && monthNumber < 9);
        } catch (ParseException e) {
            Log.e(e.getMessage(), "can't parse date", e);
        }
        return result;
    }


//    private boolean checkForMonthExist(String date) {
//        try {
//            Date currentDate = simpleDateFormat.parse(date);
//            calendar.setTime(currentDate);
//            int month = calendar.get(Calendar.MONTH);.
//            if (currentDate.getMonth() > 3 && currentDate.getMonth() < 8) {
//                monthFromMayToAugust++;
//                return true;
//            } else {
//                monthEcxeptMonthsFromMayToAugust++;
//                return false;
//            }
//        } catch (ParseException e) {
//            Log.e(e.getMessage(), "can't parse date", e);
//        }
//        return false;
//    }

    public double getPS() {
        computeCoAndPo();
        double r = getR();
        testMap.put("r", r);
        testMap.put("Co", Co);
        testMap.put("Po", Po);
        MyApplication.set("cluster", cluster);
//        computationPS1(r);  //
//        computationPS2(); //
        return Math.abs(r) >= ALPHA ? computationPS1(r) : computationPS2();
    }

    private double computationPS1(double r) {
        double b = (Math.sqrt(denominator_r_second_element) / Math.sqrt(denominator_r_first_element)) * r;
        double a = Po - (b * Co);
//        double consumoAnno = (energyOfferDetails.getYearlyKwh() != null ? energyOfferDetails.getYearlyKwh() : 0) +
//                (energyOfferDetails.getYearlyKwh2() != null ? energyOfferDetails.getYearlyKwh2() : 0) +
//                (energyOfferDetails.getYearlyKwh3() != null ? energyOfferDetails.getYearlyKwh3() : 0);

        Eng_tRipartizioniConsumiDAOImpl eng_tRipartizioniConsumiDAO = new Eng_tRipartizioniConsumiDAOImpl();
        String queryPart;

        double insertedMOnthPerc = getInsertedMonthPerc() / 100;
        double perc;

        for (int absentMonthNumber : absentMonthNumberMap.keySet()) {
            queryPart = getMonthQueryPart(absentMonthNumber);
//            perc = eng_tRipartizioniConsumiDAO.getMonthSumValue(queryPart.substring(0, queryPart.length()), cluster);
            perc = eng_tRipartizioniConsumiDAO.getInsertedMonthPerc(queryPart.substring(0, queryPart.length() - 2), cluster);
            double absentConsumption = (perc / 100) * (Coi_Sum / insertedMOnthPerc);
//            Coi_Sum += absentConsumption;
            monthConsumptionMap.put(absentMonthNumber, absentConsumption);
            POT += absentConsumption * b + a;
        }

//        for (EnergyOfferDateInterval interval : energyOfferDateIntervals) {
//            double monthCoeffcient = (consumoAnno * eng_tRipartizioniConsumiDAO.getMonthSumValue(getMonthQueryPart(interval.getDateTo()), cluster)) / 100;
//            POT += a + (monthCoeffcient * b) + interval.getPotenzaImpegnata();
//        }

        testMap.put("a", a);
        testMap.put("b", b);
        return (POT + Poi_Sum) / (12);
//        return POT / (energyOfferDateIntervals.size() + 6);
    }

    private double computationPS2() {
        GroupOfPotenza groupOfPotenza = new GroupOfPotenzaDAOImpl().getGroupOfPotenzaByValue(Po);
        BigDecimal coeffTransporto = new CoeffTransportDAOImpl().getCoeffTransportoByPotenza(energyOfferDetails.getTariffaId());
        double result = ((0.0219111774 - 0.0000001594) * Co) + groupOfPotenza.getCoefficientePotenza() +
                groupOfPotenza.getCoefficientePotenzaConsumi() * Co + coeffTransporto.doubleValue();
        testMap.put("m", result);
        return 1 / result;
    }

    private double getR() {
        for (EnergyOfferDateInterval interval : energyOfferDateIntervals) {
            double kwhSum = (interval.getKwh1()!=Constants.NO_VALUE? interval.getKwh1():0)
                    + (interval.getKwh2()!=Constants.NO_VALUE ? interval.getKwh2():0)
                    + (interval.getKwh3()!=Constants.NO_VALUE ? interval.getKwh3():0);
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

    private void computeCoAndPo() {
        initializeAbsentMonthMap();
        for (EnergyOfferDateInterval interval : energyOfferDateIntervals) {
            double currentIntervalconsumption = ((interval.getKwh1() != Constants.NO_VALUE ? interval.getKwh1() : 0)
                    + (interval.getKwh2() != Constants.NO_VALUE ? interval.getKwh2() : 0)
                    + (interval.getKwh3() != Constants.NO_VALUE ? interval.getKwh3() : 0));
            monthConsumptionMap.put(removeMonthFromMap(interval), currentIntervalconsumption);
            Coi_Sum += currentIntervalconsumption;
            Poi_Sum += interval.getPotenzaImpegnata();
        }
        presentMonthNumberMap.keySet().removeAll(absentMonthNumberMap.keySet());
        Co = Coi_Sum / N;
        Po = Poi_Sum / N;
    }

    private void initializeAbsentMonthMap() {
        for (int i = 1; i < 13; i++) {
            absentMonthNumberMap.put(i, i);
            presentMonthNumberMap.put(i, i);
        }
    }

    private int removeMonthFromMap(EnergyOfferDateInterval interval) {
        calendar.setTime(interval.getDateFrom());
        int month = calendar.get(Calendar.MONTH) + 1;
        absentMonthNumberMap.remove(month);
        return month;
    }

    private double getInsertedMonthPerc() {
        String queryPart = "";
        for (int monthNumber : presentMonthNumberMap.keySet()) {
            queryPart = queryPart + getMonthQueryPart(monthNumber);
        }
        return new Eng_tRipartizioniConsumiDAOImpl().getInsertedMonthPerc(queryPart.substring(0, queryPart.length() - 2), cluster);
    }
}
