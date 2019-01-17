package com.aimprosoft.android.optima.centralizedApp.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.AutoTestExecutor;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.listener.OnCellClickListener;
import com.aimprosoft.android.optima.centralizedApp.util.autotest.model.AutotestResultModel;

import org.w3c.dom.Text;

import java.util.List;

public class AutoTestResultRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener {
    private final int HEADER = 0;
    private final int CONTENT_ITEM = 1;
    private List<AutotestResultModel> mDataSet;
    private OnCellClickListener onCellClickListener;
    private int maxSize;
    private Context context;

    public AutoTestResultRecyclerViewAdapter(Context context, List<AutotestResultModel> mDataSet) {
        this.mDataSet = mDataSet;
        this.context = context;
    }

    public void add(int position, AutotestResultModel item) {
        if (mDataSet.size() < maxSize) {
            mDataSet.remove(mDataSet.size() - 1);
        }
        mDataSet.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = mDataSet.indexOf(item);
        mDataSet.remove(position);
        if (mDataSet.size() < maxSize) {
            mDataSet.add(null);
        }
        notifyItemRemoved(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == CONTENT_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.autotest_result_row, parent, false);
            viewHolder = new ContentItemViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.autotest_result_header, parent, false);
            viewHolder = new HeaderItemViewHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContentItemViewHolder) {
            ContentItemViewHolder viewHolder = (ContentItemViewHolder) holder;
            AutotestResultModel autotestResultModel = mDataSet.get(position);
            if (autotestResultModel != null) {
                viewHolder.testNumber.setText(String.valueOf(autotestResultModel.getTestNumber()));
                viewHolder.energy.setText(autotestResultModel.getEnergyResult());
                viewHolder.energy.setOnClickListener(this);
                viewHolder.energy.setBackgroundResource(AutoTestExecutor.getMainCellResource(autotestResultModel.isEnergyTotalResult()));
                viewHolder.gas.setText(autotestResultModel.getGasResult());
                viewHolder.gas.setOnClickListener(this);
                viewHolder.gas.setBackgroundResource(AutoTestExecutor.getMainCellResource(autotestResultModel.isGasTotalResult()));
                viewHolder.adsl.setText(autotestResultModel.getAdslResult());
                viewHolder.adsl.setOnClickListener(this);
                viewHolder.adsl.setBackgroundResource(AutoTestExecutor.getMainCellResource(autotestResultModel.isAdslTotalResult()));
                viewHolder.voce.setText(autotestResultModel.getVoceResult());
                viewHolder.voce.setOnClickListener(this);
                viewHolder.voce.setBackgroundResource(AutoTestExecutor.getMainCellResource(autotestResultModel.isVoceTotalResult()));
                viewHolder.device.setText(autotestResultModel.getDeviceResult());
                viewHolder.device.setBackgroundResource(AutoTestExecutor.getMainCellResource(autotestResultModel.isDeviceTotalResult()));
                viewHolder.device.setOnClickListener(this);
                viewHolder.total.setText(autotestResultModel.getTotalResult());
                viewHolder.total.setBackgroundResource(AutoTestExecutor.getMainCellResource(autotestResultModel.isTotalResult()));
                viewHolder.total.setOnClickListener(this);
                viewHolder.resultOfferId.setText(String.valueOf(position));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADER : CONTENT_ITEM;
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public void onClick(View v) {
        TextView contentType = (TextView) v;
        TextView itemIndex = (TextView) ((View) v.getParent()).findViewById(R.id.resultItemIndex);
        if (!contentType.getText().equals(AutotestResultModel.ABSENT)) {
            onCellClickListener.onCellClick(contentType.getContentDescription().toString().trim(),
                    mDataSet.get(Integer.valueOf(itemIndex.getText().toString().trim())));
        }
    }

    public class ContentItemViewHolder extends RecyclerView.ViewHolder {

        public TextView testNumber;
        public Button energy;
        public Button gas;
        public Button voce;
        public Button adsl;
        public Button device;
        public Button total;
        public Button resultOfferId;

        public ContentItemViewHolder(View itemView) {
            super(itemView);
            testNumber = (TextView) itemView.findViewById(R.id.testNum);
            energy = (Button) itemView.findViewById(R.id.energy);
            gas = (Button) itemView.findViewById(R.id.gas);
            voce = (Button) itemView.findViewById(R.id.voce);
            adsl = (Button) itemView.findViewById(R.id.adsl);
            device = (Button) itemView.findViewById(R.id.device);
            total = (Button) itemView.findViewById(R.id.total);
            resultOfferId = (Button) itemView.findViewById(R.id.resultItemIndex);
        }
    }

    public class HeaderItemViewHolder extends RecyclerView.ViewHolder {

        public TextView energy;
        public TextView gas;
        public TextView voce;
        public TextView adsl;
        public TextView device;
        public TextView total;

        public HeaderItemViewHolder(View itemView) {
            super(itemView);
            energy = (TextView) itemView.findViewById(R.id.energy);
            gas = (TextView) itemView.findViewById(R.id.gas);
            voce = (TextView) itemView.findViewById(R.id.voce);
            adsl = (TextView) itemView.findViewById(R.id.adsl);
            device = (TextView) itemView.findViewById(R.id.device);
            total = (TextView) itemView.findViewById(R.id.total);
        }
    }

    public OnCellClickListener getOnCellClickListener() {
        return onCellClickListener;
    }

    public void setOnCellClickListener(OnCellClickListener onCellClickListener) {
        this.onCellClickListener = onCellClickListener;
    }
}
