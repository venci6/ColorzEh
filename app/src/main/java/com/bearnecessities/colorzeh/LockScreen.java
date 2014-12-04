package com.bearnecessities.colorzeh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.security.Provider;
import java.util.Arrays;

 /*
              0 1 2 n-1
              _ _ _
         0   |_|_|_|
         1   |_|_|_|
         2   |_|_|_|
         n-1

 */



public class LockScreen extends Activity implements View.OnClickListener {

    private final String TAG = LockScreen.class.getSimpleName();

    // create pattern class
    Pattern pat;


    private ImageButton tl, tm, tr, ml, mm, mr, bl, bm, br;

    SharedPreferences sharedpreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);

        //start lock service
        startService(new Intent(getBaseContext(), LockService.class));

        sharedpreferences = getSharedPreferences(Welcome.MY_PREFERENCES, Context.MODE_PRIVATE);

        getPassword();

        initializeButtons();
        updateColorGrid();
    }

    @Override
    public void onResume() {
        super.onResume();

        getPassword();
        updateColorGrid();
    }

    private void getPassword() {
        String pwdMode = sharedpreferences.getString(Welcome.pattern, "");
        String pw = sharedpreferences.getString(Welcome.pass, "");
        String[] pwSplit = pw.split("/");

        Log.v(TAG, "mode " + pwdMode + " password " + Arrays.toString(pwSplit));

        pat = new Pattern(pwdMode, 3, pwSplit, System.currentTimeMillis());
    }

    @Override
    public void onClick(View v) {
        String color;
        char c;
        int locationX = 0;
        int locationY = 0;
        boolean unlock;

        switch(v.getId()) {
            case R.id.top_left:
                locationX = 0;
                locationY = 0;
                Log.v(TAG, "top left was clicked. Hoorah. Hodor.");
                break;
            case R.id.top_mid:
                locationX = 1;
                locationY = 0;
                Log.v(TAG, "top mid was clicked. Hoorah. Hodor.");
                break;
            case R.id.top_right:
                locationX = 2;
                locationY = 0;
                Log.v(TAG, "top right was clicked. Hoorah. Hodor.");
                break;
            case R.id.mid_left:
                locationX = 0;
                locationY = 1;
                Log.v(TAG, "mid left was clicked. Hoorah. Hodor.");
                break;
            case R.id.mid_mid:
                locationX = 1;
                locationY = 1;
                Log.v(TAG, "mid mid was clicked. Hoorah. Hodor.");
                break;
            case R.id.mid_right:
                locationX = 2;
                locationY = 1;
                Log.v(TAG, "mid right was clicked. Hoorah. Hodor.");
                break;
            case R.id.bot_left:
                locationX = 0;
                locationY = 2;
                Log.v(TAG, "bot left was clicked. Hoorah. Hodor.");
                break;
            case R.id.bot_mid:
                locationX = 1;
                locationY = 2;
                Log.v(TAG, "bot mid was clicked. Hoorah. Hodor.");
                break;
            case R.id.bot_right:
                locationX = 2;
                locationY = 2;
                Log.v(TAG, "bot right was clicked. Hoorah. Hodor.");
                break;
        }
        color = pat.getColorAtPosition(locationX,locationY);
        Log.v(TAG, "X: "+ locationX + " Y: " + locationY + " = "+ color);


        unlock = pat.input(locationX, locationY);
        Log.v(TAG, unlock + "");

        if (unlock) {
            this.finish();
        } else {
            pat.updatePattern();
            updateColorGrid();
        }
    }

    private void initializeButtons() {
        tl = (ImageButton)  findViewById(R.id.top_left);
        tm = (ImageButton)  findViewById(R.id.top_mid);
        tr = (ImageButton)  findViewById(R.id.top_right);
        ml = (ImageButton)  findViewById(R.id.mid_left);
        mm = (ImageButton)  findViewById(R.id.mid_mid);
        mr = (ImageButton)  findViewById(R.id.mid_right);
        bl = (ImageButton)  findViewById(R.id.bot_left);
        bm = (ImageButton)  findViewById(R.id.bot_mid);
        br = (ImageButton)  findViewById(R.id.bot_right);

        tl.setOnClickListener(this);
        tm.setOnClickListener(this);
        tr.setOnClickListener(this);
        ml.setOnClickListener(this);
        mm.setOnClickListener(this);
        mr.setOnClickListener(this);
        bl.setOnClickListener(this);
        bm.setOnClickListener(this);
        br.setOnClickListener(this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lock_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.set_password.
        int id = item.getItemId();
        switch(id) {
            case R.id.action_settings:
                Intent settings = new Intent(LockScreen.this, Settings.class);
                startActivity(settings);
                break;
            case R.id.action_about:
                Intent about = new Intent(LockScreen.this, Welcome.class);
                startActivity(about);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
