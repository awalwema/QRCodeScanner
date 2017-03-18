package com.hiddensound.Presenter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.hiddensound.model.HiddenModel;
import com.hiddensound.model.ModelController;
import com.hiddensound.model.ModelInterface;
import com.hiddensound.qrcodescanner.LoginActivity;
import com.hiddensound.qrcodescanner.LoginInterface;

/**
 * Created by Zane on 2/26/2017.
 */

public class LoginPresenter implements LoginPresenterInterface {
    private HiddenModel hiddenModel;
    private ModelInterface localModal;
    private JSONParse jsonParser;
    private String tokenresponse;
    private HttpHelperClient httphelper;
    private LoginInterface activity;
    private TokenHelper tokenHelper;

    private static final int REQUEST_CAMERA = 0;

    public LoginPresenter(LoginActivity loginActivity, Context context){
        this.tokenHelper = new TokenHelper(this, context);
        httphelper = new HttpHelperClient();
        activity = loginActivity;
        jsonParser = new JSONParse();
        localModal = new ModelController();
    }

    @Override
    public void checkLogin(String UserName, String Password) {
        activity.showPB();

        httphelper.requestToken(UserName, Password, new Callback<Integer>(){
            @Override
            public void onResponse(Integer integer) {
                if(httphelper.getResponse()>0){
                tokenresponse = httphelper.getTokenstring();
                hiddenModel = localModal.create(jsonParser.parseJson4Login(tokenresponse));
                tokenHelper.tokenStore(hiddenModel);

                    if(!activity.canAccessCamera()){
                        activity.requestCameraPermission();
                    } else {
                        //start decoder activity only if permission is granted
                        activity.callDecoder(hiddenModel);
                    }
                if(integer == 200){
                    tokenresponse = httphelper.getTokenstring();
                    hiddenModel = localModal.create(jsonParser.parseJson4Login(tokenresponse));
                    tokenHelper.tokenStore(hiddenModel);
                    activity.callDecoder(hiddenModel);
                    checkPhonePair();
                } else {
                    Log.e("fudge", "up");
                    activity.setToast("Invalid username and/or password. Error code:" + integer);
                }

                activity.hidePB();
            }
        });
    }

    @Override
    public void checkPhoneState(LoginActivity act, int REQUEST_PHONE_STATE) {
        int hasPhoneStatePermission = ContextCompat.checkSelfPermission(act, Manifest.permission.READ_PHONE_STATE);

        if (hasPhoneStatePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(act,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_PHONE_STATE);
        }
    }

    public void checkTokenValid() {
        hiddenModel = localModal.create(tokenHelper.tokenRetrieve());
        long expireTime = hiddenModel.getTokenTime();
        long currentTime = System.currentTimeMillis();

        if(currentTime < expireTime && expireTime!=0){
            checkPhonePair();
            activity.callDecoder(hiddenModel);
        }

    }

    @Override
    public void checkPhonePair() {
        TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        activity.setToast(tm.getDeviceId());
        localModal.setIMEI(tm.getDeviceId());
        hiddenModel = localModal.create(hiddenModel);
        httphelper.requestPhonePair(hiddenModel, new Callback<Integer>() {
            @Override
            public void onResponse(Integer integer) {
                //handle response
                if(integer == 404)
                    activity.setToast("Bad Request");
            }
        });
    }
}

