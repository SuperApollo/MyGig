package com.example.administrator.myapplication.Activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.administrator.myapplication.Utils.AESUtils;
import com.example.administrator.myapplication.Utils.PreviewSurface;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utils.TimeUtils;
import com.example.administrator.myapplication.Utils.ToastUtils;
import com.example.administrator.myapplication.widgets.MyDialog;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final int REFRESH_NUM = 0x1008611;
    private final String TAG = this.getClass().getSimpleName();
    private ImageView iv_switch;
    private boolean isOn = false;
    private ToggleButton tb_switch;
    private PreviewSurface mSurfaceView;
    private Context mContext;
    private Button btn_to_second;
    private CheckBox cb_main;
    private LinearLayout ll_graphview;
    private LinearLayout ll_graphview_point;
    private LinearLayout ll_graphview_bar;
    private EditText et_input_dsplay;
    private TextView btn_px2dp;
    private TextView btn_dp2px;
    private TextView tv_result;
    private boolean isRun;
    private int num = 0;
    private Thread notifyThread;
    private EditText et_input_num;
    private String numShow = "";
    private long mExitTime;
    private boolean isCorrectTime;
    private Button btn_encode;
    private Button btn_decode;
    private EditText et_encrypty;
    private String encrypted;
    private String key;

    private Button btn_digui;
    private TextView tv_num_display;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_NUM:
                    tv_num_display.setText(numShow);
                    break;
            }
        }
    };
    private Button btn_to_fgactivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        intit();

    }

    private void intit() {
        mContext = BaseApplication.getContext();
        mSurfaceView = new PreviewSurface(mContext);

        GraphView graphView = new GraphView(mContext);
        LineGraphSeries lineGraphSeries = new LineGraphSeries(new DataPoint[]{
                new DataPoint(0, 3),
                new DataPoint(1, 3),
                new DataPoint(2, 6),
                new DataPoint(3, 2),
                new DataPoint(4, 5)
        });
        graphView.addSeries(lineGraphSeries);
        graphView.setTitle("折线图");
        graphView.setTitleColor(getResources().getColor(R.color.tittlecolor));
        graphView.setBackgroundColor(getResources().getColor(R.color.darkgrey));
        ll_graphview.addView(graphView);

        GraphView barView = new GraphView(mContext);
        BarGraphSeries barGraphSeries = new BarGraphSeries(new DataPoint[]{
                new DataPoint(0, -2),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        barGraphSeries.setColor(Color.rgb(255, 120, 120));
        barGraphSeries.setSpacing(50);
        barView.setTitle("柱状图");
        barView.setBackgroundColor(getResources().getColor(R.color.pink));
        barView.setTitleColor(getResources().getColor(R.color.tittlecolor));
        barView.addSeries(barGraphSeries);
        ll_graphview_bar.addView(barView);

        GraphView pointView = new GraphView(mContext);
        PointsGraphSeries pointsGraphSeries = new PointsGraphSeries(new DataPoint[]{
                new DataPoint(0, -2),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        pointsGraphSeries.setShape(PointsGraphSeries.Shape.TRIANGLE);
        pointsGraphSeries.setColor(getResources().getColor(R.color.green));
        pointsGraphSeries.setSize(10.0f);
        pointView.setTitle("点图");
        pointView.setBackgroundColor(getResources().getColor(R.color.yellow));
        pointView.setTitleColor(getResources().getColor(R.color.tittlecolor));
        pointView.addSeries(pointsGraphSeries);
        ll_graphview_point.addView(pointView);

        isRun = true;

        testNotify();

    }

    private void testNotify() {
        notifyThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRun) {
                    try {
                        Thread.sleep(120000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    showNotify();
                }
            }
        });
        notifyThread.start();
    }

    private void showNotify() {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.icon_dog);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                MainActivity.this).setSmallIcon(R.drawable.icon_dog)
                .setContentTitle("模拟通知")
                .setContentText("黑额呵呵还哦啊后富豪回复金额");
        mBuilder.setTicker("New message");//第一次提示消息的时候显示在通知栏上
        mBuilder.setNumber(num++);
        mBuilder.setLargeIcon(bm);
        mBuilder.setAutoCancel(true);//自己维护通知的消失
        mBuilder.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);

        //构建一个Intent
        Intent resultIntent = new Intent(MainActivity.this,
                ThirdActivity.class);
        //封装一个Intent
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                MainActivity.this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        // 设置通知主题的意图
        mBuilder.setContentIntent(resultPendingIntent);
        //获取通知管理器对象
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(num++, mBuilder.build());

    }

    private void initView() {
        iv_switch = (ImageView) findViewById(R.id.iv_switch);
        tb_switch = (ToggleButton) findViewById(R.id.tb_switch);
        btn_to_second = (Button) findViewById(R.id.btn_to_second);
        cb_main = (CheckBox) findViewById(R.id.cb_main);
        ll_graphview = (LinearLayout) findViewById(R.id.ll_graphview);
        ll_graphview_bar = (LinearLayout) findViewById(R.id.ll_graphview_bar);
        ll_graphview_point = (LinearLayout) findViewById(R.id.ll_graphview_point);
        et_input_num = (EditText) findViewById(R.id.et_input_num);
        btn_digui = (Button) findViewById(R.id.btn_digui);
        tv_num_display = (TextView) findViewById(R.id.tv_num_display);
        et_input_dsplay = (EditText) findViewById(R.id.et_input_dsplay);
        btn_px2dp = (TextView) findViewById(R.id.btn_px2dp);
        btn_dp2px = (TextView) findViewById(R.id.btn_dp2px);
        tv_result = (TextView) findViewById(R.id.tv_result);
        btn_encode = (Button) findViewById(R.id.btn_encode);
        btn_decode = (Button) findViewById(R.id.btn_decode);
        et_encrypty = (EditText) findViewById(R.id.et_encrypty);
        btn_to_fgactivity = (Button) findViewById(R.id.btn_to_fgactivity);

        iv_switch.setOnClickListener(this);
        tb_switch.setOnClickListener(this);
        btn_to_second.setOnClickListener(this);
        btn_digui.setOnClickListener(this);
        btn_px2dp.setOnClickListener(this);
        btn_dp2px.setOnClickListener(this);
        btn_encode.setOnClickListener(this);
        btn_decode.setOnClickListener(this);
        btn_to_fgactivity.setOnClickListener(this);

        cb_main.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i(TAG, "isChecked: " + isChecked);
            }
        });

    }

    private void lightOn() {
        mSurfaceView.on();
    }

    private void lightOff() {
        mSurfaceView.off();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mSurfaceView != null) {
            mSurfaceView.release();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRun = false;
        if (notifyThread != null) {
            notifyThread.interrupt();
            notifyThread = null;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_switch:
                isOn = !isOn;
                if (isOn) {
                    iv_switch.setBackgroundResource(R.drawable.icon_button_on);
                    lightOn();
                } else {
                    iv_switch.setBackgroundResource(R.drawable.icon_button_off);
                    lightOff();
                }
                break;
            case R.id.tb_switch:
                if (tb_switch.isChecked()) {
                    lightOn();
                } else {
                    lightOff();
                }
                break;
            case R.id.btn_to_second:
                Intent intent = new Intent(this, ThirdActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_digui:
                fab();
                checkTime();
                break;
            case R.id.btn_px2dp:
                int dp = pxToDp();
                Log.i(TAG, "dp: " + dp);
                tv_result.setText(dp + "");
                break;
            case R.id.btn_dp2px:
                int px = dpToPx();
                Log.i(TAG, "px: " + px);
                tv_result.setText(px + "");
                break;
            case R.id.btn_encode:
                showAcceptDialog();
                String encrypt = et_encrypty.getText().toString().trim();
                if (TextUtils.isEmpty(encrypt))
                    return;
                String regex = ",|，|\\s+";
                String[] encrypts = null;
                String src = "";
                try {
                    encrypts = encrypt.split(regex);
                    key = encrypts[0];
                    src = encrypts[1];
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(src)) {
                    try {
                        encrypted = AESUtils.encrypt(key, src);
                        et_encrypty.setText(encrypted);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    return;
                }

                showAcceptDialog();

                break;
            case R.id.btn_decode:
                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(encrypted)) {
                    try {
                        String decrypted = AESUtils.decrypt(key, encrypted);
                        et_encrypty.setText(decrypted);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    return;
                }

                break;
            case R.id.btn_to_fgactivity:
                Intent it = new Intent(this, BaseActivity.class);
                startActivity(it);
                break;
        }
    }

    private void showAcceptDialog() {
        MyDialog.Builder builder = new MyDialog.Builder(this);
        builder.setTitle("选择框").setMessage("您同意吗?").setPositiveButton("同意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ToastUtils.show("恭喜您,终于脱单了,汪汪!");
                dialog.dismiss();
            }
        }).setNegativeButton("不同意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ToastUtils.show("对不起,您无权拒绝!");
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void checkTime() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                isCorrectTime = TimeUtils.isCurrentTimeCorrect();
                Log.i(TAG, isCorrectTime + "");
            }
        }).start();


        if (!isCorrectTime) {
            //弹出设置时间对话框

        }
    }

    private int dpToPx() {
        float scale = mContext.getResources().getDisplayMetrics().density;
        float dpValue = 0;
        try {
            dpValue = Integer.parseInt(et_input_dsplay.getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return (int) (dpValue * scale + 0.5f);
    }

    private int pxToDp() {
        float scale = mContext.getResources().getDisplayMetrics().density;
        float pxValue = 0;
        try {
            pxValue = Integer.parseInt(et_input_dsplay.getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return (int) (pxValue / scale + 0.5f);
    }

    private void fab() {
        String str = et_input_num.getText().toString().trim();
        if (TextUtils.isEmpty(str))
            return;
        int position = Integer.parseInt(str);
        if (position < 1)
            return;

        new DiguiTask().execute(position);

    }

    private class DiguiTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... params) {
            int i = params[0];
            numShow = digui(i) + "";
            mHandler.sendEmptyMessage(REFRESH_NUM);
            return null;
        }
    }

    private int digui(int position) {
        if (position == 1 || position == 2) {
            return 1;
        } else {
            return digui(position - 1) + digui(position - 2);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    /*    if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtils.show("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }*/

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            MyDialog.Builder builder = new MyDialog.Builder(this);
            builder.setTitle("提示框");
            builder.setMessage("确定要退出吗?");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();

            return true;
        }


        return super.onKeyDown(keyCode, event);
    }
}
