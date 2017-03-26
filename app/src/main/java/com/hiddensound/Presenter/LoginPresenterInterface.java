package com.hiddensound.Presenter;


import com.hiddensound.model.HiddenModel;
import com.hiddensound.qrcodescanner.LoginActivity;

/**
 * Created by Zane on 2/26/2017.
 */

public interface LoginPresenterInterface {
    void checkLogin(String UserName, String Password);
    void checkPhoneState(LoginActivity act, int RPS);
    void checkTokenValid();
    void checkPhonePair(Callback<Integer> integer);
    void registerDevice(HiddenModel hiddenModel);
    //void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);

}
