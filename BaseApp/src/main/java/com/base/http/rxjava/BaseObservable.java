package com.base.http.rxjava;


import com.base.http.common.BaseHttpClient;
import com.base.http.interceptor.RxRetryWithDelay;

import rx.Completable;
import rx.Observable;
import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BaseObservable<T> extends Observable<T> {

    public BaseObservable(OnSubscribe<T> f) {
        super(f);
    }


    @Override
    public Single<T> toSingle() {
        return super.toSingle();
    }

    @Override
    public Completable toCompletable() {
        return super.toCompletable();
    }




    public Observable<T> observableInit(){
        return this.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .retryWhen(new RxRetryWithDelay(BaseHttpClient.getConfiguration().getRetryCount(),
                        BaseHttpClient.getConfiguration().getRetryDelay(), BaseHttpClient.getConfiguration().getRetryIncreaseDelay()))
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread());
    }
}
