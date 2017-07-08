package com.github.mag0716.memorytraining.view;

/**
 * Created by mag0716 on 2017/07/08.
 */

public interface EditView extends IView {

    /**
     * 保存に成功
     */
    void saveSuccess();

    /**
     * 保存に失敗
     */
    void saveFailed();
}
