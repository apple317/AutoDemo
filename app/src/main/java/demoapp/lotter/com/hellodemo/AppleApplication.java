package demoapp.lotter.com.hellodemo;


import android.app.Application;

import com.base.http.common.BaseHttpClient;
import com.base.http.common.HttpConfiguration;
import com.base.http.cookie.okhttp.CookieJarImpl;
import com.base.http.cookie.okhttp.PersistentCookieStore;
import com.base.utils.LogUtil;


public class AppleApplication extends Application {



    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.isOpen = BuildConfig.LOTTERY_ALPHA ? true : false;
        HttpConfiguration.Builder configuration = new HttpConfiguration.Builder(this);
        configuration.retryOnConnectionFailure(true);
        configuration.diskCacheSize(1000 * 1024);
        configuration.connectTimeout(30);
        configuration.readTimeout(30);
        configuration.writeTimeout(30);
        configuration.retryOnConnectionFailure(true);
        configuration.setBaseUrl("https://mbappzxurlxl1.zzxx7.com");
        configuration.setCookieJar(new CookieJarImpl(new PersistentCookieStore(this)));
        configuration.diskCacheDir(getCacheDir());
        configuration.setOpenLog(true);
        BaseHttpClient baseHttpClient = BaseHttpClient.getBaseClient();
        baseHttpClient.init(configuration.build());
    }




}
