package demoapp.lotter.com.hellodemo.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.base.mvp.annotations.CreatePresenterAnnotation;

import butterknife.BindView;
import butterknife.OnClick;
import demoapp.lotter.com.common.baseview.BaseAppActivity;
import demoapp.lotter.com.hellodemo.R;
import demoapp.lotter.com.hellodemo.contract.IHomeFragmentContract;
import demoapp.lotter.com.hellodemo.presenter.FrameworkActivityPresenter;


@CreatePresenterAnnotation(FrameworkActivityPresenter.class)
public class MainActivity extends BaseAppActivity<IHomeFragmentContract.View, FrameworkActivityPresenter>
        implements IHomeFragmentContract.View {


    @BindView(R.id.kkk)
    TextView kkk;

    @BindView(R.id.btnReturn)
    TextView btnReturn;


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


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showAuthCode(String code) {
        Log.e("HU", "code====22222=======");
        kkk.setText(code);
    }

    @Override
    protected void onDestroy() {
        Log.e("HU","onDestroy===========");
        super.onDestroy();
    }

    @OnClick({R.id.btnReturn, R.id.kkk})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnReturn:
                finish();
                break;
            case R.id.kkk:
                Log.e("HU", "code====kkk=======");
                getMvpPresenter().getAuthCodeNew();
                break;
        }
    }
}
