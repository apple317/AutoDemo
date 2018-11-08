package demoapp.lotter.com.hellodemo;

import android.os.Bundle;
import android.widget.TextView;

import com.base.mvp.annotations.CreatePresenterAnnotation;

import butterknife.BindView;
import butterknife.OnClick;
import demoapp.lotter.com.hellodemo.contract.IHomeFragmentContract;
import demoapp.lotter.com.hellodemo.presenter.FrameworkActivityPresenter;



@CreatePresenterAnnotation(FrameworkActivityPresenter.class)
public class MainActivity extends BaseAppActivity <IHomeFragmentContract.View, FrameworkActivityPresenter>
        implements IHomeFragmentContract.View{


    @BindView(R.id.kkk)
    TextView kkk;


    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @OnClick(R.id.kkk)
    public void onClick() {
        getMvpPresenter().getAuthCodeNew();
//        new Retrofit().create(ApiService.class).getAuthCodeNew(
//                new HttpParams.Builder()
//                        .build()).observableInit()
//                .map(new HttpResultFunc<DataEntity>())
//                .map(new Func1<DataEntity, String>() {
//                    @Override
//                    public String call(DataEntity dataEntityAppBaseEntity) {
//                        return dataEntityAppBaseEntity.getVerification_code();
//                    }
//                })
//                .subscribe(new RxResSubscriber<String>() {
//                    @Override
//                    public void onError(HttpException exception) {
//                        kkk.setText(exception.getDisplayMessage() + "");
//                    }
//
//                    @Override
//                    public void onNextData(String t) {
//                        kkk.setText(t + "");
//                    }
//                });
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showAuthCode(String code) {
        kkk.setText(code);
    }
}
