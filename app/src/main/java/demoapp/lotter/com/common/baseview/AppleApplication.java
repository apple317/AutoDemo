package demoapp.lotter.com.common.baseview;


import com.base.common.BaseApplication;

import demoapp.lotter.com.common.drager.AppComponent;
import demoapp.lotter.com.common.drager.AppModule;
import demoapp.lotter.com.common.drager.DaggerAppComponent;


public class AppleApplication extends BaseApplication {

    private static AppComponent mAppComponent;

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setUpApplicationComponent();
        mAppComponent.getApiHelper().initHttp(this);
    }


    private void setUpApplicationComponent() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        mAppComponent.inject(this);
    }


}
