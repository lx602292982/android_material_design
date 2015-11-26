package com.lixiang.weather.support.animadapter;

import android.support.annotation.NonNull;

import com.nineoldandroids.animation.Animator;


public class AnimatorUtil {

    private AnimatorUtil() {
    }

    /**
     * Merges given Animators into one array.
     */
    @NonNull
    public static Animator[] concatAnimators(@NonNull final Animator[] childAnimators, @NonNull final Animator[] animators, @NonNull final Animator alphaAnimator) {
        Animator[] allAnimators = new Animator[childAnimators.length + animators.length + 1];
        int i;

        for (i = 0; i < childAnimators.length; ++i) {
            allAnimators[i] = childAnimators[i];
        }

        for (Animator animator : animators) {
            allAnimators[i] = animator;
            ++i;
        }

        allAnimators[allAnimators.length - 1] = alphaAnimator;
        return allAnimators;
    }

}