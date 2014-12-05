package com.bearnecessities.colorzeh;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Charlene on 12/3/2014.
 */
public class NxNLockScreen extends Activity {

    private final String TAG = LockScreen.class.getSimpleName();

    private GridView colorGrid;
    ColorGridViewAdapter adapter;
    int n = 2;
    // create pattern class
    Pattern pat = new Pattern(Pattern.ORDER, n, new String[] {"RGBYR" , "000102", "4110"}, 123L);

    SharedPreferences sharedpreferences;
    public static final String MY_PREFERENCES = "MyPrefs";
    public static final String pattern = "patternKey";
    public static final String pass = "passwordKey";
    public static int c1, c2, c3, c4;
    public static String[] colors;

    List<ColorGridCell> nums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
// Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }

        setContentView(R.layout.lock_screen_nxn);

        sharedpreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        colors = getResources().getStringArray(R.array.color_values_array);

        colorGrid = (GridView) findViewById(R.id.nxnGrid);

        getPassword();


        refreshGrid();

        colorGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean unlock;
                String color;
                int x = ((ColorGridCell)parent.getItemAtPosition(position)).x;
                int y = ((ColorGridCell)parent.getItemAtPosition(position)).y;
                color = pat.getColorAtPosition(x,y);

                Log.v(TAG, "X " + x + "| y" + y + " " + color);

                unlock = pat.input(x, y);
                Log.v(TAG, unlock + "");

                if (unlock) {
                    finish();
                } else {
                    pat.updatePattern();
                    refreshGrid();
                    // refresh grid
                }

            }
        });
    }

    private void refreshGrid() {

        colorGrid.setNumColumns(n);
        generateArrayForGV();

        adapter = new ColorGridViewAdapter(this, nums, pat);
        colorGrid.setAdapter(adapter);
    }
    public void generateArrayForGV() {
        ColorGridCell cell;
        nums = new ArrayList<ColorGridCell>();

        for(int i = 0; i<n; i++) {
            for(int j = 0; j < n; j++) {
                cell = new ColorGridCell(j, i);
                nums.add(cell);
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        updateColors();
        getPassword();
        refreshGrid();
    }

    private void updateColors() {
        c1 = sharedpreferences.getInt("COLOR_1", 0);
        c2 = sharedpreferences.getInt("COLOR_2", 1);
        c3 = sharedpreferences.getInt("COLOR_3", 2);
        c4 = sharedpreferences.getInt("COLOR_4", 3);
    }

    private void getPassword() {
        String pwdMode = sharedpreferences.getString(pattern, "");
        String pw = sharedpreferences.getString(pass, "");

        String[] pwSplit = pw.split("/");

        Log.v(TAG, "mode " + pwdMode + " password " + Arrays.toString(pwSplit));
        n = sharedpreferences.getInt("GRID_SIZE",3);

        pat = new Pattern(pwdMode,n, pwSplit, System.currentTimeMillis());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lock_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.action_settings:
                Intent settings = new Intent(NxNLockScreen.this, Settings.class);
                startActivity(settings);
                break;
            case R.id.action_about:
                Intent about = new Intent(NxNLockScreen.this, Welcome.class);
                startActivity(about);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean res = super.onKeyDown(keyCode, event);

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_APP_SWITCH:
                Log.v(TAG, "gu");
                return false;
            default:
        }
        return res;
    }
}
