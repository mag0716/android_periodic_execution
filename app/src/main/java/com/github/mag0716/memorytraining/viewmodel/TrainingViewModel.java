package com.github.mag0716.memorytraining.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;

import com.github.mag0716.memorytraining.BR;
import com.github.mag0716.memorytraining.R;

import timber.log.Timber;

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
     * カテゴリを選択可能かどうか
     */
    private boolean selectableCategory = true;
    /**
     * 訓練データを追加可能かどうか
     */
    private boolean addable = true;

    @Bindable
    public int getCurrentCategory() {
        return currentCategory;
    }

    private void setCurrentCategory(@IntRange(from = CATEGORY_ALL, to = CATEGORY_ONLY_NEED_TRAINING) int currentCategory) {
        this.currentCategory = currentCategory;
        notifyPropertyChanged(BR.currentCategory);
    }

    @Bindable
    public boolean isSelectableCategory() {
        return selectableCategory;
    }

    public void setSelectableCategory(boolean selectableCategory) {
        this.selectableCategory = selectableCategory;
        notifyPropertyChanged(BR.selectableCategory);
    }

    @Bindable
    public boolean isAddable() {
        return addable;
    }

    public void setAddable(boolean addable) {
        this.addable = addable;
        notifyPropertyChanged(BR.addable);
    }

    public void changeCategory(@IdRes int categoryId) {
        Timber.d("changeCategory : %d", categoryId);
        if (categoryId == R.id.navigation_all_data) {
            setCurrentCategory(CATEGORY_ALL);
        } else if (categoryId == R.id.navigation_training_data) {
            setCurrentCategory(CATEGORY_ONLY_NEED_TRAINING);
        } else {
            throw new IllegalArgumentException("invalid category ID");
        }
    }
}
