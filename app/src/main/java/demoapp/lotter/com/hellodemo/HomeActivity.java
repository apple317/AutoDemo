package demoapp.lotter.com.hellodemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.base.mvp.annotations.CreatePresenterAnnotation;

import butterknife.BindView;
import butterknife.OnClick;
import demoapp.lotter.com.hellodemo.contract.IHomeFragmentContract;
import demoapp.lotter.com.hellodemo.presenter.FrameworkActivityPresenter;


@CreatePresenterAnnotation(FrameworkActivityPresenter.class)
public class HomeActivity extends BaseAppActivity <IHomeFragmentContract.View, FrameworkActivityPresenter>
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
        startActivity(new Intent(this,MainActivity.class));
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
        Log.e("HU","code===========");
    }

    @Override
    protected void onDestroy() {
        Log.e("HU","onDestroy===========");
        super.onDestroy();
    }
}
