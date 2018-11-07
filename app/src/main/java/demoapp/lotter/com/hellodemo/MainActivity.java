package demoapp.lotter.com.hellodemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.base.http.entity.BaseEntity;
import com.base.http.listener.error.HttpException;
import com.base.http.retrofit.Retrofit;
import com.base.http.rxjava.RxResSubscriber;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.kkk)
    TextView kkk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        kkk.setText("ddd");
    }

    @OnClick(R.id.kkk)
    public void onClick() {

        new Retrofit().create(ApiService.class).getAuthCodeNew(
                new HttpParams.Builder()
                        .build()).observableInit()
                /*结果判断*/
                .map(new Func1<BaseEntity<DataEntity>, String>() {
                    @Override
                    public String call(BaseEntity<DataEntity> dataEntityAppBaseEntity) {
                        return dataEntityAppBaseEntity.Data.getVerification_code();
                    }
                })
                .subscribe(new RxResSubscriber<String>() {
                    @Override
                    public void onError(HttpException exception) {
                        kkk.setText(exception.getDisplayMessage() + "");
                    }
                    @Override
                    public void onNextData(String t) {
                        kkk.setText(t + "");
                    }

                });
    }
}
