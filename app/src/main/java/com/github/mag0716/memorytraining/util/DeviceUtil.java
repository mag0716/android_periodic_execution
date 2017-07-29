package com.github.mag0716.memorytraining.util;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.Window;

/**
 * Created by mag0716 on 2017/07/29.
 */

public class DeviceUtil {

    public static int getStatusBarHeight(@NonNull Window window) {
        final Rect rect = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }
}
