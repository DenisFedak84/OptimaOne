package com.aimprosoft.android.optima.centralizedApp.event;

import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.activity.optilife.BaseActivity;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class TestQuestionarioDialog extends BaseEvent {
    private View view;
    private int offerId;
    private String remoteAlgresult;

    public TestQuestionarioDialog(BaseActivity activity) {
        dialog = new Dialog(activity);
    }

    private void showDialog() {
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.tes_dialog_energy);
//        dialog.findViewById(R.id.localAlgoritm).setVisibility(View.VISIBLE);
//        Map<String, Double> testMap = MyApplication.get("test_map", Map.class) != null ? MyApplication.get("test_map", Map.class) : new HashMap<>();
//        ((TextView) dialog.findViewById(R.id.d1)).setText(String.valueOf(testMap.get("Co") != null ? testMap.get("Co") : 0));
//        ((TextView) dialog.findViewById(R.id.d2)).setText(String.valueOf(testMap.get("Po") != null ? testMap.get("Po") : 0));
//        ((TextView) dialog.findViewById(R.id.d3)).setText(String.valueOf(MyApplication.get("PPM", BigDecimal.class) != null ? MyApplication.get("PPM", BigDecimal.class) : 0));
////        ((TextView) dialog.findViewById(R.id.d4)).setText(String.valueOf(MyApplication.get("PS", Integer.class) != null ? MyApplication.get("PS", Integer.class) : 0));
//        ((TextView) dialog.findViewById(R.id.d5)).setText(String.valueOf(MyApplication.get("PI", BigDecimal.class) != null ? MyApplication.get("PI", BigDecimal.class) : 0));
//        ((TextView) dialog.findViewById(R.id.d6)).setText(String.valueOf(MyApplication.get("PT", BigDecimal.class) != null ? MyApplication.get("PT", BigDecimal.class) : 0));
//        ((TextView) dialog.findViewById(R.id.d7)).setText(String.valueOf(testMap.get("r") != null ? testMap.get("r") : 0));
//        ((TextView) dialog.findViewById(R.id.d8)).setText(String.valueOf(testMap.get("m") != null ? testMap.get("m") : 0));
//        ((TextView) dialog.findViewById(R.id.d9)).setText(String.valueOf(MyApplication.get("cluster", String.class) != null ? MyApplication.get("cluster", String.class) : ""));
//        ((TextView) dialog.findViewById(R.id.d11)).setText(String.valueOf(testMap.get("a") != null ? testMap.get("a") : 0));
//        ((TextView) dialog.findViewById(R.id.d12)).setText(String.valueOf(testMap.get("b") != null ? testMap.get("b") : 0));
//        ((TextView) dialog.findViewById(R.id.d13)).setText(MyApplication.get("monthConsumptionMap", Map.class) != null ? MyApplication.get("monthConsumptionMap", Map.class).toString() : "");
//        ((TextView) dialog.findViewById(R.id.d14)).setText(String.valueOf(MyApplication.get("PrezzoMancatoConsumo", Double.class) != null ? MyApplication.get("PrezzoMancatoConsumo", Double.class) : ""));
//        ((TextView) dialog.findViewById(R.id.d15)).setText(String.valueOf(MyApplication.get("PrezzoSforamento", Double.class) != null ? MyApplication.get("PrezzoSforamento", Double.class) : ""));
//        ((TextView) dialog.findViewById(R.id.d16)).setText(String.valueOf(MyApplication.get("Codice", String.class) != null ? MyApplication.get("Codice", String.class) : ""));
////            ((TextView) dialog.findViewById(R.id.d17)).setText(String.valueOf(energyAlgoritm.getScaldabagnoElettrico()));
////            ((TextView) dialog.findViewById(R.id.d18)).setText(String.valueOf(energyAlgoritm.getAltraApparecchiatura()));
////            ((TextView) dialog.findViewById(R.id.d19)).setText(String.valueOf(energyAlgoritm.getLavatriceConsumption() +
////                    energyAlgoritm.getLavastoviglieConsumption() +
////                    energyAlgoritm.getFornoConsumption() +
////                    energyAlgoritm.getAsciugatrice() +
////                    energyAlgoritm.getTelevisore() +
////                    energyAlgoritm.getComputer() +
////                    energyAlgoritm.getIlluminazione() +
////                    energyAlgoritm.getModem() +
////                    energyAlgoritm.getVario() +
////                    energyAlgoritm.getConsumoBase() +
////                    energyAlgoritm.getCondizionatoreRaffreddamento() +
////                    energyAlgoritm.getCondizionatoreRiscaldamento() +
////                    energyAlgoritm.getFrigorifero() +
////                    energyAlgoritm.getScaldabagnoElettrico() +
////                    energyAlgoritm.getAltraApparecchiatura() + " "));
//
//        dialog.findViewById(R.id.remoteAlgoritm).setVisibility(View.VISIBLE);
//        ((TextView) dialog.findViewById(R.id.remoteAlgResult)).setText(remoteAlgresult);
//
//        dialog.findViewById(R.id.bbu).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (dialog != null) {
//                    dialog.dismiss();
//                }
////                energeticiQuestionario.startNextPage(clas);
//            }
//        });
//        dialog.show();
    }

    public String EditTextGetTextString(int resourseId) {
        EditText editText = (EditText) dialog.findViewById(resourseId);
        return editText.getText().toString().trim();
    }

    public boolean isValid(int id) {
        boolean result = true;
        if (EditTextGetTextString(id) == null || EditTextGetTextString(id).trim().length() == 0) {
            dialog.findViewById(id).requestFocus();
            Animation shake = AnimationUtils.loadAnimation(activity, R.anim.shake);
            dialog.findViewById(id).startAnimation(shake);
            result = false;
        }

        return result;
    }

    public void show() {
        showDialog();
    }
}
