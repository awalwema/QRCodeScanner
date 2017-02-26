package com.hiddensound.qrcodescanner;

/**
 * Created by Andrew on 2/4/2017.
 */


import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.HttpRequestRetryHandler;

import com.loopj.android.http.*;



//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;

public class LoginActivity extends AppCompatActivity {
    EditText UserIDView;
    EditText UserPassView;
    String UserID;
    String UserPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UserIDView = (EditText) findViewById(R.id.UserID);
        UserPassView = (EditText) findViewById(R.id.UserPass);

    }

    public void onClickLogin(View v) {
        //function in the activity that corresponds to the layout button
        UserID = UserIDView.getText().toString();
        UserPass = UserPassView.getText().toString();

        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        params.put("username", UserID);
        params.put("password", UserPass);
        params.put("grant_type", "password");
        params.put("client_id", "a5U4DvFf3r2N9Kg");
        client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        client.post("https://dev-api-hiddensound.azurewebsites.net/OAuth/Token", params, new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String res) {
                        // called when response HTTP status is "200 OK"
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Error: "+statusCode,Toast.LENGTH_SHORT).show();
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                    }
                }
        );
    }

//        JSONObject post_dict = new JSONObject();
//
//        try {
////            query = String.format("username=%s&password=%s&grant_type=password&client_id=%s", URLEncoder.encode(UserID,"UTF-8"),URLEncoder.encode(UserPass,"UTF-8"), URLEncoder.encode("a5U4DvFf3r2N9Kg", "UTF-8"));
////
////            post_dict.put("username", UserID);
////            post_dict.put("password", UserPass);
////
////            post_dict.put("grant_type", "password");
////            post_dict.put("client_id", "a5U4DvFf3r2N9Kg");
////            //post_dict.put('scope', this.scope);
//
//
//
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        if (post_dict.length() > 0) {
//        }
//

//        JSONRequest request = new JSONRequest();
//        request.execute(query);
//    }

//    class JSONRequest extends AsyncTask<String, String, String> {
//        private static final String TAG = "MainActivity";
//
//        @Override
//        protected String doInBackground(String... params) {
//            String JsonResponse;
//            String JsonDATA = params[0];
//            HttpURLConnection urlConnection = null;
//            BufferedReader reader = null;
//            try {
//                URL url = new URL("https://dev-api-hiddensound.azurewebsites.net/OAuth/Token");
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setDoOutput(true);
//                // is output buffer writter
//                urlConnection.setRequestMethod("POST");
//                urlConnection.setRequestProperty("Content-Type", "application/json");
//                urlConnection.setRequestProperty("Accept", "application/json");
////set headers and method
//                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
//                writer.write(JsonDATA);
//// json data
//                writer.close();
//                int Response = urlConnection.getResponseCode();
//
//                InputStream inputStream = urlConnection.getInputStream();
////input stream
//                StringBuffer buffer = new StringBuffer();
//                if (inputStream == null) {
//                    // Nothing to do.
//                    return null;
//                }
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//
//                String inputLine;
//                while ((inputLine = reader.readLine()) != null)
//                    buffer.append(inputLine + "\n");
//                if (buffer.length() == 0) {
//                    // Stream was empty. No point in parsing.
//                    return null;
//                }
//                JsonResponse = buffer.toString();
//                //Log
//                Log.i(TAG, JsonResponse);
//                //Parsing of the string into Elements using gson
//                JsonParser parser = new JsonParser();
//                JsonElement jsonTree = parser.parse(JsonResponse);
//                if(jsonTree.isJsonObject()){
//                    JsonObject jsonObject = jsonTree.getAsJsonObject();
//                    JsonElement id = jsonObject.get("id");
//                    JsonElement email = jsonObject.get("email");
//                    JsonElement isDeveloper = jsonObject.get("isDeveloper");
//                    JsonElement isVerified = jsonObject.get("isVerified");
//
//                    if(isVerified.getAsString().equals("true")) {
//                        email.getAsString();
//                        id.getAsString();
//                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    } else if(isDeveloper.getAsString().equals("true")) {
//                        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
//                    } else {
//                        urlConnection.disconnect();
//                    }
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (final IOException e) {
//                        Log.e(TAG, "Error closing stream", e);
//                    }
//                }
//            }
//            return null;
//        }
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            String text = result;
//
//            Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
//            toast.show();
//        }
//    }

    public void startRegisterActivity(View v) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    public void startFeaturesActivity(View v) {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }
}





