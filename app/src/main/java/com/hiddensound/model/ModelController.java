package com.hiddensound.model;

/**
 * Created by AMarques on 2/22/2017.
 */

public class ModelController implements ModelInterface {
    private long tokenTime = 0;
    private  String imei = null;
    private String qrMemo = null;
    private String appName = null;
    private String token = null;

    @Override
    public void setTokenTime(long tokenTime) {
        this.tokenTime = tokenTime;
    }

    @Override
    public void setIMEI(String imei) {
        this.imei = imei;
    }

    @Override
    public void setQRMemo(String qrMemo) {
        this.qrMemo = qrMemo;
    }

    @Override
    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public HiddenModel create() {
        return new HiddenModel(imei, qrMemo, appName, token, tokenTime);
    }

    @Override
    public HiddenModel create(HiddenModel hiddenModel) {
        if(imei == null)
            imei = hiddenModel.getIMEI();
        if(qrMemo == null)
            qrMemo = hiddenModel.getQRMemo();
        if(appName == null)
            appName = hiddenModel.getAppName();
        if(token == null)
            token = hiddenModel.getToken();
        if(tokenTime == 0)
            tokenTime = hiddenModel.getTokenTime();

        return create();
    }
}
