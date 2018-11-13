package com.base.view.progress.loading;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lottery.base.R;


/**
 * name   : LoadingDialog
 * author : woodonchan
 * time   : 2016/11/8
 * desc   :
 * version: 1.0
 */
public class LoadingDialog extends ProgressDialog {


    private String message;

    private boolean isShowBackground = true;

    private boolean isShowMessage = true;

    public LoadingDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public LoadingDialog hideBackground() {
        isShowBackground = false;
        return this;
    }

    public LoadingDialog showBackground() {
        isShowBackground = true;
        return this;
    }

    public LoadingDialog hideMessage() {
        isShowMessage = false;
        return this;
    }

    public LoadingDialog showMessage() {
        isShowMessage = true;
        return this;
    }

    @Override
    public void show() {
        if (isShowing()) {
            return;
        }
        super.show();
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        }
    }

    public LoadingDialog(Context context) {
        super(context, R.style.LoadingProgressDialog);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCancelable(false);
        setCanceledOnTouchOutside(false);

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.argb(100, 0, 0, 0));
        gradientDrawable.setCornerRadius(15);

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (getWidth() / 2.5), (int) (getWidth() / 3.5));
        layout.setLayoutParams(params);
        layout.setGravity(Gravity.CENTER);

        if (isShowBackground) {
            layout.setBackground(gradientDrawable);
        }

        ProgressBar progressBar = new ProgressBar(getContext());
        LinearLayout.LayoutParams progressParams = new LinearLayout.LayoutParams(getWidth() / 8, getWidth() / 8);
        progressParams.gravity = Gravity.CENTER;
        progressBar.setLayoutParams(progressParams);
        layout.addView(progressBar);

        if (isShowBackground && isShowMessage) {
            TextView textView = new TextView(getContext());
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 20, 0, 0);
            textView.setLayoutParams(layoutParams);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            textView.setTextColor(Color.rgb(255, 255, 255));
            textView.setGravity(Gravity.CENTER);
            if (!TextUtils.isEmpty(message)) {
                textView.setText(message);
            } else {
                textView.setText(TextUtils.isEmpty(message) ? "加载中..." : message);
            }
            layout.addView(textView);
        }

        setContentView(layout, params);

    }

    private int getWidth() {
        WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        manager.getDefaultDisplay().getSize(point);
        return point.x;
    }

}
