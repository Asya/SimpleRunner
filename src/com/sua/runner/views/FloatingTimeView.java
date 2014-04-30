package com.sua.runner.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import com.sua.runner.R;
import com.sua.runner.model.Training;
import com.sua.runner.model.RunBlock;
import com.sua.runner.utilities.Config;
import com.sua.runner.utilities.PreferencesManager;
import com.sua.runner.utilities.Utils;

public class FloatingTimeView extends FrameLayout{

    private PreferencesManager prefs;

    //private int animStartPoint = 0;
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
        //animStartPoint =  -this.getHeight();
        currentAnimEndPoint =  -this.getHeight();
    }


    public void setNextAnimation() {

        switch (prefs.getCurrentActionType()){
            case Config.TYPE_NONE:
                break;
            case Config.TYPE_BEFORE_WALK:
                setAnimation(prefs.getTraining().getWalkBeforeTime(), 0);
                break;
            case Config.TYPE_RUN:
                setAnimation(prefs.getTraining().getRunBlocks().get(prefs.getCurrentRunBlockIndex()).getRunTime(), getTrainingHeightPassed());
                break;
            case Config.TYPE_WALK_IN_RUN:
                setAnimation(prefs.getTraining().getRunBlocks().get(prefs.getCurrentRunBlockIndex()).getWalkTime(), getWalkHeightPassed());
                break;
            case Config.TYPE_AFTER_WALK:
                setAnimation(prefs.getTraining().getWalkAfterTime(), getWalkAfterHeightPassed());
                break;
        }
    }

    private int getTrainingHeightPassed() {
        Training training = prefs.getTraining();
        int heightPassedBefore =  Utils.getTrainingItemHeight(getContext(), training.getWalkBeforeTime());

        //TODO: check with blocks > 1
        //TODO: change if will not include last walk time
        for(int i = 0; i < training.getRunBlocks().size() - 1; i++) {
            RunBlock block = training.getRunBlocks().get(i);
            heightPassedBefore += (Utils.getTrainingItemHeight(getContext(), block.getRunTime())
                                   + Utils.getTrainingItemHeight(getContext(), block.getWalkTime()))
                                   * block.getRepeat();
        }

        if(prefs.getCurrentRepeatCount() > 0) {
            RunBlock block = training.getRunBlocks().get(prefs.getCurrentRunBlockIndex());
            heightPassedBefore += (Utils.getTrainingItemHeight(getContext(), block.getRunTime())
                                   + Utils.getTrainingItemHeight(getContext(), block.getWalkTime()))
                                   * prefs.getCurrentRepeatCount();
        }

        return heightPassedBefore;
    }

    private int getWalkHeightPassed() {
        int runHeight = Utils.getTrainingItemHeight(getContext(), prefs.getTraining().getRunBlocks().get(prefs.getCurrentRunBlockIndex()).getRunTime());
        return getTrainingHeightPassed() + runHeight;
    }

    private int getWalkAfterHeightPassed() {
        Training training = prefs.getTraining();
        int heightPassedBefore =  Utils.getTrainingItemHeight(getContext(), training.getWalkBeforeTime());

        //TODO: check with blocks > 1
        //TODO: change if will not include last walk time
        for(int i = 0; i < training.getRunBlocks().size(); i++) {
            RunBlock block = training.getRunBlocks().get(i);
            heightPassedBefore += (Utils.getTrainingItemHeight(getContext(), block.getRunTime())
                                   + Utils.getTrainingItemHeight(getContext(), block.getWalkTime()))
                                   * (prefs.getCurrentRepeatCount());
        }

        return heightPassedBefore;
    }

    private void setAnimation(int duration, int heightPassedBefore) {
        currentAnimEndPoint = heightPassedBefore + Utils.getTrainingItemHeight(getContext(), duration);

        int timeSpace = (int)((System.currentTimeMillis() - prefs.getTimeStartedAction()) / Config.SECOND);
        int heightSpace = 0;
        if(timeSpace >= 1) {
            heightSpace = Utils.getTrainingItemHeight(getContext(), duration) / duration *  timeSpace;
        }
        heightPassedBefore += heightSpace;

        ValueAnimator animation = slideAnimator(heightPassedBefore, currentAnimEndPoint);
        if(duration - timeSpace > 0) {
            animation.setDuration((duration - timeSpace) * Config.SECOND);
            animation.start();
            this.setVisibility(View.VISIBLE);
        }
    }

    private ValueAnimator slideAnimator(int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.height = value;
                setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
}
