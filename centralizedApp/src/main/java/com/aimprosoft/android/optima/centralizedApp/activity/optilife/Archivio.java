package com.aimprosoft.android.optima.centralizedApp.activity.optilife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.app.adapter.MyListAdapter;
import com.aimprosoft.android.optima.centralizedApp.app.savefiledialog.SaveFileDialog;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.event.AddCodiceOptilife;
import com.aimprosoft.android.optima.centralizedApp.event.DeleteChoosenOfferEvent;
import com.aimprosoft.android.optima.centralizedApp.event.EmailSendEvent;
import com.aimprosoft.android.optima.centralizedApp.service.GenerateExcelService;
import com.aimprosoft.android.optima.centralizedApp.service.GetArchivioDataServiceImpl;
import com.aimprosoft.android.optima.centralizedApp.util.ActionItem;
import com.aimprosoft.android.optima.centralizedApp.util.LocalSharedPreferencesManager;
import com.aimprosoft.android.optima.centralizedApp.util.QuickAction;

import java.util.List;

public class Archivio extends BaseActivity {
    ListView listView;
    Offer offer;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.archivio_layout);
        checkForBackButton();

        MyApplication.remove(null);
        listView = (ListView) findViewById(R.id.archivioListView);
        loadData();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Object[] item = (Object[]) parent.getAdapter().getItem(position);
            int offerId = Integer.valueOf(item[7].toString());

            OfferDAOImpl offerDAO = new OfferDAOImpl();
            offer = offerDAO.getOfferById(offerId);
            MyApplication.set(Constants.OFFER_ENTITY, offer);

            Intent intent = new Intent(Archivio.this, Canone.class);
            intent.putExtra(Constants.IS_FROM_ARCHIVIO, false);
            startActivity(intent);
//                startChildActivity(Canone.class);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Object[] item = (Object[]) parent.getAdapter().getItem(position);
            final int offerId = Integer.valueOf(item[7].toString());

            ActionItem cancella = new ActionItem(1, "Cancella", ContextCompat.getDrawable(Archivio.this, R.drawable.delete));
            ActionItem codiceOptilife = new ActionItem(2, "Modifica PIVA/CF", ContextCompat.getDrawable(Archivio.this, R.drawable.add));

            cancella.setOnActionItemListener(new DeleteChoosenOfferEvent(Archivio.this, view, offerId, ()
                    -> new GetArchivioDataServiceImpl(Archivio.this, service -> loadData()).execute()));
            codiceOptilife.setOnActionItemListener(new AddCodiceOptilife(Archivio.this, view, listView, offerId));
            final QuickAction quickAction = new QuickAction(Archivio.this, QuickAction.HORIZONTAL);

            quickAction.addActionItem(cancella);
            quickAction.addActionItem(codiceOptilife);

            quickAction.show(view);
            quickAction.setAnimStyle(QuickAction.ANIM_GROW_FROM_LEFT);

            return true;
        });
    }

    public void generateExcel(View view) {

        SaveFileDialog saveFileDialog = new SaveFileDialog(this, null, new String[]{Constants.SAVED_FILE_EXTENSION},
                f -> new GenerateExcelService(Archivio.this, service -> {
                    if (service.getResult() == null) {
                        Toast.makeText(Archivio.this, "Si è verificato un errore durante l'esportazione del file", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        new EmailSendEvent(Archivio.this, (String) service.getResult());
                    }
                    Toast.makeText(Archivio.this, "Il documento è stato salvato", Toast.LENGTH_SHORT).show();
                }).execute(f));
        saveFileDialog.show();
    }

    private void loadData() {
        new GetArchivioDataServiceImpl(this, service -> {
            MyListAdapter myListAdapter = new MyListAdapter(Archivio.this, (List) service.getResult());
            listView.setAdapter(myListAdapter);
        }).execute();
    }

    public void checkForBackButton() {
        if (MyApplication.get("ModificaPoint", Boolean.class) != null) {
            findViewById(R.id.tornaIndietroArchivio).setVisibility(View.INVISIBLE);
        } else {
            findViewById(R.id.tornaIndietroArchivio).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        LocalSharedPreferencesManager.getInstance().storeSharedPreferencesBooleanValue(this, LocalSharedPreferencesManager.IS_APP_CHECKED_FOR_UPDATE, false);
    }
}