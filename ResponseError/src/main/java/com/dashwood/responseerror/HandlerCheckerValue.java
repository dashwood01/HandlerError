package com.dashwood.responseerror;

import android.text.TextUtils;

public class HandlerCheckerValue {

    public static boolean checkEmptyOrNullValue(String value) {
        if (TextUtils.isEmpty(value)) {
            return true;
        }
        if (value.equals("null")) {
            return true;
        }
        return value.equals("NULL");
    }
}
