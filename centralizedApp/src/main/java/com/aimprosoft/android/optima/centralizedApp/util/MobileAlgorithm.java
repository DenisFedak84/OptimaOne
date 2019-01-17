package com.aimprosoft.android.optima.centralizedApp.util;

import android.util.Log;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MobileAlgorithm {


    public static Map<Integer, Double> getBestBundleByPrice(double numberMinutes, int numberOfSims, int numberOfBundlesTypes, int matchingPrice) {
        Map<Integer, Double> returnMap = new LinkedHashMap<>();
        double bestBundle = Integer.MAX_VALUE;
        double totalNumberOfMinutes = numberMinutes;
        boolean bestbundle = !(matchingPrice == 0);
        int[] str_arr = new int[]{100, 200, 400, 800};
        Map<String, Map> mapofmap = new HashMap<>();
        if (!bestbundle) {
            Log.i("", "roe");
            str_arr[0] = 800;
            str_arr[1] = 400;
            str_arr[2] = 200;
            str_arr[3] = 100;
        }
        Map<Integer, Double> map;

        for (int i = 0; i < str_arr.length; i++) {
            for (int j = 0; j < str_arr.length; j++) {
                for (int k = 0; k < str_arr.length; k++) {
                    for (int l = 0; l < str_arr.length; l++) {
                        if (i != j && i != k && i != l && j != k && j != l && k != l) {
                            double maxbundlel = 0;
                            int minusl = 0;

                            do {
                                double maxbundlek = 0;
                                int minusk = 0;
                                do {

                                    double maxbundlej = 0;
                                    int minusj = 0;
                                    do {

                                        double maxbundlei = 0;
                                        int minusi = 0;

                                        do {

                                            map = new HashMap<>();
                                            numberMinutes = totalNumberOfMinutes;
                                            numberMinutes = getNumberOfBundles(numberMinutes, str_arr[i], numberOfSims, map, minusi);
                                            numberMinutes = getNumberOfBundles(numberMinutes, str_arr[j], numberOfSims, map, minusj);
                                            numberMinutes = getNumberOfBundles(numberMinutes, str_arr[k], numberOfSims, map, minusk);
                                            numberMinutes = getNumberOfBundles(numberMinutes, str_arr[l], numberOfSims, map, minusl);

                                            mapofmap.put("MappaDi minus" + minusi + " Matrix " + i + j + k + l, map);
                                            if (numberMinutes == 0) {
                                                double total = 0;

                                                double numberofbundle = 0;
                                                for (Integer key : map.keySet()) {
                                                    total = total + Prices.MINUTES_PRICES.get(key) * map.get(key);
                                                    numberofbundle = numberofbundle + map.get(key);
                                                    if (numberofbundle >= numberOfSims && !bestbundle) {
                                                        return map;
                                                    }
                                                }

                                                if (Math.abs(total - matchingPrice) <= bestBundle) {
                                                    bestBundle = Math.abs(total - matchingPrice);
                                                    returnMap = map;
                                                }
                                            }

                                            maxbundlei = map.get(str_arr[i]);
                                            minusi++;

                                        } while (maxbundlei > 1);

                                        maxbundlej = map.get(str_arr[j]);
                                        minusj++;

                                    } while (maxbundlej > 1);
                                    maxbundlek = map.get(str_arr[k]);
                                    minusk++;


                                } while (maxbundlek > 1);

                                maxbundlel = map.get(str_arr[l]);
                                minusl++;


                            } while (maxbundlel > 1);
                        }
                    }
                }
            }
        }

        return returnMap;
    }


    public static double getNumberOfBundles(double numberMinutes, int numberOfMinutesInABundle, int numberOfSims, Map map, int minus) {
        double numberOfBundles = (numberMinutes - numberMinutes % numberOfMinutesInABundle) / numberOfMinutesInABundle;
        numberOfBundles = (numberOfBundles > 0 ? numberOfBundles - minus : 0);
        numberOfBundles = numberOfBundles >= numberOfSims ? numberOfSims : numberOfBundles;
        double remainingNumberOfMinutes = numberMinutes - numberOfBundles * numberOfMinutesInABundle;
        map.put(numberOfMinutesInABundle, numberOfBundles);
        return remainingNumberOfMinutes;
    }

    public static double getTotalCost(Map<Integer, Double> bestBundleByPrice, double smsnumber, double gigabytesnumber) {
        double totalCost = 0;

        if (bestBundleByPrice != null) {
            for (Integer bundle : Prices.MINUTES_PRICES.keySet()) {
                totalCost += Prices.MINUTES_PRICES.get(bundle) * bestBundleByPrice.get(bundle);
            }
        }
        totalCost += Prices.SMS_COST * smsnumber;
        totalCost += Prices.BUNDLES_TYPE_COST * gigabytesnumber;

        return totalCost;
    }


}