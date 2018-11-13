package demoapp.lotter.com.common.data;


import com.base.data.DataManager;
import com.base.data.DbHelper;

import javax.inject.Inject;

import demoapp.lotter.com.common.drager.AppApiHelper;

public class AppDataManager implements DataManager, DbHelper {

    public AppApiHelper appApiHelper;

    public AppDbHelper dbHelper;

    public AppPreferencesHelper preferencesHelper;

    @Inject
    public AppDataManager(AppDbHelper dbHelper, AppPreferencesHelper preferencesHelper, AppApiHelper apiHelper) {
        this.dbHelper = dbHelper;
        this.preferencesHelper = preferencesHelper;
        this.appApiHelper = apiHelper;
    }

}
