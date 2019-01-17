package com.aimprosoft.android.optima.centralizedApp.activity.optilife;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.adapter.AutoTestResultRecyclerViewAdapter;
import com.aimprosoft.android.optima.centralizedApp.event.TestDetailsDialog;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.AutoTestExecutor;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.listener.OnCellClickListener;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.listener.OnTestFinishListener;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.model.AutotestResultModel;
import com.nononsenseapps.filepicker.FilePickerActivity;

import org.apache.commons.io.FilenameUtils;
import java.util.List;

public class AutoTestActivity extends BaseActivity implements OnTestFinishListener,
        OnCellClickListener {

    private static final int FILE_CODE = 13;
    private static final String ALLOWED_EXTENSION = "xlsx";
    private String fileUri;
    private RecyclerView mRevcyclerView;
    private RadioGroup configuratorTypeRadioGroup;
    private TestDetailsDialog testDetailsDialog;
    private TextView filePath;
    private int currentScreenOrientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto_test_layout);
        currentScreenOrientation = getResources().getConfiguration().orientation;
        initializeViews();
    }

    private void initializeViews() {
        filePath = (TextView) findViewById(R.id.filePath);
        findViewById(R.id.browse).setOnClickListener(v -> {
            Intent i = new Intent(AutoTestActivity.this, FilePickerActivity.class);
            i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
            i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
            i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);
            i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

            startActivityForResult(i, FILE_CODE);
        });

        mRevcyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRevcyclerView.setLayoutManager(mLayoutManager);

        configuratorTypeRadioGroup = (RadioGroup) findViewById(R.id.configuratorTypeRadioGroup);
        findViewById(R.id.runTest).setOnClickListener(v -> runTest(getConfiguratorTypeId()));
    }

    private void runTest(int configuratorType) {
        if (fileUri != null && configuratorTypeRadioGroup.getCheckedRadioButtonId() != -1) {
            new AutoTestExecutor(this, fileUri, configuratorType, this).execute();
        } else {
            Toast.makeText(this, getString(R.string.choose_file_error), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            checkFilePath(uri.getPath());
        }
    }

    private void checkFilePath(String uri) {
        if (FilenameUtils.getExtension(uri).equals(ALLOWED_EXTENSION)) {
            filePath.setText(uri);
            fileUri = uri;
        } else {
            Toast.makeText(this, getString(R.string.wrong_extension_error), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onTestFinish(List<AutotestResultModel> autotestResultModels) {
        autotestResultModels.add(0, null);
        AutoTestResultRecyclerViewAdapter autoTestResultRecyclerViewAdapter =
                new AutoTestResultRecyclerViewAdapter(this, autotestResultModels);
        autoTestResultRecyclerViewAdapter.setOnCellClickListener(this);
        mRevcyclerView.setAdapter(autoTestResultRecyclerViewAdapter);
    }

    public int getConfiguratorTypeId() {
        int configuratorType;
        switch (configuratorTypeRadioGroup.getCheckedRadioButtonId()) {
            case R.id.business:
                configuratorType = Constants.BUSINESS_CONFIGURATOR;
                break;
            case R.id.consumer:
                configuratorType = Constants.CONSUMER_CONFIGURATOR;
                break;
            default:
                configuratorType = Constants.BUSINESS_CONFIGURATOR;
        }
        return configuratorType;
    }

    @Override
    public void onCellClick(String cellType, AutotestResultModel autotestResultModel) {
        initializeDetailDialog();
        testDetailsDialog.showDialog(cellType, autotestResultModel);
    }

    private void initializeDetailDialog() {
        if (testDetailsDialog == null) {
            testDetailsDialog = new TestDetailsDialog(AutoTestActivity.this);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation != currentScreenOrientation) {
            currentScreenOrientation = newConfig.orientation;

            if (testDetailsDialog != null) {
                testDetailsDialog.dismissDialog();
            }
            testDetailsDialog = new TestDetailsDialog(this);
        }
    }
}
