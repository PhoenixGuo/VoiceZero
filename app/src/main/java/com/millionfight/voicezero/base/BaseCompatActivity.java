package com.millionfight.voicezero.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by dana.wang on 2015/10/14.
 */
public abstract class BaseCompatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        findViews();
        setListener();
    }

    protected void findViews() {
    }

    protected void setListener() {
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected abstract int getLayoutId();

    protected void showThost(CharSequence info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    protected void showThost(int infoId) {
        Toast.makeText(this, infoId, Toast.LENGTH_SHORT).show();
    }

}
