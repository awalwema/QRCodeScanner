package com.hiddensound.qrcodescanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;

/**
 * Created by Andrew on 2/4/2017.
 */

public class RegisterActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void onClickContact(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "admin@hiddensound.net" });
        intent.putExtra(Intent.EXTRA_SUBJECT, "Device Registered to Different User");
        intent.putExtra(Intent.EXTRA_TEXT, "Describe your problem below:");
        startActivity(Intent.createChooser(intent, ""));


    }
}

