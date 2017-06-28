package com.github.mag0716.memorytraining.service;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.mag0716.memorytraining.model.Memory;
import com.github.mag0716.memorytraining.repository.database.MemoryDao;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.OneoffTask;

import timber.log.Timber;

/**
 * 定期実行タスクの登録を管理して、どの API を利用して登録するのかを振り分ける
 * <p>
 * Created by mag0716 on 2017/06/28.
 */
public class TaskRegister {

    private static final String TASK_TAG = "MemoryTask";

    private TaskRegister() {
    }

    /**
     * 直近の訓練日時のデータがあればタスクを登録する
     *
     * @param dao MemoryDao
     */
    public static void registerTaskIfNeeded(@NonNull Context context, @NonNull MemoryDao dao) {
        // TODO: 各 API の振り分け, タスクの登録, 選択中の API 以外のタスクをキャンセル
//        final Memory recentMemory;
//        registerGcmTask(context, recentMemory);
    }

    /**
     * GcmNetworkManager を利用してタスクを登録する
     *
     * @param memory 直近の訓練データ
     */
    private static void registerGcmTask(@NonNull Context context, @NonNull Memory memory) {
        Timber.d("registerGcmTask : memory = %s", memory);
        final long delay = memory.getNextTrainingDatetime() - System.currentTimeMillis();
        final GcmNetworkManager manager = GcmNetworkManager.getInstance(context);
        final OneoffTask task = new OneoffTask.Builder()
                .setService(GcmTaskService.class)
                .setTag(TASK_TAG)
                .setExecutionWindow(delay, delay + 3600L) // TODO: 固定値の調整
                .build();
        manager.schedule(task);
    }
}
