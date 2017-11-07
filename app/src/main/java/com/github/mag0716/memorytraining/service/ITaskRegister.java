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
     * @param context Context
     * @return API が利用可能かどうか
     */
    boolean isAvailable(@NonNull Context context);

    /**
     * 復旧可能かどうか
     *
     * @param context Context
     * @return API が利用不可の場合にユーザが操作することで利用可能になるかどうか
     */
    boolean isResolvable(@NonNull Context context);

    /**
     * タスクの登録
     *
     * @param context Context
     * @param memory  登録対象
     */
    void registerTask(@NonNull Context context, @NonNull Memory memory);
}
