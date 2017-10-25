package com.github.mag0716.memorytraining.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.github.mag0716.memorytraining.Application;
import com.github.mag0716.memorytraining.R;
import com.github.mag0716.memorytraining.activity.TrainingActivity;
import com.github.mag0716.memorytraining.event.EventBus;
import com.github.mag0716.memorytraining.event.StartTrainingEvent;
import com.github.mag0716.memorytraining.model.Memory;
import com.github.mag0716.memorytraining.repository.database.MemoryDao;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Notification を管理する
 * <p>
 * Created by mag0716 on 2017/07/04.
 */
public class NotificationConductor {

    private static final String NOTIFICATION_CHANNEL_ID = "TrainingNotificationChannel";

    /**
     * Notification 種別
     */
    private enum NotificationType {
        /**
         * 訓練を促す Notification
         */
        TRAINING_NOTIFICATION(0);

        private final int id;

        NotificationType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    private NotificationConductor() {
    }

    /**
     * NotificationChannel を初期化
     *
     * @param context Context
     */
    public static void initChannel(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                if (notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) == null) {
                    final NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                            context.getString(R.string.notification_channel_name),
                            NotificationManager.IMPORTANCE_DEFAULT);
                    channel.setDescription(context.getString(R.string.notification_channel_description));
                    channel.enableVibration(true);
                    channel.setShowBadge(true);
                    notificationManager.createNotificationChannel(channel);
                }
            }
        }
    }

    /**
     * 訓練日時になったら通知する
     *
     * @param context Context
     * @param dao     MemoryDao
     */
    public static void notifyTrainingIfNeeded(@NonNull Context context, @NonNull MemoryDao dao) {
        Timber.d("notifyTrainingIfNeeded");

        final EventBus<StartTrainingEvent> startTrainingEventBus = ((Application) context.getApplicationContext()).getStartTrainingEventBus();
        startTrainingEventBus.send(new StartTrainingEvent());

        // 訓練中は Notification は不要
        if (!startTrainingEventBus.hasObservers()) {
            Maybe.create((MaybeOnSubscribe<Integer>) emitter -> {
                final List<Memory> memoryList = dao.loadAll(System.currentTimeMillis());
                if (memoryList.isEmpty()) {
                    emitter.onComplete();
                } else {
                    emitter.onSuccess(memoryList.size());
                }
            }).subscribeOn(Schedulers.io()).subscribe(count -> {
                final int notificationId = NotificationType.TRAINING_NOTIFICATION.getId();
                final NotificationManagerCompat manager = NotificationManagerCompat.from(context);
                final Intent intent = TrainingActivity.createIntent(context);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                final PendingIntent pendingIntent = PendingIntent.getActivity(context,
                        notificationId,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                Notification notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                        .setContentTitle(context.getString(R.string.notification_training_title))
                        .setContentText(context.getString(R.string.notification_training_message))
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .build();
                manager.notify(notificationId, notification);
            });
        }
    }

    public static void clearNotificationIfNeeded(@NonNull Context context, @NonNull MemoryDao dao) {
        Timber.d("clearNotificationIfNeeded");

        Single.create((SingleOnSubscribe<Boolean>) emitter -> {
            final List<Memory> memoryList = dao.loadAll(System.currentTimeMillis());
            emitter.onSuccess(memoryList.isEmpty());
        }).subscribeOn(Schedulers.io()).subscribe(allCleared -> {
            if (allCleared) {
                final NotificationManagerCompat manager = NotificationManagerCompat.from(context);
                manager.cancel(NotificationType.TRAINING_NOTIFICATION.getId());
            }
        });

    }
}
