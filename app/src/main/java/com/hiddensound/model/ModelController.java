package com.hiddensound.model;

/**
 * Created by AMarques on 2/22/2017.
 */

public class ModelController implements ModelInterface {
    private  String imei = null;
    private String qrMemo = null;
    private String userName = null;
    private String token = null;

    @Override
    public void setIMEI(String imei) {
        this.imei = imei;
    }

    @Override
    public void setQRMemo(String qrMemo) {
        this.qrMemo = qrMemo;
    }

    @Override
    public void setUser(String user) {
        this.userName = user;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public HiddenModel create() {
        return new HiddenModel(imei, qrMemo, userName, token);
    }
}
