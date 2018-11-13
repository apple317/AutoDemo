package demoapp.lotter.com.common.http;



import demoapp.lotter.com.hellodemo.entity.DataEntity;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface ApiService {

    @FormUrlEncoded
    @POST(HttpURL.LOGIN)
    Observable<BaseResultEntity<DataEntity>> getAuthCodeNew(@Field("type") String type);

}
