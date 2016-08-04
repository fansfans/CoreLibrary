package com.core.common.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by admin on 16/1/27.
 */
public class CheckValidityUtil {

    public static boolean checkTeleValidation(String tele) {
        if (TextUtils.isEmpty(tele)) {
            return false;
        }
        return tele.matches("[0-9]{11}");
    }

    public static boolean checkPasswordValidation(String password) {
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        return password.matches("[a-zA-Z0-9]{6,20}");
    }

    public static boolean checkIdCardValidation(String idCard) {

        if (TextUtils.isEmpty(idCard)) {
            return false;
        }
        Pattern p15 = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
        Pattern p18 = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$");
        return p15.matcher(idCard).matches() || p18.matcher(idCard).matches();
    }
}
