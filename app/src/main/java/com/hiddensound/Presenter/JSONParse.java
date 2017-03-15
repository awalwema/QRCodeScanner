package com.hiddensound.Presenter;



import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hiddensound.model.HiddenModel;
import com.hiddensound.model.ModelController;
import com.hiddensound.model.ModelInterface;

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
            localModel.setToken(jsonElement.toString());

            jsonElement = jsonObject.get("expires_in");
            localModel.setTokenTime(Long.parseLong(jsonElement.toString()));
        }
        return localModel.create();
    }

    public HiddenModel parseJson4Decoder(String QRMemo){
        jsonTree = parser.parse(QRMemo);

        if(jsonTree.isJsonObject()){
            jsonObject = jsonTree.getAsJsonObject();
            jsonElement = jsonObject.get("applicationName");
            localModel.setAppName(jsonElement.toString());

            jsonElement = jsonObject.get("authorizationCode");
            localModel.setQRMemo(jsonElement.toString());
        }

        return localModel.create();
    }

}

