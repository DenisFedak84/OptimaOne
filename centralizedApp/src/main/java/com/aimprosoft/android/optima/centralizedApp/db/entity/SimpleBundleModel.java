package com.aimprosoft.android.optima.centralizedApp.db.entity;

public class SimpleBundleModel extends BaseEntity {

    private String bundleDesc;
    private double bundleCost;
    private double bundleCostIva;

    public SimpleBundleModel() {
    }

    public SimpleBundleModel(String bundleDesc, double bundleCost, double bundleCostIva) {
        this.bundleDesc = bundleDesc;
        this.bundleCost = bundleCost;
        this.bundleCostIva = bundleCostIva;
    }

    public String getBundleDesc() {
        return bundleDesc;
    }

    public void setBundleDesc(String bundleDesc) {
        this.bundleDesc = bundleDesc;
    }

    public double getBundleCost() {
        return bundleCost;
    }

    public void setBundleCost(double bundleCost) {
        this.bundleCost = bundleCost;
    }

    public double getBundleCostIva() {
        return bundleCostIva;
    }

    public void setBundleCostIva(double bundleCostIva) {
        this.bundleCostIva = bundleCostIva;
    }
}
