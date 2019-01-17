package com.aimprosoft.android.optima.centralizedApp.util.autotest.listener;

import com.aimprosoft.android.optima.centralizedApp.util.autotest.model.AutotestResultModel;

import java.util.List;

public interface OnTestFinishListener {
    void onTestFinish(List<AutotestResultModel> autotestResultModels);
}
