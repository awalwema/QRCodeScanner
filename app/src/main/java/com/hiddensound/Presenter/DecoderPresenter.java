package com.hiddensound.Presenter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.hiddensound.model.HiddenModel;
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

    public DecoderPresenter(ModelInterface localModel, DecoderInterface dActivty) {
        this.localModel = localModel;
        hiddenModel = this.localModel.create();
        this.dActivity = dActivty;
        jsonParse = new JSONParse();
        httpHelper = new HttpHelperClient();
    }

    @Override
    public void Approve() {
        httpHelper.postApproval(this.hiddenModel, new Callback<Integer>() {
            @Override
            public void onResponse(Integer integer) {
                //handle approval response
                if(integer == 404)
                    dActivity.setToast("Server not found");
                else if(integer == 401)
                    dActivity.setToast("Unauthorized/Bad Token");
                else if(integer == 400)
                    dActivity.setToast("Bad Request");
                else if(integer == 200)
                    dActivity.setToast("Authorization successful!");
            }
        });
    }

    @Override
    public void Decline() {
        httpHelper.postDecline(this.hiddenModel, new Callback<Integer>() {
            @Override
            public void onResponse(Integer integer) {
                //handle approval response
                if(integer == 404)
                    dActivity.setToast("Server not found");
                else if(integer == 401)
                    dActivity.setToast("Unauthorized/Bad Token");
                else if(integer == 400)
                    dActivity.setToast("Bad Request");
                else if(integer == 200)
                    dActivity.setToast("Decline successful!");
            }
        });
    }

    @Override
    public void signOut() {
        TokenHelperInterface temp = new TokenHelper(dActivity.getContext());
        temp.deleteTokenInfo();
        dActivity.setToast("You have been successfully logged out.");
        dActivity.callLogin();

    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        hiddenModel = localModel.create(jsonParse.parseJson4Decoder(text));

        dActivity.setAppName(hiddenModel.getAppName());
        dActivity.expandSlideUp();
    }


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