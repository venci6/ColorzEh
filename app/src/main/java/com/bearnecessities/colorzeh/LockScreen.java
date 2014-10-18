package com.bearnecessities.colorzeh;

        /*
              0 1 2
              _ _ _
         0   |_|_|_|
         1   |_|_|_|
         2   |_|_|_|

        */

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.concurrent.locks.Lock;


public class LockScreen extends Activity implements View.OnClickListener{


    boolean unlock = false;
    String[] password = {"RGB" , "", ""};
    Pattern pat = new Pattern(password, 123L);
    private static final String TAG = LockScreen.class.getSimpleName();
    ImageButton tl, tm, tr, ml, mm, mr, bl, bm, br;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
        Thread checkOpen = new Thread()
        {
            public void run(){
                if (unlock)
                        finish();
            }
        };
        checkOpen.start();
        initializeButtons();

    }

    private void initializeButtons()
    {
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

    @Override
    public void onClick(View v) {
        int locationX;
        int locationY;
        boolean unlock = false;

        switch(v.getId()) {
            case R.id.top_left:
                locationX = 0;
                locationY = 0;
                pat.input(locationX, locationY);
                Log.v(TAG, "top left was clicked. Hoorah. Hodor.");

                break;
            case R.id.top_mid:
                locationX = 1;
                locationY = 0;
                pat.input(locationX, locationY);
                Log.v(TAG, "top mid was clicked. Hoorah. Hodor.");
                break;
            case R.id.top_right:
                locationX = 2;
                locationY = 0;
                pat.input(locationX, locationY);
                Log.v(TAG, "top right was clicked. Hoorah. Hodor.");
                break;
            case R.id.mid_left:
                locationX = 0;
                locationY = 1;
                pat.input(locationX, locationY);
                Log.v(TAG, "mid left was clicked. Hoorah. Hodor.");
                break;

            case R.id.mid_mid:
                locationX = 1;
                locationY = 1;
                pat.input(locationX, locationY);
                Log.v(TAG, "mid mid was clicked. Hoorah. Hodor.");
                break;
            case R.id.mid_right:
                locationX = 2;
                locationY = 1;
                pat.input(locationX, locationY);
                Log.v(TAG, "mid right was clicked. Hoorah. Hodor.");
                break;

            case R.id.bot_left:
                locationX = 0;
                locationY = 2;
                pat.input(locationX, locationY);
                Log.v(TAG, "bot left was clicked. Hoorah. Hodor.");
                break;
            case R.id.bot_mid:
                locationX = 1;
                locationY = 1;
                pat.input(locationX, locationY);
                Log.v(TAG, "bot mid was clicked. Hoorah. Hodor.");
                break;

            case R.id.bot_right:
                locationX = 2;
                locationY = 2;
                pat.input(locationX, locationY);
                Log.v(TAG, "bot right was clicked. Hoorah. Hodor.");
                break;
        }}


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
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
