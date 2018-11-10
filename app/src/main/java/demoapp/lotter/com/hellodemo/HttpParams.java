package demoapp.lotter.com.hellodemo;

import android.text.TextUtils;

import com.base.http.params.BaseParams;
import com.base.http.params.MD5Util;
import com.base.utils.LogUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * name   : HttpParams
 * author : woodonchan
 * time   : 2017/7/17
 * desc   :
 * version: 1.0
 */
public class HttpParams extends BaseParams {

    private HttpParams(Builder builder) {

        putHeadParams("platformCode", builder.platformCode);
        putHeadParams("token", "");
        putHeadParams("userId", "");
        putHeadParams("MerchantCode","026");
        putHeadParams("APIID","");
        putHeadParams("X-APP-AGENT","");

        putHeadParams("X-APP-CID", "");
        putHeadParams("X-PROTOCOL", "JAVAAPI");
        putHeadParams("X-APP-IP", "");
        Iterator iter = builder.headParams.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next().toString();
            String val = builder.headParams.get(key).toString();
            putHeadParams(key,val);
        }

        String timestamp = String.valueOf(System.currentTimeMillis());
        StringBuilder paramsBuilder = new StringBuilder();
        paramsBuilder.append(builder.paramsJson);
        paramsBuilder.append("");
        paramsBuilder.append(timestamp);
        String sign = MD5Util.MD5(paramsBuilder.toString(), "UTF-8");

        put("timestamp", timestamp);
        put("sign", sign);
        if (!TextUtils.isEmpty(builder.paramsJson)) {
            try {
                put("params", URLEncoder.encode(builder.paramsJson, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        if(fileParams.size()>0){
            try {
                put("multipartRequest",builder.fileParams);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        LogUtil.e("Lottery", "|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
        LogUtil.e("Lottery", "| HttpParams - time: " + timestamp);
        LogUtil.e("Lottery", "| HttpParams - params: " + builder.paramsJson);
        LogUtil.e("Lottery", "| HttpParams - sign: " + sign);
        LogUtil.e("Lottery", "| HttpParams - token: " + "");
        LogUtil.e("Lottery", "|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

    }


    public static class Builder {

        private String platformCode = "4";

        private String paramsJson;

        private Map<String, Object> params = new HashMap<>();
        private Map<String, Object> headParams = new HashMap<>();
        private ArrayList<File> fileParams = new ArrayList<File>();

        public Builder put(String key, Object value) {
            this.params.put(key, value);
            return this;
        }

        /**
         * 上传文件
         * @param key
         * @param file
         * @return
         */
        public Builder put(String key, File file) {
            this.fileParams.add(file);
            return this;
        }

        public Builder putHead(String key, Object value) {
            this.headParams.put(key, value);
            return this;
        }

        public Builder params(Map<String, Object> params) {
            this.params = params;
            return this;
        }

        public HttpParams build() {
            StringBuilder builder = new StringBuilder();
            builder.append("{");
            int i = 0;
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() instanceof Boolean) {
                    builder.append("\"" + entry.getKey() + "\":" + entry.getValue() + "");
                } else if(entry.getValue() instanceof Integer){
                    builder.append("\"" + entry.getKey() + "\":" + entry.getValue() + "");
                } else {
                    if (entry.getValue() != null && (entry.getValue().toString().startsWith("{") || entry.getValue().toString().startsWith("["))) {
                        builder.append("\"" + entry.getKey() + "\":" + entry.getValue() + "");
                    } else {
                        builder.append("\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"");
                    }
                }
                if ((i + 1) != params.entrySet().size()) {
                    builder.append(",");
                }
                i++;
            }
            builder.append("}");
            this.paramsJson = builder.toString();
            return new HttpParams(this);
        }

    }
}
