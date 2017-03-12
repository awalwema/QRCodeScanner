package com.hiddensound.Presenter;



import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hiddensound.model.HiddenModel;
import com.hiddensound.model.ModelController;
import com.hiddensound.model.ModelInterface;

import java.util.HashMap;

class JSONParse {
    private ModelInterface localModel;

    HiddenModel parseJson(String jsonElement) {
//        HashMap<String, String> jsonResult = new HashMap<>();
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(jsonElement);
        if (jsonTree.isJsonObject()) {


            JsonObject jsonObject = jsonTree.getAsJsonObject();
//            JsonElement tokenType = jsonObject.get("token_type");
            JsonElement token = jsonObject.get("access_token");
            JsonElement expiresIn = jsonObject.get("expires_in");
//            jsonResult.put("tokenType", tokenType.toString());
//            jsonResult.put("token", token.toString());
//            jsonResult.put("expiresIn", expiresIn.toString());
            localModel = new ModelController();
            localModel.setToken(token.toString());
            localModel.setTokenTime(Long.parseLong(expiresIn.toString()));





        }
//        return jsonResult;
        return localModel.create();

    }

}

