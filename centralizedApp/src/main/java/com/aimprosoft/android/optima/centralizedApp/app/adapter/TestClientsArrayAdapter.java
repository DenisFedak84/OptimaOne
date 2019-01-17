package com.aimprosoft.android.optima.centralizedApp.app.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ClientsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Clients;

import java.util.ArrayList;
import java.util.List;

public class TestClientsArrayAdapter extends ArrayAdapter<Clients> implements Filterable {
    private List<Clients> mClientsShown;
    private List<Clients> mAllClients;
    private int currentViewId;
    private Context context;


    public TestClientsArrayAdapter(Context context, int resource, int currentViewId, List<Clients> objects) {
        super(context, resource, objects);
        mAllClients = new ArrayList<>(objects);
        this.currentViewId = currentViewId;
        this.context = context;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint != null) {
                    List<Clients> result = new ArrayList<>();
                    FilterResults endResult = new FilterResults();
                    if (mClientsShown == null) {
                        mClientsShown = new ArrayList<>(mAllClients);
                    } else {
                        String columnName = currentViewId == R.id.ragioneSocialeAutocomplete ? Clients.NAME : Clients.PIVA;
                        result = new ClientsDAOImpl().getClientsListWhereNameStartWith(columnName, constraint.toString());
                    }
                    endResult.values = result;
                    endResult.count = result.size();
                    return endResult;
                }
                return new FilterResults();
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                ArrayList<Clients> newValues = (ArrayList<Clients>) results.values;
                if (newValues != null) {
                    addAll(newValues);
                    if (results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            }

//            @Override
//            public CharSequence convertResultToString(Object resultValue) {
//                Clients client = (Clients) resultValue;
//                return client.toString(currentViewId, client);
//            }
        };
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (parent != null) {
//            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            TextView textView = (TextView) layoutInflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
//            Clients clients = (Clients) ((ListView) parent).getItemAtPosition(position);
//            textView.setText(clients.toString(currentViewId, clients));
//            return textView;
//        }
//        return super.getView(position, convertView, parent);
//    }
}
