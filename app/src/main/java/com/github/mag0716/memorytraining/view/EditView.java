package com.github.mag0716.memorytraining.view;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

/**
 * Created by mag0716 on 2017/07/08.
 */

public interface EditView extends IView {

    /**
     * 保存に成功
     */
    @MainThread
    void saveSuccess();

    /**
     * 保存に失敗
     *
     * @param throwable 失敗原因
     */
    @MainThread
    void saveFailed(@NonNull Throwable throwable);
}
