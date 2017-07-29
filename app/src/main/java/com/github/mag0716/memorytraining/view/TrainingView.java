package com.github.mag0716.memorytraining.view;

/**
 * 訓練画面の View インタフェース
 * <p>
 * Created by mag0716 on 2017/07/07.
 */
public interface TrainingView extends IView {

    /**
     * 一覧画面を表示する
     *
     * @param category 表示するデータのカテゴリ
     */
    void showTrainingList(int category);

    /**
     * 追加画面を表示する
     */
    void showAddView();

    /**
     * 編集画面を表示する
     *
     * @param id Memory#id
     */
    void showEditView(long id);
}
