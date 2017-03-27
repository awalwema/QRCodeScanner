package com.hiddensound.qrcodescanner;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.hiddensound.Presenter.DecoderPresenter;
import com.hiddensound.Presenter.DecoderPresenterInterface;
import com.hiddensound.model.HiddenModel;
import com.hiddensound.model.ModelController;
import com.hiddensound.model.ModelInterface;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class DecoderActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener,
        DecoderInterface{
    private static final String TAG = "DecoderActivity";
    private QRCodeReaderView mydecoderview;
    private SlidingUpPanelLayout mLayout;
    private TextView bottomSlider;
    private ModelInterface localModel;
    private DecoderPresenterInterface pDecoder;
    private static final int REQUEST_CAMERA = 0;
    private Boolean holdTill = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        localModel = new ModelController();

        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null)
        {
            localModel.setToken(bundle.getString("hModelT"));
            localModel.setTokenTime(bundle.getLong("hModelTT"));
            localModel.setIMEI(bundle.getString("hModelI"));
        }

        pDecoder = new DecoderPresenter(localModel, this);


        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mydecoderview.setOnQRCodeReadListener(this);

        //   enable/disable decoding
        mydecoderview.setQRDecodingEnabled(true);

        //function to change the autofocus interval
        mydecoderview.setAutofocusInterval(2000L);
        mydecoderview.setBackCamera();

        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        bottomSlider = (TextView) findViewById(R.id.slider);
        mLayout.setPanelSlideListener(onSlideListener());
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        mydecoderview.stopCamera();
        pDecoder.onQRCodeRead(text, points);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(holdTill)
            mydecoderview.startCamera();
    }

    @Override
    protected void onPause(){
        super.onPause();
        mydecoderview.stopCamera();
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        return super.getSystemService(name);
    }

    @Override
    public void setToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void setAppName(String appName) {
        bottomSlider.setText(bottomSlider.getText().toString().replace("X", appName));
    }

    @Override
    public void expandSlideUp() {
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }

    private SlidingUpPanelLayout.PanelSlideListener onSlideListener() {
        return new SlidingUpPanelLayout.PanelSlideListener(){

            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.e(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelCollapsed(View panel) {
                Log.e(TAG, "onPanelCollapse");
                mydecoderview.startCamera();
            }

            @Override
            public void onPanelExpanded(View panel) {
                Log.e(TAG, "onPanelExpand");
            }

            @Override
            public void onPanelAnchored(View panel) {
                Log.e(TAG, "onPanelAnchor");
            }

            @Override
            public void onPanelHidden(View panel) {
                Log.e(TAG, "onPanelHide");
            }
        };
    }

    public void onReject(View v) {
        mydecoderview.startCamera();
        mydecoderview.setOnQRCodeReadListener(this);
        mydecoderview.setQRDecodingEnabled(true);
        mydecoderview.refreshDrawableState();

        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }

    public void onAccept(View v) {
        pDecoder.Approve();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if(ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED)    {
                    // Permission Granted
                    holdTill = false;
                } else {
                    // Permission Denied
                    this.setToast("CAMERA Access Denied");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
