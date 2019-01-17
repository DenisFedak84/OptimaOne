package com.aimprosoft.android.optima.centralizedApp.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.R;

public abstract class AbstractService<Param, Result> extends AsyncTask<Param, Void, Result> {
    public final String TAG = this.getClass().getName();
    private ProgressDialog progressDialog;
    private Result result;
    private Param param;
    private OnTaskCompleted onTaskCompleted;
    private Activity activity;

    protected AbstractService() {
    }

    public AbstractService(OnTaskCompleted onTaskCompleted) {
        this.onTaskCompleted = onTaskCompleted;
    }

    protected AbstractService(Activity activity, OnTaskCompleted onTaskCompleted) {
        this.onTaskCompleted = onTaskCompleted;
        this.activity = activity;
        progressDialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        if (progressDialog != null) {
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
        }
    }

    @Override
    protected void onPostExecute(Result result) {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            if (onTaskCompleted != null &&
                    (progressDialog == null ||
                            (activity != null && !activity.isFinishing()))) {
                onTaskCompleted.onTaskCompleted(this);
            }
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "incorrect closing progress dialog", e);
        }
    }

    @SafeVarargs
    @Override
    protected final Result doInBackground(Param... params) {
        result = doStuff(params);
        return null;
    }

    @SuppressWarnings("unchecked")
    protected abstract Result doStuff(Param... params);

    public Result getResult() {
        return result;
    }

    public interface OnTaskCompleted<T extends AbstractService> {
        void onTaskCompleted(T service);
    }

    public Param getParam() {
        return param;
    }

    public void setParam(Param param) {
        this.param = param;
    }
}
