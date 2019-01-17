package com.aimprosoft.android.optima.centralizedApp.util.autotest.parser.impl;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.DeviceCostDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.DeviceDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.DeviceResultDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDeviceDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Device;
import com.aimprosoft.android.optima.centralizedApp.db.entity.DeviceCost;
import com.aimprosoft.android.optima.centralizedApp.db.entity.DeviceResult;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.OfferDevice;
import com.aimprosoft.android.optima.centralizedApp.db.entity.OfferResult;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.listener.OnParsingErrorListener;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.parser.AbstractTestParser;

import org.apache.poi.ss.usermodel.Row;

import java.util.List;

public class DeviceTestParser extends AbstractTestParser {

    private OfferDeviceDAOImpl offerDeviceDAO = new OfferDeviceDAOImpl();
    private DeviceCostDAOImpl deviceCostDAO = new DeviceCostDAOImpl();
    private DeviceDAOImpl deviceDAO = new DeviceDAOImpl();
    private DeviceResultDAOImpl deviceResultDAO = new DeviceResultDAOImpl();
    private OnParsingErrorListener onParsingErrorListener;

    public DeviceTestParser() {
    }

    @Override
    public void parse(List<Row> rowList, Offer offer, OfferResult offerResult) {
        defineTestMode(offer);
        String deviceNumber = null;
        try {
            for (Row row : rowList) {
                deviceNumber = row.getCell(getCellIndexByTestMode(
                        R.integer.id_device_business, R.integer.id_device_consumer))
                        .getStringCellValue();
                initializeOfferResult(offerResult, row);
                createDeviceOfferDetails(row, deviceNumber, offer, offerResult);
            }
        } catch (Exception e) {
            if (onParsingErrorListener != null) {
                onParsingErrorListener.onParsingError(MyApplication.getContext()
                        .getString(R.string.device_parsing_error_message, deviceNumber, e.getMessage()));
            }
        }
    }

    private void createDeviceOfferDetails(Row row, String deviceNumber, Offer offer, OfferResult offerResult) {
        if (deviceNumber.isEmpty()) {
            listener.onParsingComplete(deviceNumber);
            return;
        }

        OfferDevice offerDevice = new OfferDevice();

        String deviceDesc = row.getCell(getCellIndexByTestMode(R.integer.tipo_device_business,
                R.integer.tipo_device_consumer)).getStringCellValue();
        Device device = deviceDAO.getDeviceByDesc(deviceDesc.trim());
        if (device != null) {
            DeviceCost deviceCost = deviceCostDAO.getDeviceCostByCode(device.getCodeDevice());

            offerDevice.setOfferId(offer.getOfferId());
            offerDevice.setDeviceDesc(device.getNameDevice());
            offerDevice.setDeviceCost(deviceCost.getCost());
            offerDevice.setDeviceCostIva(deviceCost.getCostIva());
            offerDevice.setActivationCost(deviceCost.getActivationCost());
            offerDevice.setActivationCostoExtra(deviceCost.getActivationCostoExtra());
            offerDevice.setDeviceType(device.getDeviceType());
            offerDevice.setPriority(deviceCost.getPriority());

            createDeviceResult(row, offerResult, deviceNumber);
            offerDeviceDAO.insert(offerDevice);
        }
        listener.onParsingComplete(deviceNumber);
    }

    private void createDeviceResult(Row row, OfferResult offerResult, String deviceNumber) {
        DeviceResult deviceResult = new DeviceResult();

        deviceResult.setCanoneDevice(getDoubleFromCell(row.getCell(getCellIndexByTestMode(R.integer.canone_device_business,
                R.integer.canone_device_consumer))));
        deviceResult.setCanoneDeviceConIVA(getDoubleFromCell(row.getCell(getCellIndexByTestMode(R.integer.canone_device_con_iva_business,
                R.integer.canone_device_con_iva_consumer))));
        deviceResult.setDeviceNumber(deviceNumber);
        deviceResult.setTipoDevice(row.getCell(getCellIndexByTestMode(R.integer.tipo_device_business,
                R.integer.tipo_device_consumer)).getStringCellValue().trim());
        deviceResult.setDeviceNumber(deviceNumber);
        deviceResult.setResultOfferId(offerResult.getResultOfferId());
        deviceResultDAO.insert(deviceResult);
    }

    public OnParsingErrorListener getOnParsingErrorListener() {
        return onParsingErrorListener;
    }

    public void setOnParsingErrorListener(OnParsingErrorListener onParsingErrorListener) {
        this.onParsingErrorListener = onParsingErrorListener;
    }
}
