package com.aimprosoft.android.optima.centralizedApp.util.autotest.model;

import org.apache.poi.ss.usermodel.Row;

import java.util.List;

public class OfferTestModel {
    private String servizi;
    private double testNumber;
    private List<Row> testCases;

    public OfferTestModel(String servizi, double testNumber, List<Row> testCases) {
        this.servizi = servizi;
        this.testNumber = testNumber;
        this.testCases = testCases;
    }

    public double getTestNumber() {
        return testNumber;
    }

    public void setTestNumber(double testNumber) {
        this.testNumber = testNumber;
    }

    public String getServizi() {
        return servizi;
    }

    public void setServizi(String servizi) {
        this.servizi = servizi;
    }

    public List<Row> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<Row> testCases) {
        this.testCases = testCases;
    }
}
