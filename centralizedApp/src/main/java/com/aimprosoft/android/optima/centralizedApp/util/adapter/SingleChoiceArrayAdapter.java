package com.aimprosoft.android.optima.centralizedApp.util.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import java.util.List;

public class SingleChoiceArrayAdapter<T> extends ArrayAdapter<T> {

    private Context context;

    public SingleChoiceArrayAdapter(final Context context, final List<T> objects) {
        super(context, android.R.layout.simple_list_item_single_choice, objects);
        this.context = context;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final CheckedTextView view = (CheckedTextView) super.getView(position, convertView, parent);
        assert view != null;
        view.setTextColor(context.getResources().getColor(android.R.color.background_dark));
        return view;
    }
}
