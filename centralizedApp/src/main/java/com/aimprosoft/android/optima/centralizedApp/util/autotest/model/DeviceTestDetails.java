package com.aimprosoft.android.optima.centralizedApp.util.autotest.model;

public class DeviceTestDetails extends AbstractTestModel {
    private String tipoDevice;
    private boolean tipoDeviceStatus;
    private String canoneDevice;
    private boolean canoneDeviceStatus;
    private String canoneDeviceConIVA;
    private boolean canoneDeviceConIVAStatus;
    private String deviceNumber;

    public DeviceTestDetails() {
    }

    public boolean getTotalResult() {
        return tipoDeviceStatus && canoneDeviceStatus && canoneDeviceConIVAStatus;
    }

    public String getTipoDevice() {
        return tipoDevice;
    }

    public void setTipoDevice(String tipoDevice) {
        this.tipoDevice = tipoDevice;
    }

    public boolean isTipoDeviceStatus() {
        return tipoDeviceStatus;
    }

    public void setTipoDeviceStatus(boolean tipoDeviceStatus) {
        this.tipoDeviceStatus = tipoDeviceStatus;
    }

    public String getCanoneDevice() {
        return canoneDevice;
    }

    public void setCanoneDevice(String canoneDevice) {
        this.canoneDevice = canoneDevice;
    }

    public boolean isCanoneDeviceStatus() {
        return canoneDeviceStatus;
    }

    public void setCanoneDeviceStatus(boolean canoneDeviceStatus) {
        this.canoneDeviceStatus = canoneDeviceStatus;
    }

    public String getCanoneDeviceConIVA() {
        return canoneDeviceConIVA;
    }

    public void setCanoneDeviceConIVA(String canoneDeviceConIVA) {
        this.canoneDeviceConIVA = canoneDeviceConIVA;
    }

    public boolean isCanoneDeviceConIVAStatus() {
        return canoneDeviceConIVAStatus;
    }

    public void setCanoneDeviceConIVAStatus(boolean canoneDeviceConIVAStatus) {
        this.canoneDeviceConIVAStatus = canoneDeviceConIVAStatus;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }
}
