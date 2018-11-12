package demoapp.lotter.com.hellodemo.presenter;

import com.base.http.common.BaseHttpClient;
import com.base.http.impl.BaseHttpImpl;
import com.base.http.interceptor.RxRetryWithDelay;
import com.base.http.listener.error.HttpException;
import com.base.http.rxjava.RxResSubscriber;

import demoapp.lotter.com.common.http.ApiService;
import demoapp.lotter.com.common.http.AppQueryMap;
import demoapp.lotter.com.common.http.HttpResultFunc;
import demoapp.lotter.com.hellodemo.contract.IHomeFragmentContract;
import demoapp.lotter.com.hellodemo.entity.DataEntity;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * yangyoupeng  on 2018/5/17.
 */

public class FrameworkActivityPresenter extends IHomeFragmentContract.Presenter {
    private static final String TAG = "FrameworkActivityPresen";

    @Override
    public void getAuthCodeNew() {
        BaseHttpImpl baseHttp = BaseHttpClient.getBaseClient().getHttpImpl();
        AppQueryMap headMap= new AppQueryMap();
        headMap.put("name","d");
        BaseHttpClient.getBaseClient().createApi(ApiService.class).getAuthCodeNew("0",headMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .retryWhen(new RxRetryWithDelay(BaseHttpClient.getConfiguration().getRetryCount(),
                        BaseHttpClient.getConfiguration().getRetryDelay(), BaseHttpClient.getConfiguration().getRetryIncreaseDelay()))
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<DataEntity>())
                .compose(getView().bindLifeycle())
                .map(new Func1<DataEntity, String>() {
                    @Override
                    public String call(DataEntity dataEntityAppBaseEntity) {
                        return dataEntityAppBaseEntity.getVerification_code();
                    }
                })
                .subscribe(new RxResSubscriber<String>() {
                    @Override
                    public void onError(HttpException exception) {
//                        new android.os.Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                            }
//                        },1000);
                        getView().showAuthCode(exception.getDisplayMessage());
                    }

                    @Override
                    public void onNextData(String t) {
                        getView().showAuthCode(t);
                    }
                });
    }
}
