package com.hiddensound.qrcodescanner;

/**
 * Created by Zane on 2/26/2017.
 */

public interface LoginInterface {
    void callmain();
    void setToast(String msg);
    void showPB();
    void hidePB();

    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);
}
