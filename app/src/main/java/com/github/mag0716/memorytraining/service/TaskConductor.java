package com.github.mag0716.memorytraining.service;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.mag0716.memorytraining.model.Memory;
import com.github.mag0716.memorytraining.repository.database.MemoryDao;
import com.github.mag0716.memorytraining.service.gcmnetworkmanager.GcmNetworkManagerService;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.OneoffTask;
import com.google.android.gms.gcm.Task;

import java.util.concurrent.TimeUnit;

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

    private static final String TASK_TAG = "MemoryTask";

    /**
     * 直近の訓練日時のデータがあればタスクを登録する
     *
     * @param dao MemoryDao
     */
    public static void registerTaskIfNeeded(@NonNull Context context, @NonNull MemoryDao dao) {
        // TODO: 各 API の振り分け, タスクの登録, 選択中の API 以外のタスクをキャンセル
        Maybe.create((MaybeOnSubscribe<Memory>) emitter -> {
                    final Memory recentMemory = dao.loadRecent(System.currentTimeMillis());
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
                        registerGcmTask(context, memory);
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
     * GcmNetworkManager を利用してタスクを登録する
     *
     * @param memory 直近の訓練データ
     */
    private static void registerGcmTask(@NonNull Context context, @NonNull Memory memory) {
        Timber.d("registerGcmTask : memory = %s", memory);
        final long delayMilliseconds = memory.getNextTrainingDatetime() - System.currentTimeMillis();
        final GcmNetworkManager manager = GcmNetworkManager.getInstance(context);
        final OneoffTask task = new OneoffTask.Builder()
                .setService(GcmNetworkManagerService.class)
                .setTag(TASK_TAG)
                .setExecutionWindow(TimeUnit.MILLISECONDS.toSeconds(delayMilliseconds),
                        TimeUnit.MILLISECONDS.toSeconds(delayMilliseconds) + 60L) // TODO: 固定値の調整
                .setRequiredNetwork(Task.NETWORK_STATE_ANY)
                .setRequiresCharging(false)
                .setPersisted(false)
                .build();
        manager.schedule(task);
    }
}
