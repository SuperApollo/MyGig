package com.example.administrator.myapplication.Utils;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by Administrator on 2016/6/30.
 */
public class PreviewSurface extends SurfaceView implements SurfaceHolder.Callback {
    private final Context mContext;
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private Camera.Parameters mParameters;
    private boolean mIsSurfaceCreated;
    private int mSurfaceWidth;
    private int mSurfaceHeight;

    public PreviewSurface(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    private void init() {
        this.mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mCamera = Camera.open();
    }

    public void off() {
        if (mCamera != null) {
            if (mParameters == null) {
                mParameters = mCamera.getParameters();
            }
            mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(mParameters);
        }

    }

    public void on() {
        if (mCamera != null) {
            if (mParameters == null) {
                mParameters = mCamera.getParameters();
            }
            mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(mParameters);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        try {
            super.onAttachedToWindow();
            return;
        }catch (Exception e){
            while (mCamera==null);
            release();
        }

    }

    public final void release() {
        try {
            if (mIsSurfaceCreated) {
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
                mSurfaceHolder.removeCallback(this);
                mIsSurfaceCreated = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mSurfaceHolder = holder;
            mCamera = Camera.open();
            mParameters = mCamera.getParameters();
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mIsSurfaceCreated = true;

            return;
        } catch (IOException e) {
            if (mCamera!=null){
                mCamera.release();
                mCamera = null;
                mIsSurfaceCreated = false;
            }
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mIsSurfaceCreated){
            mSurfaceWidth = width;
            mSurfaceHeight = height;
            mCamera.startPreview();
            on();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        release();

    }
}
