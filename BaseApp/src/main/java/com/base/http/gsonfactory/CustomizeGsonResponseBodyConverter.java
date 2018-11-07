package com.base.http.gsonfactory;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class CustomizeGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomizeGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        //把responsebody转为string
        String response = value.string();
//        if (BuildConfig.DEBUG) {
//            //打印后台数据
//            Log.e(BuildConfig.APPLICATION_ID, response);
//        }
      //  BaseEntity baseResponse = gson.fromJson(response, BaseEntity.class);
        // 这里只是为了检测code是否!=1,所以只解析HttpStatus中的字段,因为只要code和message就可以了
//        if (baseResponse.isSuccess()) {
//            value.close();
//            //抛出一个RuntimeException, 这里抛出的异常会到subscribe的onError()方法中统一处理
//          //  throw new ApiException(baseResponse.getMsg());
//        }
        try {
            return adapter.fromJson(response);
        } finally {
            value.close();
        }
    }

}
