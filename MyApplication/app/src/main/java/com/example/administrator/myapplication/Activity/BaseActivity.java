package com.example.administrator.myapplication.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.example.administrator.myapplication.Fragment.FirstFragment;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utils.FragmentUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/3.
 */
public class BaseActivity extends FragmentActivity implements View.OnClickListener {

    private LinearLayout fg_container;
    private RadioButton rb_fouth;
    private RadioButton rb_third;
    private RadioButton rb_second;
    private RadioButton rb_first;
    private ArrayList<RadioButton> rbs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baseactivity_layout);

        initView();
    }

    private void initView() {
        fg_container = (LinearLayout) findViewById(R.id.fg_container);
        rb_first = (RadioButton) findViewById(R.id.rb_first);
        rb_second = (RadioButton) findViewById(R.id.rb_second);
        rb_third = (RadioButton) findViewById(R.id.rb_third);
        rb_fouth = (RadioButton) findViewById(R.id.rb_fouth);

        rbs.add(rb_first);
        rbs.add(rb_second);
        rbs.add(rb_third);
        rbs.add(rb_fouth);

        rb_first.setOnClickListener(this);
        rb_second.setOnClickListener(this);
        rb_third.setOnClickListener(this);
        rb_fouth.setOnClickListener(this);

        FragmentUtil.replace(this, R.id.fg_container, new FirstFragment());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_first:
                setChecked(rb_first);

                break;
            case R.id.rb_second:

                break;
            case R.id.rb_third:

                break;
            case R.id.rb_fouth:

                break;

        }
    }

    public void setChecked(RadioButton rb) {
        for (RadioButton rbtn : rbs) {
            if (rbtn.equals(rb)) {
                rbtn.setChecked(true);
            } else {
                rbtn.setChecked(false);
            }
        }
    }
}
