package com.hiddensound.qrcodescanner;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import me.dm7.barcodescanner.zxing.ZXingScannerView;



/**
 * Created by Andrew on 1/19/2017.
 */

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private static final String TAG = "MainActivity";
    private ZXingScannerView mScannerView;
    private SlidingUpPanelLayout mLayout;
    TextView mainText;
    TextView bottomSlider;
//    private ModelInterface model;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissionCamera();

//        model = new HiddenModel();

        inti();
    }


    public void inti() {
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mainText = (TextView) findViewById(R.id.main_back);
        bottomSlider = (TextView) findViewById(R.id.slider);

        mLayout.setPanelSlideListener(onSlideListener());
    }

    private SlidingUpPanelLayout.PanelSlideListener onSlideListener() {
        return new SlidingUpPanelLayout.PanelSlideListener(){

            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.e(TAG, "onPanelSlide, offset " + slideOffset);
                mainText.setText("onPanelSlide");
                bottomSlider.setText("onPanelSlide");
            }

            @Override
            public void onPanelCollapsed(View panel) {
                Log.e(TAG, "onPanelCollapse");
                mainText.setText("onPanelCollapse");
                bottomSlider.setText("onPanelCollapse");
            }

            @Override
            public void onPanelExpanded(View panel) {
                Log.e(TAG, "onPanelExpand");
                mainText.setText("onPanelExpand");
                bottomSlider.setText("onPanelExpand");
            }

            @Override
            public void onPanelAnchored(View panel) {
                Log.e(TAG, "onPanelAnchor");
                mainText.setText("onPanelAnchor");
                bottomSlider.setText("onPanelAnchor");
            }

            @Override
            public void onPanelHidden(View panel) {
                Log.e(TAG, "onPanelHide");
                mainText.setText("onPanelHide");
                bottomSlider.setText("onPanelHide");
            }
        };
    }



    public void onClickCamera(View v){

        checkPermissionCamera();
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();

    }

    public void onClickIMEI(View v) {
        //try {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
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
//        model.setQRMemo(result.getText());
        builder.setMessage(result.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        //Resume scanning
        //mScannerView.resumeCameraPreview(this);
    }


    private static final int REQUEST_CAMERA = 0;
    private static final int REQUEST_PHONE_STATE = 1;
    private static final int REQUEST_STORAGE = 2;

    private void checkPermissionCamera() {
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
            case REQUEST_CAMERA:
                if(ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED)    {
                    // Permission Granted
                    if (mScannerView == null)
                    {
                        mScannerView = new ZXingScannerView(this);
                        setContentView(mScannerView);
                        mScannerView.setResultHandler(this);
                        mScannerView.startCamera();
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "CAMERA Denied", Toast.LENGTH_SHORT)
                            .show();
                    this.onBackPressed();
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;



            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line ="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);

                }
                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("movies");
                JSONObject finalObject = parentArray.getJSONObject(0);

                String movieName = finalObject.getString("movie");
                int year = finalObject.getInt("year");

                return movieName + " - " + year;




            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();

            } catch (JSONException e){
                e.printStackTrace();
            }
            finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null){
                        reader.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String text = result;

            Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
            toast.show();
        }
    }


}
