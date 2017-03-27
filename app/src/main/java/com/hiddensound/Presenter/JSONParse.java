package com.hiddensound.Presenter;



import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hiddensound.model.HiddenModel;
import com.hiddensound.model.ModelController;
import com.hiddensound.model.ModelInterface;

import java.util.HashMap;

public class JSONParse {
    private ModelInterface localModel;
    private JsonParser parser;
    private JsonElement jsonTree;
    private JsonObject jsonObject;
    private JsonElement jsonElement;

    public JSONParse(){
        localModel = new ModelController();
        parser = new JsonParser();
    }

    public HiddenModel parseJson4Login(String JSON) {
        jsonTree = parser.parse(JSON);

        if (jsonTree.isJsonObject()) {
            jsonObject = jsonTree.getAsJsonObject();
            jsonElement = jsonObject.get("access_token");
            localModel.setToken(jsonElement.getAsString());

            jsonElement = jsonObject.get("expires_in");
            localModel.setTokenTime(jsonElement.getAsLong());
        }
        return localModel.create();
    }

    public HiddenModel parseJson4Decoder(String QRMemo){
        jsonTree = parser.parse(QRMemo);

        if(jsonTree.isJsonObject()){
            jsonObject = jsonTree.getAsJsonObject();
            jsonElement = jsonObject.get("applicationName");
            localModel.setAppName(jsonElement.getAsString());

            jsonElement = jsonObject.get("authorizationCode");
            localModel.setQRMemo(jsonElement.getAsString());
        }

        return localModel.create();
    }

    public HashMap<String, Boolean> parseJson4RegisterStatus(String devRegStatus){
        jsonTree = parser.parse(devRegStatus);
        HashMap<String, Boolean> table = new HashMap<>();

        if(jsonTree.isJsonObject()){
            jsonObject = jsonTree.getAsJsonObject();
            jsonElement = jsonObject.get("isUserDevice");
            table.put("isUserDevice", jsonElement.getAsBoolean());

            jsonElement = jsonObject.get("isDeviceLinked");
            table.put("isDeviceLinked", jsonElement.getAsBoolean());

            jsonElement = jsonObject.get("userHasDevice");
            table.put("userHasDevice", jsonElement.getAsBoolean());

        }

        return table;
    }

}

