package demoapp.lotter.com.hellodemo;

import com.base.http.params.BaseParams;

import java.io.File;
import java.io.FileNotFoundException;
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

        Iterator iter = builder.headParams.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next().toString();
            String val = builder.headParams.get(key).toString();
            putHeadParams(key,val);
        }


        if(fileParams.size()>0){
            try {
                put("multipartRequest",builder.fileParams);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


    }


    public static class Builder {

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
            return new HttpParams(this);
        }

    }
}
