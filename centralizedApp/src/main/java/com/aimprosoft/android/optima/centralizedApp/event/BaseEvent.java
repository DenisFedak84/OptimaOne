package com.aimprosoft.android.optima.centralizedApp.event;

import android.app.Dialog;
import android.widget.ListView;

import com.aimprosoft.android.optima.centralizedApp.activity.optilife.BaseActivity;
import com.aimprosoft.android.optima.centralizedApp.app.adapter.MyListAdapter;
import com.aimprosoft.android.optima.centralizedApp.service.AbstractService;
import com.aimprosoft.android.optima.centralizedApp.service.GetArchivioDataServiceImpl;

import java.util.List;


public abstract class BaseEvent<T extends BaseActivity> {

    protected T activity;
    protected Dialog dialog;
    protected ListView listView;


    protected void loadData() {
        new GetArchivioDataServiceImpl(activity, new AbstractService.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(AbstractService service) {
                MyListAdapter myListAdapter = new MyListAdapter(activity.getBaseContext(), (List) service.getResult());
                listView.setAdapter(myListAdapter);
                listView.invalidateViews();
            }
        }).execute();
        listView.invalidateViews();
    }

    protected boolean isActivityAlive() {
        return (activity != null && !activity.isFinishing());
    }
}