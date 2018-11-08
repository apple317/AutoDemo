package demoapp.lotter.com.hellodemo;



import com.base.http.entity.BaseEntity;

import rx.functions.Func1;


public class HttpResultFunc<T> implements Func1<BaseEntity<T>, T> {

    @Override
    public T call(BaseEntity<T> tAppBaseEntity) {
        return tAppBaseEntity.getData();
    }
}


