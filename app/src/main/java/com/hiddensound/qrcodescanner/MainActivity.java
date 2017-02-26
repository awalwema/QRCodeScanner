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
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.hiddensound.Presenter.JSONRequest;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

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
        checkPermissions();

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

        checkPermissions();
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();

    }

    public void onClickIMEI(View v){
        //try {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

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

        new JSONTask().execute("https://jsonparsingdemo-cec5b.firebaseapp.com/jsonData/moviesDemoItem.txt");


        JSONObject post_dict = new JSONObject();

//        try {
//            post_dict.put("IMEI", model.getIMEI());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

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
//        model.setQRMemo(result.getText());
        builder.setMessage(result.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        //Resume scanning
        //mScannerView.resumeCameraPreview(this);
    }

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private static final int REQUEST_CAMERA = 0;
    private static final int REQUEST_PHONE_STATE = 1;
    private static final int REQUEST_STORAGE = 2;

    private void checkPermissions() {
        int hasCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int hasPhoneStatePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        int hasStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);

        }

        if (hasPhoneStatePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_PHONE_STATE);

        }

        if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_STORAGE);

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
            case REQUEST_PHONE_STATE:
                if(ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED)    {
                    // Permission Granted

                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "IMEI Access Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case REQUEST_STORAGE:
                if(ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED)    {
                    // Permission Granted

                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "Storage Access Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public class JSONTask extends AsyncTask<String, String, String>{

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
