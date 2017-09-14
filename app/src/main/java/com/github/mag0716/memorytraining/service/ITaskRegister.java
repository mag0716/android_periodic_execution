package com.github.mag0716.memorytraining.service;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.mag0716.memorytraining.model.Memory;

/**
 * Created by mag0716 on 2017/09/14.
 */
public interface ITaskRegister {

    /**
     * 利用可能かどうか
     *
     * @param context
     * @return
     */
    boolean isAvailable(@NonNull Context context);

    /**
     * タスクの登録
     *
     * @param context
     * @param memory
     */
    void registerTask(@NonNull Context context, @NonNull Memory memory);
}
