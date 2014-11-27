package com.bearnecessities.colorzeh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Charlene on 11/24/2014.
 */
public class Welcome extends Activity  {
    private Button settings;
    SharedPreferences sharedpreferences;
    public static final String MY_PREFERENCES = "MyPrefs";
    public static final String pattern = "patternKey";
    public static final String pass = "passwordKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // if already have a password set, go to the lockscreen
        sharedpreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);

        if(sharedpreferences.contains(pattern) && sharedpreferences.contains(pass)) {
            Intent lockScreen = new Intent(Welcome.this, LockScreen.class);
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
        if(sharedpreferences.contains(pattern) && sharedpreferences.contains(pass)) {
            Intent lockScreen = new Intent(Welcome.this, LockScreen.class);
            startActivity(lockScreen);
            finish();
        }
    }
}
