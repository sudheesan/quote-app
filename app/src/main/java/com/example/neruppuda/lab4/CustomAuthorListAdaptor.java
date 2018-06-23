package com.example.neruppuda.lab4;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Color;

/**
 * Created by Neruppuda on 3/26/2018.
 */

public class CustomAuthorListAdaptor extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;
    private final String[] category;

    public CustomAuthorListAdaptor(Activity context, String[] itemname, Integer[] imgid ,String [] category) {
        super(context, R.layout.auhor_list, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
        this.category=category;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.auhor_list, null,true);
        rowView.setBackgroundColor(Color.parseColor("#707070"));
        rowView.setMinimumHeight(180);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView categoryText = (TextView) rowView.findViewById(R.id.category);
        txtTitle.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);
        categoryText.setText(category[position]);
        return rowView;

    };
}
