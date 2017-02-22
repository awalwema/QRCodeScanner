package com.hiddensound.qrcodescanner;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;
import com.hiddensound.Presenter.JSONRequest;

import com.hiddensound.model.HiddenModel;
import com.hiddensound.model.ModelInterface;

import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;



/**
 * Created by Andrew on 1/19/2017.
 */

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private ModelInterface model;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = new HiddenModel();

    }

    public void onClickCamera(View v){

        checkPermission();

        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();

    }

    public void onClickIMEI(View v){
        //try {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        model.setIMEI(tm.getDeviceId());
            /*if(ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},MY_PERMISSIONS_REQUEST);
            }
            if(MY_PERMISSIONS_REQUEST!=0) {*/

        //String phone = tm.getLine1Number();

       /* CharSequence text = tm.getDeviceId();
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        toast.show();
        */
       /*     }
        } catch (Exception e){
            Log.e("IMEI", "GETTING BETTER INFO ON ERROR", e);
            Toast toast = Toast.makeText(getApplicationContext(), e.getMessage().toString(),
                    Toast.LENGTH_LONG);
            toast.show();
        }*/

        //JSONTask task = new JSONTask(getApplicationContext());


        JSONObject post_dict = new JSONObject();

        try {
            post_dict.put("IMEI", model.getIMEI());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (post_dict.length() > 0) {
            JSONRequest request = new JSONRequest(MainActivity.this);
            request.execute("http://10.0.2.2:81/api/todoitems/create",
                    String.valueOf(post_dict));
//            new JSONRequest().execute(String.valueOf(post_dict));
        }
//        JSONRequest request = new JSONRequest(MainActivity.this);
//        request.execute("http://10.0.2.2:81/api/todoitems/create");



    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mScannerView != null)
        {
            mScannerView.stopCamera();
        }
    }


    @Override
    public void handleResult(Result result) {
        //Do what you want with said capture here
        Log.w("handleResult", result.getText());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan result");
        model.setQRMemo(result.getText());
        builder.setMessage(result.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        //Resume scanning
        //mScannerView.resumeCameraPreview(this);
    }

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private static final int REQUEST_CAMERA = 0;

    private void checkPermission() {
        int hasCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if(ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED)    {
                    // Permission Granted
                    mScannerView.startCamera();
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "CAMERA Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }




}
