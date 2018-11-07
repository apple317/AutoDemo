package demoapp.lotter.com.hellodemo;


import com.base.http.entity.BaseEntity;
import com.base.http.rxjava.BaseObservable;
import com.base.http.retrofit.FieldParam;
import com.base.http.retrofit.POST;

public interface ApiService {

    @POST(HttpURL.LOGIN)
    BaseObservable<BaseEntity<DataEntity>> getAuthCodeNew(@FieldParam HttpParams params);

}
