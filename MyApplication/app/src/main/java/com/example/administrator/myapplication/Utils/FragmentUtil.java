package com.example.administrator.myapplication.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Administrator on 2016/8/4.
 */
public class FragmentUtil {
    public static void replace(FragmentActivity activity, int id, Fragment fragment) {
        activity.getSupportFragmentManager().beginTransaction().replace(id, fragment).commit();
    }
}
