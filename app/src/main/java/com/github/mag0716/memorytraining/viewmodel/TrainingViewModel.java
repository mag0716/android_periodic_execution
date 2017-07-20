package com.github.mag0716.memorytraining.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.github.mag0716.memorytraining.BR;

/**
 * Created by mag0716 on 2017/07/20.
 */
public class TrainingViewModel extends BaseObservable {

    /**
     * 訓練データを追加可能かどうか
     */
    private boolean addable = true;

    @Bindable
    public boolean isAddable() {
        return addable;
    }

    public void setAddable(boolean addable) {
        this.addable = addable;
        notifyPropertyChanged(BR.addable);
    }
}
