package com.hiddensound.model;

/**
 * Created by amarques on 2/11/2017.
 * Change.
 */

public class HiddenModel {
    private final long TOKENTIME;
    private final String IMEI;
    private final String QRMEMO;
    private final String APPNAME;
    private final String TOKEN;

    public HiddenModel(String imei, String qrMemo, String appName, String token, long tokenTime){
        IMEI = imei;
        QRMEMO = qrMemo;
        APPNAME = appName;
        TOKEN = token;
        TOKENTIME = tokenTime;
    }

    public HiddenModel(String imei, String qrMemo){
        this(imei, qrMemo, null, null, 0);
    }

    public String getIMEI() {
        return IMEI;
    }

    public String getQRMemo() {
        return QRMEMO;
    }

    public String getAppName() {
        return APPNAME;
    }

    public String getToken() {
        return TOKEN;
    }

    public long getTokenTime() { return  TOKENTIME; }
}
