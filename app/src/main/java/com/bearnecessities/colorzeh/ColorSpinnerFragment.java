package com.bearnecessities.colorzeh;

import android.graphics.ColorMatrix;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;


/**
 * Created by Charlene on 12/2/2014.
 */
public class ColorSpinnerFragment extends Fragment {
    private ArrayAdapter<String> adapter;
    int fragNum; // 1-4
    int currSelectedColor;
    Spinner categorySpinner;
    String[] colors_array, color_values_array;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.color_spinner_fragment, container, false);

        colors_array =  getResources().getStringArray(R.array.colors_array);
        color_values_array =getResources().getStringArray(R.array.color_values_array);
        categorySpinner = (Spinner) v.findViewById(R.id.colorsSpinner);

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_spinner, colors_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);



        // To know which color spinner it is (1-4)
        Bundle bundle = this.getArguments();
        if(bundle!= null) {
            fragNum = bundle.getInt("SPINNER_NUM");
        }

        SharedPreferences sharedpreferences = getActivity().getSharedPreferences(LockScreen.MY_PREFERENCES, Context.MODE_PRIVATE);
        currSelectedColor = sharedpreferences.getInt("COLOR_"+fragNum, -1);

        // Nothing's been selected; default color
        if(currSelectedColor==-1) {
            currSelectedColor = fragNum-1;
            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putInt("COLOR_" + fragNum, currSelectedColor);
            editor.commit();
        }

        categorySpinner.setSelection(currSelectedColor);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long i) {
                Log.v("CSF", "pos = " + pos);
                currSelectedColor = pos;

                // when a category is selected, display its color in the spinner
                Log.v("ColorSpinnerFragment", colors_array[pos] + "=" + color_values_array[pos]);

                int c = Color.parseColor(color_values_array[pos]);

                TextView colorView = (TextView) parent.getChildAt(0);
                colorView.setBackgroundColor(c);
                colorView.setTextColor(c+ 10000);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.v("onNothingSelected", "nothing!!!");
                currSelectedColor = fragNum-1;
            }
        });

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("COLOR_CHOSEN", currSelectedColor);
    }

    public int getColor() {
        Log.v("CurrSelectedColor", " = " + currSelectedColor);
        return this.currSelectedColor;
    }

}