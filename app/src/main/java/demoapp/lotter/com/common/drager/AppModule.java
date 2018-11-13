package demoapp.lotter.com.common.drager;

import android.content.Context;

import com.base.data.ApiHelper;
import com.base.data.DataManager;
import com.base.data.DbHelper;
import com.base.data.PreferencesHelper;
import com.base.di.qualifier.AppContext;
import com.base.di.qualifier.PreferencesName;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import demoapp.lotter.com.common.baseview.AppleApplication;
import demoapp.lotter.com.common.data.AppDataManager;
import demoapp.lotter.com.common.data.AppDbHelper;
import demoapp.lotter.com.common.data.AppPreferencesHelper;
import demoapp.lotter.com.common.http.Constants;

/**
 * name   : AppModule
 * author : woodonchan
 * time   : 2017/7/17
 * desc   :
 * version: 1.1
 */
@Module
public class AppModule {

    private final AppleApplication mAppApplication;

    public AppModule(AppleApplication appApplication) {
        this.mAppApplication = appApplication;
    }

    @AppContext
    @Provides
    Context provideBaseContext() {
        return mAppApplication;
    }

    @Singleton
    @Provides
    AppleApplication provideApplication() {
        return mAppApplication;
    }

    @Singleton
    @Provides
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Singleton
    @Provides
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Singleton
    @Provides
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }



    @Singleton
    @Provides
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @PreferencesName
    @Provides
    String providePreferencesName() {
        return Constants.PREFERENCES_NAME;
    }

    @Singleton
    @Provides
    Gson provideGson() {
        return new Gson();
    }



}
