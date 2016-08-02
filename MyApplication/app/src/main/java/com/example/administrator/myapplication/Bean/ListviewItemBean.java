package com.example.administrator.myapplication.Bean;

/**
 * Created by Administrator on 2016/7/1.
 */
public class ListviewItemBean {
    private String time;
    private String body;
    private int icon;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public ListviewItemBean(String time, String body, int icon) {
        this.time = time;
        this.body = body;
        this.icon = icon;
    }
}
