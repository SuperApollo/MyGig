package com.example.administrator.myapplication.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.Bean.ListviewItemBean;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.widgets.MyDragDeleteListView;
import com.example.administrator.myapplication.widgets.SlideDeleteListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/7/1.
 */
public class ThirdActivity extends Activity {


    private final String TAG = this.getClass().getSimpleName();
    private SlideDeleteListView lv_test;
    List<ListviewItemBean> beans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        initView();
    }

    private void initView() {
        lv_test = (SlideDeleteListView) findViewById(R.id.lv_test);
        addData();
        final MyAdapter myAdapter = new MyAdapter();
        lv_test.setAdapter(myAdapter);
        lv_test.setRemoveListener(new SlideDeleteListView.RemoveListener() {
            @Override
            public void removeItem(SlideDeleteListView.RemoveDirection direction, int position) {
                Log.i(TAG,"删除 "+position+" 行");
                beans.remove(position);
                myAdapter.notifyDataSetChanged();
            }
        });
        /*lv_test.setLeftRightText("","左滑删除");
        lv_test.setOnRemoveListener(new MyDragDeleteListView.OnRemoveListener() {
            @Override
            public void onRemoved(int position, MyDragDeleteListView.Direction direction) {
                Log.i(TAG,"删除 "+position+" 行");
//                beans.remove(position);
//                myAdapter.notifyDataSetChanged();
            }
        });*/
    }

    private void addData() {
        if (beans == null) {
            beans = new ArrayList<>();
            for (int i = 0; i < 15; i++) {
                ListviewItemBean listviewItemBean = new ListviewItemBean(getTime(), "何仙姑 " + i+" 号", R.drawable.icon_dog);
                beans.add(listviewItemBean);
            }
        }
    }

    private String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return sdf.format(date);
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return beans.size();
        }

        @Override
        public Object getItem(int position) {
            return beans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.item_layout, null);
                holder.iconView = (ImageView) convertView.findViewById(R.id.iv_item_icon);
                holder.timeView = (TextView) convertView.findViewById(R.id.tv_item_time);
                holder.bodyView = (TextView) convertView.findViewById(R.id.tv_item_body);

                convertView.setTag(holder);

            } else {
                holder = (Holder) convertView.getTag();
            }
            ListviewItemBean bean = (ListviewItemBean) getItem(position);
            int icon = bean.getIcon();
            String time = bean.getTime();
            String body = bean.getBody();

            holder.iconView.setImageResource(icon);
            holder.timeView.setText(time);
            holder.bodyView.setText(body);

            return convertView;
        }
    }

    class Holder {
        private ImageView iconView;
        private TextView timeView;
        private TextView bodyView;
    }

}
