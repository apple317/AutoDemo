package com.base.http.rxjava;

import com.base.http.common.BaseHttpClient;
import com.base.http.entity.BaseEntity;
import com.base.http.gsonfactory.CustomGsonConverterFactory;
import com.base.http.listener.error.AppException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * des:订阅封装
 * Created by xsf
 * on 2016.09.10:16
 */

/********************使用例子********************/
public abstract class RxSubscriber<T> implements Observable.OnSubscribe<T> {
    BaseHttpClient baseHttpClient;

    public RxSubscriber(BaseHttpClient httpClient) {
        this.baseHttpClient=httpClient;
    }


    @Override
    public void call(Subscriber<? super T> subscriber) {
        baseHttpClient.execute(baseHttpClient, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                subscriber.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    T t = (T) CustomGsonConverterFactory.create().responseBodyConverter(baseHttpClient.getType()).convert(response.body());
                    BaseEntity<T> baseEntity=((BaseEntity<T>)t);
                    if(((BaseEntity<T>)t).isSussess()){
                        subscriber.onNext(t);
                    }else{
                        subscriber.onError(new AppException(baseEntity.Msg));
                    }
                }else{
                    subscriber.onError(new Throwable(response.message()));
                }
            }
        });
    }
}
