package com.hiddensound.qrcodescanner;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hiddensound.Presenter.RegisterPresenter;
import com.hiddensound.Presenter.RegisterPresenterInterface;
import com.hiddensound.model.HiddenModel;
import com.hiddensound.model.ModelController;
import com.hiddensound.model.ModelInterface;

/**
 * Created by Andrew on 2/4/2017.
 */

public class RegisterActivity extends AppCompatActivity implements RegisterInterface{

    private ModelInterface localModel;
    private boolean wrongDevice;
    private RegisterPresenterInterface registerPresenter;
    private HiddenModel hiddenModel;
    private static final int REQUEST_CAMERA = 0;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localModel = new ModelController();
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            localModel.setToken(bundle.getString("hModelT"));
            localModel.setTokenTime(bundle.getLong("hModelTT"));
            localModel.setIMEI(bundle.getString("hModelI"));
            wrongDevice = bundle.getBoolean("flag");
            if (wrongDevice) {
                setContentView(R.layout.content_old_device);
            } else {
                setContentView(R.layout.content_register);
            }
        }

        hiddenModel = localModel.create();
        registerPresenter = new RegisterPresenter(hiddenModel, this);
    }

    public void onClickContact(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"admin@hiddensound.net"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Device Registered to Different User");
        intent.putExtra(Intent.EXTRA_TEXT, "Describe your problem below:");
        startActivity(Intent.createChooser(intent, ""));
    }


    public void onClickDeviceManager(View v) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://dev-hiddensound.azurewebsites.net/account/devices")));
    }

    public void onClickRegister(View view) {
        registerPresenter.registerDevice(hiddenModel);
    }

    @Override
    public void setToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void callDecoder(HiddenModel hiddenModel) {
        Intent intent = new Intent(RegisterActivity.this, DecoderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("hModelT", hiddenModel.getToken());
        bundle.putLong("hModelTT", hiddenModel.getTokenTime());
        bundle.putSerializable("hModelI", hiddenModel.getIMEI());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void callLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

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

    public boolean canAccessCamera() {
        return (hasPermission(Manifest.permission.CAMERA));
    }

    public boolean hasPermission(String perm) {
        return(PackageManager.PERMISSION_GRANTED==checkCallingOrSelfPermission(perm));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if(ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED)    {
                    // Permission Granted
                    startActivity(new Intent(this, DecoderActivity.class));
                } else {
                    // Permission Denied
                    this.setToast("CAMERA Access Denied");
                    callLogin();
                    finishRegisterActivity();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.item_logout:
                registerPresenter.signOut();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void finishRegisterActivity()
    {
        finish();
    }
}
