package com.sua.runner.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import com.sua.runner.R;
import com.sua.runner.utilities.Config;
import com.sua.runner.utilities.PreferencesManager;
import com.sua.runner.utilities.Utils;

public class FloatingTimeView extends FrameLayout{

    private int currentAnimStartPoint = 0;
    private int currentAnimEndPoint = 0;
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
        currentAnimStartPoint =  -this.getHeight();
        currentAnimEndPoint =  -this.getHeight();
    }


    public void setNextAnimation() {
        PreferencesManager prefs = new PreferencesManager(getContext());
        currentAnimStartPoint = currentAnimEndPoint;

        switch (prefs.getRunTypeType()){
            case Config.TYPE_BEFORE_WALK:
                setAnimation(prefs.getCurrentRun().getWalkBeforeTime());
                break;
            case Config.TYPE_RUN:
                setAnimation(prefs.getCurrentRun().getRunBlocks().get(prefs.getRunBlock()).getRunTime());
                break;
            case Config.TYPE_WALK_IN_RUN:
                setAnimation(prefs.getCurrentRun().getRunBlocks().get(prefs.getRunBlock()).getWalkTime());
                break;
            case Config.TYPE_AFTER_WALK:
                setAnimation(prefs.getCurrentRun().getWalkAfterTime());
                break;
        }
    }

    private void setAnimation(int duration) {
        Log.e("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!", new PreferencesManager(getContext()).getRunTypeType() + " + " + Utils.getRunItemHeight(getContext(), duration) + " + " + duration);
        currentAnimEndPoint = currentAnimStartPoint + Utils.getRunItemHeight(getContext(), duration);
        Animation animation = new TranslateAnimation(0, 0, currentAnimStartPoint, currentAnimEndPoint);
        animation.setDuration(duration * Config.SECOND);
        animation.setFillAfter(true);
        this.startAnimation(animation);
        this.setVisibility(View.VISIBLE);
    }
}
