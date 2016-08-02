package com.example.administrator.myapplication.Activity;

import android.app.Application;
import android.content.Context;

import com.example.administrator.myapplication.Utils.CrashHandler;

/**
 * Created by Administrator on 2016/6/30.
 */
public class BaseApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        init();

    }

    private void init() {
        mContext = getApplicationContext();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(mContext);
    }

    public static Context getContext() {

        return mContext;
    }
}
