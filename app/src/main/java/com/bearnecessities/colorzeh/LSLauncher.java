package com.bearnecessities.colorzeh;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Charlene on 12/4/2014.
 */
public class LSLauncher extends Activity {
    @Override
    protected void onStart() {
        super.onStart();

        Intent lock = new Intent(getBaseContext(), NxNLockScreen.class);
        startActivity(lock);
    }
}
