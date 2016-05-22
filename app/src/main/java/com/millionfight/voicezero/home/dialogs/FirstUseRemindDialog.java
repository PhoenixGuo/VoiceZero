package com.millionfight.voicezero.home.dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.millionfight.voicezero.R;
import com.millionfight.voicezero.utils.SharedPreferencesUtil;

/**
 * Created by Administrator on 2016/5/22 0022.
 */
public class FirstUseRemindDialog {

    public static void makeDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(R.string.first_use_alert_message);
        builder.setPositiveButton(R.string.i_know_it, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferencesUtil.setFirstUse(activity);
            }
        });
        builder.show();
    }

}
