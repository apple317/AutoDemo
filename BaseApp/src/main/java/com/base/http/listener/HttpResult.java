package com.base.http.listener;


import android.support.annotation.NonNull;

/**
 * Created by applehsp on 2017/7/17
 */

public class HttpResult<T> {
    @NonNull
    public  T parse;
    //http 返回code
    public  int httCode;
    //http 返回网络内容
    public  String httpResultContent;
    //服务器返回token
    public String token;
    //网络申请标记
    public String tag;
    //http返回数据中的code
    public  int httDataCode;
    //http返回数据中的msg
    public  int httDataMsg;

    //当前写入的字节数
    public long bytesRead;
    //当前总字节数
    public long contentLength;
}
