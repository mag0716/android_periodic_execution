package com.github.mag0716.memorytraining.presenter;

import android.support.annotation.NonNull;

import com.github.mag0716.memorytraining.view.IView;

/**
 * Presenter インタフェース
 * 各画面で利用する Presenter は本インタフェースを実装すること
 * <p>
 * Created by mag0716 on 2017/04/27.
 */
public interface IPresenter {

    /**
     * View の attach
     * onResume で呼び出されることを期待
     *
     * @param view IView
     */
    void attachView(@NonNull IView view);

    /**
     * View の detach
     * onPause で呼び出されることを期待
     */
    void detachView();
}
