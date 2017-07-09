package com.github.mag0716.memorytraining.presenter;

import android.support.annotation.NonNull;

import com.github.mag0716.memorytraining.model.Memory;
import com.github.mag0716.memorytraining.repository.database.MemoryDao;
import com.github.mag0716.memorytraining.view.EditView;
import com.github.mag0716.memorytraining.view.IView;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;
import timber.log.Timber;

/**
 * Created by mag0716 on 2017/07/08.
 */
@RequiredArgsConstructor
public class EditPresenter implements IPresenter {

    private final MemoryDao dao;
    private EditView view;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public void attachView(@NonNull IView view) {
        if (!(view instanceof EditView)) {
            throw new IllegalArgumentException("argument must need EditView.");
        }
        this.view = (EditView) view;
    }

    @Override
    public void detachView() {
        disposables.dispose();
        this.view = null;
    }

    /**
     * 保存
     *
     * @param memory 保存対象データ
     */
    public void save(@NonNull Memory memory) {
        Timber.d("save : %s", memory);
        disposables.add(
                Completable.create(emitter -> {
                            // TODO: 更新処理
                            dao.insert(memory);
                            emitter.onComplete();
                        }
                ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> view.saveSuccess(),
                                throwable -> view.saveFailed(throwable))
        );
    }
}
