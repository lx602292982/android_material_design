package com.lixiang.weather.support.animadapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

public class ScaleInAnimationAdapter extends AnimationAdapter {

    private static final float DEFAULT_SCALE_FROM = 0.8f;
    private static final float DEFAULT_AlPHA_FROM = 0.6f;

    private static final String SCALE_X = "scaleX";
    private static final String SCALE_Y = "scaleY";
    private static final String ALPHA = "alpha";
    private static final String TRANSLATION_Y = "translationY";

    private final float mScaleFrom;

    public ScaleInAnimationAdapter(@NonNull final BaseAdapter baseAdapter) {
        this(baseAdapter, DEFAULT_SCALE_FROM);
    }

    public ScaleInAnimationAdapter(@NonNull final BaseAdapter baseAdapter, final float scaleFrom) {
        super(baseAdapter);
        mScaleFrom = scaleFrom;
    }

    @NonNull
    @Override
    public Animator[] getAnimators(@NonNull final ViewGroup parent, @NonNull final View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, SCALE_X, mScaleFrom, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, SCALE_Y, mScaleFrom, 1f);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, TRANSLATION_Y, parent.getMeasuredHeight() >> 2, 0);
        return new ObjectAnimator[]{scaleX, scaleY,translationY};
    }
}
