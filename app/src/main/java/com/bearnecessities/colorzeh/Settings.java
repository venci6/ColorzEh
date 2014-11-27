package com.bearnecessities.colorzeh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Charlene on 11/26/2014.
 */
public class Settings extends Activity implements View.OnClickListener{
    Button setPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        setPassword = (Button) findViewById(R.id.set_pwd);
        setPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.set_pwd:
                Intent setPwd = new Intent(Settings.this, SetPassword.class);
                startActivity(setPwd);
                break;
        }
    }
}
