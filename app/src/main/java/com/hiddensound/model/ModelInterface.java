package com.hiddensound.model;

/**
 * Created by amarques on 2/15/2017.
 */

public interface ModelInterface {
    String getIMEI();
    String getQRMemo();
    String getUser();
    String getToken();
    void setIMEI(String imei);
    void setQRMemo(String  qrMemo);
    void setUser(String user);
    void setToken(String token);


    interface onModelCall{
        void setIMEI();
        void setQRMemo();
        void setUser();
        void setToken();
    }
}
