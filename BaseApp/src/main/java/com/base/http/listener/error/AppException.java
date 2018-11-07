package com.base.http.listener.error;

/**
 * Created by Administrator on 2017/7/17.
 */

public class AppException extends Throwable {
    public AppException() {
        super();
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
