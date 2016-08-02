package com.example.administrator.myapplication.test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/27.
 */
public class TestTime {

    public void printTime() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long millis = System.currentTimeMillis();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(millis);
            simpleDateFormat.format(date);
            System.out.print(date.toString());
            System.out.print("\n");
        }

    }
}
