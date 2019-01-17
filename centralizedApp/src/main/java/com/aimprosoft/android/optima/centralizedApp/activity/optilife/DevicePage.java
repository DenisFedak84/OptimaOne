package com.aimprosoft.android.optima.centralizedApp.activity.optilife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.DeviceCostDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.DeviceDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDeviceDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Device;
import com.aimprosoft.android.optima.centralizedApp.db.entity.DeviceCost;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.OfferDevice;
import com.aimprosoft.android.optima.centralizedApp.event.ConfirmationDialog;
import com.aimprosoft.android.optima.centralizedApp.event.DeviceDetailDialog;
import com.aimprosoft.android.optima.centralizedApp.service.AbstractService;
import com.aimprosoft.android.optima.centralizedApp.service.DevicesService;

import java.util.List;

public class DevicePage extends BaseActivity implements CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    private Offer offer = MyApplication.get("offerEntity", Offer.class);
    private CheckBox includiDevicesCheckBox;
    private LinearLayout devicesLayout;
    private Spinner devicesSpinner;
    private LinearLayout devicesTable;
    private LinearLayout modificaDeviceLayout;
    private LinearLayout addDeviceLayout;
    private boolean isDeviceRowSelected = false;

    private Button calcolaEnabled;
    private Button calcolaDisabled;
    private Button ricalcolaEnabled;
    private Button ricalcolaDisabled;

    private OfferDevice offerDevice = new OfferDevice();
    private OfferDeviceDAOImpl offerDeviceDAO;
    private DeviceDAOImpl deviceDAO = new DeviceDAOImpl();
    private DeviceCostDAOImpl deviceCostDAO = new DeviceCostDAOImpl();
    private DeviceDetailDialog deviceDetailDialog;
    private ConfirmationDialog confirmationDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devices_layout);
        initializeViews();
        offerDeviceDAO = new OfferDeviceDAOImpl();
    }

    private void initializeViews() {
        devicesLayout = (LinearLayout) findViewById(R.id.devicesLayout);
        includiDevicesCheckBox = (CheckBox) findViewById(R.id.includiDevicesCheckbox);
        includiDevicesCheckBox.setOnCheckedChangeListener(this);
        devicesTable = (LinearLayout) findViewById(R.id.deviceTable);
        devicesSpinner = (Spinner) findViewById(R.id.devicesSpinner);
        modificaDeviceLayout = (LinearLayout) findViewById(R.id.modificaDeviceLayout);
        addDeviceLayout = (LinearLayout) findViewById(R.id.addDeviceLayout);

        calcolaDisabled = (Button) findViewById(R.id.calcolaDeviceDisabled);
        calcolaEnabled = (Button) findViewById(R.id.calcolaDevice);
        ricalcolaDisabled = (Button) findViewById(R.id.ricalcolaDeviceDisabled);
        ricalcolaEnabled = (Button) findViewById(R.id.ricalcolaDevice);

        findViewById(R.id.addDeviceEnabled).setOnClickListener(v -> saveDevice());

        findViewById(R.id.deviceDetailButton).setOnClickListener(view -> {
            if (checkSpinnerSelection(true, devicesSpinner.getId())) {
                deviceDetailAction();
            } else {
                Toast.makeText(DevicePage.this, getString(R.string.select_device_message), Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.modificaDeviceButton).setOnClickListener(v -> {
            if (validation(true)) {
                saveDevice();
                modificaDeviceLayout.setVisibility(View.GONE);
                addDeviceLayout.setVisibility(View.VISIBLE);
                isDeviceRowSelected = false;
                continuaButtonValidation();
            }
        });

        findViewById(R.id.annullaDeviceButton).setOnClickListener(v -> {
            annulaAction();
            addDeviceLayout.setVisibility(View.VISIBLE);
            drawDevicesTable();
            isDeviceRowSelected = false;
            offerDevice = new OfferDevice();
        });

        calcolaEnabled.setOnClickListener(v -> {
            Intent intent = new Intent(DevicePage.this, Canone.class);
            intent.putExtra("ChoiceCode", 1);
            startActivity(intent);
        });

        ricalcolaEnabled.setOnClickListener(v -> startChildActivity(Canone.class));

        new DevicesService(this, new AbstractService.OnTaskCompleted<DevicesService>() {
            @Override
            public void onTaskCompleted(DevicesService service) {
                setSpinner(devicesSpinner, service.getResult());
                setSavedValues();
            }
        }).execute();

        devicesSpinner.setOnItemSelectedListener(this);
    }

    private void deviceDetailAction() {
        if (deviceDetailDialog == null) {
            deviceDetailDialog = new DeviceDetailDialog(this);
        }
        String deviceDesc = (String) devicesSpinner.getSelectedItem();
        Device device = deviceDAO.getDeviceByDesc(deviceDesc);
        deviceDetailDialog.showDeviceDialog(deviceCostDAO.getDeviceCostByCode(device.getCodeDevice()).getIdDeviceCost());
    }

    private void setSavedValues() {
        drawDevicesTable();
        includiDevicesCheckBox.setChecked(devicesTable.getChildCount() > 1);
    }

    private void annulaAction() {
        cleanDeviceFields();
        modificaDeviceLayout.setVisibility(View.GONE);
    }

    private void cleanDeviceFields() {
        devicesSpinner.setSelection(0);
    }

    private void saveDevice() {
        Device device = deviceDAO.getDeviceByDesc((String) devicesSpinner.getSelectedItem());
        DeviceCost deviceCost = deviceCostDAO.getDeviceCostByCode(device.getCodeDevice());

        offerDevice.setOfferId(offer.getOfferId());
        offerDevice.setDeviceDesc(device.getNameDevice());
        offerDevice.setDeviceCost(deviceCost.getCost());
        offerDevice.setDeviceCostIva(deviceCost.getCostIva());
        offerDevice.setActivationCost(deviceCost.getActivationCost());
        offerDevice.setActivationCostoExtra(deviceCost.getActivationCostoExtra());
        offerDevice.setDeviceType(device.getDeviceType());
        offerDevice.setPriority(deviceCost.getPriority());

        if (offerDevice.getDeviceId() == 0) {
            offerDeviceDAO.insert(offerDevice);
            drawDeviceRow(offerDevice);
            continuaButtonValidation();
        } else {
            offerDeviceDAO.update(offerDevice);
            drawDevicesTable();
        }
        offerDevice = new OfferDevice();
    }

    private void showConfirmationDialog() {
        if (confirmationDialog == null) {
            confirmationDialog = new ConfirmationDialog(this,
                    getString(R.string.device_unmodifiable_dialog_text));
        }
        confirmationDialog.setOnDialogDismissedListener(null);
        confirmationDialog.showDialog();
    }

    private void drawDevicesTable() {
        removeAllTableContent(devicesTable);
        List<OfferDevice> deviceList = offerDeviceDAO.getDevicesByOfferId(offer.getOfferId());
        for (OfferDevice device : deviceList) {
            drawDeviceRow(device);
        }
        validation(false);
        continuaButtonValidation();
    }

    private void drawDeviceRow(final OfferDevice currentDevice) {
        devicesTable.setVisibility(View.VISIBLE);

        LinearLayout row = (LinearLayout) getLayoutInflater().inflate(R.layout.device_table_item, null);

        TextView deviceName = (TextView) row.findViewById(R.id.deviceName);
        TextView tipoDevice = (TextView) row.findViewById(R.id.tipoDevice);
        final TextView deviceIdView = (TextView) row.findViewById(R.id.deviceId);

        final String name = "Device " + (devicesTable.getChildCount());

        tipoDevice.setText(currentDevice.getDeviceDesc());
        deviceName.setText(name);
        currentDevice.setDeviceName(name);
        deviceIdView.setText(String.valueOf(currentDevice.getDeviceId()));
        offerDeviceDAO.update(currentDevice);

        row.setOnClickListener(v -> {
            offerDevice = currentDevice;
            if (isDeviceOfferRecovered()) {
                v.setBackgroundColor(ContextCompat.getColor(this, R.color.item_background_blue));
                isDeviceRowSelected = true;
                addDeviceLayout.setVisibility(View.GONE);
                modificaDeviceLayout.setVisibility(View.VISIBLE);
                devicesTable.setClickable(false);
                continuaButtonValidation();
            } else {
                offerDevice = new OfferDevice();
                showConfirmationDialog();
            }
        });

        row.findViewById(R.id.deleteButtonLayout).
                setOnClickListener(v -> {
                            if (!isDeviceRowSelected) {
                                offerDeviceDAO.deleteOfferDeviceById(Integer.valueOf(deviceIdView.getText().toString().trim()));
                                if (devicesTable.getChildCount() > 1) {
                                    devicesTable.setVisibility(View.GONE);
                                }
                                drawDevicesTable();
                                continuaButtonValidation();
                            }
                        }

                );
        devicesTable.addView(row);
        devicesSpinner.setSelection(0);

        validation(false);
    }

    private boolean isDeviceOfferRecovered() {
        boolean isRecovered = false;
        for (int i = 1; i < devicesSpinner.getCount(); i++) {
            Device currentDevice = deviceDAO.getDeviceByDesc((String) devicesSpinner.getItemAtPosition(i));
            if (currentDevice.getNameDevice().equals(offerDevice.getDeviceDesc())) {
                devicesSpinner.setSelection(i);
                isRecovered = true;
                break;
            }
        }

        return isRecovered;
    }

    private void continuaButtonValidation() {
        boolean isEnabled = devicesTable.getChildCount() > 1 && modificaDeviceLayout.getVisibility() == View.GONE || !includiDevicesCheckBox.isChecked();
        setContinuaButtonEnabled(isEnabled);
    }

    private void setContinuaButtonEnabled(boolean isEnabled) {
        int visibility1 = isEnabled ? View.VISIBLE : View.GONE;
        int visibility2 = !isEnabled ? View.VISIBLE : View.GONE;
        setVisibilityToGroupOfViews(visibility1, calcolaEnabled, ricalcolaEnabled);
        setVisibilityToGroupOfViews(visibility2, calcolaDisabled, ricalcolaDisabled);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        devicesLayout.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        if (isChecked) {
            continuaButtonValidation();
        } else {
            if (offer != null) {
                offerDeviceDAO.deleteOfferDeviceByOfferId(offer.getOfferId());
            }
            cleanTable(devicesTable);
            devicesTable.setVisibility(View.GONE);
            setContinuaButtonEnabled(true);
        }
    }

    private boolean validation(boolean isAnimated) {
        boolean result = checkSpinnerSelection(isAnimated, devicesSpinner.getId());
        if (result) {
            changeViewVisibility(R.id.addDeviceDisabled, R.id.addDeviceEnabled);
        } else {
            changeViewVisibility(R.id.addDeviceEnabled, R.id.addDeviceDisabled);
        }
        return result;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        validation(false);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
