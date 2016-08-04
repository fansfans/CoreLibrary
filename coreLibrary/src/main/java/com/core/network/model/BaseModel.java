package com.core.network.model;

/**
 * Created by admin on 15/12/17.
 */
public class BaseModel {

    public int code;
    public String msg;

    public boolean isSuccess() {
        return code == 1;
    }
}
