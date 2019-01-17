package com.aimprosoft.android.optima.centralizedApp.util;

import java.util.HashMap;
import java.util.Map;

public class Prices {

    public static final Map<Integer, Integer> MINUTES_PRICES;

    static {
        MINUTES_PRICES = new HashMap<>();
        MINUTES_PRICES.put(100, 5);
        MINUTES_PRICES.put(200, 9);
        MINUTES_PRICES.put(400, 17);
        MINUTES_PRICES.put(800, 32);
    }
    public static final double SMS_COST = 0.04;
    public static final double BUNDLES_TYPE_COST = 4;

}
