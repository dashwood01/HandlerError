package com.dashwood.responseerror;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class ResponseError {
    private static HandlerErrors handlerErrors = new HandlerErrors();

    public static String errorResponding(String value, VolleyError error
            , Context context) {
        NetworkResponse networkResponse = error.networkResponse;
        if (networkResponse == null) {
            setError(error, context);
            return context.getString(R.string.toast_text_error_server_not_online);
        }
        String jsonError = new String(networkResponse.data);
        try {
            JSONObject jObject = new JSONObject(jsonError);
            if (!HandlerCheckerValue.checkEmptyOrNullValue(value)) {
                return handlerErrors.getMessageErrorWithKey(jObject, value);
            } else {
                return handlerErrors.getMessageErrorWithoutKey(jObject);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return setError(error, context);
    }

    public static String errorResponding(String value, JSONObject jObject
            , Context context) {
        try {
            if (!HandlerCheckerValue.checkEmptyOrNullValue(value)) {
                return handlerErrors.getMessageErrorWithKey(jObject, value);
            } else {
                return handlerErrors.getMessageErrorWithoutKey(jObject);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return context.getString(R.string.toast_text_error_network_not_online);
    }

    private static String setError(VolleyError error, Context context) {
        if (error instanceof NetworkError) {
            return context.getString(R.string.toast_text_error_network_not_online);
        } else if (error instanceof ServerError) {
            return context.getString(R.string.toast_text_error_server_not_online);
        } else if (error instanceof AuthFailureError) {
            return context.getString(R.string.toast_text_error_network_not_online);
        } else if (error instanceof ParseError) {
            return context.getString(R.string.toast_text_error_server_not_online);
        }
        if (error instanceof TimeoutError) {
            return context.getString(R.string.toast_text_error_network_not_online);
        }
        return "None";
    }

}