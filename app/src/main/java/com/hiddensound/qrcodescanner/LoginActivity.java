package com.hiddensound.qrcodescanner;

/**
 * Created by Andrew on 2/4/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }
    public void startRegisterActivity(View v){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    public void startFeaturesActivity(View v){
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }



}
