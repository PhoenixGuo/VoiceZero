package com.millionfight.voicezero.home.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.millionfight.voicezero.UMengShare;
import com.millionfight.voicezero.base.BaseCompatActivity;
import com.millionfight.voicezero.home.dialogs.FirstUseRemindDialog;
import com.millionfight.voicezero.utils.ConstantUtils;
import com.millionfight.voicezero.utils.JsonParser;
import com.millionfight.voicezero.utils.SharedPreferencesUtil;
import com.skyfishjy.library.RippleBackground;
import com.umeng.socialize.bean.SHARE_MEDIA;


public class MainActivity extends BaseCompatActivity
        implements
        View.OnClickListener,
        RecognizerDialogListener,
        Toolbar.OnMenuItemClickListener {

    private static String TAG = "IatDemo";

    private Button btn_start_listening;
    private Toolbar toolbar;
    private DrawerLayout dl_main;
    private Toast backToast;
    private NavigationView nv_left;
    private NavigationView nv_right;

    private SpeechRecognizer mIat;
    private RecognizerDialog iatDialog;
    private InitListener mInitListener;

    private RippleBackground rippleBackground;

    private boolean isFirstUse;

    private StringBuilder sb = new StringBuilder();

    private Menu menu;
    private MenuItem remindItem;

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
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_top_main_activity, menu);
//        showRemindAnimation(menu.getItem(1), 1);
        /*for (int i = 0; i < menu.size(); i++) {
            showRemindAnimation(menu.getItem(i), 1);
        }*/
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
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dl_main = (DrawerLayout) findViewById(R.id.dl_main);
        nv_left = (NavigationView) findViewById(R.id.nv_left);
        nv_right = (NavigationView) findViewById(R.id.nv_right);

        //默认上来是左手操作
        dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, nv_right);
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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                dl_main,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        dl_main.addDrawerListener(toggle);
        //添加home打开drawerlayout的动画
        toggle.syncState();

    }

    //OnClickListener_start
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
    //OnClickListener_end

    @Override
    public void onResult(RecognizerResult recognizerResult, boolean b) {
        String voiceResult = JsonParser.parseIatResult(recognizerResult.getResultString());
        sb.append(voiceResult);
        if (b) {
            showToast(sb.toString());
        }
    }

    @Override
    public void onError(SpeechError speechError) {
        Log.d("speechError", speechError.toString());
    }

    //OnMenuItemClickListener_start
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                SHARE_MEDIA[] shareMedias = new SHARE_MEDIA[]
                        {
                                SHARE_MEDIA.WEIXIN,
                                SHARE_MEDIA.WEIXIN_CIRCLE,
                                SHARE_MEDIA.EMAIL,
                                SHARE_MEDIA.SMS
                        };
                new UMengShare(MainActivity.this, shareMedias).showShare();
                break;
            case R.id.remind:
                showRemindAnimation(menu.getItem(1));
                break;
            case R.id.gesture:
                hideRemindAnimation();
                ConstantUtils.isRightMode = !ConstantUtils.isRightMode;
                showNavigationView();
                break;
            case R.id.setting:

                break;
        }

        return false;
    }
    //OnMenuItemClickListener_end

    public void showNavigationView() {
        if (ConstantUtils.isRightMode) {
            showToast("切换至右手操作");
            dl_main.closeDrawer(nv_left);
            dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, nv_left);
            dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, nv_right);
        } else {
            showToast("切换至左手操作");
            dl_main.closeDrawer(nv_right);
            dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, nv_right);
            dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, nv_left);
        }
    }

    public void showRemindAnimation(MenuItem item) {
        remindItem = item;

        View menuItemActionView = getLayoutInflater().inflate(
                R.layout.action_view, null
        );
        ImageView iv = (ImageView) menuItemActionView.findViewById(R.id.centerImage);
        iv.setImageDrawable(item.getIcon());
        remindItem.setActionView(menuItemActionView);

/*        if (item.getItemId() == menu.getItem(itemIndex).getItemId()) {

        }*/

        rippleBackground = (RippleBackground) menuItemActionView.findViewById(R.id.content);
        rippleBackground.startRippleAnimation();
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("打开消息界面");
            }
        });

    }

    private void hideRemindAnimation() {
        if (remindItem != null) {
            View view = remindItem.getActionView();
            if (view != null) {
                view.clearAnimation();
                remindItem.setActionView(null);
            }
        }
    }

    @Override
    public void onBackPressed() {
        exitApp();
    }

    private void exitApp() {
        if (backToast != null
                && backToast.getView().getWindowToken() != null) {
            finish();
        } else {
            backToast = Toast.makeText(
                    MainActivity.this,
                    R.string.back,
                    Toast.LENGTH_SHORT
            );
            backToast.show();
        }
    }

}
