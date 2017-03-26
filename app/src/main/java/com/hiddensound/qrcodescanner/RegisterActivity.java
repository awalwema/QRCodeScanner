package com.hiddensound.qrcodescanner;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hiddensound.model.ModelController;
import com.hiddensound.model.ModelInterface;

/**
 * Created by Andrew on 2/4/2017.
 */

public class RegisterActivity extends AppCompatActivity{

    private ModelInterface localModel;
    private boolean wrongDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        localModel = new ModelController();

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            localModel.setToken(bundle.getString("hModelT"));
            localModel.setTokenTime(bundle.getLong("hModelTT"));
            localModel.setIMEI(bundle.getString("hModelI"));
            wrongDevice = bundle.getBoolean("flag");
        }
    }

    public void onClickContact(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"admin@hiddensound.net"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Device Registered to Different User");
        intent.putExtra(Intent.EXTRA_TEXT, "Describe your problem below:");
        startActivity(Intent.createChooser(intent, ""));
    }


    public void startRegisterDevice(View v) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://dev-hiddensound.azurewebsites.net/register")));
    }
}
