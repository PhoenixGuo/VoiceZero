package com.millionfight.voicezero.application;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.umeng.socialize.PlatformConfig;

/**
 * Created by fengxiao on 2016/5/12.
 */
public class VoiceZeroApplication extends Application {

    //讯飞语音appId
    public static final String APP_ID_XF = "57348589";

    //微信AppID与AppSecret
    public static final String APP_ID_WE_CHAT = "wx658aaed57dca6d86";
    public static final String APP_SECRET_WE_CHAT = "6840f862a3ad3fe593b3e6317fa0693f";

    @Override
    public void onCreate() {
        super.onCreate();
        //科大讯飞配置
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=" + APP_ID_XF);
        //友盟社会化分享，微信配置
        PlatformConfig.setWeixin(APP_ID_WE_CHAT, APP_SECRET_WE_CHAT);
    }

}
