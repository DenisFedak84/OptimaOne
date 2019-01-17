package com.aimprosoft.android.optima.centralizedApp.app.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.aimprosoft.android.optima.centralizedApp.db.entity.Town;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteTownAdapter extends ArrayAdapter<Town> implements Filterable, MemorySafeAdapter<Town> {

    private List<Town> mTownsShown;
    private List<Town> mAllTowns;

    public AutoCompleteTownAdapter(Context context, int textViewResourceId, List<Town> objects) {
        super(context, textViewResourceId, objects);
        mAllTowns = new ArrayList<>(objects);
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Town> result = new ArrayList<>();
                FilterResults r = new FilterResults();
                if (constraint != null) {
                    if (mTownsShown == null) {
                        mTownsShown = new ArrayList<>(mAllTowns);
                    } else {
                        result = new ArrayList<>();
                        for (Town mTownShown : mTownsShown)
                            if (constraint.length() > 0
                                    && mTownShown.getTownDesc().toLowerCase()
                                    .startsWith(constraint.toString().toLowerCase()))
                                result.add(mTownShown);
                    }
                    r.values = result;
                    r.count = result.size();
                    return r;
                }
                return new FilterResults();
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                ArrayList<Town> newValues = (ArrayList<Town>) results.values;
                if (newValues != null) {
                    for (Town newValue : newValues) {
                        add(newValue);
                    }
                    if (results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                Town town = (Town) resultValue;
                return town.getTownDesc() + " (" + town.getProvinceDesc() + ")";
            }
        };
    }

    public void clearAdapter() {
        mAllTowns.clear();
        if (mTownsShown != null) {
            mTownsShown.clear();
        }
        this.clear();
    }

    @Override
    public void notifyDataSetUpdated(List<Town> list) {
        mAllTowns.addAll(list);
        if (mTownsShown != null) {
            mTownsShown.addAll(list);
        }
    }

}




