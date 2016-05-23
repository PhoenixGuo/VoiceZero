package com.millionfight.voicezero;

import android.app.Activity;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by Administrator on 2016/5/24 0024.
 */
public class UMengShare {

    private Activity activity;
    private SHARE_MEDIA[] shareMedias;

    public UMengShare(
            Activity activity,
            SHARE_MEDIA[] shareMedias
    ) {
        this.activity = activity;
        this.shareMedias = shareMedias;
    }

    public void showShare() {
        new ShareAction(activity).
                setDisplayList(shareMedias).
                withText("我正在使用虚拟助手“音零”，推荐你也一起使用").
                withTitle("title").
                withTargetUrl("http://www.baidu.com").
                setListenerList(new MyUMShareListener()).
                open();
    }

    class MyUMShareListener implements UMShareListener {

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(
                    activity,
                    platform + activity.getString(R.string.umeng_share_succeed),
                    Toast.LENGTH_SHORT
            ).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(
                    activity,
                    platform + activity.getString(R.string.umeng_share_failed),
                    Toast.LENGTH_SHORT
            ).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(
                    activity,
                    platform + activity.getString(R.string.umeng_share_canceled),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

}
