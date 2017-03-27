package com.hiddensound.Presenter;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.hiddensound.model.HiddenModel;
import com.hiddensound.model.ModelController;
import com.hiddensound.model.ModelInterface;
import com.hiddensound.qrcodescanner.RegisterActivity;
import com.hiddensound.qrcodescanner.RegisterInterface;

/**
 * Created by amarques on 3/26/2017.
 */

public class RegisterPresenter implements RegisterPresenterInterface {
    private RegisterInterface activity;
    private ModelInterface localModel;
    private HiddenModel hiddenModel;
    private HttpHelperClient httphelper;

    public RegisterPresenter(HiddenModel hiddenModel, RegisterActivity activity){
        this.activity = activity;
        localModel = new ModelController();
        this.hiddenModel = hiddenModel;
        httphelper = new HttpHelperClient();
    }

    @Override
    public void registerDevice(HiddenModel hiddenModel) {
        TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        localModel.setIMEI(tm.getDeviceId());
        localModel.setType(Build.MODEL);
        this.hiddenModel = localModel.create(hiddenModel);
        httphelper.registerDevice(this.hiddenModel, new Callback<Integer>() {
            @Override
            public void onResponse(Integer integer) {
                //handle response
                if(integer == 404)
                    activity.setToast("Bad Server");
                else if(integer == 401)
                    activity.setToast("Bad Headers");
                else if(integer == 400)
                    activity.setToast("Bad Request");
                else if(integer == 200) {
                    activity.setToast("Success!!");
                    callDecoder();
                }
                // paired[0] = true;
            }
        });
    }

    public void callDecoder(){
        activity.callDecoder(hiddenModel);
    }
}
