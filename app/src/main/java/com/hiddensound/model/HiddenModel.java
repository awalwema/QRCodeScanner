package com.hiddensound.model;

/**
 * Created by amarques on 2/11/2017.
 */

public class HiddenModel implements ModelInterface{
    protected String IMEI = null;
    private String QRMEMO = null;
    private String USERNAME = null;
    private String TOKERN = null;

    @Override
    public String getIMEI() {
        return IMEI;
    }

    @Override
    public String getQRMemo() {
        return QRMEMO;
    }

    @Override
    public String getUser() {
        return USERNAME;
    }

    @Override
    public String getToken() {
        return TOKERN;
    }

    @Override
    public void setIMEI(String imei) {
        this.IMEI = imei;
    }

    @Override
    public void setQRMemo(String qrMemo) {
        this.QRMEMO = qrMemo;
    }

    @Override
    public void setUser(String user) {
        this.USERNAME = user;
    }

    @Override
    public void setToken(String token) {
        this.TOKERN = token;
    }


}
