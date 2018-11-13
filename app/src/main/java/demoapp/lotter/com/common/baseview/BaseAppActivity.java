package demoapp.lotter.com.common.baseview;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.mvp.common.BaseMvpActivity;
import com.base.mvp.common.BasePresenter;
import com.base.mvp.common.IBaseView;


/**
 * yangyoupeng  on 2018/4/20.
 */

public abstract class BaseAppActivity<V extends IBaseView, P extends BasePresenter<V>> extends BaseMvpActivity<V, P> {

    @Nullable
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
