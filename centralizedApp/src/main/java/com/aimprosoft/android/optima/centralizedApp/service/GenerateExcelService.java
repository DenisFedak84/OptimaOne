
package com.aimprosoft.android.optima.centralizedApp.service;

import android.os.Environment;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.activity.optilife.BaseActivity;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.BundleDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ClientsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyMeterDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.LineTypesDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WlrOfferDetailsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WlrOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Clients;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDateInterval;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOfferDateInterval;
import com.aimprosoft.android.optima.centralizedApp.db.entity.InternetOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.LineTypes;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.TLCOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Town;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WlrOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WlrOffers;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class GenerateExcelService extends AbstractService<File, String> {

    private BaseActivity mActivity;
    private int currentLine;
    private int currentLine2;
    private Sheet sheet;
    private Sheet secondSheet;
    private String filePath;
    private int currentEnLine = 1;
    private int currentGasLine = 1;

    public GenerateExcelService(BaseActivity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
        this.mActivity = activity;
        currentLine = 0;
        currentLine2 = 0;
    }

    @Override
    protected String doStuff(File... files) {
        filePath = String.valueOf(files[0]);
        String[] headsExcel = mActivity.getResources().getStringArray(R.array.heads_excel);
        String[] headsExcel2 = mActivity.getResources().getStringArray(R.array.heads_excel_2);

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.w(this.getClass().getName(), "Storage not available or read only");
            return null;
        }

        final Workbook workbook = new HSSFWorkbook();

        final CellStyle cs = workbook.createCellStyle();
        cs.setAlignment(CellStyle.ALIGN_RIGHT);
        cs.setWrapText(true);

        sheet = workbook.createSheet("Offerte");
        secondSheet = workbook.createSheet("Wlr linea");

        Row row = sheet.createRow(currentLine);
        for (int index = 0; index < headsExcel.length; index++) {
            String line = headsExcel[index];
            fillCell(row, cs, index, line);
        }

        Row row2 = secondSheet.createRow(currentLine2);
        for (int index = 0; index < headsExcel2.length; index++) {
            String line = headsExcel2[index];
            fillCell(row2, cs, index, line);
        }
        currentLine++;
        currentLine2++;


        new ExcelInfoService() {
            @Override
            protected void onPostExecute(Map map) {

                fillExcelWithData(sheet, cs, map);
                fillExcelWithDataSecondSheet(secondSheet, cs, map);

                if (!filePath.contains(Constants.SAVED_FILE_EXTENSION))
                    filePath += Constants.SAVED_FILE_EXTENSION;
                File file = new File(filePath);
//                if(file.exists()) file.delete();
                FileOutputStream fileOutputStream = null;

                try {
                    fileOutputStream = new FileOutputStream(file);
                    workbook.write(fileOutputStream);
                    Log.w(getClass().getName(), "Writing file" + file);
                } catch (IOException e) {
                    Log.w(getClass().getName(), "Error writing " + file, e);
                } catch (Exception e) {
                    Log.w(e.getClass().getName(), "Failed to save file", e);
                } finally {
                    try {
                        if (fileOutputStream != null)
                            fileOutputStream.close();
                    } catch (Exception ex) {
                        Log.w(ex.getClass().getName(), "Error closing");
                    }
                }
            }
        }.execute();
        return filePath;
    }


    private void fillExcelWithData(Sheet sheet, CellStyle cellStyle, Map data) {
        EnergyMeterDAOImpl energyMeterDAO = new EnergyMeterDAOImpl();
        BundleDAOImpl bundleDAO = new BundleDAOImpl();
        Workbook workbook = sheet.getWorkbook();

        for (Object index : data.keySet()) {
            int i = currentLine;

            List entities = (List) data.get(index);
            Row row = sheet.createRow(currentLine);

            Offer offer = (Offer) entities.get(0);

            //Data offerta
            fillCell(row, cellStyle, 0, offer.getCreationDate());

            //Id Consumer
            fillCell(row, cellStyle, 1, offer.getOptilifeCode());

            //Codice fiscale
            fillCell(row, cellStyle, 2, offer.getNi());

            //ragione sociale
            fillCell(row, cellStyle, 3, offer.getName());

            //piva
            fillCell(row, cellStyle, 4, offer.getPiva());

            //comune
            Town town = (Town) entities.get(1);
            fillCell(row, cellStyle, 5, town.getTownDesc() + " (" + town.getProvinceDesc() + ")");

            //servizi
            fillCell(row, cellStyle, 6, getShortServiceDesc(offer));

            //costo offerta
            fillCell(row, cellStyle, 7, String.valueOf(new DecimalFormat("#####0.00").format(offer.getOfferCost())));

            //Costo Offerta IVA inclusa
            fillCell(row, cellStyle, 8, String.valueOf(new DecimalFormat("#####0.00").format(offer.getOfferCostIVA())));

            //costo una tantum
            fillCell(row, cellStyle, 9, String.valueOf(new DecimalFormat("#####0.00").format(offer.getOfferCostExtra())));

            //offera 1
            fillCell(row, cellStyle, 10, offer.getOfferte1ServiceCode());

            //cost offerta 1
            fillCell(row, cellStyle, 11, String.valueOf(offer.getOfferte1Cost()));

            //offerta 2
            fillCell(row, cellStyle, 12, offer.getOfferte2ServiceCode());

            //costo offerta 2
            fillCell(row, cellStyle, 13, String.valueOf(offer.getOfferte2Cost()));

            //offerta 3
            fillCell(row, cellStyle, 14, offer.getOfferte3ServiceCode());

            //costoOfferta 3
            fillCell(row, cellStyle, 15, String.valueOf(offer.getOfferte3Cost()));

            //servizio
            fillCell(row, cellStyle, 16, "Energia");

            EnergyOffer energyOffer = (EnergyOffer) entities.get(2);

            if (energyOffer != null) {

//                Potenza contatore
//                fillCell(row, cellStyle, 17, energyMeterDAO.getEnergyMeterDescById(energyOffer.getEnergyMeter()));

                //Tensione
//                fillCell(row, cellStyle, 18, energyOffer.getTensione() == 1 ? mActivity.getString(R.string.tensioneBT) : mActivity.getString(R.string.tensioneMT));

//                Taglia kwh
                fillCell(row, cellStyle, 19, String.valueOf(energyOffer.getYearlyConsumption()));

//                Tipologia conatatore
//                fillCell(row, cellStyle, 20, String.valueOf(energyOffer.getTipo_contatore() != 0 ? energyOffer.getTensione() == 1 ? mActivity.getString(R.string.tipoContatoreIntegratore) : mActivity.getString(R.string.tipoContatoreFasciaOraria) : ""));

                //noinspection unchecked
                ArrayList<EnergyOfferDateInterval> datesList = (ArrayList<EnergyOfferDateInterval>) entities.get(3);
                if (datesList != null) {
                    int rowIndex = i;
                    int trueCurrentRow = datesList.size();
                    Row nextRow = row;
                    for (EnergyOfferDateInterval interval : datesList) {
                        if (rowIndex != i)
                            nextRow = sheet.createRow(rowIndex);

                        CellStyle cs = getCellStyleForDate(workbook);

//                        data da
                        fillCell(nextRow, cs, 21, interval.getDateFrom());

//                        data a
                        fillCell(nextRow, cs, 22, interval.getDateTo());

//                        kwh1
                        fillCell(nextRow, cellStyle, 23, String.valueOf(interval.getKwh1() != Constants.NO_VALUE ? interval.getKwh1() : ""));

//                        kwh2
                        fillCell(nextRow, cellStyle, 24, String.valueOf(interval.getKwh2() != Constants.NO_VALUE ? interval.getKwh2() : ""));
//
//                        kwh3
                        fillCell(nextRow, cellStyle, 25, String.valueOf(interval.getKwh3() != Constants.NO_VALUE ? interval.getKwh3() : ""));

                        rowIndex++;
                        if (rowIndex > currentLine) {
                            currentEnLine = trueCurrentRow + i;
                        }
//                        enInterval++;
                    }
                } else {
//                    kwh1
                    fillCell(row, cellStyle, 23, String.valueOf(energyOffer.getYearlyKwh()));
//                    kwh2
//                    fillCell(row, cellStyle, 24, String.valueOf(energyOffer.getYearlyKwh2()));
//                    kwh3
//                    fillCell(row, cellStyle, 25, String.valueOf(energyOffer.getYearlyKwh3()));

                }
                //costo offerta
                fillCell(row, cellStyle, 26, String.valueOf(energyOffer.getOfferCost()));

                //Costo Offerta IVA inclusa
                fillCell(row, cellStyle, 27, String.valueOf(energyOffer.getOfferCostIVA()));
            }

            //servizio
            fillCell(row, cellStyle, 28, "Gas");

            GasOffer gasOffer = (GasOffer) entities.get(4);

            if (gasOffer != null) {

                //Categoria di utilizzo
//                fillCell(row, cellStyle, 29, new CategorieUsoDAOImpl().getCategorieUsoDescById(gasOffer.getCtegoria_duso()));

                //Classe di prelievo
//                fillCell(row, cellStyle, 30, new ClassePrelievoGasDAOImpl().getClasseDiPrelievoDescById(gasOffer.getClasse_de_prelievo()));

//               Taglia
                fillCell(row, cellStyle, 31, String.valueOf(gasOffer.getYearlyConsumption()));

                //noinspection unchecked
                ArrayList<GasOfferDateInterval> gasOfferDateIntervals = (ArrayList<GasOfferDateInterval>) entities.get(5);
                if (gasOfferDateIntervals != null) {
                    int rowIndex1 = i;
                    int trueRow = gasOfferDateIntervals.size();
                    Row nextRow = row;
                    for (GasOfferDateInterval interval : gasOfferDateIntervals) {
                        if (rowIndex1 != i) {
                            if (sheet.getRow(rowIndex1) != null) {
                                nextRow = sheet.getRow(rowIndex1);
                            } else {
                                nextRow = sheet.createRow(rowIndex1);
                            }
                        }
                        CellStyle cs = getCellStyleForDate(workbook);

//                        data da
                        fillCell(nextRow, cs, 32, interval.getDateFrom());

//                        data a
                        fillCell(nextRow, cs, 33, interval.getDateTo());

//                        smc
                        fillCell(nextRow, cellStyle, 34, String.valueOf(interval.getSmc()));

                        rowIndex1++;
                        if (rowIndex1 > currentLine) {
                            currentGasLine = trueRow + i;
                        }
//                        gasInterval++;
                    }
                } else {
                    //smc
//                    fillCell(row, cellStyle, 34, String.valueOf(gasOffer.getYearlySmc()));
                }

                //costo offerta
                fillCell(row, cellStyle, 35, String.valueOf(gasOffer.getOfferCost()));

                //Costo Offerta IVA inclusa
                fillCell(row, cellStyle, 36, String.valueOf(gasOffer.getOfferCostIVA()));
            }


            TLCOffer tlcOffer = (TLCOffer) entities.get(6);

            //servizio
            fillCell(row, cellStyle, 37, offer.getWlrOfferId() == null ? "TLC" : "TLC WLR");

            if (tlcOffer != null | offer.getWlrOfferId() != null) {
                Clients clients = new ClientsDAOImpl().getClientsByClientId(offer.getOldTlcOfferId());
//                Minuti verso fisso
                WlrOffers wlrOffers = new WlrOffersDAOImpl().getWlrOfferById(offer.getWlrOfferId() != null ? offer.getWlrOfferId() : 0);
//                int tlcLocalId = tlcOffer != null ?
//                        tlcOffer.getLocalBundleId() != null ? tlcOffer.getLocalBundleId() : 0 :
//                        wlrOffers.getLocalBundleId() != null ? wlrOffers.getLocalBundleId() : 0;
//                if (bundleDAO.getBundleDescByBundleId(tlcLocalId).equals("Flat")) {
//                    fillCell(row, cellStyle, 38, "illimitati");
//                } else {
//                    fillCell(row, cellStyle, 38, offer.getOldTlcOfferUsed() ? String.valueOf(clients.getLocalMinutes()) : bundleDAO.getBundleDescByBundleId(tlcLocalId));
//                }

//                int mobileId = tlcOffer != null ?
//                        tlcOffer.getMobileBundleId() != null ? tlcOffer.getMobileBundleId() : 0 :
//                        wlrOffers.getMobileBundleId() != null ? wlrOffers.getMobileBundleId() : 0;
//                Minuti verso mobile
//                fillCell(row, cellStyle, 39, offer.getOldTlcOfferUsed() ? String.valueOf(clients.getMobileMinutes()) : bundleDAO.getBundleDescByBundleId(mobileId));

//                Numero di linee
//                fillCell(row, cellStyle, 40, String.valueOf(tlcOffer != null ? tlcOffer.getLinesNumber() : ""));

//                costo offerta
                fillCell(row, cellStyle, 41, String.valueOf(new DecimalFormat("#####0.00").format(tlcOffer != null ? tlcOffer.getBundleCost() : wlrOffers.getBundleCost())));

                // Costo Offerta IVA inclusa
                fillCell(row, cellStyle, 42, String.valueOf(new DecimalFormat("#####0.00").format(tlcOffer != null ? tlcOffer.getBundleCostIVA() : wlrOffers.getBundleCostIVA())));
            }
            InternetOffer internetOffer = (InternetOffer) entities.get(7);

            //servizio
            fillCell(row, cellStyle, 43, "Internet");

            if (internetOffer != null) {
                //adsl
//                fillCell(row, cellStyle, 44, bundleDAO.getBundleDescByBundleId(internetOffer.getBundleId()));
                //costo offerta
                fillCell(row, cellStyle, 45, String.valueOf(internetOffer.getCost()));

                //Costo Offerta IVA inclusa
                fillCell(row, cellStyle, 46, String.valueOf(internetOffer.getCostIVA()));
            }

//            //servizio
//            fillCell(row, cellStyle, 47, "Mobile");
//
//            MobileOffer mobileOffer = (MobileOffer) entities.get(8);
//            if (mobileOffer != null) {
////                Numero Sim
//                fillCell(row, cellStyle, 48, String.valueOf(mobileOffer.getSimNumber()));
//
////                Minuti mese desiderati
//                fillCell(row, cellStyle, 49, String.valueOf(mobileOffer.getMinutesNumber()));
//
////                SMS mese
//                fillCell(row, cellStyle, 50, String.valueOf(mobileOffer.getMontlySmsNumber()));
////                dati mese
//                fillCell(row, cellStyle, 51, String.valueOf(mobileOffer.getMontlyDataNumber()) + " GB");
//
////                costo offerta
//                fillCell(row, cellStyle, 52, String.valueOf(mobileOffer.getOfferCost()));
//
//            }
            if (currentEnLine == currentGasLine) {
                currentLine++;
            } else {
                currentLine = currentEnLine > currentGasLine ? currentEnLine : currentGasLine;
            }
        }
    }


    private void fillExcelWithDataSecondSheet(Sheet secondSheet, CellStyle cs, Map map) {
        currentLine2 = 1;
        Workbook workbook = secondSheet.getWorkbook();
        WlrOffersDAOImpl wlrOffersDAO = new WlrOffersDAOImpl();
        WlrOfferDetailsDAOImpl offerDetailsDAO = new WlrOfferDetailsDAOImpl();
        BundleDAOImpl bundleDAO = new BundleDAOImpl();
        LineTypesDAOImpl lineTypesDAO = new LineTypesDAOImpl();

        for (Object index : map.keySet()) {

            List entities = (List) map.get(index);
            int i = currentLine2;
            Row row = secondSheet.createRow(currentLine2);

            CellStyle cellStyle = getCellStyleForDate(workbook);

            Offer offer = (Offer) entities.get(0);
            if (offer.getWlrOfferId() != null) {
                WlrOffers wlrOffers = wlrOffersDAO.getWlrOfferById(offer.getWlrOfferId());

                //id Offerta
                fillCell(row, cs, 0, String.valueOf(offer.getOfferId()));

                Clients clients = new ClientsDAOImpl().getClientsByClientId(offer.getOldTlcOfferId());

////                Minuti verso fisso
//                fillCell(row, cellStyle, 1, offer.getOldTlcOfferUsed() ? String.valueOf(clients.getLocalMinutes()) :
//                        bundleDAO.getBundleDescByBundleId(wlrOffers.getLocalBundleId() != null ? wlrOffers.getLocalBundleId() : 0));
//
////                Minuti verso mobile
//                fillCell(row, cellStyle, 2, offer.getOldTlcOfferUsed() ? String.valueOf(clients.getMobileMinutes()) :
//                        bundleDAO.getBundleDescByBundleId(wlrOffers.getMobileBundleId() != null ? wlrOffers.getMobileBundleId() : 0));

                List<WlrOfferDetails> wlrOfferDetailses = offerDetailsDAO.getWlrOfferDetailsByWlrOfferId(wlrOffers.getWlrOfferId(), true);
                if (wlrOfferDetailses != null && wlrOfferDetailses.size() != 0) {
                    int rowIndex = i;
                    Row nextRow = row;
                    for (WlrOfferDetails details : wlrOfferDetailses) {
                        if (rowIndex != i)
                            nextRow = secondSheet.createRow(rowIndex);

                        CellStyle cellStyle1 = getCellStyleForDate(workbook);
                        LineTypes lineTypes = lineTypesDAO.getLineTypesById(details.getLineId());

                        // Tipologia cliente
                        fillCell(nextRow, cellStyle1, 3, String.valueOf(lineTypes.getClientType()));

                        //Tipologia linea
                        fillCell(nextRow, cellStyle1, 4, String.valueOf(lineTypes.getLineDesc()));

                        //Linea descrizione
                        fillCell(nextRow, cellStyle1, 5, String.valueOf(lineTypes.getLineDet()));

                        //Numero linee
                        fillCell(nextRow, cellStyle1, 6, String.valueOf(details.getNumLines()));

                        //Numero aggiuntivi
                        fillCell(nextRow, cellStyle1, 7, String.valueOf(details.getServiceAddictionNumber() != null ? details.getServiceAddictionNumber() : ""));

                        //Linea cost
                        fillCell(nextRow, cellStyle1, 8, String.valueOf(lineTypes.getLineCost() * details.getNumLines()));

                        //Linea costIVA
                        fillCell(nextRow, cellStyle1, 9, String.valueOf(lineTypes.getLineCostIVA() * details.getNumLines()));

                        rowIndex++;
                        if (rowIndex > currentLine2) {
                            currentLine2 = rowIndex;
                        }
                    }
                }
            }
        }
    }

    private void fillCell(Row row, CellStyle cellStyle, int index, String value) {
        Cell cell = row.createCell(index);
        cell.setCellStyle(cellStyle);
        row.getSheet().setColumnWidth(index, 9000);
        cell.setCellValue(value);
    }

    private void fillCell(Row row, CellStyle cellStyle, int index, Date value) {
        Cell cell = row.createCell(index);
        cell.setCellStyle(cellStyle);
        row.getSheet().setColumnWidth(index, 9000);
        cell.setCellValue(value);
    }

    private CellStyle getCellStyleForDate(Workbook workbook) {
        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle cs = workbook.createCellStyle();
        cs.setDataFormat(
                createHelper.createDataFormat().getFormat("yyyy-mm-dd"));
        return cs;
    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }

    private String getShortServiceDesc(Offer offer) {
        String energy = offer.getEnergyOfferId() != null ? "E" : "";
        String gas = offer.getGasOfferId() != null ? "G" : "";
        String tlc = offer.getTlcOfferId() != null || offer.getWlrOfferId() != null ? "V" : "";
        String internet = offer.getInternetOfferId() != null ? "A" : "";
        String mobile = offer.getMobileOfferId() != null ? "M" : "";
        return energy + gas + tlc + internet + mobile;
    }
}
