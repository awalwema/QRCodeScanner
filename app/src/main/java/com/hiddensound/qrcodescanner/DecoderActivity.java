package com.hiddensound.qrcodescanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.hiddensound.Presenter.DecoderPresenter;
import com.hiddensound.Presenter.DecoderPresenterInterface;
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
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoder);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
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

    @Override
    public void callLogin() {
        Intent intent = new Intent(DecoderActivity.this, LoginActivity.class);
        startActivity(intent);
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

        pDecoder.Decline();
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }

    public void onAccept(View v) {
        pDecoder.Approve();
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
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
                pDecoder.signOut();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
