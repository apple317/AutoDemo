package demoapp.lotter.com.hellodemo.presenter;

import com.base.http.listener.error.HttpException;
import com.base.http.retrofit.Retrofit;
import com.base.http.rxjava.RxResSubscriber;

import demoapp.lotter.com.hellodemo.ApiService;
import demoapp.lotter.com.hellodemo.DataEntity;
import demoapp.lotter.com.hellodemo.HttpParams;
import demoapp.lotter.com.hellodemo.HttpResultFunc;
import demoapp.lotter.com.hellodemo.contract.IHomeFragmentContract;
import rx.functions.Func1;

/**
 * yangyoupeng  on 2018/5/17.
 */

public class FrameworkActivityPresenter extends IHomeFragmentContract.Presenter {
    private static final String TAG = "FrameworkActivityPresen";

    @Override
    public void getAuthCodeNew() {
        new Retrofit().create(ApiService.class).getAuthCodeNew(
                new HttpParams.Builder()
                        .build()).observableInit()
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
                        getView().showAuthCode(exception.getDisplayMessage());
                    }

                    @Override
                    public void onNextData(String t) {
                        getView().showAuthCode(t);
                    }
                });
    }
}
