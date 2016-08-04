package com.core.network;

import android.content.Context;

import com.core.network.callback.BaseModelCallback;
import com.core.network.cyclecontext.HttpCycleContext;
import com.core.network.param.RequestParam;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.OkHttpRequestBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.log.LoggerInterceptor;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by admin on 15/12/16.
 */
public class NetworkHelper {

    private static final String HTTP_RESPONSE_DISK_DIR = "HttpResponseCache";      // cache/HttpResponseCache
    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024; //10M
    private static final int DEFAULT_CONNECT_READ_WRITE_TIME_OUT_MILLISECONDS = 10000; // 10s

    private Map<String, String> commonHeads = new HashMap<>();

    private static NetworkHelper instance;

    public static NetworkHelper getInstance() {
        if (instance == null) {
            synchronized (NetworkHelper.class) {
                if (instance == null) {
                    instance = new NetworkHelper();
                }
            }
        }
        return instance;
    }

    public static void init(Context context, boolean debug) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_CONNECT_READ_WRITE_TIME_OUT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.readTimeout(DEFAULT_CONNECT_READ_WRITE_TIME_OUT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.writeTimeout(DEFAULT_CONNECT_READ_WRITE_TIME_OUT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.cache(new Cache(new File(context.getCacheDir(), HTTP_RESPONSE_DISK_DIR), HTTP_RESPONSE_DISK_CACHE_MAX_SIZE));
        builder.cookieJar(new CookieJarImpl(new PersistentCookieStore(context)));
        builder.addInterceptor(new LoggerInterceptor(NetworkHelper.class.getSimpleName(), debug));
        OkHttpClient okHttpClient = builder.build();

        OkHttpUtils.initClient(okHttpClient);
    }

    public static void cancel(HttpCycleContext cycleContext) {
        OkHttpUtils.getInstance().cancelTag(cycleContext.getHttpTaskTag());
    }

    public static RequestCall doGet(HttpCycleContext cycleContext, String url, RequestParam param, BaseModelCallback callback) {

        if (param == null) {
            param = RequestParam.REQUEST_PARAM_DEFAULT;
        }

        RequestCall call = OkHttpUtils.get().tag(cycleContext.getHttpTaskTag()).url(url).params(param.getParams()).build();
        call.execute(callback);
        return call;
    }

    public static RequestCall doPost(HttpCycleContext cycleContext, String url, RequestParam param, BaseModelCallback callback) {

        if (param == null) {
            param = RequestParam.REQUEST_PARAM_DEFAULT;
        }

        RequestCall call = OkHttpUtils.post().tag(cycleContext.getHttpTaskTag()).url(url).params(param.getParams()).build();
        call.execute(callback);
        return call;
    }

    public static RequestCall doPostFile(HttpCycleContext cycleContext, String url, RequestParam param, BaseModelCallback callback) {

        PostFormBuilder builder = OkHttpUtils.post();
        builder.tag(cycleContext.getHttpTaskTag());
        builder.url(url);
        builder.params(param.getParams());

        Set<String> fileNames = param.getParamFiles().keySet();
        for (String fileName : fileNames) {
            builder.addFile("file", fileName, param.getParamFiles().get(fileName));
        }
        RequestCall call = builder.build();
        call.execute(callback);

        return call;
    }

    public static RequestCall downloadFile(HttpCycleContext cycleContext, String fileUrl, FileCallBack callBack) {
        RequestCall call = OkHttpUtils.post().tag(cycleContext.getHttpTaskTag()).url(fileUrl).build();
        call.execute(callBack);
        return call;
    }

    //..........................common参数只适用于实例方法...............................//

    public void setCommonHeads(Map<String , String > headers) {
        this.commonHeads.putAll(headers);
    }

    public void addCommonHead(String key, String value) {
        this.commonHeads.put(key, value);
    }

    public RequestCall doGet2(HttpCycleContext cycleContext, String url, RequestParam param, BaseModelCallback callback) {

        if (param == null) {
            param = RequestParam.REQUEST_PARAM_DEFAULT;
        }

        OkHttpRequestBuilder builder = OkHttpUtils.get().tag(cycleContext.getHttpTaskTag()).url(url).params(param.getParams());
        return executeProcess(builder, callback);
    }

    public RequestCall doPost2(HttpCycleContext cycleContext, String url, RequestParam param, BaseModelCallback callback) {

        if (param == null) {
            param = RequestParam.REQUEST_PARAM_DEFAULT;
        }

        OkHttpRequestBuilder builder = OkHttpUtils.post().tag(cycleContext.getHttpTaskTag()).url(url).params(param.getParams());
        return executeProcess(builder, callback);
    }

    public RequestCall doPostFile2(HttpCycleContext cycleContext, String url, RequestParam param, BaseModelCallback callback) {

        PostFormBuilder builder = OkHttpUtils.post();
        builder.tag(cycleContext.getHttpTaskTag());
        builder.url(url);
        builder.params(param.getParams());

        Set<String> fileNames = param.getParamFiles().keySet();
        for (String fileName : fileNames) {
            builder.addFile("file", fileName, param.getParamFiles().get(fileName));
        }
        return executeProcess(builder, callback);
    }

    public RequestCall downloadFile2(HttpCycleContext cycleContext, String fileUrl, FileCallBack callBack) {
        OkHttpRequestBuilder builder = OkHttpUtils.post().tag(cycleContext.getHttpTaskTag()).url(fileUrl);
        return executeProcess(builder, callBack);
    }

    private RequestCall executeProcess(OkHttpRequestBuilder builder, Callback callback) {

        RequestCall call = null;
        try {
            builder.headers(this.commonHeads);
            call = builder.build();
            call.execute(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return call;
    }
}
