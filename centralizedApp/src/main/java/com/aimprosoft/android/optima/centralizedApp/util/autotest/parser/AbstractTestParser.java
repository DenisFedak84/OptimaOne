package com.aimprosoft.android.optima.centralizedApp.util.autotest.parser;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferResultDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.OfferResult;
import com.aimprosoft.android.optima.centralizedApp.event.DoubleButtonConfirmationDialog;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.listener.OnParsingCompleteListener;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.listener.OnParsingErrorListener;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public abstract class AbstractTestParser {
    public Resources resources = MyApplication.getContext().getResources();
    protected boolean isBusinessMode = false;
    protected OnParsingCompleteListener listener;
    public static String SPECIAL_SPACE = "\u00a0";
    public static DecimalFormat decimalFormat;

    static {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormat = new DecimalFormat("0.0", decimalFormatSymbols);
    }

    public AbstractTestParser() {
    }

    public abstract void parse(List<Row> rowList, Offer offer, OfferResult offerResult);

    public abstract void setOnParsingErrorListener(OnParsingErrorListener onParsingErrorListener);

    protected void defineTestMode(Offer offer) {
        isBusinessMode = offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR;
    }

    public void initializeOfferResult(OfferResult offerResult, Row row) {
        if (offerResult.getResultOfferId() == 0) {
            offerResult.setCanoneMensile(
                    row.getCell(getCellIndexByTestMode(R.integer.canone_mensile_business,
                            R.integer.canone_mensile_consumer)).getNumericCellValue());
            offerResult.setCanoneMensileConIva(
                    row.getCell(getCellIndexByTestMode(R.integer.canone_mensile_con_iva_business,
                            R.integer.canone_mensile_con_iva_consumer)).getNumericCellValue());
            offerResult.setCanoneMensileIvaEsclusa(
                    row.getCell(getCellIndexByTestMode(R.integer.canone_iva_esclusa_business,
                            R.integer.canone_iva_esclusa_consumer)).getNumericCellValue());
            offerResult.setContoRelax(
                    row.getCell(getCellIndexByTestMode(R.integer.conto_relax_business,
                            R.integer.conto_relax_consumer)).getNumericCellValue());
            offerResult.setCostoAttivazione(
                    row.getCell(getCellIndexByTestMode(R.integer.costo_attivazione_business,
                            R.integer.costo_attivazione_consumer)).getNumericCellValue());
            offerResult.setCanoneDevice(
                    row.getCell(getCellIndexByTestMode(R.integer.canone_mensile_device_business,
                            R.integer.canone_mensile_device_consumer)).getNumericCellValue());
            new OfferResultDAOImpl().insert(offerResult);
        }
    }

    protected int getCellIndexByTestMode(int businessCellIndexId, int consumerCellIndexId) {
        return resources.getInteger(isBusinessMode ? businessCellIndexId : consumerCellIndexId);
    }

    public OnParsingCompleteListener getListener() {
        return listener;
    }

    public void setListener(OnParsingCompleteListener listener) {
        this.listener = listener;
    }

    public String getSystem() {
        return isBusinessMode ? Constants.BUSINESS_CONFIGURATOR_FLAG : Constants.CONSUMER_CONFIGURATOR_FLAG;
    }

    public int getIntFromCell(Cell cell) {
        int value;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                value = (int) cell.getNumericCellValue();
                break;
            case Cell.CELL_TYPE_STRING:
                value = getIntFromString(cell.getStringCellValue());
                break;
            default:
                value = -1;
                break;
        }
        return value;
    }

    private int getIntFromString(String value) {
        int result = 0;
        try {
            result = Integer.valueOf(value);
        } catch (NumberFormatException e) {
            Log.e(this.getClass().getSimpleName(), "error while parse value from cell", e);
        }
        return result;
    }

    private double getDoubleFromString(String value) {
        double result = 0;
        try {
            result = Double.valueOf(value.replaceAll(",", "."));
        } catch (NumberFormatException e) {
            Log.e(this.getClass().getSimpleName(), "error while parse value from cell", e);
        }
        return result;
    }

    public double getDoubleFromCell(Cell cell) {
        double value;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                value = cell.getNumericCellValue();
                break;
            case Cell.CELL_TYPE_STRING:
                value = getDoubleFromString(cell.getStringCellValue());
                break;
            default:
                value = 0;
                break;
        }
        return value;
    }
}
