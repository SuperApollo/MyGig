package com.example.administrator.myapplication.Utils;

import android.content.Context;
import android.widget.Toast;

import com.example.administrator.myapplication.Activity.BaseApplication;

/**
 * Created by Administrator on 2016/7/22.
 */
public class ToastUtils {
    public static void show(String s) {
        Context context = BaseApplication.getContext();
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
