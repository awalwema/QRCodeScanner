package com.hiddensound.qrcodescanner;

import android.content.Context;

import com.hiddensound.model.HiddenModel;

/**
 * Created by amarques on 3/18/2017.
 */

public interface RegisterInterface {
    void setToast(String msg);
    void callDecoder(HiddenModel hiddenModel);
    Object getSystemService(String Name);
    boolean canAccessCamera();
    boolean hasPermission(String perm);
    void requestCameraPermission();
    Context getContext();
    void callLogin();
    void finishRegisterActivity();
}
