package com.hiddensound.qrcodescanner;

/**
 * Created by Andrew on 2/4/2017.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.hiddensound.Presenter.JSONRequest;
import com.hiddensound.model.HiddenModel;

import org.json.JSONException;
import org.json.JSONObject;

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
//        UserID = UserIDView.getText().toString();
//        UserPass = UserPassView.getText().toString();
        HiddenModel.setUSERNAME(UserIDView.getText().toString());
        HiddenModel.setPASS(UserPassView.getText().toString());

        JSONObject post_dict = new JSONObject();

       try {
            post_dict.put("email", HiddenModel.getUSERNAME());
            post_dict.put("password", HiddenModel.getPASS());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (post_dict.length() > 0) {
            JSONRequest request = new JSONRequest(LoginActivity.this);
            request.execute("https://dev-api-hiddensound.azurewebsites.net/Application/Auth/Login",
                    String.valueOf(post_dict));
//            new JSONRequest().execute(String.valueOf(post_dict));
        }
    }

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
//                URL url = new URL("https://dev-api-hiddensound.azurewebsites.net/Application/
// Auth/Login");
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setDoOutput(true);
//                // is output buffer writter
//                urlConnection.setRequestMethod("POST");
//                urlConnection.setRequestProperty("Content-Type", "application/json");
//                urlConnection.setRequestProperty("Accept", "application/json");
////set headers and method
//                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.
// getOutputStream(), "UTF-8"));
//                writer.write(JsonDATA);
//// json data
//                writer.close();
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





