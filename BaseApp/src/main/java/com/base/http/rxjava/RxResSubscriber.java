package com.base.http.rxjava;

import com.base.http.listener.error.FactoryException;
import com.base.http.listener.error.HttpException;

import rx.Subscriber;

/**
 * des:订阅封装
 * Created by xsf
 * on 2016.09.10:16
 */

/********************使用例子********************/
public  class RxResSubscriber<T> extends Subscriber<T> {


    public RxResSubscriber() {
    }


    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        onError(FactoryException.analysisExcetpion(e));
    }



    @Override
    public void onNext(T tBaseEntity) {
        onNextData(tBaseEntity);
    }

    public void onNextData(T t){

    }


    public void onError(HttpException exception){

    }

}
