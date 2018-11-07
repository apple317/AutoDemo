package com.base.http.listener.error;

/**
 * Created by applehsp on 2017/7/17.
 */

public class HttpException  {
    /*错误码*/
    private int code;
    /*显示的信息*/
    private String displayMessage;
    public Throwable throwable;
    public HttpException(Throwable e) {
        throwable=e;
    }

    public HttpException(Throwable cause,int code, String showMsg) {
        throwable=cause;
        setCode(code);
        setDisplayMessage(showMsg);
    }

    public HttpException(@CodeException.CodeEp int code) {
        setCode(code);
    }

    @CodeException.CodeEp
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }
}
