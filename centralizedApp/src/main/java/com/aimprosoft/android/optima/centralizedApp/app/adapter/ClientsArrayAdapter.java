package com.aimprosoft.android.optima.centralizedApp.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ClientsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Clients;

import java.util.ArrayList;
import java.util.List;

public class ClientsArrayAdapter extends ArrayAdapter<Clients> implements Filterable, MemorySafeAdapter<Clients> {
    private List<Clients> mClientsShown;
    private List<Clients> mAllClients;
    private int currentViewId;
    private Context context;


    public ClientsArrayAdapter(Context context, int resource, int currentViewId, List<Clients> objects) {
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

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                Clients client = (Clients) resultValue;
                return client.toString(currentViewId);
            }
        };
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;

        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.textViewItem = (TextView) convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        if (parent != null) {
            Clients clients = (Clients) ((ListView) parent).getItemAtPosition(position);
            viewHolder.textViewItem.setText(clients.toString(currentViewId));
            return convertView;
        }
        return super.getView(position, convertView, parent);
    }

    private static class ViewHolderItem {
        TextView textViewItem;
    }

    public void clearAdapter(){
        mAllClients.clear();
        if (mClientsShown != null){
            mClientsShown.clear();
        }
        this.clear();
    }

    @Override
    public void notifyDataSetUpdated(List<Clients> list) {
        mAllClients.addAll(list);
        if(mClientsShown!=null){
            mClientsShown.addAll(list);
        }
    }
}
