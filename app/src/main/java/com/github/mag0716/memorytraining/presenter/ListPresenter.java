package com.github.mag0716.memorytraining.presenter;

import android.support.annotation.NonNull;

import com.github.mag0716.memorytraining.repository.database.MemoryDao;
import com.github.mag0716.memorytraining.view.IView;
import com.github.mag0716.memorytraining.view.ListView;

import lombok.RequiredArgsConstructor;
import timber.log.Timber;

/**
 * 一覧画面の Presenter
 * <p>
 * Created by mag0716 on 2017/06/19.
 */
@RequiredArgsConstructor
public class ListPresenter implements IPresenter {

    private final MemoryDao dao;
    private ListView view;

    @Override
    public void attachView(@NonNull IView view) {
        if (!(view instanceof ListView)) {
            throw new IllegalArgumentException("argument must need ListView.");
        }
        this.view = (ListView) view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    /**
     * 記憶できていたのでレベルアップ
     *
     * @param id 訓練対象データ ID
     */
    public void levelUp(long id) {
        Timber.d("levelUp : %d", id);
        // TODO: DB 更新 -> View 更新
    }

    /**
     * 記憶できていなかったのでレベルダウン
     *
     * @param id 訓練対象データ ID
     */
    public void levelDown(long id) {
        Timber.d("levelDown : %d", id);
        // TODO: DB 更新 -> View 更新
    }
}
