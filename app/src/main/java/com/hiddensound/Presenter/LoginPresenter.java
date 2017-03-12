package com.hiddensound.Presenter;

import android.Manifest;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.hiddensound.qrcodescanner.LoginActivity;
import com.hiddensound.qrcodescanner.LoginInterface;
import com.hiddensound.model.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

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

    private LoginActivity act;

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
                hiddenModel = jsonParser.parseJson(tokenresponse);
                tokenHelper.tokenStore(hiddenModel);
                activity.callmain();
                } else {
                    Log.e("fudge", "up");
                    activity.setToast("Invalid username and/or password.");
                }

                activity.hidePB();
            }
        });
//            httphelper.setResponseValid(false);
//            if (httphelper.getResponse() > 0) {
//                //Tell login activity to call main activity
//                tokenresponse = httphelper.getTokenstring();
//                hiddenModel = jsonParser.parseJson(tokenresponse);
//                activity.callmain();
//
//
//            } else if (httphelper.getResponse() < 0) {
//                activity.setToast("Invalid username or password.");
//
//            }
        }

    @Override
    public void checkPhoneState(LoginActivity act, int REQUEST_PHONE_STATE) {

        this.act = act;
        int hasPhoneStatePermission = ContextCompat.checkSelfPermission(act, Manifest.permission.READ_PHONE_STATE);


        if (hasPhoneStatePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(act,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_PHONE_STATE);

        }

    }

    public void checkTokenValid() {
        hiddenModel = tokenHelper.tokenRetrieve();
        long expireTime = hiddenModel.getTokenTime();
        long currentTime = System.currentTimeMillis();


        if(currentTime < expireTime && expireTime!=0)
            activity.callmain();
    }
}

