package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;

import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;

import java.util.ArrayList;
import java.util.List;

public class GetOffersByPivaService extends AbstractService<String, List<Offer>> {

    public GetOffersByPivaService(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
    }

    @Override
    protected List<Offer> doStuff(String... strings) {
//        ///////// test
//        StringBuilder stringBuilder = new StringBuilder();
//        try {
//            stringBuilder.append("INTEGRATION TEST: Piva/CF from Wvoce desc - " + strings[0] + "\n");
//            stringBuilder.append("INTEGRATION TEST: Piva/CF from Wvoce length - " + strings[0].length() + "\n");
//            stringBuilder.append("INTEGRATION TEST: Offer list" + "\n");
//            Log.v(this.getClass().getSimpleName(), "INTEGRATION TEST: Piva/CF desc - " + strings[0]);
//            Log.v(this.getClass().getSimpleName(), "INTEGRATION TEST: Piva/CF length - " + strings[0].length());
//
//            List<Offer> offers = new OfferDAOImpl().getAllRows();
//            Log.v(this.getClass().getSimpleName(), "INTEGRATION TEST: Offer list" + "\n");
//            for (Offer offer : offers) {
//                if (offer.getConfiguratorType() == Constants.CONSUMER_CONFIGURATOR) {
//                    Log.v(this.getClass().getSimpleName(), "INTEGRATION TEST: Offer's CF - " + offer.getCodiceFiscale() + " ");
//                    stringBuilder.append("INTEGRATION TEST: Offer's CF - " + offer.getCodiceFiscale() + "\n");
//                } else {
//                    Log.v(this.getClass().getSimpleName(), "INTEGRATION TEST: Offer's PIVA - " + offer.getPiva());
//                    stringBuilder.append("INTEGRATION TEST: Offer's PIVA - " + offer.getPiva() + "\n");
//                }
//            }
//
//        } catch (Exception e) {
//            Log.v(this.getClass().getSimpleName(), "INTEGRATION TEST: error - " + e + ", error message - " + e.getMessage());
//            stringBuilder.append("INTEGRATION TEST: error - " + e + ", error message - " + e.getMessage() + "\n");
//        }
//        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + SDCardHelper.FOLDER_NAME + File.separator + "integration_test.txt");
//        try {
//            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
//            bw.write(stringBuilder.toString());
//            bw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        ////////////// test
        if (strings[0].length() == 11) {
            return new OfferDAOImpl().getOffersByPiva(strings[0]);
        } else if (strings[0].length() == 16) {
            return new OfferDAOImpl().getOffersByCodiceFiscale(strings[0]);
        }
        return new ArrayList<>();
    }
}
