package com.bearnecessities.colorzeh;

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

        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, colors_array);

        /*
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, colors_array) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                return view;
            }
        };
        */
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        categorySpinner.setAdapter(adapter);

        // if Editing something; need to load what was previously selected
        Bundle bundle = this.getArguments();
        if(bundle!= null) {
            fragNum = bundle.getInt("SPINNER_NUM");
        }

        SharedPreferences sharedpreferences = getActivity().getSharedPreferences(LockScreen.MY_PREFERENCES, Context.MODE_PRIVATE);
        //currSelectedColor = sharedpreferences.getInt("COLOR_"+fragNum, fragNum-1);


        currSelectedColor = sharedpreferences.getInt("COLOR_"+fragNum, -1);

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

                ((TextView) parent.getChildAt(0)).setBackgroundColor(Color.parseColor(color_values_array[pos]));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
                Log.v("onNothingSelected", "nothing!!!");
                currSelectedColor = fragNum-1;
                //categorySelected = "Default";
            }
        });
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //outState.putInt("CATEGORY_POST", posSelected);
    }

    public int getColor() {
        Log.v("CurrSelectedColor", " = " + currSelectedColor);
        return this.currSelectedColor;
    }

}