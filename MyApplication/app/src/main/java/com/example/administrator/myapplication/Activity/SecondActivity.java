package com.example.administrator.myapplication.Activity;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Process;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.administrator.myapplication.Bean.ListviewItemBean;
import com.example.administrator.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2016/6/30.
 */
public class SecondActivity extends Activity {

    private ToggleButton tb_second_switch;
//    private Camera mCamera = Camera.open();
    private Camera mCamera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        initView();
    }

    private void initView() {

        tb_second_switch = (ToggleButton) findViewById(R.id.tb_second_switch);
        tb_second_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tb_second_switch.isChecked()) {
//                    lightOn();
                } else {
//                    lightOff();
                }
            }
        });




    }


    private void lightOn() {

        Camera.Parameters onParam = mCamera.getParameters();
        onParam.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        mCamera.setParameters(onParam);
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                camera.autoFocus(this);
            }
        });
        mCamera.startPreview();

    }

    private void lightOff() {
        Camera.Parameters offParam = mCamera.getParameters();
        offParam.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        mCamera.setParameters(offParam);
        mCamera.cancelAutoFocus();
        mCamera.stopPreview();
    }

    private void release() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        android.os.Process.killProcess(Process.myPid());
    }
}
