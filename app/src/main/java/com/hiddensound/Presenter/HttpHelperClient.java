package com.hiddensound.Presenter;

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
    private String deviceRegisterStatus;

    public HttpHelperClient(){
//        client = new AsyncHttpClient();
//        params = new RequestParams();

    }

    public void registerDevice(HiddenModel hiddenModel, final  Callback<Integer> callback){
        RequestParams params = new RequestParams();
        params.put("IMEI", hiddenModel.getIMEI());
        params.put("Name", hiddenModel.getType());

        AsyncHttpClient client = addHeaders(hiddenModel);
        client.post(MAINULR + "/Mobile/Device/Link", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if(callback != null)
                    callback.onResponse(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if(callback != null)
                    callback.onResponse(statusCode);
            }
        });
    }

    public void checkPair(HiddenModel hiddenModel, final Callback<Integer> callback)
    {
        RequestParams params = new RequestParams();
        params.put("IMEI", hiddenModel.getIMEI());

        AsyncHttpClient client = addHeaders(hiddenModel);
        client.post(MAINULR + "/Mobile/Device/Check", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                                  Throwable throwable) {
                if(callback != null){
                    callback.onResponse(statusCode);
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                deviceRegisterStatus = responseString;

                if(callback != null){
                    callback.onResponse(statusCode);
                }
            }
        });

    }

    public void postApproval(HiddenModel hiddenModel, final Callback<Integer> callback){
        RequestParams params = new RequestParams();
        params.put("AuthorizationCode", hiddenModel.getQRMemo());
        params.put("IMEI", hiddenModel.getIMEI());

        AsyncHttpClient client = addHeaders(hiddenModel);
        client.post(MAINULR + "/Mobile/Authorization/Approve", params, new TextHttpResponseHandler() {
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
            RequestParams params = new RequestParams();
            params.put("username", UserID);
            params.put("password", UserPass);
            params.put("grant_type", "password");
            params.put("client_id", "a5U4DvFf3r2N9Kg");

            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("Content-Type", "application/x-www-form-urlencoded");
            client.post(MAINULR + "/OAuth/Token", params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString,
                                          Throwable throwable) {
                        if(callback != null){
                            callback.onResponse(statusCode);
                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        tokenstring = responseString;

                        if(callback != null){
                            callback.onResponse(statusCode);
                        }
                    }
                }
            );

        } catch (Exception e) {

        }
    }

    public String getTokenstring() {
        return tokenstring;
    }

    public String getDeviceRegisterStatus() {return deviceRegisterStatus;}

    private AsyncHttpClient addHeaders(HiddenModel hiddenModel){
        AsyncHttpClient client = new AsyncHttpClient();
        String token = "Bearer "+ hiddenModel.getToken();
        if(hiddenModel.getToken() != null){
            client.addHeader("Content-Type", "application/x-www-form-urlencoded");
            client.addHeader("Authorization", token);
        }
        else
            client.addHeader("Content-Type", "application/x-www-form-urlencoded");

        return client;
    }

}
