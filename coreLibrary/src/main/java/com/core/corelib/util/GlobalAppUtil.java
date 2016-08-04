package com.core.corelib.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.core.corelib.constant.GlobalConstant;


/**
 * Created by admin on 15/12/29.
 */
public class GlobalAppUtil {


    protected static GlobalAppUtil mInstance;

    protected static Context mContext;

    protected static SharedPreferences mPreferences;
    protected static SharedPreferences.Editor mEditor;

    private static final String USER_ID_KEY = "user_id_key";
    private static final String USER_TOKEN_KEY = "user_token_key";

    protected GlobalAppUtil(){
        if (mContext == null) {
            throw new NullPointerException("please invoke AppUtil.init() method");
        }
        mPreferences = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public static GlobalAppUtil getInstance() {
        if (mInstance == null) {
            synchronized (GlobalAppUtil.class) {
                if (mInstance == null) {
                    mInstance = new GlobalAppUtil();
                }
            }
        }
        return mInstance;
    }

    public static void init(Context context) {
        mContext = context;
    }

    public Context getApplicationContext() {
        return mContext;
    }

    public boolean setUserId(String userId) {
        return mEditor.putString(USER_ID_KEY, userId).commit();
    }

    public String getUserId() {
        return mPreferences.getString(USER_ID_KEY, null);
    }

    public boolean setUserToken(String token) {
        return mEditor.putString(USER_TOKEN_KEY, token).commit();
    }

    public String getUserToken() {
        return mPreferences.getString(USER_TOKEN_KEY, null);
    }

    public boolean isLogin() {

        return getUserToken() != null;
    }

    public boolean put(String key, String value) {
        return mEditor.putString(key, value).commit();
    }

    public boolean put(String key, boolean value) {
        return mEditor.putBoolean(key, value).commit();
    }

    public boolean put(String key, int value) {
        return mEditor.putInt(key, value).commit();
    }

    public boolean put(String key, float value) {
        return mEditor.putFloat(key, value).commit();
    }

    public boolean put(String key, long value) {
        return mEditor.putLong(key, value).commit();
    }

    public PackageInfo getPackageInfo() {
        PackageManager packageManager = mContext.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }

    public void logout() {
        setUserId(null);
        setUserToken(null);
        Intent i = new Intent();
        i.setAction(GlobalConstant.USER_LOGOUT_ACTION);
        mContext.sendBroadcast(i);
    }

}
