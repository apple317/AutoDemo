package demoapp.lotter.com.hellodemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        Toast.makeText(getApplicationContext(),"dd",Toast.LENGTH_LONG).show();
    }
}
