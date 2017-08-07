package com.github.mag0716.memorytraining.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.mag0716.memorytraining.model.Memory;
import com.github.mag0716.memorytraining.repository.database.MemoryDao;
import com.github.mag0716.memorytraining.view.EditView;
import com.github.mag0716.memorytraining.view.IView;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
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
     * @param memory                  保存対象データ
     * @param isClearTrainingDatetime 訓練日時をクリアするかどうか
     */
    public void save(@NonNull Memory memory, boolean isClearTrainingDatetime) {
        Timber.d("save : %s, %b", memory, isClearTrainingDatetime);
        disposables.add(
                Completable.create(emitter -> {
                            if (memory.isNewData()) {
                                dao.insert(memory);
                            } else {
                                memory.setNextTrainingDatetime(0);
                                dao.update(memory);
                            }
                            emitter.onComplete();
                        }
                ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> view.saveSuccess(),
                                throwable -> view.saveFailed(throwable))
        );
    }

    public void loadIfNeeded(@Nullable Bundle bundle, @NonNull String key) {
        Timber.d("loadIfNeeded");

        if (bundle != null && bundle.containsKey(key)) {
            final long id = bundle.getLong(key);
            disposables.add(
                    Single.create((SingleOnSubscribe<Memory>) emitter -> {
                        final Memory memory = dao.load(id);
                        if (memory != null) {
                            emitter.onSuccess(memory);
                        } else {
                            emitter.onError(new IllegalArgumentException());
                        }
                    }).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(memory -> view.showMemory(memory),
                                    throwable -> view.showMemory(new Memory())));
        } else {
            view.showMemory(new Memory());
        }
    }
}
