package demoapp.lotter.com.hellodemo.contract;


import com.base.common.BasePresenter;
import com.base.common.IBaseView;

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
