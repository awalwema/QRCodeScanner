package com.hiddensound.Presenter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.hiddensound.model.ModelInterface;
import com.hiddensound.qrcodescanner.MainActivity;

/**
 * Created by amarques on 2/15/2017.
 */

public class MainPresenter implements MainPresenterInterface, ModelInterface.onModelCall{
    private ModelInterface model;

    public MainPresenter(ModelInterface model){
        this.model = model;
    }
    public MainPresenter(){};

    @Override
    public void Approve(boolean approval) {

    }

    @Override
    public void checkPermissions(MainActivity activity, int value) {
        int hasCameraPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);

        if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CAMERA},
                    value);

        }

    }

    @Override
    public void setIMEI() {
//        model.setIMEI();
    }


}
