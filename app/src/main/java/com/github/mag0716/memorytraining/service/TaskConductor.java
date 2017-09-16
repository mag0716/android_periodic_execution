package com.github.mag0716.memorytraining.service;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.mag0716.memorytraining.model.Memory;
import com.github.mag0716.memorytraining.repository.database.MemoryDao;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * 定期実行タスクの登録を管理して、どの API を利用して登録するのかを振り分ける
 * <p>
 * Created by mag0716 on 2017/06/28.
 */
public class TaskConductor {

    public static final String TASK_EXTRAS_TRAINING_DATETIME_KEY = "TrainingDatetime";

    private Context context;

    private MemoryDao memoryDao;

    private ITaskRegister taskRegister;

    public TaskConductor(@NonNull Context context, @NonNull MemoryDao memoryDao) {
        this.context = context;
        this.memoryDao = memoryDao;
        updateTaskRegister();
    }

    /**
     * 直近の訓練日時のデータがあればタスクを登録する
     */
    public void registerTaskIfNeeded() {
        Maybe.create((MaybeOnSubscribe<Memory>) emitter -> {
                    final Memory recentMemory = memoryDao.loadRecent(System.currentTimeMillis());
                    if (recentMemory != null) {
                        emitter.onSuccess(recentMemory);
                    } else {
                        emitter.onComplete();
                    }
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<Memory>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(Memory memory) {
                        registerTask(memory);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.w("registerTaskIfNeeded#onError", e);
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("registerTaskIfNeeded#onComplete");
                    }
                });
    }

    /**
     * 定期実行タスク登録に利用する ITaskRegsiter を更新
     */
    private void updateTaskRegister() {
        // TODO: ITaskRegister が切り替わったら前回利用していた ITaskRegister の定期実行をキャンセルする
        for (TaskRegisterType taskRegisterType : TaskRegisterType.values()) {
            ITaskRegister taskRegister = taskRegisterType.getTaskRegister();
            if (taskRegister != null && taskRegister.isAvailable(context)) {
                this.taskRegister = taskRegister;
            }
        }
    }

    /**
     * 利用してタスクを登録する
     *
     * @param memory 直近の訓練データ
     */
    private void registerTask(@NonNull Memory memory) {
        if (taskRegister != null) {
            taskRegister.registerTask(context, memory);
        }
    }
}
