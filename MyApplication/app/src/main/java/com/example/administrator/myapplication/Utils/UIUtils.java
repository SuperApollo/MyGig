package com.example.administrator.myapplication.Utils;

import android.content.Context;

/**
 * Created by Administrator on 2016/7/1.
 */
public class UIUtils {

    public static float dip2px(Context context, float dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (dip * scale + 0.5f);
    }

    public static float px2dip(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (px / scale + 0.5f);
    }
}
