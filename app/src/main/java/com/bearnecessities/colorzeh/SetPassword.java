package com.bearnecessities.colorzeh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;

/**
 * Created by Charlene on 11/20/2014.
 */
public class SetPassword extends Activity implements View.OnClickListener{

    private final String TAG = SetPassword.class.getSimpleName();

    // a default password
    private static final String[] password = {"RGBYR" , "000102", "4110"};
    Pattern pat = new Pattern(Pattern.ORDER, 3, password, System.currentTimeMillis());

    Spinner seqOrder, locOrder, quantOrder;
    int seqNum, locNum, quantNum;

    CheckBox seqBox, locBox, quantBox;
    boolean seqChecked, locChecked, quantChecked;

    Button seqSet, locSet, quantSet, finish, verify;
    private ImageButton tl, tm, tr, ml, mm, mr, bl, bm, br;

    String seqPwd ="", locPwd="";
    int[] quantPwd={0,0,0,0};

    // pattern modes in the order user sets ex[ODER, POSITION, ]
    String[] mode = {"","",""};

    // ex["RRR", "0000", 0000]
    String[] entirePassword = {"","",""};

    // ex[ORDER_QUANTITY_POSITION]
    String entireMode = "";

    // -1 - NOTHING
    // SETTING PW: 0 - ORDER, 1 - POSITION, 2 - QUANTITY
    // VERIFYING PW:
    int passwordMode = -1;

    // for pattern combinations
    int numModes = 0;

    int c1, c2, c3, c4;
    String[] colors;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_password);

        initializeViews();
        updateColorGrid();
    }

    private void initializeViews() {

        sharedPreferences = getSharedPreferences(LockScreen.MY_PREFERENCES, Context.MODE_PRIVATE);
        colors = getResources().getStringArray(R.array.color_values_array);

        c1 = sharedPreferences.getInt("COLOR_1", 0);
        c2 = sharedPreferences.getInt("COLOR_2", 1);
        c3 = sharedPreferences.getInt("COLOR_3", 2);
        c4 = sharedPreferences.getInt("COLOR_4", 3);

        seqOrder = (Spinner) findViewById(R.id.sequenceOrder);
        locOrder = (Spinner) findViewById(R.id.locationOrder);
        quantOrder = (Spinner) findViewById(R.id.quantityOrder);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.patterns_array, R.layout.custom_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        seqOrder.setAdapter(adapter);
        seqOrder.setSelection(0);
        seqOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                seqNum = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                Log.v(TAG, "SELECTED FOR SEQ ORDER " + seqNum);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                seqNum = 0;
            }
        });


        locOrder.setAdapter(adapter);
        locOrder.setSelection(1);
        locOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                locNum = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                Log.v(TAG, "SELECTED FOR LOC ORDER " + locNum);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                locNum = 0;
            }
        });

        quantOrder.setAdapter(adapter);
        quantOrder.setSelection(2);
        quantOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                quantNum = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                Log.v(TAG, "SELECTED FOR QUANT ORDER " + quantNum);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                quantNum = 0;
            }
        });

        seqBox = (CheckBox) findViewById(R.id.sequenceChecked);
        locBox = (CheckBox) findViewById(R.id.locationChecked);
        quantBox = (CheckBox) findViewById(R.id.quantityChecked);

        seqSet = (Button) findViewById(R.id.sequenceSetBtn);
        locSet = (Button) findViewById(R.id.locationSetBtn);
        quantSet = (Button) findViewById(R.id.quantitySetBtn);
        finish = (Button) findViewById(R.id.finish);
        verify = (Button) findViewById(R.id.verify);

        tl = (ImageButton)  findViewById(R.id.top_left_set);
        tm = (ImageButton)  findViewById(R.id.top_mid_set);
        tr = (ImageButton)  findViewById(R.id.top_right_set);
        ml = (ImageButton)  findViewById(R.id.mid_left_set);
        mm = (ImageButton)  findViewById(R.id.mid_mid_set);
        mr = (ImageButton)  findViewById(R.id.mid_right_set);
        bl = (ImageButton)  findViewById(R.id.bot_left_set);
        bm = (ImageButton)  findViewById(R.id.bot_mid_set);
        br = (ImageButton)  findViewById(R.id.bot_right_set);

        tl.setOnClickListener(this);
        tm.setOnClickListener(this);
        tr.setOnClickListener(this);
        ml.setOnClickListener(this);
        mm.setOnClickListener(this);
        mr.setOnClickListener(this);
        bl.setOnClickListener(this);
        bm.setOnClickListener(this);
        br.setOnClickListener(this);

        seqSet.setOnClickListener(this);
        locSet.setOnClickListener(this);
        quantSet.setOnClickListener(this);
        finish.setOnClickListener(this);
        verify.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(isGridButton(id)) {
            int locationX = 0, locationY = 0;
            String color;
            char c;

            switch(id) {
                case R.id.top_left_set:
                    locationX = 0;
                    locationY = 0;
                    break;
                case R.id.top_mid_set:
                    locationX = 1;
                    locationY = 0;
                    break;
                case R.id.top_right_set:
                    locationX = 2;
                    locationY = 0;
                    break;
                case R.id.mid_left_set:
                    locationX = 0;
                    locationY = 1;
                    break;
                case R.id.mid_mid_set:
                    locationX = 1;
                    locationY = 1;
                    break;
                case R.id.mid_right_set:
                    locationX = 2;
                    locationY = 1;
                    break;
                case R.id.bot_left_set:
                    locationX = 0;
                    locationY = 2;
                    break;
                case R.id.bot_mid_set:
                    locationX = 1;
                    locationY = 2;
                    break;
                case R.id.bot_right_set:
                    locationX = 2;
                    locationY = 2;
                    break;
            }
            color = pat.getColorAtPosition(locationX,locationY);
            Log.v(TAG, " clicked "+ color);
            c = color.charAt(0);

            switch(passwordMode) {
                case 0:
                    seqPwd += "" + c;
                    pat.updatePattern();
                    updateColorGrid();
                    break;
                case 1:
                    locPwd += "" + locationX + "" + locationY;
                    pat.updatePattern();
                    updateColorGrid();
                    break;
                case 2:
                    if (c == 'R') {
                        quantPwd[0]++;
                    } else if (c == 'B') {
                        quantPwd[1]++;
                    } else if (c == 'Y') {
                        quantPwd[2]++;
                    } else {
                        quantPwd[3]++;
                    }
                    pat.updatePattern();
                    updateColorGrid();
                    break;
                case 3:
                    // verifying the password
                    boolean unlock = pat.input(locationX, locationY);
                    if (unlock) {
                        Toast.makeText(this, "Password confirmed!", Toast.LENGTH_SHORT).show();
                        finish.setEnabled(true);
                        verify.setText("Verify");
                        enableCheckedButtons();
                        passwordMode = -1;
                    } else {
                        pat.updatePattern();
                        updateColorGrid();
                    }
                    break;

                default:
                    Log.v(TAG, "Grid button pressed but not setting anything");
                    break;
            }
        } else {
            switch (id) {
                case R.id.sequenceSetBtn:
                    if (seqSet.getText().toString().equalsIgnoreCase("Set")) {
                        if(passwordMode == -1) {
                            seqSet.setText("Finish");
                            passwordMode = 0;
                            seqPwd = "";

                            enterSettingPasswordMode();
                        } else {
                            Toast.makeText(this, "Please finish setting a password before trying to set another one", Toast.LENGTH_SHORT).show();
                        }
                    } else if (seqSet.getText().toString().equalsIgnoreCase("Finish")) {
                        seqSet.setText("Set");
                        leaveSettingPasswordMode();
                    }
                    break;

                case R.id.locationSetBtn:
                    if (locSet.getText().toString().equalsIgnoreCase("Set")) {
                        if(passwordMode == -1) {
                            locSet.setText("Finish");
                            passwordMode = 1;
                            locPwd = "";

                            enterSettingPasswordMode();
                        } else {
                            Toast.makeText(this, "Please finish setting a password before trying to set another one", Toast.LENGTH_SHORT).show();
                        }
                    } else if (locSet.getText().toString().equalsIgnoreCase("Finish")) {
                        locSet.setText("Set");
                        leaveSettingPasswordMode();
                    }
                    break;

                case R.id.quantitySetBtn:
                    if (quantSet.getText().toString().equalsIgnoreCase("Set")) {
                        if(passwordMode == -1) {
                            quantSet.setText("Finish");
                            passwordMode = 2;
                            quantPwd[0] = 0;
                            quantPwd[1] = 0;
                            quantPwd[2] = 0;
                            quantPwd[3] = 0;

                            enterSettingPasswordMode();
                        }else {
                            Toast.makeText(this, "Please finish setting a password before trying to set another one", Toast.LENGTH_SHORT).show();
                        }
                    } else if (quantSet.getText().toString().equalsIgnoreCase("Finish")) {
                        quantSet.setText("Set");
                        leaveSettingPasswordMode();
                    }
                    break;
                case R.id.verify:
                    if(verify.getText().toString().equalsIgnoreCase("Stop")) {
                        passwordMode = -1;
                        enableCheckedButtons();
                        verify.setText("Verify");
                    } else {
                        if (checkSameNumbers()) {
                            Toast.makeText(this, "You cannot have two patterns have the same order number!", Toast.LENGTH_SHORT).show();
                        } else {
                            setUpVerification();

                        }
                    }
                    break;
                case R.id.finish:
                    setPasswords();
                    Settings.needReset = false;
                    //start lock service
                    startService(new Intent(getBaseContext(), LockService.class));
                    this.finish();
                    break;
            }
        }
    }

    /**
     *  User is currently setting a pattern
     *  Cannot click on any CheckBoxes, change Spinners, or click Verify/Finish
     */
    private void enterSettingPasswordMode() {
        disableCheckSpins();
        verify.setEnabled(false);
        finish.setEnabled(false);
    }

    /**
     *  User finished setting a pattern
     *  Can now click on any CheckBoxes, change Spinners, and potentially Verify
     */
    private void leaveSettingPasswordMode() {
        passwordMode = -1;
        enableCheckSpins();
        checkEnableVerify();
    }

    /**
     *  Can only enable Verify button if ALL patterns that are checked have a set pattern
     */
    private void checkEnableVerify() {
        Log.v(TAG, seqChecked + " seq = " + seqPwd + ", " +seqChecked + "loc = "
                + locPwd +", quant = " + Arrays.toString(quantPwd));

        if( (seqPwd.equals("") && seqChecked) || (locPwd.equals("") && locChecked) ||
                (quantChecked && quantPwd[0]==0&&quantPwd[1]==0&&quantPwd[2]==0&&quantPwd[3]==0)) {
            verify.setEnabled(false);
            Log.v(TAG, "One of the patterns is not set.");
        } else verify.setEnabled(true);
    }

    /**
     * Disable ALL Pattern Set buttons, CheckBoxes, and Spinners
     */
    private void disableButtons() {
        seqSet.setEnabled(false);
        locSet.setEnabled(false);
        quantSet.setEnabled(false);

        disableCheckSpins();
    }

    /**
     * Disable ALL CheckBoxes and Spinners
     */
    private void disableCheckSpins() {
        seqOrder.setEnabled(false);
        quantOrder.setEnabled(false);
        locOrder.setEnabled(false);

        seqBox.setEnabled(false);
        locBox.setEnabled(false);
        quantBox.setEnabled(false);
    }
    /**
     * Enable Set buttons for only the patterns that are checked and ALL CheckBoxes and Spinners
     */
    private void enableCheckedButtons() {
        if(seqBox.isChecked()) {
            seqSet.setEnabled(true);
        }

        if(locBox.isChecked()) {
            locSet.setEnabled(true);
        }

        if(quantBox.isChecked()) {
            quantSet.setEnabled(true);
        }

        enableCheckSpins();
    }

    /**
     * Enable ALL CheckBoxes and Spinners
     */
    private void enableCheckSpins() {
        seqBox.setEnabled(true);
        locBox.setEnabled(true);
        quantBox.setEnabled(true);

        seqOrder.setEnabled(true);
        quantOrder.setEnabled(true);
        locOrder.setEnabled(true);
    }

    /**
     * Don't allow user to click on anything besides the grid when verifying password
     * Create the password pattern order and password
     * Recreate new Pattern and updates grid
     */
    private void setUpVerification() {
        Toast.makeText(this, "Please verify that you know your password!", Toast.LENGTH_SHORT).show();
        disableButtons();
        verify.setText("Stop");

        composePasswordOrder();
        composePassword();

        passwordMode = 3;

        Log.v(TAG, "entire mode = " + entireMode + ", entirePassword = "+Arrays.toString(entirePassword));
        pat = new Pattern(entireMode, 3, entirePassword,System.currentTimeMillis());

        updateColorGrid();
    }

    /**
     * Creates Password Array
     */
    private void composePassword() {
        entirePassword[0] = seqPwd;
        entirePassword[1] = locPwd;
        entirePassword[2] = quantPwd[0] + "" + quantPwd[1] + quantPwd[2] + quantPwd[3];
    }

    /**
     * Creates Password Order String
     */
    private void composePasswordOrder () {
        switch(numModes) {
            case 1:
                if (seqChecked) {
                    entireMode = "ORDER";
                } else if (locChecked) {
                    entireMode = "POSITION";
                } else { // quantChecked
                    entireMode = "QUANTITY";
                }
                break;

            case 2:
                mode[0] = "";
                mode[1] = "";
                mode[2] = "";

                if (!seqChecked) {
                    mode[locNum - 1] = "POSITION";
                    mode[quantNum - 1] = "QUANTITY";
                } else if (!locChecked) {
                    mode[seqNum - 1] = "ORDER";
                    mode[quantNum - 1] = "QUANTITY";
                } else { //!quantChecked
                    mode[seqNum - 1] = "ORDER";
                    mode[locNum - 1] = "POSITION";
                }

                entireMode="";

                for (int i = 0; i < 3; i++) {
                    if(!mode[i].equals("")) {
                        entireMode += "_" +  mode[i];
                    }
                }
                entireMode = entireMode.substring(1);
                break;
            case 3:
                mode[seqNum - 1] = "ORDER";
                mode[locNum - 1] = "POSITION";
                mode[quantNum - 1] = "QUANTITY";

                entireMode = mode[0] + "_" + mode[1] + "_" + mode[2];
                break;
        }

        Log.v(TAG, "PASSWORD MODE = " + entireMode);

    }

    /**
     *
     * @return if any of the checked patterns are the same number
     */
    private boolean checkSameNumbers() {
        boolean sameNumbers = false;

        if(seqChecked && locChecked && seqNum==locNum) {
            sameNumbers = true;
        }
        if(seqChecked && quantChecked && seqNum == quantNum) {
            sameNumbers = true;
        }
        if(locChecked && quantChecked && locNum == quantNum) {
            sameNumbers = true;
        }
        return sameNumbers;

    }

    /**
     * Enters password into SharedPreferences
     */
    private void setPasswords() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("patternKey", entireMode);
        editor.putString("passwordKey", entirePassword[0] +"/"+ entirePassword[1] +"/"+ entirePassword[2]);
        editor.commit();
    }

    public void onCheckboxClicked(View v) {

        boolean isChecked = ((CheckBox) v).isChecked();

        switch(v.getId()) {
            case R.id.sequenceChecked:
                seqChecked = isChecked;
                seqSet.setEnabled(seqChecked);
                break;
            case R.id.locationChecked:
                locChecked = isChecked;
                locSet.setEnabled(locChecked);
                break;
            case R.id.quantityChecked:
                quantChecked = isChecked;
                quantSet.setEnabled(quantChecked);
                break;
        }

        if(isChecked) {
            numModes++;
        } else numModes--;

        if(numModes == 0) {
            verify.setEnabled(false);
        } else if (numModes > 0) {
            checkEnableVerify();
        }

        finish.setEnabled(false);
    }

    private boolean isGridButton(int id) {
        boolean bool;
        switch(id) {
            case R.id.top_left_set:
            case R.id.top_mid_set:
            case R.id.top_right_set:
            case R.id.mid_left_set:
            case R.id.mid_mid_set:
            case R.id.mid_right_set:
            case R.id.bot_left_set:
            case R.id.bot_mid_set:
            case R.id.bot_right_set:
                bool = true;
                break;
            default:
                bool = false;
                break;
        }
        return bool;
    }

    private void updateColorGrid() {
        setBtnColor(tl, pat.getColorAtPosition(0,0));
        setBtnColor(tm, pat.getColorAtPosition(1,0));
        setBtnColor(tr, pat.getColorAtPosition(2,0));

        setBtnColor(ml, pat.getColorAtPosition(0,1));
        setBtnColor(mm, pat.getColorAtPosition(1,1));
        setBtnColor(mr, pat.getColorAtPosition(2,1));

        setBtnColor(bl, pat.getColorAtPosition(0,2));
        setBtnColor(bm, pat.getColorAtPosition(1,2));
        setBtnColor(br, pat.getColorAtPosition(2,2));
    }

    private void setBtnColor(ImageButton btn, String color ) {

        if(color.equals("RED")) {
            btn.setBackgroundColor(Color.parseColor(colors[c1]));
        } else if(color.equals("BLUE")) {
            btn.setBackgroundColor(Color.parseColor(colors[c2]));
        } else if(color.equals("GREEN")) {
            btn.setBackgroundColor(Color.parseColor(colors[c3]));
        } else if(color.equals("YELLOW")) {
            btn.setBackgroundColor(Color.parseColor(colors[c4]));
        } else {
            btn.setBackgroundColor(getResources().getColor(R.color.White));
        }
    }

}
