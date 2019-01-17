package com.aimprosoft.android.optima.centralizedApp.util;

public class URLs {
    public static final String USER_AGENT = "com.optima.ConfiguratoreUnico";

    //POST
    public static final String LOGIN_URL_TEST = "http://wvocetest.optimaitalia.com:8999/Appuntamenti.svc/Login"; // test
//    public static final String LOGIN_URL = "http://api-wvoce.optimaitalia.com:8099/Appuntamenti.svc/Login"; //old
    public static final String LOGIN_URL = "http://wvoces.optimaitalia.com:8033/Appuntamenti.svc/Login"; //new

    //    public static final String UPDATE_URL = "http://titano.optimaitalia.com/apps/test/";  //test
    public static final String UPDATE_URL = "http://titano.optimaitalia.com/apps/";

    public static final String B2B_YEARLY_CONSUMPTION_SERVICE_PROD_URL = "http://startserviceapp.optimaitalia.com:9888/ServiceApp.svc/GetQueryGroup/GetConsumoUtente_V5/";
    public static final String CONSUMER_YEARLY_CONSUMPTION_SERVICE_PROD_URL = "http://startserviceapp.optimaitalia.com:9888/ServiceApp.svc/GetQueryGroup/GetConsumo/";

    public static final String SEND_SDC_JSON_TO_WEB_SERVER_URL = "https://dealerstation.optimaitalia.com/dealer/api/getSDCJsonService";  //production
    public static final String SEND_SDC_TO_WEB_SERVER_URL_TEST = "http://dealerstation.optimaitalia.com/dealer-pre/api/getSDCJsonService"; // pre prod

    //  public static final String SSD_JSON_TO_WEB_SERVER_URL = "http://192.168.10.26:8080/api/getSDCJsonService"; // local test url

}
