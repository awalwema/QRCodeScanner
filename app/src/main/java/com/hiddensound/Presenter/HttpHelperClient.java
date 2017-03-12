package com.hiddensound.Presenter;

import android.content.Intent;

import com.hiddensound.model.HiddenModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Zane on 2/26/2017.
 */

public class HttpHelperClient {
    private static final String MAINULR = "https://dev-api-hiddensound.azurewebsites.net";
    private String tokenstring;
    private AsyncHttpClient client;
    private RequestParams params;
    private int response;

    public HttpHelperClient(){
        client = new AsyncHttpClient();
        params = new RequestParams();

    }

    public void requestPhonePair(HiddenModel hiddenModel, final Callback<Integer> callback)
    {
        params.put("imei",hiddenModel.getIMEI());
        client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        client.addHeader("Token", hiddenModel.getToken());
        client.post(MAINULR + "/Mobile/Devices/Check", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                                  Throwable throwable) {
                if(callback != null){
                    callback.onResponse(statusCode);
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if(callback != null){
                    callback.onResponse(statusCode);
                }
            }
        });
    }

    public void postApproval(HiddenModel hiddenModel, final Callback<Integer> callback){
        params.put("authorizationCode", hiddenModel.getQRMemo());
        params.put("imei", hiddenModel.getIMEI());
        client.addHeader("Contt-Type", "application/x-www-form-urlencoded");
        client.addHeader("token", hiddenModel.getToken());
        client.post(MAINULR + "/Mobile/Trasaction/Authorize", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                                  Throwable throwable) {
                if(callback != null){
                    callback.onResponse(statusCode);
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if(callback != null){
                    callback.onResponse(statusCode);
                }
            }
        });
    }

    public void requestToken(String UserID, String UserPass, final Callback<Integer> callback) {
        try {
            params.put("username", UserID);
            params.put("password", UserPass);
            params.put("grant_type", "password");
            params.put("client_id", "a5U4DvFf3r2N9Kg");
            client.addHeader("Content-Type", "application/x-www-form-urlencoded");

            client.post(MAINULR + "/OAuth/Token", params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString,
                                          Throwable throwable) {
                        response = -1;
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        response = 0;

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();

                        if(callback != null){
                            callback.onResponse(1);
                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        response = 1;
                        tokenstring = responseString;
                    }
                }
            );
        } catch (Exception e) {

        }
    }

    public String getTokenstring() {
        return tokenstring;
    }

    public int getResponse()
    {
        return response;
    }



}

