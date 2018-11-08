package demoapp.lotter.com.hellodemo;

import com.base.http.params.BaseParams;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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
