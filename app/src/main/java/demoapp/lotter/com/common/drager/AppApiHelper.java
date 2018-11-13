package demoapp.lotter.com.common.drager;


import android.app.Application;

import com.base.data.ApiHelper;
import com.base.http.common.BaseHttpClient;
import com.base.http.common.HttpConfiguration;
import com.base.http.cookie.okhttp.CookieJarImpl;
import com.base.http.cookie.okhttp.PersistentCookieStore;
import com.base.http.impl.okhttp.OkHttpImpl;
import com.base.http.listener.error.AppException;
import com.base.http.rxjava.RxHelper;
import com.base.utils.LogUtil;

import javax.inject.Inject;

import demoapp.lotter.com.common.http.ApiService;
import demoapp.lotter.com.common.http.BaseResultEntity;
import demoapp.lotter.com.common.http.Constants;
import demoapp.lotter.com.hellodemo.BuildConfig;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by SeanLiu on 17/6/16.
 */
public class AppApiHelper implements ApiHelper {


    public OkHttpImpl baseHttp;

    public RxHelper rxHelper;

    @Inject
    public AppApiHelper() {
        baseHttp = getBaseHttp();
        rxHelper=new RxHelper();
    }


    public void initHttp(Application context) {
        LogUtil.isOpen = BuildConfig.LOTTERY_ALPHA ? true : false;
        HttpConfiguration.Builder configuration = new HttpConfiguration.Builder(context);
        configuration.retryOnConnectionFailure(true);
        configuration.diskCacheSize(1000 * 1024);
        configuration.setBaseUrl(Constants.getBaseUrl(context));
        configuration.setCookieJar(new CookieJarImpl(new PersistentCookieStore(context)));
        configuration.diskCacheDir(context.getCacheDir());
        configuration.setOpenLog(BuildConfig.LOTTERY_ALPHA ? true : false);
        BaseHttpClient baseHttpClient = BaseHttpClient.getBaseClient();
        baseHttpClient.init(configuration.build());
    }


    public OkHttpImpl getBaseHttp() {
        return BaseHttpClient.getBaseClient().getHttpImpl();
    }



    /**
     * 对结果进行预处理
     *
     * @param <T>
     * @return
     */
    public  <T> Observable.Transformer<BaseResultEntity<T>, T> handleResult() {
        return new Observable.Transformer<BaseResultEntity<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseResultEntity<T>> tObservable) {
                return tObservable.flatMap(new Func1<BaseResultEntity<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(BaseResultEntity<T> result) {
                        if (result.getCode() == 200) {
                            return createData(result.getData());
                        } else {
                            return Observable.error(new AppException(result.Msg));
                        }
                    }
                }).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 创建成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    private  <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }






    public ApiService createApiService() {
        return baseHttp.createApi(ApiService.class);
    }


//    public T observableInit(Observable<T> observable) {
//        return observable.subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .retryWhen(new RxRetryWithDelay(BaseHttpClient.getConfiguration().getRetryCount(),
//                        BaseHttpClient.getConfiguration().getRetryDelay(), BaseHttpClient.getConfiguration().getRetryIncreaseDelay()))
//                /*回调线程*/
//                .observeOn(AndroidSchedulers.mainThread());
//    }

}
