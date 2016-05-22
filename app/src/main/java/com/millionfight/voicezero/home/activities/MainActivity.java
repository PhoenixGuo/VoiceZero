package com.millionfight.voicezero.home.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.millionfight.voicezero.R;
import com.millionfight.voicezero.base.BaseCompatActivity;
import com.millionfight.voicezero.home.dialogs.FirstUseRemindDialog;
import com.millionfight.voicezero.utils.JsonParser;
import com.millionfight.voicezero.utils.SharedPreferencesUtil;


public class MainActivity extends BaseCompatActivity
        implements
        View.OnClickListener,
        RecognizerDialogListener,
        Toolbar.OnMenuItemClickListener {

    private static String TAG = "IatDemo";

    private Button btn_start_listening;
    private Toolbar toolbar;

    private SpeechRecognizer mIat;
    private RecognizerDialog iatDialog;
    private InitListener mInitListener;

    private boolean isFirstUse;

    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 初始化监听器。
         */
        mInitListener = new InitListener() {
            @Override
            public void onInit(int code) {
                Log.d(TAG, "SpeechRecognizer init() code = " + code);
                if (code != ErrorCode.SUCCESS) {
                    Toast.makeText(
                            MainActivity.this,
                            "初始化失败，错误码：" + code,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        };
        //1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
        iatDialog = new RecognizerDialog(this, mInitListener);
        //2.设置听写参数，同上节
        iatDialog.setParameter(SpeechConstant.DOMAIN, "iat");
        iatDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        iatDialog.setParameter(SpeechConstant.ACCENT, "mandarin ");
        //3.设置回调接口
        iatDialog.setListener(this);

        //取出sp中isFirstUse的值
        isFirstUse = SharedPreferencesUtil.getFirstUse(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViews() {
        super.findViews();
        btn_start_listening = (Button) findViewById(R.id.btn_start_listening);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstUse) {
            FirstUseRemindDialog.makeDialog(this);
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        btn_start_listening.setOnClickListener(this);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_listening:
                sb.setLength(0);
                //4.开始听写
                iatDialog.show();
                break;
        }
    }

    @Override
    public void onResult(RecognizerResult recognizerResult, boolean b) {
        String voiceResult = JsonParser.parseIatResult(recognizerResult.getResultString());
        sb.append(voiceResult);
        if (b) {
            showThost(sb.toString());
        }
    }

    @Override
    public void onError(SpeechError speechError) {
        Log.d("speechError", speechError.toString());
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                showThost(R.string.coming_soon);
                break;
            case R.id.remind:
                showThost(R.string.coming_soon);
                break;
            case R.id.favorite:
                showThost(R.string.coming_soon);
                break;
            case R.id.setting:

                break;
        }

        return false;
    }
}
