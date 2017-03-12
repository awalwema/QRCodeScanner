package com.hiddensound.Presenter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.hiddensound.model.HiddenModel;
import com.hiddensound.model.ModelController;
import com.hiddensound.model.ModelInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * Created by Zane on 3/2/2017.
 */


public class TokenHelper implements TokenHelperInterface {
    protected LoginPresenter loginPresenter;
    protected Context context;
    ModelInterface localModel;
    HiddenModel hiddenModel;

    public TokenHelper(LoginPresenter loginPresenter, Context context) {

        this.loginPresenter = loginPresenter;
        this.context = context;
        localModel = new ModelController();

    }


    public void tokenStore(HiddenModel model) {
        this.hiddenModel = model;
        long tokenTime = hiddenModel.getTokenTime();

        tokenTime = tokenTime * 1000;
        long expireTime = System.currentTimeMillis() + tokenTime;
        localModel.setTokenTime(expireTime);
        hiddenModel = localModel.create(hiddenModel);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("Token", hiddenModel.getToken());
        editor.putLong("TokenExpire", hiddenModel.getTokenTime());
        editor.apply();
    }

    public HiddenModel tokenRetrieve() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        long expireTime = (long) prefs.getLong("TokenExpire", 0);
        String tempToken = prefs.getString("Token", null);
        localModel.setTokenTime(expireTime);
        localModel.setToken(tempToken);
        hiddenModel = localModel.create();

        if (expireTime == 0) {

        }

        return hiddenModel;
    }

}