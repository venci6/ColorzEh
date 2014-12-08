package com.bearnecessities.colorzeh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.locks.Lock;

/**
 * Created by Charlene on 11/24/2014.
 */
public class Welcome extends Activity  {
    private Button settings;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        sharedPreferences = getSharedPreferences(NxNLockScreen.MY_PREFERENCES, Context.MODE_PRIVATE);

        NxNLockScreen.c1 = sharedPreferences.getInt("COLOR_1", 0);
        NxNLockScreen.c2 = sharedPreferences.getInt("COLOR_2", 1);
        NxNLockScreen.c3 = sharedPreferences.getInt("COLOR_3", 2);
        NxNLockScreen.c4 = sharedPreferences.getInt("COLOR_4", 3);

        NxNLockScreen.colors = getResources().getStringArray(R.array.color_values_array);
        if(sharedPreferences.contains(NxNLockScreen.pattern) && sharedPreferences.contains(NxNLockScreen.pass)) {
            Intent lockScreen = new Intent(Welcome.this, NxNLockScreen.class);
            startActivity(lockScreen);
            finish();
        } else {
            setContentView(R.layout.welcome);

            settings = (Button) findViewById(R.id.go_settings);

            settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent settings = new Intent(Welcome.this, Settings.class);
                    startActivity(settings);
                }
            });
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if(sharedPreferences.contains(NxNLockScreen.pattern) && sharedPreferences.contains(NxNLockScreen.pass)) {
            Intent lockScreen = new Intent(Welcome.this, NxNLockScreen.class);
            startActivity(lockScreen);
            finish();
        }
    }
}
