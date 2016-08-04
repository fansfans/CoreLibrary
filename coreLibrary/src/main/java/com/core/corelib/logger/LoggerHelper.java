package com.core.corelib.logger;

import com.orhanobut.logger.Logger;

/**
 * Created by admin on 16/1/30.
 */
public class LoggerHelper {

    private static final String DEFALUT_TAG = LoggerHelper.class.getSimpleName();

    public static void init() {
        Logger.init(DEFALUT_TAG);
    }

    public static void d(String message, Object... args) {
        Logger.d(message, args);
    }

    public static void e(String message, Object... args) {
        Logger.e(message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        Logger.e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        Logger.i(message, args);
    }

    public static void v(String message, Object... args) {
        Logger.v(message, args);
    }

    public static void w(String message, Object... args) {
        Logger.w(message, args);
    }

    public static void wtf(String message, Object... args) {
        Logger.wtf(message, args);
    }

    public static void json(String json) {
        Logger.json(json);
    }

    public static void xml(String xml) {
        Logger.xml(xml);
    }

//    public static void clear() {
//        Logger.clear();

//    }

    public static void t(String tag) {
        Logger.t(tag);
    }

    public static void t(int methodCount) {
        Logger.t(methodCount);
    }

    public static void t(String tag, int methodCount) {
        Logger.t(tag, methodCount);
    }
}
