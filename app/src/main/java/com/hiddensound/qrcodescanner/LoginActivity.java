package com.hiddensound.qrcodescanner;

/**
 * Created by Andrew on 2/4/2017.
 */


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hiddensound.Presenter.LoginPresenter;
import com.hiddensound.Presenter.LoginPresenterInterface;
import com.hiddensound.Presenter.TokenHelper;


//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;

public class LoginActivity extends AppCompatActivity implements LoginInterface{
    LoginPresenterInterface loginPresenter;


    EditText UserIDView;
    EditText UserPassView;
    String UserID;
    String UserPass;
    ProgressBar checkBar;
    private static final int REQUEST_PHONE_STATE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UserIDView = (EditText) findViewById(R.id.UserID);
        UserPassView = (EditText) findViewById(R.id.UserPass);
        checkBar = (ProgressBar) findViewById(R.id.pbbar);
        loginPresenter = new LoginPresenter(this, getApplicationContext());
        loginPresenter.checkTokenValid();
        loginPresenter.checkPhoneState(this, REQUEST_PHONE_STATE);





    }

    public void onClickLogin(View v) {

        //function in the activity that corresponds to the layout button
        UserID = UserIDView.getText().toString();
        UserPass = UserPassView.getText().toString();
        loginPresenter.checkLogin(UserID, UserPass);
    }

    public void setToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showPB() {
        checkBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePB() {
        checkBar.setVisibility(View.INVISIBLE);
    }


    public void startRegisterActivity(View v) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://dev-hiddensound.azurewebsites.net/register")));

    }

    public void startFeaturesActivity(View v) {
        startActivity(new Intent(LoginActivity.this, DecoderActivity.class));
    }

    @Override
    public void callmain() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_STATE:
                if(ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED)    {
                    // Permission Granted

                } else {
                    // Permission Denied
                    this.setToast("IMEI Access Denied");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }



}





