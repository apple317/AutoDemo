package demoapp.lotter.com.common.http;


import com.base.http.listener.error.AppException;

import rx.functions.Func1;


public class HttpResultFunc<T> implements Func1<BaseResultEntity<T>, T> {

    @Override
    public T call(BaseResultEntity<T> tAppBaseEntity) {
        if(tAppBaseEntity.Code!=200){
            throw new IllegalStateException(new AppException(tAppBaseEntity.Msg));
        }
        return tAppBaseEntity.getData();
    }
}


