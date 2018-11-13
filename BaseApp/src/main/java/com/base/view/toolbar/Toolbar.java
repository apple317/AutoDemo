package com.base.view.toolbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lottery.base.R;


/**
 * name   : Toolbar
 * author : woodonchan
 * time   : 2017/7/6
 * desc   : 通用Toolbar
 * version: 1.0
 */
public class Toolbar extends FrameLayout {

    private Context context;

    private ImageView leftImageView;
    private ImageView rightImageView;
    private TextView titleTextView;
    private TextView leftTextView;
    private TextView rightTextView;

    private OnLeftClickListener onLeftClickListener;

    private OnRightClickListener onRightClickListener;

    public ImageView getLeftImageView() {
        return leftImageView;
    }

    public ImageView getRightImageView() {
        return rightImageView;
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public TextView getLeftTextView() {
        return leftTextView;
    }

    public TextView getRightTextView() {
        return rightTextView;
    }

    public void setOnLeftClickListener(OnLeftClickListener onLeftClickListener) {
        this.onLeftClickListener = onLeftClickListener;
    }

    public void setOnRightClickListener(OnRightClickListener onRightClickListener) {
        this.onRightClickListener = onRightClickListener;
    }

    public Toolbar(Context context) {
        this(context, null);
    }

    public Toolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Toolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setOnTouchListener((view, motionEvent) -> true);
        final TypedArray customAttrs = context.obtainStyledAttributes(attrs,
                R.styleable.Toolbar);
        int titleTxtAttr = customAttrs.getResourceId(R.styleable.Toolbar_titleTxt, 0);
        int leftImgAttr = customAttrs.getResourceId(R.styleable.Toolbar_leftImg, 0);
        int rightImgAttr = customAttrs.getResourceId(R.styleable.Toolbar_rightImg, 0);
        boolean showLeftImgAttr = customAttrs.getBoolean(R.styleable.Toolbar_showLeftImg, false);
        boolean showRightImgAttr = customAttrs.getBoolean(R.styleable.Toolbar_showRightImg, false);
        boolean showLeftTvAttr = customAttrs.getBoolean(R.styleable.Toolbar_showLeftTxt, false);
        boolean showRightTvAttr = customAttrs.getBoolean(R.styleable.Toolbar_showRightTxt, false);
        int leftTxtAttr = customAttrs.getResourceId(R.styleable.Toolbar_leftTxt, 0);
        int rightTxtAttr = customAttrs.getResourceId(R.styleable.Toolbar_rightTxt, 0);
        int background = customAttrs.getResourceId(R.styleable.Toolbar_background, 0);

        setBackgroundResource(background);

        RelativeLayout layout = new RelativeLayout(context);
        layout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(context, 48)));

        RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        RelativeLayout.LayoutParams leftImgParams = new RelativeLayout.LayoutParams(dp2px(context, 20), dp2px(context, 20));
        leftImgParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        RelativeLayout.LayoutParams rightImgParams = new RelativeLayout.LayoutParams(dp2px(context, 20), dp2px(context, 20));
        leftImgParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        RelativeLayout.LayoutParams leftTvParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams rightTvParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

        RelativeLayout rightLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams rightLayoutParams = new RelativeLayout.LayoutParams(dp2px(context, 96), dp2px(context, 48));
        rightLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        rightLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        rightLayout.setLayoutParams(rightLayoutParams);
        rightLayout.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        rightLayout.setPadding(0, 0, dp2px(context, 10), 0);
        rightLayout.setOnClickListener(view -> {
            if (onRightClickListener != null)
                onRightClickListener.onRightClick(view);
        });

        LinearLayout leftLayout = new LinearLayout(context);
        leftLayout.setOrientation(LinearLayout.HORIZONTAL);
        leftLayout.setPadding(dp2px(context, 10), 0, 0, 0);
        RelativeLayout.LayoutParams leftLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dp2px(context, 48));
        leftLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        leftLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        leftLayout.setLayoutParams(leftLayoutParams);
        leftLayout.setGravity(Gravity.CENTER);
        leftLayout.setOnClickListener(view -> {
            if (onLeftClickListener != null)
                onLeftClickListener.onLeftClick(view);
        });

        /*左按钮*/
        leftImageView = new ImageView(context);
        leftImageView.setScaleType(ImageView.ScaleType.CENTER);
        leftImageView.setLayoutParams(leftImgParams);
        leftImageView.setVisibility(showLeftImgAttr ? VISIBLE : GONE);
        if (leftImgAttr > 0) {
            leftImageView.setBackgroundResource(leftImgAttr);
            leftLayout.addView(leftImageView);
        } else {
           // leftImageView.setBackgroundResource(R.drawable.icon_back);
            leftLayout.addView(leftImageView);

            TextView tvBack = new TextView(context);
            tvBack.setTextColor(getResources().getColor(R.color.color_ffffff));
            tvBack.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            tvBack.setText("返回");
            leftLayout.addView(tvBack);
        }

         /*左侧文本*/
        leftTextView = new TextView(context);
        leftTextView.setLayoutParams(leftTvParams);
        leftTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        leftTextView.setGravity(Gravity.CENTER);
        leftTextView.setVisibility(showLeftTvAttr ? VISIBLE : GONE);
        leftTextView.setText(leftTxtAttr > 0 ? customAttrs.getResources().getText(leftTxtAttr) : customAttrs.getString(R
                .styleable.Toolbar_leftTxt));
        leftLayout.addView(leftTextView);

        layout.addView(leftLayout);

        /*标题*/
        titleTextView = new TextView(context);
        titleTextView.setLayoutParams(titleParams);
        titleTextView.setTextColor(Color.rgb(255, 255, 255));
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
        titleTextView.setPadding(dp2px(context, 45), dp2px(context, 0), dp2px(context, 55), dp2px(context, 0));
        titleTextView.setSingleLine();
        titleTextView.setGravity(Gravity.CENTER);
        titleTextView.setText(titleTxtAttr > 0 ? customAttrs.getResources().getText(titleTxtAttr) : customAttrs.getString(R
                .styleable.Toolbar_titleTxt));
        layout.addView(titleTextView);

        /*右按钮*/
        rightImageView = new ImageView(context);
        rightImageView.setLayoutParams(rightImgParams);
        rightImageView.setScaleType(ImageView.ScaleType.CENTER);
        if (rightImgAttr > 0) {
            rightImageView.setBackgroundResource(rightImgAttr);
        }
        rightImageView.setVisibility(showRightImgAttr ? VISIBLE : GONE);
        rightLayout.addView(rightImageView);

        /*右侧文本*/
        rightTextView = new TextView(context);
        rightTextView.setLayoutParams(rightTvParams);
        rightTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        rightTextView.setGravity(Gravity.CENTER_VERTICAL);
        rightTextView.setVisibility(showRightTvAttr ? VISIBLE : GONE);
        rightTextView.setText(rightTxtAttr > 0 ? customAttrs.getResources().getText(rightTxtAttr) : customAttrs.getString(R
                .styleable.Toolbar_rightTxt));
        rightTextView.setTextColor(Color.rgb(255, 255, 255));
        rightLayout.addView(rightTextView);

        layout.addView(rightLayout);

        addView(layout);

        customAttrs.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(widthSize, dp2px(context, 48));
    }

    private int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    public interface OnLeftClickListener {
        void onLeftClick(View v);
    }

    public interface OnRightClickListener {
        void onRightClick(View v);
    }
}
