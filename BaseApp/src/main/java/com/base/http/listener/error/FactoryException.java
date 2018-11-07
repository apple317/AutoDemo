package com.base.http.listener.error;

import android.net.ParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by applehsp on 2017/7/17.
 */

public class FactoryException {
    private static final String HttpException_MSG = "网络错误";
    private static final String ConnectException_MSG = "连接失败";
    private static final String JSONException_MSG = "解析失败";
    private static final String UnknownHostException_MSG = "无法解析该域名";

    /**
     * 解析异常
     *
     * @param e
     * @return
     */
    public static HttpException analysisExcetpion(Throwable e) {
        HttpException apiException = new HttpException(e);
        if (e instanceof HttpTimeException) {
             /*自定义运行时异常*/
            HttpTimeException exception = (HttpTimeException) e;
            apiException.setCode(CodeException.RUNTIME_ERROR);
            apiException.setDisplayMessage(exception.getMessage());
        } else if (e instanceof ConnectException ||e instanceof SocketTimeoutException) {
             /*链接异常*/
            apiException.setCode(CodeException.HTTP_ERROR);
            apiException.setDisplayMessage(ConnectException_MSG);
        } else if (e instanceof JSONException || e instanceof ParseException) {
             /*fastjson解析异常*/
            apiException.setCode(CodeException.JSON_ERROR);
            apiException.setDisplayMessage(JSONException_MSG);
        }else if (e instanceof UnknownHostException){
            /*无法解析该域名异常*/
            apiException.setCode(CodeException.UNKOWNHOST_ERROR);
            apiException.setDisplayMessage(UnknownHostException_MSG);
        }else if(e instanceof AppException){
            apiException.setCode(CodeException.UNKOWNHOST_ERROR);
            apiException.setDisplayMessage(e.getMessage());
        }else {
            /*未知异常*/
            apiException.setCode(CodeException.NETWORD_ERROR);
            apiException.setDisplayMessage(HttpException_MSG);
        }
        return apiException;
    }



}
