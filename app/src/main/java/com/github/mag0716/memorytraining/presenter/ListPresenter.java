package com.github.mag0716.memorytraining.presenter;

import android.support.annotation.NonNull;

import com.annimon.stream.Stream;
import com.github.mag0716.memorytraining.Application;
import com.github.mag0716.memorytraining.model.Level;
import com.github.mag0716.memorytraining.model.Memory;
import com.github.mag0716.memorytraining.notification.NotificationConductor;
import com.github.mag0716.memorytraining.repository.database.MemoryDao;
import com.github.mag0716.memorytraining.service.TaskConductor;
import com.github.mag0716.memorytraining.tracking.TrackerConductor;
import com.github.mag0716.memorytraining.view.IView;
import com.github.mag0716.memorytraining.view.ListView;
import com.github.mag0716.memorytraining.viewmodel.TrainingViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
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
    private final TaskConductor taskConductor;
    private ListView view;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public void attachView(@NonNull IView view) {
        if (!(view instanceof ListView)) {
            throw new IllegalArgumentException("argument must need ListView.");
        }
        this.view = (ListView) view;
    }

    @Override
    public void detachView() {
        disposables.dispose();
        view = null;
    }

    /**
     * 訓練日時が過ぎているデータを取得する
     *
     * @param category         カテゴリ
     * @param trainingDatetime 訓練日時
     */
    public void loadTrainingData(int category, long trainingDatetime) {
        Timber.d("loadTrainingData : %d, %d", category, trainingDatetime);
        disposables.add(Single.create((SingleOnSubscribe<List<Memory>>) emitter -> {
            final List<Memory> memoryList;
            if (category == TrainingViewModel.CATEGORY_ALL) {
                memoryList = dao.loadAll();
            } else if (category == TrainingViewModel.CATEGORY_ONLY_NEED_TRAINING) {
                memoryList = dao.loadAll(trainingDatetime);
            } else {
                memoryList = new ArrayList<>();
            }
            emitter.onSuccess(memoryList);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(memoryList -> {
                            view.showMemoryList(memoryList);
                            final TrackerConductor trackerConductor = ((Application) view.getContext().getApplicationContext()).getTrackerConductor();
                            trackerConductor.trackUserInformation(Stream.of(memoryList).map(Memory::getTotalCount).count());
                        },
                        throwable -> Timber.w(throwable, "loadTrainingData")));
    }

    /**
     * 記憶できていたのでレベルアップ
     *
     * @param id 訓練対象データ ID
     */
    public void levelUp(long id) {
        Timber.d("levelUp : %d", id);
        disposables.add(loadMemory(id)
                .map(memory -> {
                    final Level currentLevel = Level.values()[memory.getLevel()];
                    final Level nextLevel = currentLevel.getNextLevel(memory.getCount());
                    memory.setNextTrainingDatetime(System.currentTimeMillis() + nextLevel.getTrainingInterval());
                    memory.levelUp(nextLevel.getId());
                    dao.update(memory);
                    taskConductor.registerTaskIfNeeded();
                    NotificationConductor.clearNotificationIfNeeded(view.getContext(), dao);
                    return memory;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(memory -> {
                            view.dismissMemory(memory.getId());
                        },
                        throwable -> Timber.w(throwable, "failed levelUp.")
                ));
    }

    /**
     * 記憶できていなかったのでレベルダウン
     *
     * @param id 訓練対象データ ID
     */
    public void levelDown(long id) {
        Timber.d("levelDown : %d", id);
        disposables.add(loadMemory(id)
                .map(memory -> {
                    final Level currentLevel = Level.values()[memory.getLevel()];
                    final Level previousLevel = currentLevel.getPreviousLevel(memory.getCount());
                    memory.setNextTrainingDatetime(System.currentTimeMillis() + previousLevel.getTrainingInterval());
                    memory.levelDown(previousLevel.getId());
                    dao.update(memory);
                    taskConductor.registerTaskIfNeeded();
                    NotificationConductor.clearNotificationIfNeeded(view.getContext(), dao);
                    return memory;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(memory -> {
                            view.dismissMemory(memory.getId());
                        },
                        throwable -> Timber.w(throwable, "failed levelDown.")
                ));
    }

    /**
     * データの編集
     *
     * @param id 編集対象データ ID
     */
    public void edit(long id) {
        Timber.d("edit : %d", id);
        view.editMemory(id);
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
