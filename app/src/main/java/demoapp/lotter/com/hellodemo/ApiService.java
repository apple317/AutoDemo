package demoapp.lotter.com.hellodemo;


import com.base.http.entity.BaseEntity;
import com.base.http.retrofit.POST;
import com.base.http.rxjava.BaseObservable;

import retrofit2.http.Field;

public interface ApiService {

    @POST(HttpURL.LOGIN)
    BaseObservable<BaseEntity<DataEntity>> getAuthCodeNew(@Field("name") String name);

}
