package com.base.http.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseQueryMap extends HashMap {


    private List<Object> listKey = new ArrayList<>();

    @Override
    public Object put(Object key, Object value) {
        listKey.add(key);
        return super.put(key, value);
    }



    /**
     * 得到所有key数组
     *
     * @return
     */
    public List<Object> getListKey() {
        return listKey;
    }

    /**
     * 申请参数转成json数据
     * @return
     */
    public String contentToJson() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        int i = 0;
        for (Object key : keySet()) {
            if (get(key) instanceof Boolean) {
                builder.append("\"" + key+ "\":" + get(key) + "");
            } else if (get(key) instanceof Integer) {
                builder.append("\"" + key + "\":" + get(key) + "");
            } else {
                if (get(key) != null && (get(key).toString().startsWith("{") || get(key).toString().startsWith("["))) {
                    builder.append("\"" + key + "\":" + get(key) + "");
                } else {
                    builder.append("\"" + key + "\":\"" + get(key) + "\"");
                }
            }
            if ((i + 1) != keySet().size()) {
                builder.append(",");
            }
            i++;
        }
        builder.append("}");
        return builder.toString();
    }
}
