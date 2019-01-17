package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;

import com.aimprosoft.android.optima.centralizedApp.activity.optilife.BaseActivity;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.CategorieUsoDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasCurveConsumoDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasOfferDateIntervalDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOfferDateInterval;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GasIntervalComputationServiceImpl extends AbstractService<Object, Integer> {
    private BaseActivity activity;

    public GasIntervalComputationServiceImpl(Activity activity, OnTaskCompleted onTaskCompleted) {
        super(activity, onTaskCompleted);
        this.activity = (BaseActivity) activity;
    }

    @Override
    protected Integer doStuff(Object... param) {
        int categoriaUsoId = (int) param[0];
        int townId = (int) param[1];
        int gasDetailsOfferId = (int) param[2];
        int classePrelievo = (int) param[3];
        String codiceCategoria = new CategorieUsoDAOImpl().getCodiceCategoriaById(categoriaUsoId);
        Double perc = 0.00;
        int insertedConsumption = 0;

        List<GasOfferDateInterval> gasOfferDateIntervals = new GasOfferDateIntervalDAOImpl().getOfferDateIntervalByGasDetailsId(gasDetailsOfferId);

        for (GasOfferDateInterval gasOfferDateInterval : gasOfferDateIntervals) {
            BigDecimal percValue = new GasCurveConsumoDAOImpl().sumPercValue(townId, codiceCategoria,
                    changeDateFormat(gasOfferDateInterval.getDateFrom()), activity.getNextDay(BaseActivity.simpleDateFormat.format(gasOfferDateInterval.getDateTo())), classePrelievo);
            perc += percValue != null && percValue.doubleValue() != 0.00 ? percValue.doubleValue() : 1;
            insertedConsumption += gasOfferDateInterval.getSmc();
        }
//            consumption += percValue != null && percValue.doubleValue() != 0.00 ? (gasOfferDateInterval.getSmc() * 100) / percValue.doubleValue() : 0;
        Double consumption = (double)(insertedConsumption * 100) / perc;
        return consumption.intValue();
    }

    private String changeDateFormat(Date date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "";
    }
}
