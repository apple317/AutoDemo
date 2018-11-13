package demoapp.lotter.com.hellodemo.presenter;

import com.base.http.listener.error.HttpException;
import com.base.http.rxjava.RxResSubscriber;

import demoapp.lotter.com.common.baseview.AppleApplication;
import demoapp.lotter.com.common.drager.AppApiHelper;
import demoapp.lotter.com.hellodemo.contract.IHomeFragmentContract;
import demoapp.lotter.com.hellodemo.entity.DataEntity;
import rx.functions.Func1;

/**
 * yangyoupeng  on 2018/5/17.
 */

public class FrameworkActivityPresenter extends IHomeFragmentContract.Presenter {

    private static final String TAG = "FrameworkActivityPresen";

    private AppApiHelper appApiHelper;

    public FrameworkActivityPresenter() {
        appApiHelper = AppleApplication.getAppComponent().getApiHelper();
    }


    @Override
    public void getAuthCodeNew() {
//        AppQueryMap headMap= new AppQueryMap();
//        headMap.put("name","d");
        // BaseHttpClient.getBaseClient().createApi(ApiService.class).getAuthCodeNew("0")
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .retryWhen(new RxRetryWithDelay(BaseHttpClient.getConfiguration().getRetryCount(),
//                        BaseHttpClient.getConfiguration().getRetryDelay(), BaseHttpClient.getConfiguration().getRetryIncreaseDelay()))
//                /*回调线程*/
//                .observeOn(AndroidSchedulers.mainThread())
        appApiHelper.createApiService().getAuthCodeNew("0").compose(appApiHelper.handleResult())
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
