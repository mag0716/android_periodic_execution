package com.github.mag0716.memorytraining.util;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Created by mag0716 on 2017/06/17.
 */
public class BindingUtil {

    @BindingAdapter("visibleOrInvisible")
    public static void setVisibleOrInvisible(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    @BindingAdapter("visibleOrGone")
    public static void setVisibleOrGone(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}
