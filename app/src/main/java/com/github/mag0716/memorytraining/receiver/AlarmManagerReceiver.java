package com.github.mag0716.memorytraining.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.github.mag0716.memorytraining.Application;
import com.github.mag0716.memorytraining.notification.NotificationConductor;
import com.github.mag0716.memorytraining.service.TaskConductor;

import timber.log.Timber;

public class AlarmManagerReceiver extends BroadcastReceiver {

    public static PendingIntent createPendingIntent(@NonNull Context context, @NonNull Intent intent) {
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.d("AlarmManagerReceiver#onReceive");

        final Application application = ((Application) context.getApplicationContext());

        application.getTrackerConductor().trackNotifyEvent(context, getClass().getSimpleName(),
                intent.getLongExtra(TaskConductor.TASK_EXTRAS_TRAINING_DATETIME_KEY, 0L));

        NotificationConductor.notifyTrainingIfNeeded(context, application.getDatabase().memoryDao());

        application.getTaskConductor().registerTaskIfNeeded();
    }
}
