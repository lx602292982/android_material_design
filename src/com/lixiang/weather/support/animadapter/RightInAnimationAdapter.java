package com.lixiang.weather.support.animadapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

public class RightInAnimationAdapter extends SingleAnimationAdapter {

    private static final String TRANSLATION_Y = "translationY";

    public RightInAnimationAdapter(@NonNull final BaseAdapter baseAdapter) {
        super(baseAdapter);
    }

    @NonNull
    @Override
    protected Animator getAnimator(@NonNull final ViewGroup parent, @NonNull final View view) {
    	return ObjectAnimator.ofFloat(view, TRANSLATION_Y, parent.getMeasuredHeight() >> 2, 0);
    }
}