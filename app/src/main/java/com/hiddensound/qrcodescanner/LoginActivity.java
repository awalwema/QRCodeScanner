package com.hiddensound.qrcodescanner;

/**
 * Created by Andrew on 2/4/2017.
 */

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
        setContentView(R.layout.content_token);

//        UserIDView = (EditText) findViewById(R.id.UserID);
//        UserPassView = (EditText) findViewById(R.id.UserPass);
        checkBar = (ProgressBar) findViewById(R.id.pbbar);
        showPB();

        loginPresenter = new LoginPresenter(this, getApplicationContext());
        loginPresenter.checkPhoneState(this, REQUEST_PHONE_STATE);
        loginPresenter.checkTokenValid();
    }

    public void onClickLogin(View v) {
        //function in the activity that corresponds to the layout button
        loginPresenter.checkLogin(UserIDView.getText().toString(), UserPassView.getText().toString());
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


    public void startWebSignUp(View v) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://dev-hiddensound.azurewebsites.net/register")));
    }
    // backdoor for testing purposes
//    public void startFeaturesActivity(View v) {
//        if(!canAccessCamera()){
//            requestCameraPermission();
//        } else {
//            //start only if permission is granted
//            startActivity(new Intent(LoginActivity.this, DecoderActivity.class));
//        }
//    }

    @Override
    public void callDecoder(HiddenModel hiddenModel) {
        Intent intent = new Intent(LoginActivity.this, DecoderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("hModelT", hiddenModel.getToken());
        bundle.putLong("hModelTT", hiddenModel.getTokenTime());
        bundle.putString("hModelI", hiddenModel.getIMEI());
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
                    this.setToast("Access Denied");

                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            this);
                    builder.setCancelable(true);
                    builder.setTitle("Permission to Access Phone's Unique Identifier");
                    builder.setMessage("This app will not handle anything related to phone calls. In order to uniquely identify" +
                            " your phone and for you to use this app, the app needs to access your phone's state.");

                    builder.setPositiveButton("Allow Access",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                    checkPhoneState(REQUEST_PHONE_STATE);
                                }
                            });
                    builder.setNegativeButton("Cancel and Exit App",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
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
    public void requestIMEIPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Log.d("permissions",
                    "Displaying camera permission rationale to provide additional context.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE},
                    REQUEST_CAMERA);


        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE},
                    REQUEST_PHONE_STATE);
        }
    }

    @Override
    public void callRegister(HiddenModel hiddenModel, boolean wrongDevice) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("flag", wrongDevice);
        bundle.putString("hModelT", hiddenModel.getToken());
        bundle.putLong("hModelTT", hiddenModel.getTokenTime());
        bundle.putSerializable("hModelI", hiddenModel.getIMEI());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean canAccessCamera() {
        return (hasPermission(Manifest.permission.CAMERA));
    }

    private boolean hasPermission(String perm) {
        return(PackageManager.PERMISSION_GRANTED==checkCallingOrSelfPermission(perm));
    }

    @Override
    public boolean canAccessIMEI() {
        return (hasPermission(Manifest.permission.READ_PHONE_STATE));
    }

    @Override
    public void finishLoginActivity()
    {
        finish();
    }

    public void checkPhoneState(int REQUEST_PHONE_STATE) {
        int hasPhoneStatePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (hasPhoneStatePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_PHONE_STATE);
        }
    }
    @Override
    public void setLoginContent() {
        hidePB();
        setContentView(R.layout.content_login);

        UserIDView = (EditText) findViewById(R.id.UserID);
        UserPassView = (EditText) findViewById(R.id.UserPass);
        checkBar = (ProgressBar) findViewById(R.id.pbbar);
    }
}





