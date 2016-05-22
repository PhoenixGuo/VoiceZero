package com.millionfight.voicezero.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by Administrator on 2016/5/22 0022.
 */
public class SharedPreferencesUtil {

    private final static String VOICE_ZERO = "VoiceZero";
    private final static String SP_FIRST_USE = "FirstUse";

    public static void setFirstUse(Context context) {
        SharedPreferences sp = context.getSharedPreferences(VOICE_ZERO, Context.MODE_PRIVATE);
        Editor edit = sp.edit();
        edit.putBoolean(SP_FIRST_USE, false);
        edit.commit();
    }

    public static boolean getFirstUse(Context context) {
        SharedPreferences sp = context.getSharedPreferences(VOICE_ZERO, Context.MODE_PRIVATE);
        return sp.getBoolean(SP_FIRST_USE, true);
    }

}
