package com.hiddensound.Presenter;

import android.app.Activity;
import android.util.Log;

import com.hiddensound.qrcodescanner.LoginActivity;
import com.hiddensound.qrcodescanner.LoginInterface;

import java.util.HashMap;

/**
 * Created by Zane on 2/26/2017.
 */

public class LoginPresenter implements LoginPresenterInterface {

    private HashMap<String, String> userTable;
    private JSONParse jsonParser;
    private String tokenresponse;
    private HttpHelperClient httphelper;
    private LoginInterface activity;

    public LoginPresenter(LoginActivity loginActivity){
        httphelper = new HttpHelperClient();
        activity = loginActivity;
        jsonParser = new JSONParse();
    }


    @Override
    public void checklogin(String UserName, String Password) {
        activity.showPB();

        httphelper.requestToken(UserName, Password, new Callback<Integer>(){
            @Override
            public void onResponse(Integer integer) {
                if(httphelper.getResponse()>0){
                tokenresponse = httphelper.getTokenstring();
                userTable = jsonParser.parseJson(tokenresponse);
                activity.callmain();}
                else {
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
//                userTable = jsonParser.parseJson(tokenresponse);
//                activity.callmain();
//
//
//            } else if (httphelper.getResponse() < 0) {
//                activity.setToast("Invalid username or password.");
//
//            }
        }

    }

