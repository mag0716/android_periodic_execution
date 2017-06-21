package com.github.mag0716.memorytraining.view;

import android.support.annotation.NonNull;

import com.github.mag0716.memorytraining.model.Memory;

import java.util.List;

/**
 * 一覧画面の View インタフェース
 * <p>
 * Created by mag0716 on 2017/06/19.
 */
public interface ListView extends IView {

    /**
     * 訓練対象データを表示
     *
     * @param memoryList 訓練対象データ一覧
     */
    void showMemoryList(@NonNull List<Memory> memoryList);

    /**
     * 訓練対象データを非表示にする
     *
     * @param id Memory#id
     */
    void dismissMemory(long id);

    /**
     * 訓練完了
     */
    void completedTraining();
}
