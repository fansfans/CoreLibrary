package com.core.network.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 16/3/7.
 */
public class UserAgentInterceptor implements Interceptor {

    private Map<String, String> mHeaders = new HashMap<>();

    public UserAgentInterceptor(String key, String value) {
        mHeaders.put(key, value);
    }

    public UserAgentInterceptor(Map<String, String> headers) {
        mHeaders.putAll(headers);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        final Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();

        for(Map.Entry<String, String> entry : mHeaders.entrySet()) {
            builder.removeHeader(entry.getKey());
            builder.addHeader(entry.getKey(), entry.getValue());
        }

        final Request requestWithUserAgent = builder.build();

        return chain.proceed(requestWithUserAgent);
    }
}
