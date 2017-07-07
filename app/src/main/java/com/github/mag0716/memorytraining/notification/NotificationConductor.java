package com.github.mag0716.memorytraining.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.github.mag0716.memorytraining.R;
import com.github.mag0716.memorytraining.activity.TrainingActivity;
import com.github.mag0716.memorytraining.model.Memory;
import com.github.mag0716.memorytraining.repository.database.MemoryDao;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Notification を管理する
 * <p>
 * Created by mag0716 on 2017/07/04.
 */
public class NotificationConductor {

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
     * 訓練日時になったら通知する
     *
     * @param context Context
     * @param dao     MemoryDao
     */
    public static void notifyTrainingIfNeeded(@NonNull Context context, @NonNull MemoryDao dao) {
        Timber.d("notifyTrainingIfNeeded");

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
            Notification notification = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                    .setContentTitle(context.getString(R.string.notification_training_title))
                    .setContentText(context.getString(R.string.notification_training_message_format, count))
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .build();
            manager.notify(notificationId, notification);
        });
    }
}
