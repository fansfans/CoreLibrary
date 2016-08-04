package com.core.network.callback;

import android.text.TextUtils;

import com.core.corelib.json.JsonHelper;
import com.core.network.exception.NetworkException;
import com.core.network.model.BaseModel;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.Call;

/**
 * Created by admin on 15/12/17.
 */
public abstract class BaseModelCallback <T extends BaseModel> extends Callback<T>{


    @Override
    public T parseNetworkResponse(okhttp3.Response response, int id) throws Exception {
        String s = response.body().string();
        if (TextUtils.isEmpty(s)) {

            throw new NetworkException("返回数据空值");
        } else {

            ParameterizedType parameterizedType = null;
            try {
                parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
            } catch (ClassCastException e) {
                e.printStackTrace();
                throw new NetworkException("参数类型转换异常 client error:-1");
            }

            Class<T> entityClass = null;
            try {
                entityClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
            } catch (ClassCastException e) {
                e.printStackTrace();
                throw new NetworkException("字节码类型转换异常 client error:-2");
            }

            T bean = null;
            try {
                bean = JsonHelper.deserialize(s, entityClass);
            } catch (Exception e) {
                throw new NetworkException("数据解析异常");
            }
            return bean;
        }
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        if (call.isCanceled()) {
            return;
        }
        if (e instanceof SocketTimeoutException) {

            onFailure("连接超时");
        } else if (e instanceof ConnectException){

            onFailure("连接异常");
        } else if (e instanceof NetworkException) {

            onFailure(e.getMessage());
        } else if (e instanceof IOException) {

            onFailure("返回数据异常");
        } else if (e instanceof RuntimeException) {

            onFailure("返回状态异常");
        } else if (e instanceof Exception) {

            onFailure("未知异常来源");
        }
    }

    @Override
    public final void onResponse(T response, int id) {

        if (response == null) {
            return;
        }
        if (response.isSuccess()) {
            onSuccess(response);
        } else {
            onFailure(response.msg);
        }
    }

    protected abstract void onFailure(String errorMsg);

    protected abstract void onSuccess(T model);

    protected Class getModelClazz(){
        return BaseModel.class;
    };


}
