package com.base.http.listener.error;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Created by Administrator on 2017/7/17.
 */
public class CodeException {

    /*网络错误*/
    public static final int NETWORD_ERROR = 0x1;
    /*http_错误*/
    public static final int HTTP_ERROR = 0x2;
    /*fastjson错误*/
    public static final int JSON_ERROR = 0x3;
    /*未知错误*/
    public static final int UNKNOWN_ERROR = 0x4;
    /*运行时异常-包含自定义异常*/
    public static final int RUNTIME_ERROR = 0x5;
    /*无法解析该域名*/
    public static final int UNKOWNHOST_ERROR = 0x6;


    /*无法解析该域名*/
    public static final int APP_NETWORK_ERROR = 0x7;

    @IntDef({APP_NETWORK_ERROR,NETWORD_ERROR, HTTP_ERROR, RUNTIME_ERROR, UNKNOWN_ERROR, JSON_ERROR, UNKOWNHOST_ERROR})
    @Retention(RetentionPolicy.SOURCE)

    public @interface CodeEp {
    }
}
