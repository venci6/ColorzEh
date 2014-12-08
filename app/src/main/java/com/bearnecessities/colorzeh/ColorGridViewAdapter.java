package com.bearnecessities.colorzeh;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * Created by Charlene on 12/3/2014.
 */
public class ColorGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<ColorGridCell> mItems;
    private Pattern pat;

    public ColorGridViewAdapter(Context context, List<ColorGridCell> items, Pattern pat) {
        this.mContext = context;
        this.mItems = items;
        this.pat = pat;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(mContext);

            convertView = inflater.inflate(R.layout.grid_cell, parent, false);
        }

        TextView view = (TextView) convertView.findViewById(android.R.id.text1);


        //Log.v("getView", "x="+mItems.get(position).x + "y="+mItems.get(position).y);

        String color = pat.getColorAtPosition(mItems.get(position).x, mItems.get(position).y);
        //Log.v("getView", "color="+color);

        setBtnColor(view, color);
            //view.setBackgroundColor(parent.getResources().getColor(R.color.gray));




        return convertView;
    }

    private void setBtnColor(View v, String color ) {


        if(color.equals("RED")) {
            v.setBackgroundColor(Color.parseColor(NxNLockScreen.colors[NxNLockScreen.c1]));
        } else if(color.equals("BLUE")) {
            v.setBackgroundColor(Color.parseColor(NxNLockScreen.colors[NxNLockScreen.c2]));
        } else if(color.equals("GREEN")) {
            v.setBackgroundColor(Color.parseColor(NxNLockScreen.colors[NxNLockScreen.c3]));
        } else  {
            v.setBackgroundColor(Color.parseColor(NxNLockScreen.colors[NxNLockScreen.c4]));
        }
    }
}

