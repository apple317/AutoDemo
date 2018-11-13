package demoapp.lotter.com.common.http;

import android.app.Application;
import android.content.Context;

import demoapp.lotter.com.hellodemo.BuildConfig;
import demoapp.lotter.com.hellodemo.R;

/**
 * Created by woodonchan on 2017/6/14.
 * Constants
 */

public class Constants {

    public static final String PREFERENCES_NAME = "apple";


    /**
     * 获取BaseURL地址集
     */
    public static String[] getLineDetectionData(Context context) {
        String[] lines = context.getResources().getStringArray(BuildConfig.LOTTERY_RELEASE ? R.array.LineDetections : BuildConfig.LOTTERY_BETA ? R.array.BetaLineDetections: R.array.AlphaLineDetections);
        return lines;
    }

    /**
     * 获取BaseURL
     */
    public static String getBaseUrl(Application context) {
        String line = getLineDetectionData(context)[0];
//        if (TextUtils.isEmpty(ACache.get(context).getAsString("selBaseUrl"))) {
//            ACache.get(context).put("selBaseUrl", line);
//        }
        return  line;
    }



}
