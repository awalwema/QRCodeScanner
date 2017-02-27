package com.hiddensound.Presenter;


import android.content.Intent;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hiddensound.qrcodescanner.MainActivity;
import com.hiddensound.qrcodescanner.RegisterActivity;

import java.util.HashMap;

public class JSONParse {

    JsonParser parser;
    JsonElement jsonTree;

    public HashMap<String, String> parseJson(String jsonElement) {
        HashMap<String, String> jsonResult = new HashMap<>();
        parser = new JsonParser();
        jsonTree = parser.parse(jsonElement);
        if (jsonTree.isJsonObject()) {


            JsonObject jsonObject = jsonTree.getAsJsonObject();
            JsonElement tokenType = jsonObject.get("token_type");
            JsonElement token = jsonObject.get("access_token");
            JsonElement expiresIn = jsonObject.get("expires_in");
            jsonResult.put("tokenType", tokenType.toString());
            jsonResult.put("token", token.toString());
            jsonResult.put("expiresIn", expiresIn.toString());






        }
        return jsonResult;
    }

}

