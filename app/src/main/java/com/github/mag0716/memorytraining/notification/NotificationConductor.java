package com.github.mag0716.memorytraining.notification;

import android.app.Notification;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.github.mag0716.memorytraining.R;
import com.github.mag0716.memorytraining.model.Memory;
import com.github.mag0716.memorytraining.repository.database.MemoryDao;

import java.util.List;
import java.util.Locale;

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
            // TODO: アイコン、文言
            final NotificationManagerCompat manager = NotificationManagerCompat.from(context);
            Notification notification = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                    .setContentTitle("訓練日時になりました")
                    .setContentText(String.format(Locale.getDefault(), "訓練対象データが%d件があります。覚えているか確認しましょう。", count))
                    .build();
            // TODO: ID
            manager.notify(0, notification);
        });

    }
}
