package com.aimprosoft.android.optima.centralizedApp.event;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.activity.optilife.BaseActivity;
import com.aimprosoft.android.optima.centralizedApp.activity.optilife.Canone;

public class SelectWhatToShowDialog extends BaseEvent implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup radioGroup;
    private int choicecode;

    public SelectWhatToShowDialog(BaseActivity activity) {
        this.activity = activity;
        showDialog();
    }

    private void showDialog() {
        if(!isActivityAlive()){
            return;
        }
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.what_offer_to_show_dialog);

        radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);

        dialog.findViewById(R.id.continueBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Canone.class);
                intent.putExtra("ChoiceCode", choicecode);
                activity.startActivity(intent);
            }
        });
        dialog.show();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        choicecode = group.indexOfChild(group.findViewById(checkedId));
    }
}
