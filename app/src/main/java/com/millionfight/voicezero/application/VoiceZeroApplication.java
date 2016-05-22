package com.millionfight.voicezero.application;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

/**
 * Created by fengxiao on 2016/5/12.
 */
public class VoiceZeroApplication extends Application {

    //讯飞语音appId
    public static final String APP_ID_XF = "57348589";

    @Override
    public void onCreate() {
        super.onCreate();
        // 将“12345678”替换成您申请的APPID，申请地址：http://open.voicecloud.cn
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=" + APP_ID_XF);
    }

}
