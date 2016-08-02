package com.example.administrator.myapplication.widgets;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2016/8/1.
 */
public class MyDialog extends Dialog {
    public MyDialog(Context context) {
        super(context);
    }

    public MyDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negtiveButtonText;
        private View contentView;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param message
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negtiveButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negtiveButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public MyDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //设置主题
            final MyDialog dialog = new MyDialog(context, R.style.dialog_theme);
            View layout = inflater.inflate(R.layout.mydialog_layout, null);
            dialog.addContentView(layout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            //设置标题
            TextView tv_mydialog_tittle = (TextView) layout.findViewById(R.id.tv_mydialog_tittle);
            tv_mydialog_tittle.setText(title);
            //设置确定按钮
            Button btn_mydialog_ok = (Button) layout.findViewById(R.id.btn_mydialog_ok);
            if (!TextUtils.isEmpty(positiveButtonText)) {
                btn_mydialog_ok.setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    btn_mydialog_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
            } else {
                btn_mydialog_ok.setVisibility(View.GONE);
            }
            //设置取消按钮
            Button btn_mydialog_cancel = (Button) layout.findViewById(R.id.btn_mydialog_cancel);
            if (!TextUtils.isEmpty(negtiveButtonText)) {
                btn_mydialog_cancel.setText(negtiveButtonText);
                if (negativeButtonClickListener != null) {
                    btn_mydialog_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }

            } else {
                btn_mydialog_cancel.setVisibility(View.GONE);
            }

            TextView tv_mydialog_msg = (TextView) layout.findViewById(R.id.tv_mydialog_msg);
            if (!TextUtils.isEmpty(message)) {
                tv_mydialog_msg.setText(message);
            } else if (contentView != null) {
                LinearLayout ll_content_mydialog = (LinearLayout) layout.findViewById(R.id.ll_content_mydialog);
                ll_content_mydialog.addView(contentView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

            }
            dialog.setContentView(layout);
            return dialog;
        }

    }

}
