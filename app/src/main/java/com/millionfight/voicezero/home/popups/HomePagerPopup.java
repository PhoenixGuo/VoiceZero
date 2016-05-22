package com.millionfight.voicezero.home.popups;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/22 0022.
 */
public class HomePagerPopup extends PopupWindow {

    private View mView;
    private Context mContext;

    ArrayList<String> popupItems = new ArrayList<>();

    private HomePagerPopup(Context context, View parent) {
        mContext = context;
//        mView = LayoutInflater.from(context).inflate(R.layout.popwindow_layout, null);

    }

}
