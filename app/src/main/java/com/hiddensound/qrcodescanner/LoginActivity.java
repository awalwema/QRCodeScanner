package com.hiddensound.qrcodescanner;

/**
 * Created by Andrew on 2/4/2017.
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hiddensound.Presenter.LoginPresenter;
import com.hiddensound.Presenter.LoginPresenterInterface;
import com.hiddensound.model.HiddenModel;

public class LoginActivity extends AppCompatActivity implements LoginInterface{
    private LoginPresenterInterface loginPresenter;
    private EditText UserIDView;
    private EditText UserPassView;
    private ProgressBar checkBar;
    private static final int REQUEST_PHONE_STATE = 1;
    private static final int REQUEST_CAMERA = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserIDView = (EditText) findViewById(R.id.UserID);
        UserPassView = (EditText) findViewById(R.id.UserPass);
        checkBar = (ProgressBar) findViewById(R.id.pbbar);

        loginPresenter = new LoginPresenter(this, getApplicationContext());
        loginPresenter.checkPhoneState(this, REQUEST_PHONE_STATE);
        loginPresenter.checkTokenValid();
    }

    public void onClickLogin(View v) {
        //function in the activity that corresponds to the layout button
        loginPresenter.checkLogin(UserIDView.getText().toString(), UserPassView.getText().toString());
//        loginPresenter.checkPhonePair();
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
        if(!canAccessCamera()){
            requestCameraPermission();
        } else {
//start only if permission is granted
            startActivity(new Intent(LoginActivity.this, DecoderActivity.class));
        }
    }

    @Override
    public void callDecoder(HiddenModel hiddenModel) {
        Intent intent = new Intent(LoginActivity.this, DecoderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("hModelT", hiddenModel.getToken());
        bundle.putLong("hModelTT", hiddenModel.getTokenTime());
        bundle.putSerializable("hModelI", hiddenModel.getIMEI());
        intent.putExtras(bundle);
        startActivity(intent);
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
            case REQUEST_CAMERA:
                if(ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED)    {
                    // Permission Granted
                    startActivity(new Intent(LoginActivity.this, DecoderActivity.class));
                } else {
                    // Permission Denied
                    this.setToast("CAMERA Access Denied");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void requestCameraPermission() {


        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Log.d("permissions",
                    "Displaying camera permission rationale to provide additional context.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.CAMERA},
                    REQUEST_CAMERA);


        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.CAMERA},
                    REQUEST_CAMERA);
        }
    }

    @Override
    public boolean canAccessCamera() {
        return (hasPermission(Manifest.permission.CAMERA));
    }

    private boolean hasPermission(String perm) {
        return(PackageManager.PERMISSION_GRANTED==checkCallingOrSelfPermission(perm));
    }
}





