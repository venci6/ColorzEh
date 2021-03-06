package com.bearnecessities.colorzeh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Charlene on 11/26/2014.
 */
public class Settings extends FragmentActivity implements View.OnClickListener{
    Button setPassword, setColors, setSize, confirm;
    int color1, color2, color3, color4;
    ColorSpinnerFragment csf1, csf2, csf3, csf4;
    Spinner NXNspinner;
    int n;
    static boolean needReset = false;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        sharedPreferences = getSharedPreferences(LockScreen.MY_PREFERENCES, Context.MODE_PRIVATE);

        setSize = (Button) findViewById(R.id.set_grid_size);
        setSize.setOnClickListener(this);

        NXNspinner = (Spinner) findViewById(R.id.nxnPicker);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.NXN_array, R.layout.custom_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NXNspinner.setAdapter(adapter);
        n = sharedPreferences.getInt("GRID_SIZE", 0);
        NXNspinner.setSelection(adapter.getPosition(""+n));
        NXNspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                n =  Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                setSize.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                n = 3;
            }
        });

        setPassword = (Button) findViewById(R.id.set_pwd);
        setPassword.setOnClickListener(this);

        setColors = (Button) findViewById(R.id.set_colors);
        setColors.setOnClickListener(this);

        confirm = (Button) findViewById(R.id.confirm_button);
        confirm.setOnClickListener(this);

        // Spinner 1
        if(findViewById(R.id.colorSpinner1)!=null) {
            if(savedInstanceState != null) {
                return;
            }

            Bundle b1 = new Bundle();
            b1.putInt("SPINNER_NUM",1);

            csf1 = new ColorSpinnerFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();

            csf1.setArguments(b1);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.add(R.id.colorSpinner1, csf1, "c1_frag");
            fragmentTransaction.commit();
        }

        // Spinner 2
        if(findViewById(R.id.colorSpinner1)!=null) {
            if(savedInstanceState != null) {
                return;
            }

            Bundle b2 = new Bundle();
            b2.putInt("SPINNER_NUM",2);

            csf2 = new ColorSpinnerFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();

            csf2.setArguments(b2);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.add(R.id.colorSpinner2, csf2, "c2_frag");
            fragmentTransaction.commit();
        }


        // Spinner 3
        if(findViewById(R.id.colorSpinner1)!=null) {
            if(savedInstanceState != null) {
                return;
            }

            Bundle b3 = new Bundle();
            b3.putInt("SPINNER_NUM",3);

            csf3 = new ColorSpinnerFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();

            csf3.setArguments(b3);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.add(R.id.colorSpinner3, csf3, "c3_frag");
            fragmentTransaction.commit();
        }

        // Spinner 4
        if(findViewById(R.id.colorSpinner4)!=null) {
            if(savedInstanceState != null) {
                return;
            }

            Bundle b4 = new Bundle();
            b4.putInt("SPINNER_NUM",4);

            csf4 = new ColorSpinnerFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();

            csf4.setArguments(b4);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.add(R.id.colorSpinner4, csf4, "c4_frag");
            fragmentTransaction.commit();
        }

    }

    @Override
    public void onBackPressed() {
        if(needReset) {
            Toast.makeText(this, "Please change your password!", Toast.LENGTH_LONG).show();
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.set_pwd:
                Intent setPwd = new Intent(Settings.this, SetNxNPassword.class);
                startActivity(setPwd);
                break;
            case R.id.set_colors:
                if(noDuplicateColors()) {
                    SharedPreferences sharedpreferences = getSharedPreferences(LockScreen.MY_PREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    editor.putInt("COLOR_1", color1);
                    editor.putInt("COLOR_2", color2);
                    editor.putInt("COLOR_3", color3);
                    editor.putInt("COLOR_4", color4);
                    editor.commit();

                    Toast.makeText(this, "Colors successfully changed! Please change your password", Toast.LENGTH_SHORT).show();
                    needReset = true;
                } else {
                    Toast.makeText(this, "You cannot select a color more than once!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.set_grid_size:

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt("GRID_SIZE", n);
                editor.commit();

                Toast.makeText(this, "Grid size changed. Please change your password.", Toast.LENGTH_SHORT).show();
                needReset = true;
                break;
            case R.id.confirm_button:
                this.finish();
                break;
        }
    }
    private boolean noDuplicateColors() {
        Set<Integer> selectedColors = new HashSet<Integer>();
        //ColorSpinnerFragment c1 = (ColorSpinnerFragment)getSupportFragmentManager().findFragmentByTag("c1_frag");
        //ColorSpinnerFragment c2 = (ColorSpinnerFragment)getSupportFragmentManager().findFragmentByTag("c2_frag");
       // ColorSpinnerFragment c3 = (ColorSpinnerFragment)getSupportFragmentManager().findFragmentByTag("c3_frag");
       // ColorSpinnerFragment c4 = (ColorSpinnerFragment)getSupportFragmentManager().findFragmentByTag("c4_frag");
        color1 = csf1.getColor();
        color2 = csf2.getColor();
        color3 = csf3.getColor();
        color4 = csf4.getColor();

        selectedColors.add(color1);
        selectedColors.add(color2);
        selectedColors.add(color3);
        selectedColors.add(color4);
        Log.v("Settings", selectedColors.toString() + " = " + color1 + "," + color2 +","+color3+","+color4);

        if(selectedColors.size()==4) {
            return true;
        } else return false;





    }
}
