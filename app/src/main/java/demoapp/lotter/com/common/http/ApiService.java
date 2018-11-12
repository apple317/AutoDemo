package demoapp.lotter.com.common.http;


import demoapp.lotter.com.hellodemo.entity.DataEntity;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {

    @FormUrlEncoded
    @POST(HttpURL.LOGIN)
    Observable<BaseResultEntity<DataEntity>> getAuthCodeNew(@Query("type") String type, @FieldMap AppQueryMap map);

}
