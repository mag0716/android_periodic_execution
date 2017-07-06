package com.github.mag0716.memorytraining.view.behavior;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * スクロールに連動して表示／非表示状態を切り替える Behavior
 * <p>
 * Created by mag0716 on 2017/07/06.
 */
@SuppressWarnings("unused") // レイアウトファイルから利用されている
public class FloatingActionButtonShowHideBehavior extends FloatingActionButton.Behavior {

    public FloatingActionButtonShowHideBehavior(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

        if ((child.getVisibility() == View.VISIBLE) && (dyConsumed > 0)) {
            // https://issuetracker.google.com/issues/37130108
            child.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                @Override
                public void onHidden(FloatingActionButton fab) {
                    super.onHidden(fab);
                    fab.setVisibility(View.INVISIBLE);
                }
            });
        } else if ((child.getVisibility() != View.VISIBLE) && (dyConsumed < 0)) {
            child.show();
        }
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }
}
