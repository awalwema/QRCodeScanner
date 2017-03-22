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
    private final String TYPE;

    public HiddenModel(String imei, String qrMemo, String appName, String token, long tokenTime,
                       String type){
        IMEI = imei;
        QRMEMO = qrMemo;
        APPNAME = appName;
        TOKEN = token;
        TOKENTIME = tokenTime;
        TYPE = type;
    }

    public HiddenModel(String imei, String qrMemo){
        this(imei, qrMemo, null, null, 0, null);
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

    public String getType() {
        return TYPE;
    }
}
