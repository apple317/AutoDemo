package com.base.common;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.base.utils.Utils;


/**
 * yangyoupeng  on 2018/4/3.
 */

public class BaseApplication extends Application {
    private static BaseApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;

        Utils.init(this);
        BaseApi.host(BaseApi.HostType.TEST_150, true);  //初始化api环境
    }

    /**
     * bugly热修复
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
    }


    /**
     * 利用单例模式获取HuoQApplication实例
     *
     * @return
     */
    public static BaseApplication getInstance() {
        if (null == mApplication) {
            mApplication = new BaseApplication();
        }
        return mApplication;
    }

//    public LockPatternUtils getLockPatternUtils() {
//        if (mLockPatternUtils == null) {
//            mLockPatternUtils = new LockPatternUtils(mApplication);
//        }
//        return mLockPatternUtils;
//    }
}
