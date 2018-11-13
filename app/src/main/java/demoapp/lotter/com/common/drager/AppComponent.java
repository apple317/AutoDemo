package demoapp.lotter.com.common.drager;

import android.content.Context;

import com.base.data.DataManager;
import com.base.data.DbHelper;
import com.base.di.qualifier.AppContext;
import com.base.di.qualifier.PreferencesName;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import demoapp.lotter.com.common.baseview.AppleApplication;

/**
 * name   : AppComponent
 * author : woodonchan
 * time   : 2017/7/17
 * desc   :
 * version: 1.1
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(AppleApplication appApplication);

    @AppContext
    Context getContext();

    DataManager getDataManager();

    DbHelper getDbHelper();


    @PreferencesName
    String getPreferencesName();

    Gson getGson();

    AppApiHelper getApiHelper();
}
