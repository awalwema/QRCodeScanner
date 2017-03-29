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
    protected Context context;
    private ModelInterface localModel;
    private HiddenModel hiddenModel;

    public TokenHelper(Context context) {
        this.context = context;
        localModel = new ModelController();
    }

    public void tokenStore(HiddenModel model) {
        long tokenTime = model.getTokenTime();

        tokenTime = tokenTime * 1000;
        long expireTime = System.currentTimeMillis() + tokenTime;
        localModel.setTokenTime(expireTime);
        localModel.setToken(model.getToken());
        hiddenModel = localModel.create();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("Token", hiddenModel.getToken());
        editor.putLong("TokenExpire", hiddenModel.getTokenTime());
        editor.apply();
    }

    public HiddenModel tokenRetrieve() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        long expireTime = prefs.getLong("TokenExpire", 0);
        String tempToken = prefs.getString("Token", null);
        localModel.setTokenTime(expireTime);
        localModel.setToken(tempToken);
        hiddenModel = localModel.create();

        return hiddenModel;
    }

    public void deleteTokenInfo()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }

}