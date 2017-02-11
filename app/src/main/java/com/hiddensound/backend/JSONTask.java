package com.hiddensound.backend;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by amarques on 2/8/2017.
 */

public class JSONTask extends AsyncTask<String, String, String> {

    Context appContext = null;
    public JSONTask(Context temp){
        appContext = temp;
    }
/*
    @Override
    public AsyncTask<String, String, String> execute(String... params){
        super.execute(params);
        return null;
    }
    */

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder stringResponse = null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            connection.setUseCaches(false);
            //connection.setConnectTimeout(1000000000);
            //connection.setReadTimeout(1000000000);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Host", "localhost:51349");
            connection.connect();

            // this should be change to have all the required fields from a separete method
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("ID", "6bb8a868-dba1-4f1a-93b7-24ebce87e243");
            jsonParam.put("Name", "NEW_SAMPLE_PHONE");
            jsonParam.put("Notes", "THIS_SHOULD_WORK");
            jsonParam.put("Done", false);

            //send POST
            DataOutputStream printout = new DataOutputStream(connection.getOutputStream());
            printout.writeBytes(jsonParam.toString());
            printout.flush();
            printout.close();

            int HttpResult = connection.getResponseCode();
            if(HttpResult == HttpURLConnection.HTTP_OK){
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),
                        "utf-8"));
                String line = null;
                while (((line = reader.readLine()) != null)){
                    stringResponse.append(line+"\n");
                }
                reader.close();

                Log.e("SERVER_RESPONSE::", ""+stringResponse.toString());
                Toast toast = Toast.makeText(appContext , "Done reading server response",
                        Toast.LENGTH_LONG);
                toast.show();
            }
            else{
                Log.e("SERVER_RESPONSE::",connection.getResponseMessage());

            }

            /*InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line ="";
            while ((line = reader.readLine()) != null){
                buffer.append(line);

            }
            String finalJson = buffer.toString();

            JSONObject parentObject = new JSONObject(finalJson);
            JSONArray parentArray = parentObject.getJSONArray("movies");
            JSONObject finalObject = parentArray.getJSONObject(0);

            String movieName = finalObject.getString("movie");
            int year = finalObject.getInt("year");

            return movieName + " - " + year;
*/



        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();

        } catch (JSONException e){
            e.printStackTrace();
        }
        finally {
            if(connection != null) {
                connection.disconnect();
            }
            try {
                if(reader != null){
                    reader.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        String text = result;

        Toast toast = Toast.makeText(appContext , text, Toast.LENGTH_LONG);
        toast.show();
    }
}
