package com.github.mag0716.memorytraining.view;

import android.support.annotation.NonNull;

import com.github.mag0716.memorytraining.viewmodel.ListViewModel;

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
     * @param viewModelList 訓練対象データ一覧
     */
    void showMemoryList(@NonNull List<ListViewModel> viewModelList);

    /**
     * 訓練完了
     */
    void completedTraining();
}
