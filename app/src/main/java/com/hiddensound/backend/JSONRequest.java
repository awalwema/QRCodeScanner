package com.hiddensound.backend;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hiddensound.qrcodescanner.LoginActivity;
import com.hiddensound.qrcodescanner.MainActivity;
import com.hiddensound.qrcodescanner.RegisterActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by amarques on 2/11/2017.
 */

public class JSONRequest extends AsyncTask<String, String, String> implements Application.ActivityLifecycleCallbacks{
    private static final String TAG = "MainActivity";
    Context appContext = null;
    Activity appActivity = null;
    public JSONRequest(Activity act){
        super();
        appActivity = act;
        appContext = appActivity.getApplicationContext();
    }

    @Override
    protected String doInBackground(String... params) {
        String JsonResponse;
        String JsonDATA = params[1];
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            // is output buffer writter
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
//set headers and method
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    urlConnection.getOutputStream(), "UTF-8"));
            writer.write(JsonDATA);
// json data
            writer.close();
            InputStream inputStream = urlConnection.getInputStream();
//input stream
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String inputLine;
            while ((inputLine = reader.readLine()) != null)
                buffer.append(inputLine + "\n");
            if (buffer.length() == 0) {
                // Stream was empty. No point in parsing.
                return null;
            }
            JsonResponse = buffer.toString();
            //Log
            Log.i(TAG, JsonResponse);
            //Parsing of the string into Elements using gson
            JsonParser parser = new JsonParser();
            JsonElement jsonTree = parser.parse(JsonResponse);
            if(jsonTree.isJsonObject()){
                JsonObject jsonObject = jsonTree.getAsJsonObject();
                JsonElement id = jsonObject.get("id");
                JsonElement email = jsonObject.get("email");
                JsonElement isDeveloper = jsonObject.get("isDeveloper");
                JsonElement isVerified = jsonObject.get("isVerified");

                if(isVerified.getAsString().equals("true")) {
                    email.getAsString();
                    id.getAsString();
                    appActivity.startActivity(new Intent(appActivity, MainActivity.class));
                } else if(isDeveloper.getAsString().equals("true")) {
                    appActivity.startActivity(new Intent(appActivity, RegisterActivity.class));
                } else {
                    urlConnection.disconnect();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }
        return null;
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        String text = result;

        Toast toast = Toast.makeText(appContext, text, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}


