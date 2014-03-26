package com.sua.runner.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import com.sua.runner.R;

public class FloatingTimeView extends FrameLayout{

    public FloatingTimeView(Context context) {
        super(context);
        init(context);
    }

    public FloatingTimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FloatingTimeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.floating_time, this, true);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Animation animation = new TranslateAnimation(0, 0,-this.getHeight(), 0);
        animation.setDuration(60000);
        animation.setFillAfter(true);
        this.startAnimation(animation);
        this.setVisibility(View.VISIBLE);
    }


}
