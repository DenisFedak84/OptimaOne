package com.aimprosoft.android.optima.centralizedApp.activity.wvoce;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.activity.optilife.BaseActivity;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.service.AbstractService;
import com.aimprosoft.android.optima.centralizedApp.service.GetOffersByPivaService;
import com.aimprosoft.android.optima.centralizedApp.service.GetOffersForJsonService;
import com.aimprosoft.android.optima.centralizedApp.util.adapter.SingleChoiceArrayAdapter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class GetOffersByPivaActivity extends BaseActivity {

    private ListView listView;
    private Offer selectedOffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_offers_by_piva_activity);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedOffer = (Offer) adapterView.getAdapter().getItem(i);
                final CheckedTextView checkedTextView = (CheckedTextView) view;
                checkedTextView.setChecked(true);
            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            Uri uri = intent.getData();
            if (uri != null) {
                String data = uri.getSchemeSpecificPart();
                if (data != null) {
                    String pivaOrCodiseFiscale = data.replace("//", "");
                    GetOffersByPivaService getOffersByPivaService = new GetOffersByPivaService(this, new AbstractService.OnTaskCompleted() {
                        @Override
                        public void onTaskCompleted(AbstractService service) {
                            List<Offer> offers = (List<Offer>) service.getResult();
                            if (offers == null || offers.size() == 0) {
                                setResult(RESULT_CANCELED);
                                finish();
                            }
                            SingleChoiceArrayAdapter<Offer> singleChoiceArrayAdapter = new SingleChoiceArrayAdapter<>(GetOffersByPivaActivity.this, (List<Offer>) service.getResult());
                            listView.setAdapter(singleChoiceArrayAdapter);
                        }
                    });
                    getOffersByPivaService.execute(pivaOrCodiseFiscale);
                }
            }
        }
    }

    public void next(View view) {
        if (selectedOffer == null) {
            Toast.makeText(this, "Please, select offer", Toast.LENGTH_SHORT).show();
            return;
        }
        GetOffersForJsonService service = new GetOffersForJsonService(this, new AbstractService.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(AbstractService service) {
                Intent bundle = new Intent();
                Map<String, Object> result = (Map<String, Object>) service.getResult();
                if (result.size() == 0) {
                    setResult(RESULT_CANCELED);
                } else {
                    Bundle mapBundle = new Bundle();
                    mapBundle.putSerializable(GetOffersForJsonService.COF_OFFERTA, (Serializable) result.get(GetOffersForJsonService.COF_OFFERTA));
                    mapBundle.putSerializable(GetOffersForJsonService.INPUT_DATA, (Serializable) result.get(GetOffersForJsonService.INPUT_DATA));
                    bundle.putExtra("data", mapBundle);
                    setResult(Activity.RESULT_OK, bundle);
                }
                finish();
            }
        });
        service.execute(selectedOffer);
    }
}