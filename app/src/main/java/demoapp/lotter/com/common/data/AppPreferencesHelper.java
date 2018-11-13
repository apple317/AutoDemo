package demoapp.lotter.com.common.data;

import android.content.Context;

import com.base.data.PreferencesHelper;
import com.base.di.qualifier.AppContext;
import com.base.di.qualifier.PreferencesName;
import com.base.utils.SharePrefUtils;

import javax.inject.Inject;

public class AppPreferencesHelper implements PreferencesHelper {

    private final SharePrefUtils mSharePrefUtils;

    @Inject
    public AppPreferencesHelper(@AppContext Context context, @PreferencesName String preferencesName) {
        mSharePrefUtils = new SharePrefUtils(context, preferencesName);
    }
}
