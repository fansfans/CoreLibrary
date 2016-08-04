package com.core.network.param;

import com.core.corelib.util.GlobalAppUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 15/12/16.
 */
public class RequestParam {

    private Map<String , String> mParams = new HashMap<>();
    private Map<String , File> mParamFiles = new HashMap<>();

    public RequestParam() {
    }

    public RequestParam put(String key, String value) {
        mParams.put(key,value);
        return this;
    }

    public RequestParam put(String key, int value) {
        mParams.put(key, String.valueOf(value));
        return this;
    }

    public RequestParam put(String key, long value) {
        mParams.put(key, String.valueOf(value));
        return this;
    }

    public RequestParam put(String key, float value) {
        mParams.put(key, String.valueOf(value));
        return this;
    }

    public RequestParam put(String key, double value) {
        mParams.put(key, String.valueOf(value));
        return this;
    }

    public RequestParam putFile(String fileName, File file) {
        mParamFiles.put(fileName, file);
        return this;
    }

    /**
     * 添加token
     *
     * @return
     */
    public Map<String , String> getParams() {
        if (GlobalAppUtil.getInstance().getUserToken() != null) {
            mParams.put("token", GlobalAppUtil.getInstance().getUserToken());
            mParams.put("source", String.valueOf(2));
        }
        return mParams;
    }

    public Map<String , File> getParamFiles() {
        return mParamFiles;
    }

    public static RequestParam REQUEST_PARAM_DEFAULT = new RequestParam();

}
