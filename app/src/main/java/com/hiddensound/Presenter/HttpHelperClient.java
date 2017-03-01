package com.hiddensound.Presenter;

import android.content.Intent;
import android.preference.PreferenceActivity;
import android.widget.Toast;

import com.hiddensound.qrcodescanner.LoginActivity;
import com.hiddensound.qrcodescanner.MainActivity;
import com.hiddensound.qrcodescanner.RegisterActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Zane on 2/26/2017.
 */

public class HttpHelperClient {
    private String tokenstring;
    private AsyncHttpClient client;
    private RequestParams params;
    private int response;

    public HttpHelperClient(){
        client = new AsyncHttpClient();
        params = new RequestParams();

    }

    public void requestToken(String UserID, String UserPass, final Callback<Integer> callback) {
        try {
            params.put("username", UserID);
            params.put("password", UserPass);
            params.put("grant_type", "password");
            params.put("client_id", "a5U4DvFf3r2N9Kg");
            client.addHeader("Content-Type", "application/x-www-form-urlencoded");

            client.post("https://dev-api-hiddensound.azurewebsites.net/OAuth/Token", params,
                    new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString,
                                          Throwable throwable) {
                        response = -1;
                    }

//                    @Override
//                    public void onStart() {
//                        super.onStart();
//                        response = 0;
//                    }


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



//                    @Override
//                    public void onSuccess(int statusCode, PreferenceActivity.Header[] headers, String res) {
//                        // called when response HTTP status is "200 OK"
////                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
//
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String res, Throwable t) {
////                        Toast.makeText(getApplicationContext(), "Error: " + statusCode, Toast.LENGTH_SHORT).show();
//                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//
////                        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
//                    }
                    }
            );
            if (response == 1) {
                return;

            }
        } catch (Exception e) {

        }


//        return response;
    }
    public String getTokenstring() {
        return tokenstring;
    }


    public int getResponse()
    {
        return response;
    }

}

