package com.hiddensound.qrcodescanner;

import com.hiddensound.model.HiddenModel;

/**
 * Created by Zane on 2/26/2017.
 */

public interface LoginInterface {
    void callDecoder(HiddenModel hiddenModel);
    void setToast(String msg);
    void showPB();
    void hidePB();
    Object getSystemService(String Name);
    boolean canAccessCamera();
    boolean canAccessIMEI();
    void requestCameraPermission();
    void requestIMEIPermission();
    void callRegister(HiddenModel hiddenModel, boolean wrongDevice);
    void finishLoginActivity();

}
