package com.github.mag0716.memorytraining.presenter;

import android.support.annotation.NonNull;

import com.github.mag0716.memorytraining.view.IView;

/**
 * Created by mag0716 on 2017/04/27.
 */
public interface IPresenter {

    void attachView(@NonNull IView view);

    void detachView();
}
