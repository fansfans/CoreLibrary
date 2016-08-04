package com.core.network.exception;

/**
 * Created by admin on 16/6/24.
 */
public class NetworkException extends Exception {


    public NetworkException(){}

    public NetworkException(String detailMessage) {
        super(detailMessage);
    }

    public NetworkException(String detailMessage, int errCode) {
        super(detailMessage);
    }
}
