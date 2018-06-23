package com.example.neruppuda.lab4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Neruppuda on 3/31/2018.
 */

public class CustomCategoryListAdaptor extends ArrayAdapter<String>{
    private final Activity context;
    private final String[] categoryNames;

    public CustomCategoryListAdaptor(Activity context, String[] categoryNames) {
        super(context, R.layout.category_list, categoryNames);
        this.context=context;
        this.categoryNames=categoryNames;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.category_list, null,true);
        rowView.setBackgroundColor(Color.parseColor("#707070"));
        rowView.setMinimumHeight(180);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.category_item);
        txtTitle.setText(categoryNames[position]);
        return rowView;

    };
}
