package com.github.mag0716.memorytraining.presenter;

import android.support.annotation.NonNull;

import com.github.mag0716.memorytraining.view.IView;
import com.github.mag0716.memorytraining.view.TrainingView;

import timber.log.Timber;

/**
 * 訓練画面の Presenter
 * <p>
 * Created by mag0716 on 2017/07/08.
 */
public class TrainingPresenter implements IPresenter {

    private TrainingView view;

    @Override
    public void attachView(@NonNull IView view) {
        if (!(view instanceof TrainingView)) {
            throw new IllegalArgumentException("argument must need TrainingView.");
        }
        this.view = (TrainingView) view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    /**
     * 訓練データの追加
     */
    public void addMemory() {
        Timber.d("addMemory");
        view.showAddView();
    }
}
