package com.base.http.common;


import com.base.utils.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/19.
 */

public class RetryWithDelay implements Interceptor {

    public int maxRetry;//最大重试次数
    private int retryNum = 0;//假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）

    public RetryWithDelay(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        System.out.println("retryNum=" + retryNum);
        Response response = doRequest(chain, request);
        LogUtil.e("Lottery", "RetryWithDelay - parse:" + retryNum);
        while (!response.isSuccessful() && retryNum < maxRetry) {
            retryNum++;
            LogUtil.e("Lottery", "RetryWithDelay - while - parse:" + retryNum);
        }
        return response;
    }

    private Response doRequest(Chain chain, Request request) {
        Response response = null;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
        }
        return response;
    }

}

