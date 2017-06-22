package com.github.mag0716.memorytraining.util;

import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by mag0716 on 2017/06/17.
 */
public class BindingUtil {

    @BindingAdapter("visibleOrInvisible")
    public static void setVisibleOrInvisible(@NonNull View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    @BindingAdapter("visibleOrGone")
    public static void setVisibleOrGone(@NonNull View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("srcCompat")
    public static void setSrcCompat(@NonNull ImageView view, @DrawableRes int resourceId) {
        view.setImageResource(resourceId);
    }
}
