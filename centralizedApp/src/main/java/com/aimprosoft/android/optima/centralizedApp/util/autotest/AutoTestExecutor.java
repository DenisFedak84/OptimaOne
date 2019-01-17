package com.aimprosoft.android.optima.centralizedApp.util.autotest;

import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.activity.optilife.BaseActivity;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.DatabaseHelper;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ADSLResultDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.DeviceResultDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyResultDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasResultDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDeviceDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferResultDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.VoceResultDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.ADSLResult;
import com.aimprosoft.android.optima.centralizedApp.db.entity.DeviceResult;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyResult;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasResult;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.OfferDevice;
import com.aimprosoft.android.optima.centralizedApp.db.entity.OfferResult;
import com.aimprosoft.android.optima.centralizedApp.db.entity.VoceResult;
import com.aimprosoft.android.optima.centralizedApp.event.HorizontalProgressDialog;
import com.aimprosoft.android.optima.centralizedApp.service.AbstractService;
import com.aimprosoft.android.optima.centralizedApp.service.DeleteAllOfferInfo;
import com.aimprosoft.android.optima.centralizedApp.service.TotalCostJarService;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.listener.OnCancelClickListener;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.listener.OnParsingCompleteListener;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.listener.OnParsingErrorListener;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.listener.OnTestFinishListener;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.model.ADSLTestDetails;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.model.AbstractTestModel;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.model.AutotestResultModel;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.model.DeviceTestDetails;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.model.EnergyTestDetails;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.model.GasTestDetails;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.model.MainOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.model.OfferTestModel;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.model.VoceTestDetails;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.parser.AbstractTestParser;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.parser.impl.ADSLTestParser;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.parser.impl.DeviceTestParser;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.parser.impl.EnergyTestParser;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.parser.impl.GasTestParser;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.parser.impl.VoceTestParser;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.CentralizedCostUtil;
import com.optima.pricing.domain.Pricing;
import com.optima.pricing.domain.ResponsePrincing;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AutoTestExecutor extends AbstractService<Void, Void>
        implements OnParsingCompleteListener, OnCancelClickListener, OnParsingErrorListener {
    private static final String ENERGY = "E";
    private static final String GAS = "G";
    private static final String ADSL = "A";
    private static final String VOCE = "V";
    private static final String DEVICE = "D";
    private final int BUSINESS_HEADER_COUNT = 108;
    private final int CONSUMER_HEADER_COUNT = 106;

    private OfferDAOImpl offerDAO = new OfferDAOImpl();
    private OfferResultDAOImpl offerResultDAO = new OfferResultDAOImpl();
    private EnergyResultDAOImpl energyResultDAO = new EnergyResultDAOImpl();
    private GasResultDAOImpl gasResultDAO = new GasResultDAOImpl();
    private ADSLResultDAOImpl adslResultDAO = new ADSLResultDAOImpl();
    private VoceResultDAOImpl voceResultDAO = new VoceResultDAOImpl();
    private DeviceResultDAOImpl deviceResultDAO = new DeviceResultDAOImpl();
    private OfferDeviceDAOImpl offerDeviceDAO = new OfferDeviceDAOImpl();

    private List<OfferTestModel> testModelList = new ArrayList<>();
    private List<AutotestResultModel> resultModelList = new ArrayList<>();

    private Resources resources = MyApplication.getContext().getResources();
    private String filePath;
    private int configuratoreType;
    private int podParserCount = 0;
    private int completeParserCount = 0;
    private int testOffersCount = 0;
    private int currentTestOfferNumber = 0;

    private boolean isCanceled = false;
    private boolean isErrorHappend = false;
    private String errorMessage;
    private static DecimalFormat decimalFormat;
    private BaseActivity baseActivity;

    private HorizontalProgressDialog progressDialog;

    private OnTestFinishListener onTestFinishListener;

    public AutoTestExecutor(BaseActivity activity, String filePath, int configuratoreType,
                            OnTestFinishListener onTestFinishListener) {
        this.baseActivity = activity;
        this.filePath = filePath;
        this.configuratoreType = configuratoreType;
        this.onTestFinishListener = onTestFinishListener;
        setUpDialog(activity);
    }

    private void setUpDialog(BaseActivity context) {
        progressDialog = new HorizontalProgressDialog(context);
        progressDialog.setOnCancelClickListener(this);
        progressDialog.setMessage("Preparing file for parsing");
        progressDialog.showDialog();
    }

    private void showErrorMessage(String errorMessage) {
        progressDialog.complete(errorMessage);
    }

    private boolean checkForCorrectTestFile(Sheet sheet) {
        int headersCount = sheet.getRow(1).getLastCellNum();
        return headersCount == (configuratoreType == Constants.BUSINESS_CONFIGURATOR ?
                BUSINESS_HEADER_COUNT : CONSUMER_HEADER_COUNT);
    }

    private void proccessSheet(Sheet sheet) {
        Iterator<Row> iter = sheet.rowIterator();
        iter.next();
        iter.next();

        List<Row> offerRows = new ArrayList<>();
        double currentNumber = 0;
        String servizi = null;
        while (iter.hasNext()) {
            Row row = iter.next();
            if (row.getCell(0) == null) continue;
            Cell testNumberCell = row.getCell(resources.getInteger(R.integer.id_caso_test));
            double testNumber = testNumberCell.getNumericCellValue();
            if (currentNumber == 0 || testNumber == currentNumber) {
                offerRows.add(row);
                servizi = getServiziFromRow(row);
            } else {
                testModelList.add(new OfferTestModel(servizi.trim(), currentNumber, offerRows));
                servizi = getServiziFromRow(row);
                offerRows = new ArrayList<>();
                offerRows.add(row);
            }
            currentNumber = testNumber;
        }
        checkForLastTestRunned(offerRows, servizi, currentNumber);
        resultModelList.clear();
        processingTests();
    }

    private String getServiziFromRow(Row row) {
        Cell serviziCell = row.getCell(resources.getInteger(R.integer.servizi));
        String servizi = serviziCell.getStringCellValue().replaceAll(AbstractTestParser.SPECIAL_SPACE,
                Constants.EMPTY_STRING).trim();
        return servizi;
    }

    private void processingTests() {
        definePodParserCount();
        for (OfferTestModel offerTestModel : testModelList) {
            startParcing(offerTestModel);
        }
    }

    private void definePodParserCount() {
        for (OfferTestModel model : testModelList) {
            podParserCount += model.getTestCases().size() * model.getServizi().length();
        }
    }

    private void checkForLastTestRunned(List<Row> offerRows, String servizi, double currentNumber) {
        if (!offerRows.isEmpty()) {
            testModelList.add(new OfferTestModel(servizi, currentNumber, offerRows));
        }
    }

    private void startParcing(OfferTestModel offerTestModel) {
        String[] serviziTypeArray = offerTestModel.getServizi().split("(?!^)");
        runParsers(serviziTypeArray, offerTestModel.getTestCases(), offerTestModel.getTestNumber());
    }

    private void runParsers(String[] serviziTypeArray, List<Row> offerRows, double testNumber) {
        Offer offer = new Offer();
        offer.setOldTlcOfferId((int) testNumber);
        OfferResult offerResult = new OfferResult();
        buildFakeOffer(offer, offerResult);
        for (String servizi : serviziTypeArray) {
            if (!isCanceled && !TextUtils.isEmpty(servizi)) {
                AbstractTestParser parser = getParserByCode(servizi);
                parser.setListener(this);
                parser.setOnParsingErrorListener(this);
                parser.parse(offerRows, offer, offerResult);
            }
        }
    }

    private void buildFakeOffer(Offer offer, OfferResult offerResult) {
        offer.setConfigurator_type(configuratoreType);
        offer.setOfferCost(new BigDecimal(0.00));
        offer.setOfferCostExtra(new BigDecimal(0.00));
        offer.setOfferCostIVA(new BigDecimal(0.00));
        offer.setSurname(Constants.EMPTY_STRING);
        offer.setName(Constants.EMPTY_STRING);
        offer.setCap(Constants.EMPTY_STRING);
        offer.setCodiceFiscale(Constants.EMPTY_STRING);
        offer.setIndirizzoDiFornitura(Constants.EMPTY_STRING);
        offer.setTownId(0);
        offer.setBirthDateTownId(0);
        offer.setMail(Constants.EMPTY_STRING);
        offer.setOptilifeCode(Constants.TEST_OFFER_CODE);
        offer.setNi(Constants.EMPTY_STRING);
        offer.setOfferte1ServiceCode("");
        offer.setOfferte2ServiceCode("");
        offer.setOfferte3ServiceCode("");
        offer.setMainServiceCode("");
        offer.setCreationDate("");
        offer.setOldTlcOfferUsed(false);
        offer.setPdfSendingRequired(false);
        offer.setClientTypeId(0);
        offer.setCentroCommerciale(false);
        offer.setSex("M");
        offer.setBirthDate("");
        offer.setClimaticZone("");
        offer.setPiva("");
        offer.setPricingOfferId("");
        offerDAO.insert(offer);
        offerResult.setOfferId(offer.getOfferId());
    }

    private void runJarCalulationService() {
        List<Offer> testOfferList = offerDAO.getOffersForAutoTest();
        testOffersCount = testOfferList.size();
        progressDialog.setMessage("Preparation for calculation");
        startCalculation(testOfferList);
    }

    private void startCalculation(final List<Offer> testOfferList) {
        if (isCanceled) {
            cancelProcess();
            return;
        }

        if (currentTestOfferNumber < testOffersCount) {
            final Offer offer = testOfferList.get(currentTestOfferNumber);
            new TotalCostJarService(new AbstractService.OnTaskCompleted<TotalCostJarService>() {
                @Override
                public void onTaskCompleted(TotalCostJarService service) {
                    try {
                        processingResult((Pricing) service.getResult().get(CentralizedCostUtil.PRICING),
                                (ResponsePrincing) service.getResult().get(CentralizedCostUtil.RESPONCE_PRICING),
                                offer);
                        Log.e("test offer number", "currentTestOfferNumber: " + currentTestOfferNumber);
                        currentTestOfferNumber++;
                        disablingIndeterminateMode(currentTestOfferNumber);
                        progressDialog.setProgress(currentTestOfferNumber);
                        startCalculation(testOfferList);
                    } catch (Exception e) {
                        progressDialog.setProgressIndicatorVisibility(false);
                        progressDialog.complete("Error while processing result of test №" + offer.getOldTlcOfferId());
                    }
                }
            }).execute(offer);
        } else {
            deleteTempData();
        }
    }

    private void deleteTempData() {
        List<Offer> testOfferList = offerDAO.getOffersForAutoTest();
        testOffersCount = testOfferList.size();
        progressDialog.setMessage("Deleting temp data");
        progressDialog.setMax(testOffersCount);
        progressDialog.setProgress(0);
        currentTestOfferNumber = 0;
        deleteOfferInfo(testOfferList);
    }

    private void deleteOfferInfo(final List<Offer> testOfferList) {
        if (currentTestOfferNumber < testOffersCount) {
            Offer offer = testOfferList.get(currentTestOfferNumber);
            new DeleteAllOfferInfo(new AbstractService.OnTaskCompleted() {
                @Override
                public void onTaskCompleted(AbstractService service) {
                    currentTestOfferNumber++;
                    progressDialog.setProgress(currentTestOfferNumber);
                    deleteOfferInfo(testOfferList);
                }
            }).execute(offer.getOfferId());
        } else {
            deleteAllTempData();
            progressDialog.complete(MyApplication.getContext().getString(R.string.done));
            if (onTestFinishListener != null) {
                onTestFinishListener.onTestFinish(resultModelList);
            }
        }
    }

    private void deleteAllTempData() {
        DatabaseHelper.clearDbTable(OfferResult.class);
        DatabaseHelper.clearDbTable(EnergyResult.class);
        DatabaseHelper.clearDbTable(GasResult.class);
        DatabaseHelper.clearDbTable(VoceResult.class);
        DatabaseHelper.clearDbTable(ADSLResult.class);
        DatabaseHelper.clearDbTable(DeviceResult.class);
        offerDAO.deleteAllOffersForAutoTest();
    }

    private void processingResult(Pricing pricing, ResponsePrincing responsePrincing, Offer offer) {
        if (responsePrincing != null) {
            OfferResult offerResult = offerResultDAO.getOfferResultByOfferId(offer.getOfferId());
            AutotestResultModel autotestResultModel = new AutotestResultModel();
            autotestResultModel.setEnergyTestDetailsList(buildEnergyTestDetails(responsePrincing, offerResult.getResultOfferId()));
            autotestResultModel.setGasTestDetailsList(buildGasTestDetails(responsePrincing, offerResult.getResultOfferId()));
            autotestResultModel.setAdslTestDetailsList(buildADSLTestDetails(responsePrincing, offerResult.getResultOfferId()));
            autotestResultModel.setVoceTestDetailsList(buildVoceTestDetails(responsePrincing, offerResult.getResultOfferId()));
            autotestResultModel.setDeviceTestDetailsList(buildDeviceTestDetails(offerResult.getResultOfferId(), offer.getOfferId()));
            autotestResultModel.setMainOfferDetails(buildMainTestDetails(offer, offerResult));

            autotestResultModel.setTestNumber(offer.getOldTlcOfferId());
            autotestResultModel.setEnergyResult(pricing.getDatiEnergia() != null ?
                    getServiceStatus(responsePrincing.getPricingEnergiaResponse().getError(),
                            autotestResultModel.getEnergyTestDetailsList()) : AutotestResultModel.ABSENT);
            autotestResultModel.setGasResult(pricing.getDatiGas() != null ?
                    getServiceStatus(responsePrincing.getPricingGasResponse().getError(),
                            autotestResultModel.getGasTestDetailsList()) : AutotestResultModel.ABSENT);
            autotestResultModel.setAdslResult(pricing.getDatiAdsl() != null ?
                    getServiceStatus(responsePrincing.getPricingAdslResponse().getError(),
                            autotestResultModel.getAdslTestDetailsList()) : AutotestResultModel.ABSENT);
            autotestResultModel.setVoceResult(pricing.getDatiVoce() != null ?
                    getServiceStatus(responsePrincing.getPricingVoceResponse().getError(),
                            autotestResultModel.getVoceTestDetailsList()) : AutotestResultModel.ABSENT);
            autotestResultModel.setDeviceResult(autotestResultModel.getDeviceTestDetailsList() == null ||
                    autotestResultModel.getDeviceTestDetailsList().isEmpty() ? AutotestResultModel.ABSENT
                    : getDeviceServiceStatus(autotestResultModel.isDeviceTotalResult()));
            autotestResultModel.setTotalResult(autotestResultModel.isTotalResult() ?
                    AutotestResultModel.SUCCESS : AutotestResultModel.FAILED);
            autotestResultModel.setOfferId(offer.getOfferId());

            resultModelList.add(autotestResultModel);
        }
    }

    private String getDeviceServiceStatus(boolean deviceTotalResult) {
        return deviceTotalResult ? AutotestResultModel.SUCCESS : AutotestResultModel.FAILED;
    }

    private MainOfferDetails buildMainTestDetails(Offer offer, OfferResult offerResult) {
        if (decimalFormat == null) {
            String pattern = "#####0.0";
            DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
            decimalFormatSymbols.setDecimalSeparator('.');
            decimalFormat = new DecimalFormat(pattern, decimalFormatSymbols);
        }

        MainOfferDetails mainOfferDetails = new MainOfferDetails();

        double cost = Double.valueOf(decimalFormat.format(offer.getOfferCost().doubleValue()));
        mainOfferDetails.setCanoneMensile(MyApplication.getContext()
                .getString(R.string.result_comparation_desc,
                        cost, offerResult.getCanoneMensile()));
        mainOfferDetails.setCanoneMensileStatus(cost == offerResult.getCanoneMensile());

        double costIva = Double.valueOf(decimalFormat.format(offer.getOfferCostIVA().doubleValue()));
        mainOfferDetails.setCanoneMensileConIVA(MyApplication.getContext()
                .getString(R.string.result_comparation_desc,
                        costIva, offerResult.getCanoneMensileConIva()));
        mainOfferDetails.setCanoneMensileConIVAStatus(costIva == offerResult.getCanoneMensileConIva());

        mainOfferDetails.setContoRelax(MyApplication.getContext()
                .getString(R.string.result_comparation_desc,
                        offer.getConto_relax(), offerResult.getContoRelax()));
        mainOfferDetails.setContoRelaxStatus(offer.getConto_relax() == offerResult.getContoRelax());

        double activationCost = offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR ?
                offer.getOfferCostExtra().doubleValue() * 4 : offer.getOfferCostExtra().doubleValue();
        mainOfferDetails.setCostoAttivazione(MyApplication.getContext()
                .getString(R.string.result_comparation_desc,
                        activationCost, offerResult.getCostoAttivazione()));
        mainOfferDetails.setCostoAttivazioneStatus(activationCost ==
                offerResult.getCostoAttivazione());
        return mainOfferDetails;
    }

    private List<DeviceTestDetails> buildDeviceTestDetails(int offerResult, int offerId) {
        List<DeviceResult> deviceResultList = deviceResultDAO.getDeviceResultListByResultOfferId(offerResult);
        List<OfferDevice> offerDevices = offerDeviceDAO.getDevicesByOfferId(offerId);
        List<DeviceTestDetails> deviceTestDetailses = null;
        if (deviceResultList != null) {
            deviceTestDetailses = new ArrayList<>();
            for (int i = 0; i < offerDevices.size(); i++) {
                DeviceResult deviceResult = deviceResultList.get(i);
                OfferDevice offerDevice = offerDevices.get(i);

                DeviceTestDetails deviceTestDetails = new DeviceTestDetails();

                deviceTestDetails.setDeviceNumber(deviceResult.getDeviceNumber());

                deviceTestDetails.setCanoneDevice((MyApplication.getContext()
                        .getString(R.string.result_comparation_desc,
                                offerDevice.getDeviceCost(), deviceResult.getCanoneDevice())));
                deviceTestDetails.setCanoneDeviceStatus(offerDevice.getDeviceCost() == deviceResult.getCanoneDevice());

                deviceTestDetails.setCanoneDeviceConIVA((MyApplication.getContext()
                        .getString(R.string.result_comparation_desc,
                                offerDevice.getDeviceCostIva(), deviceResult.getCanoneDeviceConIVA())));
                deviceTestDetails.setCanoneDeviceConIVAStatus(offerDevice.getDeviceCostIva() == deviceResult.getCanoneDeviceConIVA());

                deviceTestDetails.setTipoDevice((MyApplication.getContext()
                        .getString(R.string.result_comparation_desc_str,
                                offerDevice.getDeviceDesc(), deviceResult.getTipoDevice())));
                deviceTestDetails.setTipoDeviceStatus(offerDevice.getDeviceDesc().equals(deviceResult.getTipoDevice()));

                deviceTestDetailses.add(deviceTestDetails);
            }
        }
        return deviceTestDetailses;
    }

    private List<EnergyTestDetails> buildEnergyTestDetails(ResponsePrincing responsePrincing, int offerResult) {
        List<EnergyResult> energyResultList = energyResultDAO.getEnergyResultListByResultOfferId(offerResult);
        List<EnergyTestDetails> energyTestDetailses = null;
        if (energyResultList != null && !energyResultList.isEmpty()) {
            energyTestDetailses = new ArrayList<>();
            List<ResponsePrincing.PricingEnergiaResponse.ResponseOfferta> offertaList =
                    responsePrincing.getPricingEnergiaResponse().getResponseOfferta();
            for (int i = 0; i < offertaList.size(); i++) {
                EnergyResult energyResult = energyResultList.get(i);
                ResponsePrincing.PricingEnergiaResponse.ResponseOfferta responceOfferta
                        = offertaList.get(i);

                EnergyTestDetails energyTestDetails = new EnergyTestDetails();

                energyTestDetails.setPotenza(MyApplication.getContext()
                        .getString(R.string.result_comparation_desc,
                                responceOfferta.getPotenza(), energyResult.getPotenza()));
                energyTestDetails.setPotenzaStatus(responceOfferta.getPotenza() == energyResult.getPotenza());

                energyTestDetails.setTagliaMeseEEStatus(true);

                energyTestDetails.setPrezzoEE(MyApplication.getContext()
                        .getString(R.string.result_comparation_desc,
                                responceOfferta.getCostoPacchettoMensile(), energyResult.getPrezzo()));
                energyTestDetails.setPrezzoEEStatus(responceOfferta.getCostoPacchettoMensile() == energyResult.getPrezzo());

                energyTestDetails.setPrezzoEEConIva(MyApplication.getContext()
                        .getString(R.string.result_comparation_desc,
                                responceOfferta.getCostoPacchettoMensileIva(), energyResult.getPrezzoConIva()));
                energyTestDetails.setPrezzoEEConIvaStatus(responceOfferta.getCostoPacchettoMensileIva() == energyResult.getPrezzoConIva());

                energyTestDetails.setSforamento(MyApplication.getContext()
                        .getString(R.string.result_comparation_desc,
                                responceOfferta.getPrezzoSforamentoMancatoConsumo().getPrezzoSforamento(), energyResult.getSforamento()));
                energyTestDetails.setSforamentoStatus(responceOfferta.getPrezzoSforamentoMancatoConsumo().getPrezzoSforamento() ==
                        (float) energyResult.getSforamento());

                energyTestDetails.setMancatoConsumo(MyApplication.getContext()
                        .getString(R.string.result_comparation_desc,
                                responceOfferta.getPrezzoSforamentoMancatoConsumo().getPrezzoMancatoConsumo(),
                                energyResult.getMancatoConsumo()));
                energyTestDetails.setMancatoConsumoStatus(responceOfferta.getPrezzoSforamentoMancatoConsumo().getPrezzoMancatoConsumo()
                        == (float) energyResult.getMancatoConsumo());

                energyTestDetails.setEnergyPerc(MyApplication.getContext()
                        .getString(R.string.result_comparation_desc_str,
                                AbstractTestParser.decimalFormat.format(responceOfferta.getPercentuale()),
                                AbstractTestParser.decimalFormat.format(energyResult.getEnergyPerc())));
                energyTestDetails.setEnergyPercStatus(AbstractTestParser.decimalFormat.format(responceOfferta.getPercentuale()).equals(
                        AbstractTestParser.decimalFormat.format(energyResult.getEnergyPerc())));

                energyTestDetails.setCodiceTariffa(MyApplication.getContext()
                        .getString(R.string.result_comparation_desc_str,
                                responceOfferta.getPrezzoSforamentoMancatoConsumo().getCodice(),
                                energyResult.getCodiceTariffa()));
                energyTestDetails.setCodiceTariffaStatus(responceOfferta.getPrezzoSforamentoMancatoConsumo().getCodice()
                        .equals(energyResult.getCodiceTariffa()));
                energyTestDetails.setPodNumber(energyResult.getPodNumber());
                energyTestDetailses.add(energyTestDetails);
            }
        }
        return energyTestDetailses;
    }

    private List<GasTestDetails> buildGasTestDetails(ResponsePrincing responsePrincing, int offerResult) {
        List<GasResult> gasResultList = gasResultDAO.getGasResultListByResultOfferId(offerResult);
        List<GasTestDetails> gasTestDetailses = null;
        if (gasResultList != null) {
            gasTestDetailses = new ArrayList<>();
            List<ResponsePrincing.PricingGasResponse.ResponseOfferta> offertaList =
                    responsePrincing.getPricingGasResponse().getResponseOfferta();
            for (int i = 0; i < offertaList.size(); i++) {
                GasResult gasResult = gasResultList.get(i);
                ResponsePrincing.PricingGasResponse.ResponseOfferta responceOfferta
                        = offertaList.get(i);

                GasTestDetails gasTestDetails = new GasTestDetails();

                gasTestDetails.setTagliaMeseGasStatus(true);

                gasTestDetails.setPrezzoGas(MyApplication.getContext()
                        .getString(R.string.result_comparation_desc,
                                responceOfferta.getCostoPacchettoMensile(), gasResult.getPrezzo()));
                gasTestDetails.setPrezzoGasStatus(responceOfferta.getCostoPacchettoMensile() == gasResult.getPrezzo());

                gasTestDetails.setPrezzoGasConIva(MyApplication.getContext()
                        .getString(R.string.result_comparation_desc,
                                responceOfferta.getCostoPacchettoMensileIva(), gasResult.getPrezzoConIva()));
                gasTestDetails.setPrezzoGasConIvaStatus(responceOfferta.getCostoPacchettoMensileIva() == gasResult.getPrezzoConIva());

                gasTestDetails.setSforamento(MyApplication.getContext()
                        .getString(R.string.result_comparation_desc,
                                responceOfferta.getPrezzoSforamentoMancatoConsumo().get(0).getPrezzoSforamento(), gasResult.getSforamento()));
                gasTestDetails.setSforamentoStatus(responceOfferta.getPrezzoSforamentoMancatoConsumo().get(0).getPrezzoSforamento() ==
                        (float) gasResult.getSforamento());

                gasTestDetails.setMancatoConsumo(MyApplication.getContext()
                        .getString(R.string.result_comparation_desc,
                                responceOfferta.getPrezzoSforamentoMancatoConsumo().get(0).getPrezzoMancatoConsumo(),
                                gasResult.getMancatoConsumo()));
                gasTestDetails.setMancatoConsumoStatus(responceOfferta.getPrezzoSforamentoMancatoConsumo().get(0).getPrezzoMancatoConsumo()
                        == (float) gasResult.getMancatoConsumo());

                gasTestDetails.setGasPerc(MyApplication.getContext()
                        .getString(R.string.result_comparation_desc_str,
                                AbstractTestParser.decimalFormat.format(responceOfferta.getPercentuale()),
                                AbstractTestParser.decimalFormat.format(gasResult.getGasPerc())));
                gasTestDetails.setGasPercStatus(AbstractTestParser.decimalFormat.format(responceOfferta.getPercentuale()).equals(
                        AbstractTestParser.decimalFormat.format(gasResult.getGasPerc())));

                gasTestDetails.setCodiceTariffa(MyApplication.getContext()
                        .getString(R.string.result_comparation_desc_str,
                                responceOfferta.getPrezzoSforamentoMancatoConsumo().get(0).getCodice(),
                                gasResult.getCodiceTariffa()));
                gasTestDetails.setCodiceTariffaStatus(responceOfferta.getPrezzoSforamentoMancatoConsumo().get(0).getCodice()
                        .equals(gasResult.getCodiceTariffa()));
                gasTestDetails.setPdrNumber(gasResult.getPdrNumber());
                gasTestDetailses.add(gasTestDetails);
            }
        }
        return gasTestDetailses;
    }

    private List<ADSLTestDetails> buildADSLTestDetails(ResponsePrincing responsePrincing, int offerResult) {
        List<ADSLResult> adslResultList = adslResultDAO.getADSLResultListByResultOfferId(offerResult);
        List<ADSLTestDetails> adslTestDetailses = null;
        if (adslResultList != null) {
            adslTestDetailses = new ArrayList<>();
            List<ResponsePrincing.PricingAdslResponse.ResponseOfferta> offertaList =
                    responsePrincing.getPricingAdslResponse().getResponseOfferta();
            for (int i = 0; i < offertaList.size(); i++) {
                ADSLResult adslResult = adslResultList.get(i);
                ResponsePrincing.PricingAdslResponse.ResponseOfferta responceOfferta
                        = offertaList.get(i);

                ADSLTestDetails adslTestDetails = new ADSLTestDetails();

                adslTestDetails.setPrezzoAdsl(MyApplication.getContext()
                        .getString(R.string.result_comparation_desc,
                                responceOfferta.getCostoPacchettoMensile(), adslResult.getPrezzo()));
                adslTestDetails.setPrezzoAdslStatus(responceOfferta.getCostoPacchettoMensile()
                        == (float) adslResult.getPrezzo());

                adslTestDetails.setPrezzoAdslConIva(MyApplication.getContext()
                        .getString(R.string.result_comparation_desc,
                                responceOfferta.getCostoPacchettoMensileIva(), adslResult.getPrezzoConIva()));
                adslTestDetails.setPrezzoAdslConIvaStatus(responceOfferta.getCostoPacchettoMensileIva() ==
                        (float) adslResult.getPrezzoConIva());

                adslTestDetails.setAdslPerc(MyApplication.getContext()
                        .getString(R.string.result_comparation_desc_str,
                                AbstractTestParser.decimalFormat.format(responceOfferta.getPercentuale()),
                                AbstractTestParser.decimalFormat.format(adslResult.getAdslPerc())));
                adslTestDetails.setAdslPercStatus(AbstractTestParser.decimalFormat.format(responceOfferta.getPercentuale()).equals(
                        AbstractTestParser.decimalFormat.format(adslResult.getAdslPerc())));
                adslTestDetails.setAdslNumber(adslResult.getAdslNumber());
                adslTestDetailses.add(adslTestDetails);
            }
        }
        return adslTestDetailses;
    }

    private List<VoceTestDetails> buildVoceTestDetails(ResponsePrincing responsePrincing, int offerResult) {
        List<VoceResult> voceResultList = voceResultDAO.getVoceResultListByResultOfferId(offerResult);
        List<VoceTestDetails> voceTestDetailses = null;
        if (voceResultList != null) {
            voceTestDetailses = new ArrayList<>();
            List<ResponsePrincing.PricingVoceResponse.ResponseOfferta> offertaList =
                    responsePrincing.getPricingVoceResponse().getResponseOfferta();
            for (int i = 0; i < offertaList.size(); i++) {
                VoceResult voceResult = voceResultList.get(i);
                ResponsePrincing.PricingVoceResponse.ResponseOfferta responceOfferta
                        = offertaList.get(i);

                VoceTestDetails voceTestDetails = new VoceTestDetails();

                voceTestDetails.setPrezzoVoce(MyApplication.getContext()
                        .getString(R.string.result_comparation_desc,
                                responceOfferta.getCostoPacchettoMensile(), voceResult.getPrezzo()));
                voceTestDetails.setPrezzoVoceStatus(responceOfferta.getCostoPacchettoMensile()
                        == (float) voceResult.getPrezzo());

                voceTestDetails.setPrezzoVoceConIva(MyApplication.getContext()
                        .getString(R.string.result_comparation_desc,
                                responceOfferta.getCostoPacchettoMensileIva(), voceResult.getPrezzoConIva()));
                voceTestDetails.setPrezzoVoceConIvaStatus(responceOfferta.getCostoPacchettoMensileIva() ==
                        (float) voceResult.getPrezzoConIva());

                voceTestDetails.setVocePerc(MyApplication.getContext()
                        .getString(R.string.result_comparation_desc_str,
                                AbstractTestParser.decimalFormat.format(responceOfferta.getPercentuale()),
                                AbstractTestParser.decimalFormat.format(voceResult.getVocePerc())));
                voceTestDetails.setVocePercStatus(AbstractTestParser.decimalFormat.format(responceOfferta.getPercentuale()).equals(
                        AbstractTestParser.decimalFormat.format(voceResult.getVocePerc())));
                voceTestDetails.setVoceNumber(voceResult.getVoceNumber());
                voceTestDetailses.add(voceTestDetails);
            }
        }
        return voceTestDetailses;
    }

    private String getServiceStatus(String error, List<? extends AbstractTestModel> testModels) {
        boolean modelResult = true;
        if (testModels != null) {
            for (AbstractTestModel testModel : testModels) {
                if (!testModel.getTotalResult()) {
                    modelResult = testModel.getTotalResult();
                    break;
                }
            }
        } else {
            modelResult = false;
        }
        return error == null && modelResult ?
                AutotestResultModel.SUCCESS : AutotestResultModel.FAILED;
    }

    private void disablingIndeterminateMode(int currentTestOfferNumber) {
        if (currentTestOfferNumber == 1) {
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(testOffersCount);
            progressDialog.setMessage("Process calculations");
            progressDialog.setProgressIndicatorVisibility(true);
        }
    }

    @Override
    public void onParsingComplete(String testNumber) {
        if (baseActivity != null && !baseActivity.isFinishing()) {
            completeParserCount++;
            Log.d(this.getClass().getSimpleName(), "TEST!!!!!!!!!   = " + testNumber);
            updateDialogInfo();
            if (!isCanceled) {
                if (completeParserCount == podParserCount) {
                    runJarCalulationService();
                }
            } else {
                cancelProcess();
            }
        }
    }

    private void updateDialogInfo() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (completeParserCount == 1) {
                    progressDialog.setIndeterminate(false);
                    progressDialog.setMax(podParserCount);
                }
                progressDialog.setProgressIndicatorVisibility(true);
                progressDialog.setProgress(completeParserCount);
            }
        });
    }

    private void cancelProcess() {
        progressDialog.dismissDialog();
        deleteAllTempData();
    }

    @Override
    public void onCancelClick() {
        isCanceled = true;
    }

    @Override
    public void onParsingError(String errorMessage) {
//        progressDialog.setMessage(errorMessage);
//        showErrorMessage(errorMessage);
        isErrorHappend = true;
        this.errorMessage = errorMessage;
    }

    @Override
    protected Void doStuff(Void... voids) {
        try {
            progressDialog.setMessage("Parsing data");
            deleteAllTempData();
            InputStream in = new FileInputStream(filePath);
            Workbook wb = new XSSFWorkbook(in);
            Sheet sheet = wb.getSheetAt(0);
            if (checkForCorrectTestFile(sheet)) {
                proccessSheet(sheet);
            } else {
                isErrorHappend = true;
                errorMessage = MyApplication.getContext().getString(R.string.configurator_type_error);
            }
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "error ...", e);
            isErrorHappend = true;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (isErrorHappend) {
            showErrorMessage(errorMessage != null ? errorMessage
                    : MyApplication.getContext().getString(R.string.error_processing_autotest));
        }
        super.onPostExecute(aVoid);
    }

    private AbstractTestParser getParserByCode(String code) {
        AbstractTestParser parser = null;
        switch (code) {
            case ENERGY:
                parser = new EnergyTestParser();
                break;
            case GAS:
                parser = new GasTestParser();
                break;
            case VOCE:
                parser = new VoceTestParser();
                break;
            case ADSL:
                parser = new ADSLTestParser();
                break;
            case DEVICE:
                parser = new DeviceTestParser();
                break;
        }
        return parser;
    }

    public static int getDetailCellResource(boolean valueStatus) {
        return valueStatus ? R.drawable.green_dettaglio_table_cell : R.drawable.red_dettaglio_table_cell;
    }

    public static int getMainCellResource(boolean valueStatus) {
        return valueStatus ? R.drawable.green_cell_selector : R.drawable.red_cell_selector;
    }
}
