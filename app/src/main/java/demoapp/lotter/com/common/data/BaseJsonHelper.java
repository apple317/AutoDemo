package demoapp.lotter.com.common.data;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.reactivex.Flowable;

/**
 * name   : BaseJsonHelper
 * author : woodonchan
 * time   : 2017/7/19
 * desc   :
 * version: 1.0
 */
public class BaseJsonHelper {

    private Context context;

    protected Gson gson;

    public BaseJsonHelper(Context context, Gson gson) {
        this.context = context;
        this.gson = gson;
    }

    public Flowable<String> loadJson(String fileName) {
        return Flowable.just(fileName).map(s -> {
            StringBuilder stringBuilder = new StringBuilder();
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        });
    }
}
