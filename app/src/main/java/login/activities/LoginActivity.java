package login.activities;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.millionfight.voicezero.R;
import com.millionfight.voicezero.base.BaseCompatActivity;
import com.millionfight.voicezero.home.activities.MainActivity;

/**
 * Created by Administrator on 2016/5/26 0026.
 */
public class LoginActivity extends BaseCompatActivity {

    private Button btn_login;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void findViews() {
        super.findViews();
        btn_login = (Button) findViewById(R.id.btn_login);
    }

    @Override
    protected void setListener() {
        super.setListener();
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.btn_login: {
                i = new Intent();
                i.setClassName(this, MainActivity.class.getName());
                startActivity(i);
                break;
            }
        }
    }

}
