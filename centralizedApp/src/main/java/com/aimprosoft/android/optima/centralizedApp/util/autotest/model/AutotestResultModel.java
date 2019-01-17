package com.aimprosoft.android.optima.centralizedApp.util.autotest.model;

import java.util.List;

public class AutotestResultModel {
    public static final String SUCCESS = "Success";
    public static final String FAILED = "Failed";
    public static final String ABSENT = "------";

    private int testNumber;
    private String energyResult;
    private List<EnergyTestDetails> energyTestDetailsList;
    private List<GasTestDetails> gasTestDetailsList;
    private List<ADSLTestDetails> adslTestDetailsList;
    private List<VoceTestDetails> voceTestDetailsList;
    private List<DeviceTestDetails> deviceTestDetailsList;
    private MainOfferDetails mainOfferDetails;
    private String gasResult;
    private String voceResult;
    private String adslResult;
    private String deviceResult;
    private String totalResult;
    private int offerId;

    public AutotestResultModel() {
    }

    public String getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(String totalResult) {
        this.totalResult = totalResult;
    }

    public List<DeviceTestDetails> getDeviceTestDetailsList() {
        return deviceTestDetailsList;
    }

    public void setDeviceTestDetailsList(List<DeviceTestDetails> deviceTestDetailsList) {
        this.deviceTestDetailsList = deviceTestDetailsList;
    }

    public MainOfferDetails getMainOfferDetails() {
        return mainOfferDetails;
    }

    public void setMainOfferDetails(MainOfferDetails mainOfferDetails) {
        this.mainOfferDetails = mainOfferDetails;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public List<VoceTestDetails> getVoceTestDetailsList() {
        return voceTestDetailsList;
    }

    public void setVoceTestDetailsList(List<VoceTestDetails> voceTestDetailsList) {
        this.voceTestDetailsList = voceTestDetailsList;
    }

    public List<ADSLTestDetails> getAdslTestDetailsList() {
        return adslTestDetailsList;
    }

    public void setAdslTestDetailsList(List<ADSLTestDetails> adslTestDetailsList) {
        this.adslTestDetailsList = adslTestDetailsList;
    }

    public List<GasTestDetails> getGasTestDetailsList() {
        return gasTestDetailsList;
    }

    public void setGasTestDetailsList(List<GasTestDetails> gasTestDetailsList) {
        this.gasTestDetailsList = gasTestDetailsList;
    }

    public List<EnergyTestDetails> getEnergyTestDetailsList() {
        return energyTestDetailsList;
    }

    public void setEnergyTestDetailsList(List<EnergyTestDetails> energyTestDetailsList) {
        this.energyTestDetailsList = energyTestDetailsList;
    }

    public int getTestNumber() {
        return testNumber;
    }

    public void setTestNumber(int testNumber) {
        this.testNumber = testNumber;
    }

    public String getEnergyResult() {
        return energyResult;
    }

    public void setEnergyResult(String energyResult) {
        this.energyResult = energyResult;
    }

    public String getGasResult() {
        return gasResult;
    }

    public void setGasResult(String gasResult) {
        this.gasResult = gasResult;
    }

    public String getVoceResult() {
        return voceResult;
    }

    public void setVoceResult(String voceResult) {
        this.voceResult = voceResult;
    }

    public String getAdslResult() {
        return adslResult;
    }

    public void setAdslResult(String adslResult) {
        this.adslResult = adslResult;
    }

    public String getDeviceResult() {
        return deviceResult;
    }

    public void setDeviceResult(String deviceResult) {
        this.deviceResult = deviceResult;
    }

    public boolean isEnergyTotalResult() {
        return getIsAllResultCorrect(energyTestDetailsList);
    }

    public boolean isGasTotalResult() {
        return getIsAllResultCorrect(gasTestDetailsList);
    }

    public boolean isVoceTotalResult() {
        return getIsAllResultCorrect(voceTestDetailsList);
    }


    public boolean isDeviceTotalResult() {
        return getIsAllResultCorrect(deviceTestDetailsList);
    }

    public boolean isAdslTotalResult() {
        return getIsAllResultCorrect(adslTestDetailsList);
    }

    public boolean isTotalResult() {
        return mainOfferDetails.isCanoneMensileStatus() && mainOfferDetails.isCanoneMensileConIVAStatus() &&
                mainOfferDetails.isContoRelaxStatus() && mainOfferDetails.isCostoAttivazioneStatus();
    }


    private boolean getIsAllResultCorrect(List<? extends AbstractTestModel> testModels) {
        boolean result = true;
        if (testModels != null) {
            for (AbstractTestModel model : testModels) {
                if (!model.getTotalResult()) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }
}
