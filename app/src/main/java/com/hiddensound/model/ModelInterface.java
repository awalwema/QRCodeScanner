package com.hiddensound.model;

/**
 * Created by amarques on 2/15/2017.
 */

public interface ModelInterface {

    void setTokenTime (long tokenTime);
    void setIMEI(String imei);
    void setQRMemo(String  qrMemo);
    void setUser(String user);
    void setToken(String token);
    HiddenModel create();
    HiddenModel create(HiddenModel hiddenModel);

    interface onModelCall{
        void setIMEI();
    }
}
