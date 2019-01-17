package com.aimprosoft.android.optima.centralizedApp.event;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.AutoTestExecutor;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.model.ADSLTestDetails;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.model.AutotestResultModel;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.model.DeviceTestDetails;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.model.EnergyTestDetails;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.model.GasTestDetails;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.model.MainOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.model.VoceTestDetails;

public class TestDetailsDialog extends BaseEvent {

    private static final float DIALOG_TAKEN_AREA_PERCENT_Y = 0.8f;
    private static final float DIALOG_TAKEN_AREA_PERCENT_X = 0.80f;
    private static final String ENERGY_DIALOG = "E";
    private static final String GAS_DIALOG = "G";
    private static final String VOCE_DIALOG = "V";
    private static final String ADSL_DIALOG = "A";
    private static final String DEVICE_DIALOG = "D";
    private static final String TOTAL_DIALOG = "T";

    private Button okButton;
    private LinearLayout resultContainer;
    private TextView title;

    public TestDetailsDialog(Activity activity) {
        setUpDialog(activity);
        calculateDimensions();
    }

    private void initializeDialogByType(String dialogType, AutotestResultModel autotestResultModel) {
        resultContainer = (LinearLayout) dialog.findViewById(R.id.resultContainer);
        resultContainer.removeAllViews();
        int titleResourceId = -1;
        switch (dialogType) {
            case ENERGY_DIALOG:
                setUpEnergyDialog(autotestResultModel);
                titleResourceId = R.string.energy_details_title;
                break;
            case GAS_DIALOG:
                setUpGasDialog(autotestResultModel);
                titleResourceId = R.string.gas_details_title;
                break;
            case VOCE_DIALOG:
                setUpVoceDialog(autotestResultModel);
                titleResourceId = R.string.voce_details_title;
                break;
            case ADSL_DIALOG:
                setUpADSLDialog(autotestResultModel);
                titleResourceId = R.string.adsl_details_title;
                break;
            case DEVICE_DIALOG:
                setUpDeviceDialog(autotestResultModel);
                titleResourceId = R.string.device_details_title;
                break;
            case TOTAL_DIALOG:
                setUpTotalDialog(autotestResultModel);
                titleResourceId = R.string.total_details_title;
                break;
        }

        title.setText(dialog.getContext().getString(titleResourceId));
    }

    private void setUpTotalDialog(AutotestResultModel autotestResultModel) {
        LinearLayout headers = (LinearLayout) dialog.getLayoutInflater().inflate(R.layout.total_result_header, null);
        resultContainer.addView(headers);
        MainOfferDetails mainOfferDetails = autotestResultModel.getMainOfferDetails();
        LinearLayout row = (LinearLayout) dialog.getLayoutInflater().inflate(R.layout.total_result_row, null);

        TextView canone = (TextView) row.findViewById(R.id.canoneMensile);
        TextView canoneIva = (TextView) row.findViewById(R.id.canoneMensileIva);
        TextView contoRelax = (TextView) row.findViewById(R.id.contoRelax);
        TextView costoAttivazione = (TextView) row.findViewById(R.id.activationCost);

        canone.setText(mainOfferDetails.getCanoneMensile());
        canone.setBackgroundResource(AutoTestExecutor.getDetailCellResource(mainOfferDetails.isCanoneMensileStatus()));
        canoneIva.setText(mainOfferDetails.getCanoneMensileConIVA());
        canoneIva.setBackgroundResource(AutoTestExecutor.getDetailCellResource(mainOfferDetails.isCanoneMensileConIVAStatus()));
        contoRelax.setText(mainOfferDetails.getContoRelax());
        contoRelax.setBackgroundResource(AutoTestExecutor.getDetailCellResource(mainOfferDetails.isContoRelaxStatus()));
        costoAttivazione.setText(mainOfferDetails.getCostoAttivazione());
        costoAttivazione.setBackgroundResource(AutoTestExecutor.getDetailCellResource(mainOfferDetails.isCostoAttivazioneStatus()));
        resultContainer.addView(row);
    }

    private void setUpDeviceDialog(AutotestResultModel autotestResultModel) {
        LinearLayout headers = (LinearLayout) dialog.getLayoutInflater().inflate(R.layout.device_result_header, null);
        resultContainer.addView(headers);

        for (DeviceTestDetails deviceDetails : autotestResultModel.getDeviceTestDetailsList()) {
            LinearLayout row = (LinearLayout) dialog.getLayoutInflater().inflate(R.layout.device_result_row, null);

            TextView deviceNumber = (TextView) row.findViewById(R.id.testNumber);
            TextView canoneDevice = (TextView) row.findViewById(R.id.canoneDevice);
            TextView canoneDeviceIVA = (TextView) row.findViewById(R.id.canoneDeviceIVA);
            TextView tipoDevice = (TextView) row.findViewById(R.id.tipoDevice);

            deviceNumber.setText(deviceDetails.getDeviceNumber());

            canoneDevice.setText(deviceDetails.getCanoneDevice());
            canoneDevice.setBackgroundResource(AutoTestExecutor.getDetailCellResource(deviceDetails.isCanoneDeviceStatus()));
            canoneDeviceIVA.setText(deviceDetails.getCanoneDeviceConIVA());
            canoneDeviceIVA.setBackgroundResource(AutoTestExecutor.getDetailCellResource(deviceDetails.isCanoneDeviceConIVAStatus()));
            tipoDevice.setText(deviceDetails.getTipoDevice());
            tipoDevice.setBackgroundResource(AutoTestExecutor.getDetailCellResource(deviceDetails.isTipoDeviceStatus()));
            resultContainer.addView(row);
        }
    }

    private void setUpADSLDialog(AutotestResultModel autotestResultModel) {
        LinearLayout headers = (LinearLayout) dialog.getLayoutInflater().inflate(R.layout.adsl_result_header, null);
        resultContainer.addView(headers);

        for (ADSLTestDetails adslDetails : autotestResultModel.getAdslTestDetailsList()) {
            LinearLayout row = (LinearLayout) dialog.getLayoutInflater().inflate(R.layout.adsl_result_row, null);

            TextView idAdsl = (TextView) row.findViewById(R.id.testNumber);
            TextView prezzoAdsl = (TextView) row.findViewById(R.id.prezzoADSL);
            TextView prezzoAdslConIva = (TextView) row.findViewById(R.id.prezzoAdslConIVA);
            TextView percAdsl = (TextView) row.findViewById(R.id.percAdsl);

            idAdsl.setText(adslDetails.getAdslNumber());

            prezzoAdsl.setText(adslDetails.getPrezzoAdsl());
            prezzoAdsl.setBackgroundResource(AutoTestExecutor.getDetailCellResource(adslDetails.isPrezzoAdslStatus()));
            prezzoAdslConIva.setText(adslDetails.getPrezzoAdslConIva());
            prezzoAdslConIva.setBackgroundResource(AutoTestExecutor.getDetailCellResource(adslDetails.isPrezzoAdslConIvaStatus()));
            percAdsl.setText(adslDetails.getAdslPerc());
            percAdsl.setBackgroundResource(AutoTestExecutor.getDetailCellResource(adslDetails.isAdslPercStatus()));
            resultContainer.addView(row);
        }
    }

    private void setUpVoceDialog(AutotestResultModel autotestResultModel) {
        LinearLayout headers = (LinearLayout) dialog.getLayoutInflater().inflate(R.layout.voce_result_header, null);
        resultContainer.addView(headers);

        for (VoceTestDetails voceDetails : autotestResultModel.getVoceTestDetailsList()) {
            LinearLayout row = (LinearLayout) dialog.getLayoutInflater().inflate(R.layout.voce_result_row, null);

            TextView idVoce = (TextView) row.findViewById(R.id.testNumber);
            TextView prezzoVoce = (TextView) row.findViewById(R.id.prezzoVoce);
            TextView prezzoVoceConIva = (TextView) row.findViewById(R.id.prezzoVoceConIVA);
            TextView percVoce = (TextView) row.findViewById(R.id.percVoce);

            idVoce.setText(voceDetails.getVoceNumber());

            prezzoVoce.setText(voceDetails.getPrezzoVoce());
            prezzoVoce.setBackgroundResource(AutoTestExecutor.getDetailCellResource(voceDetails.isPrezzoVoceStatus()));
            prezzoVoceConIva.setText(voceDetails.getPrezzoVoceConIva());
            prezzoVoceConIva.setBackgroundResource(AutoTestExecutor.getDetailCellResource(voceDetails.isPrezzoVoceConIvaStatus()));
            percVoce.setText(voceDetails.getVocePerc());
            percVoce.setBackgroundResource(AutoTestExecutor.getDetailCellResource(voceDetails.isVocePercStatus()));
            resultContainer.addView(row);
        }
    }

    private void setUpGasDialog(AutotestResultModel autotestResultModel) {
        LinearLayout headers = (LinearLayout) dialog.getLayoutInflater().inflate(R.layout.gas_result_header, null);
        resultContainer.addView(headers);

        for (GasTestDetails gasDetails : autotestResultModel.getGasTestDetailsList()) {
            LinearLayout row = (LinearLayout) dialog.getLayoutInflater().inflate(R.layout.gas_result_row, null);

            TextView idGas = (TextView) row.findViewById(R.id.testNumber);
            TextView tagliaMeseGas = (TextView) row.findViewById(R.id.tagliaMeseGas);
            TextView prezzoGas = (TextView) row.findViewById(R.id.prezzoGas);
            TextView prezzoGasConIva = (TextView) row.findViewById(R.id.prezzoGasConIva);
            TextView sforamento = (TextView) row.findViewById(R.id.sforamento);
            TextView mancatoConsumo = (TextView) row.findViewById(R.id.mancatoConsumo);
            TextView codiceTariffa = (TextView) row.findViewById(R.id.codiceTariffa);
            TextView percGas = (TextView) row.findViewById(R.id.gasPerc);

            idGas.setText(gasDetails.getPdrNumber());

            tagliaMeseGas.setText(gasDetails.getTagliaMeseGas());
            tagliaMeseGas.setBackgroundResource(AutoTestExecutor.getDetailCellResource(gasDetails.isTagliaMeseGasStatus()));
            prezzoGas.setText(gasDetails.getPrezzoGas());
            prezzoGas.setBackgroundResource(AutoTestExecutor.getDetailCellResource(gasDetails.isPrezzoGasStatus()));
            prezzoGasConIva.setText(gasDetails.getPrezzoGasConIva());
            prezzoGasConIva.setBackgroundResource(AutoTestExecutor.getDetailCellResource(gasDetails.isPrezzoGasConIvaStatus()));
            sforamento.setText(gasDetails.getSforamento());
            sforamento.setBackgroundResource(AutoTestExecutor.getDetailCellResource(gasDetails.isSforamentoStatus()));
            mancatoConsumo.setText(gasDetails.getMancatoConsumo());
            mancatoConsumo.setBackgroundResource(AutoTestExecutor.getDetailCellResource(gasDetails.isMancatoConsumoStatus()));
            codiceTariffa.setText(gasDetails.getCodiceTariffa());
            codiceTariffa.setBackgroundResource(AutoTestExecutor.getDetailCellResource(gasDetails.isCodiceTariffaStatus()));
            percGas.setText(gasDetails.getGasPerc());
            percGas.setBackgroundResource(AutoTestExecutor.getDetailCellResource(gasDetails.isGasPercStatus()));
            resultContainer.addView(row);
        }
    }

    private void setUpEnergyDialog(AutotestResultModel autotestResultModel) {
        LinearLayout headers = (LinearLayout) dialog.getLayoutInflater().inflate(R.layout.energy_result_header, null);
        resultContainer.addView(headers);

        for (EnergyTestDetails energyDetails : autotestResultModel.getEnergyTestDetailsList()) {
            LinearLayout row = (LinearLayout) dialog.getLayoutInflater().inflate(R.layout.energy_result_row, null);

            TextView idEE = (TextView) row.findViewById(R.id.testNumber);
            TextView potenza = (TextView) row.findViewById(R.id.potenza);
            TextView tagliaMeseEE = (TextView) row.findViewById(R.id.tagliaMeseEE);
            TextView prezzoEE = (TextView) row.findViewById(R.id.prezzoEE);
            TextView prezzoEEConIva = (TextView) row.findViewById(R.id.prezzoEEConIva);
            TextView sforamento = (TextView) row.findViewById(R.id.sforamento);
            TextView mancatoConsumo = (TextView) row.findViewById(R.id.mancatoConsumo);
            TextView codiceTariffa = (TextView) row.findViewById(R.id.codiceTariffa);
            TextView percEE = (TextView) row.findViewById(R.id.percEE);

            idEE.setText(energyDetails.getPodNumber());

            potenza.setText(energyDetails.getPotenza());
            potenza.setBackgroundResource(AutoTestExecutor.getDetailCellResource(energyDetails.isPotenzaStatus()));
            tagliaMeseEE.setText(energyDetails.getTagliaMeseEE());
            tagliaMeseEE.setBackgroundResource(AutoTestExecutor.getDetailCellResource(energyDetails.isTagliaMeseEEStatus()));
            prezzoEE.setText(energyDetails.getPrezzoEE());
            prezzoEE.setBackgroundResource(AutoTestExecutor.getDetailCellResource(energyDetails.isPrezzoEEStatus()));
            prezzoEEConIva.setText(energyDetails.getPrezzoEEConIva());
            prezzoEEConIva.setBackgroundResource(AutoTestExecutor.getDetailCellResource(energyDetails.isPrezzoEEConIvaStatus()));
            sforamento.setText(energyDetails.getSforamento());
            sforamento.setBackgroundResource(AutoTestExecutor.getDetailCellResource(energyDetails.isSforamentoStatus()));
            mancatoConsumo.setText(energyDetails.getMancatoConsumo());
            mancatoConsumo.setBackgroundResource(AutoTestExecutor.getDetailCellResource(energyDetails.isMancatoConsumoStatus()));
            codiceTariffa.setText(energyDetails.getCodiceTariffa());
            codiceTariffa.setBackgroundResource(AutoTestExecutor.getDetailCellResource(energyDetails.isCodiceTariffaStatus()));
            percEE.setText(energyDetails.getEnergyPerc());
            percEE.setBackgroundResource(AutoTestExecutor.getDetailCellResource(energyDetails.isEnergyPercStatus()));
            resultContainer.addView(row);
        }
    }

    private void setUpDialog(Activity activity) {
        dialog = new Dialog(activity, R.style.AppDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);
        dialog.setContentView(R.layout.test_result_dialog);

        title = (TextView) dialog.findViewById(R.id.title);

        okButton = (Button) dialog.findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && isActivityAlive()) {
                    okAction();
                }
            }
        });
    }

    private void okAction() {
        dismissDialog();
    }

    public void dismissDialog() {
        if (dialog != null && isActivityAlive()) {
            dialog.dismiss();
        }
    }

    public void showDialog(String dialogType, AutotestResultModel autotestResultModel) {
        initializeDialogByType(dialogType, autotestResultModel);
        dialog.show();
    }

    private void calculateDimensions() {
        WindowManager wm = (WindowManager) dialog.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = (int) (size.y * DIALOG_TAKEN_AREA_PERCENT_Y);
        int width = (int) (size.x * DIALOG_TAKEN_AREA_PERCENT_X);
        dialog.getWindow().setLayout(width, height);
    }
}
