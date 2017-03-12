package com.hiddensound.Presenter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

import com.hiddensound.model.HiddenModel;
import com.hiddensound.model.ModelController;
import com.hiddensound.model.ModelInterface;
import com.hiddensound.qrcodescanner.DecoderActivity;
import com.hiddensound.qrcodescanner.DecoderInterface;

/**
 * Created by amarques on 2/15/2017.
 */

public class DecoderPresenter implements DecoderPresenterInterface {
    private ModelInterface localModel;
    private HiddenModel hiddenModel;
    private DecoderInterface dActivity;
    private JSONParse jsonParse;
    private HttpHelperClient httpHelper;

    public DecoderPresenter(HiddenModel hiddenModel, DecoderInterface dActivty) {
        localModel = new ModelController();
        this.hiddenModel = hiddenModel;
        this.dActivity = dActivty;
        jsonParse = new JSONParse();
        httpHelper = new HttpHelperClient();
    }

    @Override
    public void Approve() {
        hiddenModel = localModel.create(hiddenModel);
        httpHelper.postApproval(hiddenModel, new Callback<Integer>() {
            @Override
            public void onResponse(Integer integer) {
                //handle approval response
            }
        });
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        hiddenModel = localModel.create(jsonParse.parseJson4Decoder(text));

        dActivity.setAppName(hiddenModel.getAppName());
        dActivity.expandSlideUp();
    }

    @Override
    public void checkPermissions(DecoderActivity activity, int value) {
        int hasCameraPermission = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.CAMERA);

        if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CAMERA},
                    value);
        }
    }
}