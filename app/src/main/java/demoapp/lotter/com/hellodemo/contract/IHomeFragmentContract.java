package demoapp.lotter.com.hellodemo.contract;


import com.base.mvp.common.BasePresenter;
import com.base.mvp.common.IBaseView;

/**
 * yangyoupeng  on 2018/4/25.
 */

public interface IHomeFragmentContract {

    interface View extends IBaseView {
        void showAuthCode(String code);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getAuthCodeNew();
    }


}
