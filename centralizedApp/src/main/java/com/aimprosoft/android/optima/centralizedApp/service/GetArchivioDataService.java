package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;

import java.util.List;

public class GetArchivioDataService extends AsyncTask<Void, Void, List<Object[]>> {
    private ProgressDialog progressDialog;

    public GetArchivioDataService(Activity activity) {
        progressDialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
    }

    @Override
    protected List<Object[]> doInBackground(Void... params) {
        return new OfferDAOImpl().getArchivioList();
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }
}
