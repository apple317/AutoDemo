package com.base.http.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseHeadMap extends HashMap {


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

}
