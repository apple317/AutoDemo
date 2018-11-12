package demoapp.lotter.com.common.http;

import android.text.TextUtils;

import com.base.http.entity.BaseQueryMap;
import com.base.utils.MD5Util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AppQueryMap extends BaseQueryMap {

    String timestamp = String.valueOf(System.currentTimeMillis());
    StringBuilder paramsBuilder = new StringBuilder();

    @Override
    public String toString() {
        return super.toString();
    }


    public AppQueryMap() {
        String paramJson=toString();
        paramsBuilder.append(paramJson).append(timestamp);
        String sign = MD5Util.MD5(paramsBuilder.toString(), "UTF-8");
        if (!TextUtils.isEmpty(paramJson)) {
            try {
                put("params", URLEncoder.encode(paramJson, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        put("timestamp", timestamp);
        put("sign", sign);
    }

}
