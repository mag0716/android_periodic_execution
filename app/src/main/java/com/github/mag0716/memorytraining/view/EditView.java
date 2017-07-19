package com.github.mag0716.memorytraining.view;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import com.github.mag0716.memorytraining.model.Memory;

/**
 * Created by mag0716 on 2017/07/08.
 */

public interface EditView extends IView {

    /**
     * 訓練データを表示する
     *
     * @param memory 訓練データ
     */
    @MainThread
    void showMemory(@NonNull Memory memory);

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
