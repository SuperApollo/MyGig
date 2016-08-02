package com.example.administrator.myapplication.Utils;

import android.util.Log;

import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/25.
 */
public class TimeUtils {


    /**
     * 判断本地时间和网络时间误差不小于5分钟为true
     *
     * @return
     */
    public static boolean isCurrentTimeCorrect() {

        long localTime = System.currentTimeMillis();
        long webTime = 0;
        URL url = null;//取得资源对象
        URLConnection uc = null;
        try {
            url = new URL("http://www.baidu.com");
            uc = url.openConnection();//生成连接对象
            uc.connect(); //发出连接
            webTime = uc.getDate(); //取得网站日期时间
        } catch (Exception e) {
            e.printStackTrace();
        }


        int devTime = (int) (Math.abs(webTime - localTime) / (1000 * 60));
//        Log.i("TimeUtils", "localTime: " + localTime + " webTime: " + webTime + " devTime: " + devTime);
//        Log.i("TimeUtils", "local: " + getDate(localTime) + " web: " + getDate(webTime));
        if (devTime > 5) {
            return false;
        } else {
            return true;
        }
    }

    public static String getDate(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(millis);
        return sdf.format(date);
    }

}
