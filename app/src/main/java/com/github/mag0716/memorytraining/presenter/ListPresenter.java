package com.github.mag0716.memorytraining.presenter;

import android.support.annotation.NonNull;

import com.github.mag0716.memorytraining.model.Memory;
import com.github.mag0716.memorytraining.repository.database.MemoryDao;
import com.github.mag0716.memorytraining.view.IView;
import com.github.mag0716.memorytraining.view.ListView;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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
     * 訓練データの追加
     */
    public void addMemory() {
        Timber.d("addMemory");
        // TODO: 追加画面へ遷移
    }

    /**
     * 記憶できていたのでレベルアップ
     *
     * @param id 訓練対象データ ID
     */
    public void levelUp(long id) {
        Timber.d("levelUp : %d", id);
        loadMemory(id)
                .map(memory -> {
                    // TODO: DB 更新
                    return memory;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(memory -> {
                            //TODO: View 更新
                        },
                        throwable -> Timber.w(throwable, "failed levelUp.")
                );
    }

    /**
     * 記憶できていなかったのでレベルダウン
     *
     * @param id 訓練対象データ ID
     */
    public void levelDown(long id) {
        Timber.d("levelDown : %d", id);
        loadMemory(id)
                .map(memory -> {
                    // TODO: DB 更新
                    return memory;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(memory -> {
                            //TODO: View 更新
                        },
                        throwable -> Timber.w(throwable, "failed levelDown.")
                );
    }

    private Single<Memory> loadMemory(long id) {
        return Single.create(emitter -> {
            final Memory memory = dao.load(id);
            Timber.d("load : %s", memory);
            if (memory != null) {
                emitter.onSuccess(memory);
            } else {
                emitter.onError(new IllegalArgumentException("cannot load by id = " + id));
            }
        });
    }
}
