package com.hiddensound.qrcodescanner;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class DecoderActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener{
    private static final String TAG = "DecoderActivity";
    private TextView resultTextView;
    private QRCodeReaderView mydecoderview;

    private SlidingUpPanelLayout mLayout;
    TextView mainText;
    TextView bottomSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mydecoderview.setOnQRCodeReadListener(this);

        //   enable/disable decoding
        mydecoderview.setQRDecodingEnabled(true);

        //function to change the autofocus interval
        mydecoderview.setAutofocusInterval(2000L);

        mydecoderview.setBackCamera();

        inti();

    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        mydecoderview.stopCamera();
        Log.w("handleResult", text);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan result");
//        model.setQRMemo(result.getText());
        builder.setMessage(text);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    protected void onResume(){
        super.onResume();
        mydecoderview.startCamera();
    }

    @Override
    protected void onPause(){
        super.onPause();
        mydecoderview.stopCamera();
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

    public void onClickIMEI(View v) {
        //try {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Toast.makeText(DecoderActivity.this, tm.getDeviceId(), Toast.LENGTH_SHORT)
                .show();
    }

    public void onClickCamera(View v) {
        //try {
        mydecoderview.startCamera();
    }
}
