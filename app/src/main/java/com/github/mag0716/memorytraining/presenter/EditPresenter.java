package com.github.mag0716.memorytraining.presenter;

import android.support.annotation.NonNull;

import com.github.mag0716.memorytraining.model.Memory;
import com.github.mag0716.memorytraining.repository.database.MemoryDao;
import com.github.mag0716.memorytraining.view.EditView;
import com.github.mag0716.memorytraining.view.IView;

import lombok.RequiredArgsConstructor;
import timber.log.Timber;

/**
 * Created by mag0716 on 2017/07/08.
 */
@RequiredArgsConstructor
public class EditPresenter implements IPresenter {

    private final MemoryDao dao;
    private EditView view;

    @Override
    public void attachView(@NonNull IView view) {
        if (!(view instanceof EditView)) {
            throw new IllegalArgumentException("argument must need EditView.");
        }
        this.view = (EditView) view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    /**
     * 保存
     *
     * @param memory 保存対象データ
     */
    public void save(@NonNull Memory memory) {
        Timber.d("save : %s", memory);
        // TODO: 保存処理
    }
}
