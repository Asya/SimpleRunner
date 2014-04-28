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
import com.sua.runner.model.Run;
import com.sua.runner.model.RunBlock;
import com.sua.runner.utilities.Config;
import com.sua.runner.utilities.PreferencesManager;
import com.sua.runner.utilities.Utils;

public class FloatingTimeView extends FrameLayout{

    private PreferencesManager prefs;

    private int animStartPoint = 0;
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
        prefs = new PreferencesManager(getContext());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        animStartPoint =  -this.getHeight();
        currentAnimEndPoint =  -this.getHeight();
    }


    public void setNextAnimation() {

        switch (prefs.getRunTypeType()){
            case Config.TYPE_NONE:
                break;
            case Config.TYPE_BEFORE_WALK:
                setAnimation(prefs.getRun().getWalkBeforeTime(), 0);
                break;
            case Config.TYPE_RUN:
                setAnimation(prefs.getRun().getRunBlocks().get(prefs.getRunBlock()).getRunTime(), getRunHeightPassed());
                break;
            case Config.TYPE_WALK_IN_RUN:
                setAnimation(prefs.getRun().getRunBlocks().get(prefs.getRunBlock()).getWalkTime(), getWalkHeightPassed());
                break;
            case Config.TYPE_AFTER_WALK:
                setAnimation(prefs.getRun().getWalkAfterTime(), getWalkAfterHeightPassed());
                break;
        }
    }

    private int getRunHeightPassed() {
        Run run = prefs.getRun();
        int heightPassedBefore =  Utils.getRunItemHeight(getContext(), run.getWalkBeforeTime());

        //TODO: check with blocks > 1
        //TODO: change if will not include last walk time
        for(int i = 0; i < run.getRunBlocks().size() - 1; i++) {
            RunBlock block = run.getRunBlocks().get(i);
            heightPassedBefore += (block.getRunTime() + block.getWalkTime()) * block.getRepeat();
        }

        if(prefs.getRepeatCount() > 0) {
            RunBlock block = run.getRunBlocks().get(prefs.getRunBlock());
            heightPassedBefore += (Utils.getRunItemHeight(getContext(), block.getRunTime())
                                   + Utils.getRunItemHeight(getContext(), block.getWalkTime()))
                                   * (prefs.getRepeatCount());
        }

        return heightPassedBefore;
    }

    private int getWalkHeightPassed() {
        int runHeight = Utils.getRunItemHeight(getContext(), prefs.getRun().getRunBlocks().get(prefs.getRunBlock()).getRunTime());
        return getRunHeightPassed() + runHeight;
    }

    private int getWalkAfterHeightPassed() {
        Run run = prefs.getRun();
        int heightPassedBefore =  Utils.getRunItemHeight(getContext(), run.getWalkBeforeTime());

        //TODO: check with blocks > 1
        //TODO: change if will not include last walk time
        for(int i = 0; i < run.getRunBlocks().size(); i++) {
            RunBlock block = run.getRunBlocks().get(i);
            heightPassedBefore += (Utils.getRunItemHeight(getContext(), block.getRunTime())
                                   + Utils.getRunItemHeight(getContext(), block.getWalkTime()))
                                   * (prefs.getRepeatCount());
        }

        return heightPassedBefore;
    }

    private void setAnimation(int duration, int heightPassedBefore) {
        int currentAnimStartPoint = animStartPoint + heightPassedBefore;
        int timeSpace = (int)((System.currentTimeMillis() - prefs.getTimeStartedAction()) / Config.SECOND);
        int heightSpace = 0;
        if(timeSpace >= 1) {
            heightSpace = Utils.getRunItemHeight(getContext(), duration) / duration *  timeSpace;
        }
        currentAnimStartPoint += heightSpace;
        currentAnimEndPoint = currentAnimStartPoint + Utils.getRunItemHeight(getContext(), duration - timeSpace);
        Animation animation = new TranslateAnimation(0, 0, currentAnimStartPoint, currentAnimEndPoint);
        animation.setDuration((duration - timeSpace) * Config.SECOND);
        animation.setFillAfter(true);
        this.startAnimation(animation);
        this.setVisibility(View.VISIBLE);
    }
}
