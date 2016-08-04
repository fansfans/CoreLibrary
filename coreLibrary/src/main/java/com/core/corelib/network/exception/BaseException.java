package com.core.corelib.network.exception;

/**
 * Created by admin on 16/6/24.
 */
public class BaseException extends Exception {


    public BaseException(){}

    public BaseException(String detailMessage) {
        super(detailMessage);
    }

    public BaseException(String detailMessage, int errCode) {
        super(detailMessage);
    }
}
