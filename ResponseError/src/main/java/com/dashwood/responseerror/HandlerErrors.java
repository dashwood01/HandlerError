package com.dashwood.responseerror;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * DASHWOOD create this class
 * Handler Error
 * When u using volly module and get error u must handler that and u can use this class
 */
public class HandlerErrors {

    /**
     * If u know error u get where u send and what that key and u want get that use this method
     *
     * @param jsonObject get jsonObject for checking what inside
     * @param valueKey   value Key mean what key u want and get result that key
     * @return String return message that key and if string key u pass this method not in json data method return to u 'nothing'
     */
    String getMessageErrorWithKey(JSONObject jsonObject, String valueKey) {
        Iterator<String> inter = jsonObject.keys();
        while (inter.hasNext()) {
            String key = inter.next();
            try {
                if (valueKey.equals(key)) {
                    return jsonObject.getString(key);
                } else if (key.equals("errors")) {
                    return getStringJsonWithKey(jsonObject, valueKey, key);
                } else if (key.equals("Status")) {
                    return getStringJsonWithKey(jsonObject, valueKey, key);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return "Nothing";
    }

    /**
     * When u don't know key json, use this method
     *
     * @param jsonObject send json as jsonObject to checking
     * @return string last message in json
     */
    public String getMessageErrorWithoutKey(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) {
            return "";
        }
        Iterator<String> inter = jsonObject.keys();
        while (inter.hasNext()) {
            String key = inter.next();
            if (key.equals("errors")) {
                return getStringJsonWithoutKey(jsonObject, key);
            } else if (key.equals("error")) {
                return getStringJsonWithoutKey(jsonObject, key);
            }
        }
        return getErrorMessage(jsonObject);
    }

    private String getErrorMessage(JSONObject jsonObject) {
        if (jsonObject.has("message")) {
            return jsonObject.optString("message");
        } else if (jsonObject.has("error")) {
            return jsonObject.optString("error");
        } else if (jsonObject.has("errors")) {
            return jsonObject.optString("errors");
        }
        return jsonObject.toString();
    }

    /**
     * send String and get jsonObject
     *
     * @param value must something like json if u send something bad this method use JsonException and log to u Error
     * @return return JsonObject as String value
     * @throws JSONException when u send bad String here run and log to u error
     */
    private JSONObject getJsonObject(String value) throws JSONException {
        return new JSONObject(value);
    }

    /**
     * send String value and get JsonArray and u must to know that if send something bad jsonException run
     *
     * @param value String json value
     * @return get String jsonArray value and return jsonarray
     * @throws JSONException when u pass String bad value this method running
     */
    private JSONArray getJsonArray(String value) throws JSONException {
        return new JSONArray(value);
    }

    /**
     * When u key inside Error Json this method call and send key and jsonObject and Error key
     *
     * @param jsonObject send JsonObject Error for checking u key inside that
     * @param valueKey   key we want get message
     * @param key        always errors for now
     * @return return message key as String
     * @throws JSONException when some bad happens
     *                       And if not find key return Nothing
     */
    private String getStringJsonWithKey(JSONObject jsonObject, String valueKey, String key) throws JSONException {
        JSONObject jObject = getJsonObject(jsonObject.getString(key));
        Iterator<String> iterator = jObject.keys();
        while (iterator.hasNext()) {
            String sKey = iterator.next();
            if (!valueKey.equals(sKey)) {
                continue;
            }
            if (isJSONValid(jObject.getString(sKey))) {
                JSONArray jsonArray = getJsonArray(jObject.getString(sKey));
                return jsonArray.getString(0);
            } else {
                return jObject.getString(sKey);
            }
        }
        return "nothing";
    }

    /**
     * if u dont know key and json have Errors key this method call to get first result inside errors json
     *
     * @param jsonObject send jsonObject that error json
     * @param key        for now always errors
     * @return return first message key inside errors
     * @throws JSONException when something wrong
     */
    private String getStringJsonWithoutKey(JSONObject jsonObject, String key) throws JSONException {
        JSONObject jObject = getJsonObject(jsonObject.getString(key));
        Iterator<String> iterator = jObject.keys();
        String sKey = iterator.next();
        if (checkJsonArray(jObject, sKey)) {
            JSONArray jsonArray = getJsonArray(jObject.getString(sKey));
            return jsonArray.getString(0);
        }
        if (checkJsonObject(jObject.getString(sKey))) {
            JSONObject jMessage = jObject.getJSONObject(sKey);
            Iterator<String> itKeys = jMessage.keys();
            return getAllMessage(jMessage, itKeys);
        }
        return jObject.getString(sKey);
    }

    /**
     * This method check all message in json and get all of them
     *
     * @param jsonObject
     * @param keys
     * @return
     * @throws JSONException
     */
    private String getAllMessage(JSONObject jsonObject, Iterator<String> keys) throws JSONException {
        StringBuilder stringBuilder = new StringBuilder();
        while (keys.hasNext()) {
            String mKey = keys.next();
            String message = jsonObject.optString(mKey);
            if (checkStringJsonArray(message)) {
                message = jsonObject.getJSONArray(mKey).getString(0);
            }
            stringBuilder.append(message);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private boolean checkJsonArray(JSONObject jsonObject, String key) {
        return jsonObject.optJSONArray(key) != null;
    }

    private boolean checkJsonObject(String jsonObject) {
        try {
            new JSONObject(jsonObject);
            return true;
        } catch (JSONException e) {
            //e.printStackTrace();
            return false;
        }
    }

    protected boolean checkStringJsonArray(String jsonArray) {
        try {
            new JSONArray(jsonArray);
            return true;
        } catch (JSONException e) {
            //e.printStackTrace();
            return false;
        }
    }

    /**
     * U must checking this string i pass to this class is a Json or not
     *
     * @param value get string value like json
     * @return checking string value if this string json or jsonarray return some boolean
     */
    private boolean isJSONValid(String value) {
        try {
            new JSONObject(value);
        } catch (JSONException ex) {
            try {
                new JSONArray(value);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
}
