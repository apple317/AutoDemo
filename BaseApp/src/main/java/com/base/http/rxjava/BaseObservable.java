package com.base.http.rxjava;


import rx.Completable;
import rx.Observable;
import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

public class BaseObservable<T> extends Observable<T> {

    protected BaseObservable(OnSubscribe<T> f) {
        super(f);
    }


    public static <T> BaseObservable<T> create(OnSubscribe<T> f) {
        return new BaseObservable<T>(RxJavaHooks.onCreate(f));
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
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread());
    }
}
