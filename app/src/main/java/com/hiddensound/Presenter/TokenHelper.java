package com.hiddensound.Presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.hiddensound.model.HiddenModel;
import com.hiddensound.model.ModelController;
import com.hiddensound.model.ModelInterface;


/**
 * Created by Zane on 3/2/2017.
 * Test comment;
 */


public class TokenHelper implements TokenHelperInterface {
    protected LoginPresenter loginPresenter;
    protected Context context;
    private ModelInterface localModel;
    private HiddenModel hiddenModel;

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

        return hiddenModel;
    }

    public void deleteTokenInfo()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().clear();
        prefs.edit().commit();
    }

}