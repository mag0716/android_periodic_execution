package com.github.mag0716.memorytraining.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.github.mag0716.memorytraining.Application;
import com.github.mag0716.memorytraining.notification.NotificationConductor;
import com.github.mag0716.memorytraining.repository.database.MemoryDao;
import com.github.mag0716.memorytraining.service.TaskConductor;

import timber.log.Timber;

/**
 * 端末起動を検知する BroadcastReceiver
 * <p>
 * タスクの再登録
 * Notification の通知
 * <p>
 * を実行する
 * <p>
 * Created by mag0716 on 2017/07/02.
 */
public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        Timber.d("BootCompletedReceiver#onReceive : action = %s", action);
        if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            final MemoryDao dao = ((Application) context.getApplicationContext()).getDatabase().memoryDao();
            TaskConductor.registerTaskIfNeeded(context, dao);
            NotificationConductor.notifyTraining(context, dao);
        }
    }
}
