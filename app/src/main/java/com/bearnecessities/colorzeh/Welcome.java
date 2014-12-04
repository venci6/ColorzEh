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

        sharedPreferences = getSharedPreferences(LockScreen.MY_PREFERENCES, Context.MODE_PRIVATE);
        if(sharedPreferences.contains(LockScreen.pattern) && sharedPreferences.contains(LockScreen.pass)) {
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
        if(sharedPreferences.contains(LockScreen.pattern) && sharedPreferences.contains(LockScreen.pass)) {
            Intent lockScreen = new Intent(Welcome.this, LockScreen.class);
            startActivity(lockScreen);
            finish();
        }
    }
}
