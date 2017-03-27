package com.hiddensound.Presenter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.hiddensound.model.HiddenModel;
import com.hiddensound.model.ModelController;
import com.hiddensound.model.ModelInterface;
import com.hiddensound.qrcodescanner.LoginActivity;
import com.hiddensound.qrcodescanner.LoginInterface;


import java.util.HashMap;

/**
 * Created by Zane on 2/26/2017.
 */

public class LoginPresenter implements LoginPresenterInterface {
    private HiddenModel hiddenModel;
    private ModelInterface localModel;
    private JSONParse jsonParser;
    private String tokenresponse;
    private String registerStatus;
    private HttpHelperClient httphelper;
    private LoginInterface activity;
    private TokenHelper tokenHelper;

    private static final int REQUEST_CAMERA = 0;
    int status;

    public LoginPresenter(LoginActivity loginActivity, Context context){
        this.tokenHelper = new TokenHelper(this, context);
        httphelper = new HttpHelperClient();
        activity = loginActivity;
        jsonParser = new JSONParse();
        localModel = new ModelController();
    }

    @Override
    public void checkLogin(String UserName, String Password) {
        activity.showPB();

        httphelper.requestToken(UserName, Password, new Callback<Integer>(){
            @Override
            public void onResponse(Integer integer) {
                if(integer == 200){
                    tokenresponse = httphelper.getTokenstring();
                    hiddenModel = localModel.create(jsonParser.parseJson4Login(tokenresponse));
                    tokenHelper.tokenStore(hiddenModel);

                    checkPhonePair(new Callback<Integer>(){
                        @Override
                        public void onResponse(Integer integer) {

                            if (integer == 200){
                                registerStatus = calculateDevStatus(httphelper.getDeviceRegisterStatus());

                                if (registerStatus.equalsIgnoreCase("Everything is good")) {
                                    //redirect to decoder activity
                                    if (!activity.canAccessCamera()) {
                                        activity.requestCameraPermission();
                                    } else {
                                        //start decoder activity only if permission is granted
                                        activity.callDecoder(hiddenModel);
                                    }
                                }

                                else if(registerStatus.equalsIgnoreCase("Different device already registered"))
                                {
                                    activity.callRegister(hiddenModel, true);
                                }

                                else if(registerStatus.equalsIgnoreCase("You can register device"))
                                {
                                    activity.callRegister(hiddenModel, false);
                                }


                            }
                            else {
                                //activity.callRegister(hiddenModel);
                            }
                        }
                    });


                } else {
                    Log.e("fudge", "up");
                    activity.setToast("Invalid username and/or password. Error code:" + integer);
                }

                activity.hidePB();
            }
        });
    }

    public String calculateDevStatus(String devStatus) {
        HashMap<String, Boolean> table = jsonParser.parseJson4RegisterStatus(devStatus);

        boolean isUserDevice = table.get("isUserDevice"); //does device belong to user?
//        boolean isDeviceLinked = table.get("isDeviceLinked"); //is device already linked?
        boolean userHasDevice = table.get("userHasDevice"); //does user already have device linked?

        String result = "Something wasn't covered";

        if(isUserDevice)
            result = "Everything is good";

        else if(isUserDevice == false && userHasDevice == true)
            result = "Different device already registered";

        else if(isUserDevice == false && userHasDevice == false)
            result = "You can register device";

//        else if(isUserDevice == false && isDeviceLinked == true && userHasDevice == false)
//            result = "Device is already registered with different user";

        return result;
    }

    @Override
    public void checkPhoneState(LoginActivity activity, int REQUEST_PHONE_STATE) {
        int hasPhoneStatePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);

        if (hasPhoneStatePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_PHONE_STATE);
        }
    }

    public void checkTokenValid() {
        hiddenModel = localModel.create(tokenHelper.tokenRetrieve());
        final long expireTime = hiddenModel.getTokenTime();
        final long currentTime = System.currentTimeMillis();

        checkPhonePair(new Callback<Integer>(){
            @Override
            public void onResponse(Integer integer) {

                if (integer == 200){
                    registerStatus = calculateDevStatus(httphelper.getDeviceRegisterStatus());

                    if (registerStatus.equalsIgnoreCase("Everything is good")) {
                        //redirect to decoder activity
                        if (!activity.canAccessCamera()) {
                            activity.requestCameraPermission();
                        } else {
                            //start decoder activity only if permission is granted
                            activity.callDecoder(hiddenModel);
                        }
                    }

                    else if(registerStatus.equalsIgnoreCase("Different device already registered"))
                    {
                        activity.callRegister(hiddenModel, true);
                    }

                    else if(registerStatus.equalsIgnoreCase("You can register device"))
                    {
                        activity.callRegister(hiddenModel, false);
                    }


                }
                else {
                    //activity.callRegister(hiddenModel);
                }

                if(currentTime < expireTime && expireTime!=0 && calculateDevStatus(httphelper.getDeviceRegisterStatus()).equalsIgnoreCase("Everything is good")){
                    activity.callDecoder(hiddenModel);
                }

                else if(currentTime > expireTime && expireTime!=0)
                {
                    //delete everything stored in shared preferences.
                    tokenHelper.deleteTokenInfo();
                }
            }
        });



    }

    @Override
    public void checkPhonePair(final Callback<Integer> startCallBack) {
        final boolean[] paired = {false};
        TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        //activity.setToast(tm.getDeviceId());
        localModel.setIMEI(tm.getDeviceId());
        hiddenModel = localModel.create(hiddenModel);
        httphelper.checkPair(hiddenModel, new Callback<Integer>() {
            @Override
            public void onResponse(Integer integer) {
                status = integer;
                //handle response
                if(integer == 404)
                    activity.setToast("Bad Server");
                else if(integer == 401)
                    activity.setToast("Token Expired");
                else if(integer == 400)
                    activity.setToast("Bad Request");
                else if(integer == 200)
                    paired[0] = true;

                startCallBack.onResponse(integer);
            }
        });

    }

    @Override
    public void registerDevice(HiddenModel hiddenModel) {
        if (activity == null) {

        }

        TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        localModel.setIMEI(tm.getDeviceId());
        localModel.setType(Build.MODEL);
        hiddenModel = localModel.create(hiddenModel);
        httphelper.registerDevice(hiddenModel, new Callback<Integer>() {
            @Override
            public void onResponse(Integer integer) {
                //handle response
                if(integer == 404)
                    activity.setToast("Bad Server");
                else if(integer == 401)
                    activity.setToast("Bad Headers");
                else if(integer == 400)
                    activity.setToast("Bad Request");
                else if(integer == 200)
                    activity.setToast("Success!!");
                   // paired[0] = true;
            }
        });
    }
}

