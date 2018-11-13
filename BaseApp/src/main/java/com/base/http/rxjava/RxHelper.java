package com.base.http.rxjava;


import com.base.http.common.BaseHttpClient;
import com.base.http.interceptor.RxRetryWithDelay;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxHelper<T> {

    public  Observable<T> observableInit(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .retryWhen(new RxRetryWithDelay(BaseHttpClient.getConfiguration().getRetryCount(),
                        BaseHttpClient.getConfiguration().getRetryDelay(), BaseHttpClient.getConfiguration().getRetryIncreaseDelay()))
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread());
    }
}
