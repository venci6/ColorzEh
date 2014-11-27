package com.bearnecessities.colorzeh;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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

    private final String TAG = LockScreen.class.getSimpleName();

    private static final String[] password = {"RGBYR" , "000102", "4110"};
    Pattern pat = new Pattern(Pattern.ORDER, password, System.currentTimeMillis());

    //EditText seqOrder, locOrder, quantOrder;
    Spinner seqOrder, locOrder, quantOrder;
    CheckBox seqBox, locBox, quantBox;
    boolean seqChecked, locChecked, quantChecked;
    Button seqSet, locSet, quantSet, finish, verify;
    String seqPwd ="", locPwd="";
    int seqNum, locNum, quantNum;
    String[] mode = {"","",""};
    String[] entirePassword = {"","",""};
    String entireMode = "";

    int[] quantPwd={0,0,0,0};

    // 0 - ORDER, 1 - POSITION, 2 - QUANTITY, -1 - NOT SET
    int passwordMode = -1;

    int numModes = 0;

    private ImageButton tl, tm, tr, ml, mm, mr, bl, bm, br;
    // constant to determine which sub-activity returns
    private static final int REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_password);

        initializeViews();
        updateColorGrid();
    }

    private void initializeViews() {
        seqOrder = (Spinner) findViewById(R.id.sequenceOrder);
        locOrder = (Spinner) findViewById(R.id.locationOrder);
        quantOrder = (Spinner) findViewById(R.id.quantityOrder);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.patterns_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        seqOrder.setAdapter(adapter);

        locOrder.setSelection(0);
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
                    Log.v(TAG, "top left was clicked. Hoorah. Hodor.");
                    break;
                case R.id.top_mid_set:
                    locationX = 1;
                    locationY = 0;
                    Log.v(TAG, "top mid was clicked. Hoorah. Hodor.");
                    break;
                case R.id.top_right_set:
                    locationX = 2;
                    locationY = 0;
                    Log.v(TAG, "top r was clicked. Hoorah. Hodor.");
                    break;
                case R.id.mid_left_set:
                    locationX = 0;
                    locationY = 1;
                    Log.v(TAG, "mid left was clicked. Hoorah. Hodor.");
                    break;
                case R.id.mid_mid_set:
                    locationX = 1;
                    locationY = 1;
                    Log.v(TAG, "mid mid was clicked. Hoorah. Hodor.");
                    break;
                case R.id.mid_right_set:
                    locationX = 2;
                    locationY = 1;
                    Log.v(TAG, "mid rogjt was clicked. Hoorah. Hodor.");
                    break;
                case R.id.bot_left_set:
                    locationX = 0;
                    locationY = 2;
                    Log.v(TAG, "bot left was clicked. Hoorah. Hodor.");
                    break;
                case R.id.bot_mid_set:
                    locationX = 1;
                    locationY = 2;
                    Log.v(TAG, "bot mid was clicked. Hoorah. Hodor.");
                    break;
                case R.id.bot_right_set:
                    locationX = 2;
                    locationY = 2;
                    Log.v(TAG, "bot rigjt was clicked. Hoorah. Hodor.");
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
                    locPwd+= "" + locationX + "" + locationY;
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
                        enableButtons();
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
            Log.v(TAG, "pw mode " + passwordMode);
            switch (id) {
                case R.id.sequenceSetBtn:
                    Log.v(TAG, "clicked on seq");

                    if (seqSet.getText().toString().equalsIgnoreCase("Set")) {
                        if(passwordMode == -1) {
                            finish.setEnabled(false);
                            seqPwd = "";
                            seqSet.setText("Finish");
                            verify.setEnabled(false);
                            passwordMode = 0;
                        } else {
                            Toast.makeText(this, "Please finish setting a password before trying to set another one", Toast.LENGTH_SHORT).show();
                        }


                    } else if (seqSet.getText().toString().equalsIgnoreCase("Finish")) {
                        passwordMode = -1;
                        seqSet.setText("Set");

                        verifyEnabled();

                        Toast.makeText(this, seqPwd + " ", Toast.LENGTH_SHORT).show();
                    }



                    break;

                case R.id.locationSetBtn:
                    Log.v(TAG, "clicked on loc");

                    if (locSet.getText().toString().equalsIgnoreCase("Set")) {
                        if(passwordMode == -1) {
                            finish.setEnabled(false);
                            locPwd = "";
                            locSet.setText("Finish");
                            verify.setEnabled(false);
                            passwordMode = 1;
                        } else {
                            Toast.makeText(this, "Please finish setting a password before trying to set another one", Toast.LENGTH_SHORT).show();
                        }
                    } else if (locSet.getText().toString().equalsIgnoreCase("Finish")) {
                        passwordMode = -1;
                        locSet.setText("Set");
                        verifyEnabled();
                        Toast.makeText(this, locPwd + " ", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.quantitySetBtn:
                    Log.v("set_password", "clicked on quan");

                    if (quantSet.getText().toString().equalsIgnoreCase("Set")) {
                        if(passwordMode == -1) {
                            finish.setEnabled(false);
                            quantPwd[0] = 0;
                            quantPwd[1] = 0;
                            quantPwd[2] = 0;
                            quantPwd[3] = 0;
                            quantSet.setText("Finish");
                            verify.setEnabled(false);
                            passwordMode = 2;
                        }else {
                            Toast.makeText(this, "Please finish setting a password before trying to set another one", Toast.LENGTH_SHORT).show();
                        }
                    } else if (quantSet.getText().toString().equalsIgnoreCase("Finish")) {
                        passwordMode = -1;
                        quantSet.setText("Set");
                        verifyEnabled();
                        Toast.makeText(this, Arrays.toString(quantPwd) + " ", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.verify:
                    if(verify.getText().toString().equalsIgnoreCase("Stop")) {
                        passwordMode = -1;
                        enableButtons();
                        verify.setText("Verify");
                    } else {
                        if (checkSameNumbers()) {
                            Toast.makeText(this, "You cannot have two patterns have the same order number!", Toast.LENGTH_SHORT).show();
                        } else {
                            composePasswordOrder();
                            if (checkOrderNumbers()) {
                                verifyPassword();
                            } else {
                                Toast.makeText(this, "Please your order from 1 and increment", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    break;
                case R.id.finish:
                    setPasswords();
                    finish();
                    break;
            }
        }

    }

    private void disableButtons() {
        seqSet.setEnabled(false);
        locSet.setEnabled(false);
        quantSet.setEnabled(false);

        seqOrder.setEnabled(false);
        quantOrder.setEnabled(false);
        locOrder.setEnabled(false);

        seqBox.setEnabled(false);
        locBox.setEnabled(false);
        quantBox.setEnabled(false);

    }
    private void enableButtons() {
        seqSet.setEnabled(true);
        locSet.setEnabled(true);
        quantSet.setEnabled(true);

        seqOrder.setEnabled(true);
        quantOrder.setEnabled(true);
        locOrder.setEnabled(true);

        seqBox.setEnabled(true);
        locBox.setEnabled(true);
        quantBox.setEnabled(true);
    }
    private void verifyPassword() {
        composePassword();

        passwordMode = 3;
        Log.v(TAG, "entire mode =" + entireMode + " entirePassword = "+Arrays.toString(entirePassword));
        pat = new Pattern(entireMode, entirePassword,System.currentTimeMillis());
        disableButtons();
        updateColorGrid();
        verify.setText("Stop");
        Toast.makeText(this, "Please verify that you know your password!", Toast.LENGTH_SHORT).show();
    }
    private void composePassword() {
        entirePassword[0] = seqPwd;
        entirePassword[1] = locPwd;
        entirePassword[2] = quantPwd[0] + "" + quantPwd[1] + quantPwd[2] + quantPwd[3];
    }

    private void composePasswordOrder () {
        if(seqChecked) {
            mode[seqNum-1] = "ORDER";
        }
        if(locChecked) {
            mode[locNum-1] = "SEQUENCE";
        }
        if(quantChecked) {
            mode[quantNum-1] = "QUANTITY";
        }

        entireMode = "" + mode[0];

        for(int i = 1; i < numModes; i++) {
            entireMode += "_" + mode[i];
        }

        Log.v(TAG, "PASSWORD MODE" + entireMode);

    }

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

    private boolean checkOrderNumbers() {
        boolean startFromOne = true;
        if( (numModes == 1 && mode[0].equals(""))|| (numModes == 2 && !mode[2].equals(""))) {
            startFromOne = false;
        }
        return startFromOne;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
//            if (data.hasExtra("PASSWORD") && data.hasExtra("PASSWORD_MODE") ) {
//                String mode = data.getExtras().getString("PASSWORD_MODE");
//                String pwd = data.getExtras().getString("PASSWORD");
//
//                if (mode.equalsIgnoreCase("ORDER")) {
//                    seqPwd = pwd;
//                    Toast.makeText(this, mode + " " + seqPwd, Toast.LENGTH_SHORT).show();
//                } else if(mode.equalsIgnoreCase("POSITION")) {
//                    locPwd = pwd;
//                    Toast.makeText(this, mode + " " + pwd, Toast.LENGTH_SHORT).show();
//                } else {
//                    quantPwd = data.getExtras().getIntArray("PASSWORD");
//
//                }
//
//
//
//            }
//        }
//    }

    private void verifyEnabled() {
        boolean bool = false;

        Log.v(TAG, seqChecked + " seq = |" + seqPwd + "| " +seqChecked + "loc = |" + locPwd +"| quant = |" + Arrays.toString(quantPwd));

        if( (seqPwd.equals("") && seqChecked) || (locPwd.equals("") && locChecked) ||
                (quantChecked && quantPwd[0]==0&&quantPwd[1]==0&&quantPwd[2]==0&&quantPwd[3]==0)) {
            bool = true;
            Log.v(TAG, "empty pwds");
        }

        if(bool) {
            verify.setEnabled(false);
        } else verify.setEnabled(true);
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
    private void setPasswords() {
        SharedPreferences sharedpreferences = getSharedPreferences(Welcome.MY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

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
            verifyEnabled();
        }

        finish.setEnabled(false);
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
            btn.setBackgroundColor(getResources().getColor(R.color.Red));
        } else if(color.equals("BLUE")) {
            btn.setBackgroundColor(getResources().getColor(R.color.Blue));
        } else if(color.equals("GREEN")) {
            btn.setBackgroundColor(getResources().getColor(R.color.Green));
        } else if(color.equals("YELLOW")) {
            btn.setBackgroundColor(getResources().getColor(R.color.Yellow));
        } else {
            btn.setBackgroundColor(getResources().getColor(R.color.White));
        }
    }

}
