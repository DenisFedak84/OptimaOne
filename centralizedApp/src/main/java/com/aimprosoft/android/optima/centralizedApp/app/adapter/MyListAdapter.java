package com.aimprosoft.android.optima.centralizedApp.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;

import java.text.DecimalFormat;
import java.util.List;

public class MyListAdapter extends ArrayAdapter<List<Object[]>> {

    private final Context context;
    private final List<Object[]> items;


    public MyListAdapter(Context context, List items) {
        super(context, R.layout.list, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.list, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            if (row != null) {
                viewHolder.name = (TextView) row.findViewById(R.id.name);
                viewHolder.comune = (TextView) row.findViewById(R.id.comune);
                viewHolder.piva = (TextView) row.findViewById(R.id.piva);
                viewHolder.code = (TextView) row.findViewById(R.id.code);
                viewHolder.ni = (TextView) row.findViewById(R.id.ni);
                viewHolder.canone = (TextView) row.findViewById(R.id.canone);
                viewHolder.dateDiCreazione = (TextView) row.findViewById(R.id.dateDiCreazione);
                row.setTag(viewHolder);
            }
        } else {
            row = convertView;
        }

        assert row != null;
        ViewHolder holder = (ViewHolder) row.getTag();
        Object[] listItems = items.get(position);

        String imp = listItems[5].toString();
        double cost = Double.valueOf(imp);

        if (listItems != null) {

            int type = Integer.parseInt(listItems[8].toString());
            if (type == Constants.BUSINESS_CONFIGURATOR) {
                holder.name.setText(listItems[0].toString());
                holder.comune.setText(listItems[1].toString());
                holder.piva.setText(listItems[2].toString());
                holder.code.setText("Id: " + listItems[3].toString());
                holder.ni.setText("NI: " + listItems[4].toString());
                holder.canone.setText(new DecimalFormat("#####0.00").format(cost));
                holder.dateDiCreazione.setText(listItems[6].toString());
            } else {
                holder.name.setText(listItems[9].toString() + " " + listItems[10].toString());
                holder.comune.setText(listItems[1].toString());
                holder.piva.setText(listItems[11].toString());
                holder.code.setText("Id: " + listItems[3].toString());
                holder.ni.setText("NI: " + listItems[4].toString());
                holder.canone.setText(new DecimalFormat("#####0.00").format(cost));
                holder.dateDiCreazione.setText(listItems[6].toString());
            }
        }
        return row;
    }

    private static class ViewHolder {
        TextView name;
        TextView comune;
        TextView piva;
        TextView code;
        TextView ni;
        TextView canone;
        TextView dateDiCreazione;
    }
}

