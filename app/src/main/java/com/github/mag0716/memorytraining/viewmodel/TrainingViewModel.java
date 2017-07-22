package com.github.mag0716.memorytraining.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.IntRange;

import com.github.mag0716.memorytraining.BR;

/**
 * Created by mag0716 on 2017/07/20.
 */
public class TrainingViewModel extends BaseObservable {

    /**
     * 訓練が必要なデータのみ
     */
    public static final int CATEGORY_ONLY_NEED_TRAINING = -1;
    /**
     * 全てのデータ
     */
    public static final int CATEGORY_ALL = -2;

    /**
     * 現在選択中のカテゴリ
     */
    private int currentCategory = CATEGORY_ONLY_NEED_TRAINING;
    /**
     * 訓練データを追加可能かどうか
     */
    private boolean addable = true;

    public int getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(@IntRange(from = CATEGORY_ALL, to = CATEGORY_ONLY_NEED_TRAINING) int currentCategory) {
        this.currentCategory = currentCategory;
    }

    @Bindable
    public boolean isAddable() {
        return addable;
    }

    public void setAddable(boolean addable) {
        this.addable = addable;
        notifyPropertyChanged(BR.addable);
    }
}
