package com.github.mag0716.memorytraining;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import com.github.mag0716.memorytraining.event.EventBus;
import com.github.mag0716.memorytraining.event.StartTrainingEvent;
import com.github.mag0716.memorytraining.notification.NotificationConductor;
import com.github.mag0716.memorytraining.repository.database.ApplicationDatabase;
import com.github.mag0716.memorytraining.service.TaskConductor;
import com.github.mag0716.memorytraining.tracking.FirebaseTracker;
import com.github.mag0716.memorytraining.tracking.TrackerConductor;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

/**
 * アプリケーション情報
 * アプリケーションのライフサイクルと同じ生存期間のインスタンスを保持する
 * <p>
 * Created by mag0716 on 2017/04/30.
 */
@SuppressLint("Registered")
public class Application extends android.app.Application implements IConfiguration {

    private static final String DB_NAME = "memory.db";

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    protected static ApplicationDatabase database;

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    protected TaskConductor taskConductor;

    private EventBus<StartTrainingEvent> startTrainingEventBus;

    private final TrackerConductor trackerConductor = new TrackerConductor();

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        // TODO: ログ出力を抑制する
        Timber.plant(new Timber.DebugTree());
        startTrainingEventBus = new EventBus<>();
        setUpTracker();
        NotificationConductor.initChannel(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        trackerConductor.clear();
    }

    @NonNull
    @Override
    public ApplicationDatabase getDatabase() {
        if (database == null) {
            database = Room.databaseBuilder(this, ApplicationDatabase.class, DB_NAME)
                    .addMigrations(ApplicationDatabase.MIGRATION_1_2, ApplicationDatabase.MIGRATION_2_3)
                    .build();
        }
        return database;
    }

    @NonNull
    @Override
    public TaskConductor getTaskConductor() {
        if (taskConductor == null) {
            taskConductor = new TaskConductor(this, getDatabase().memoryDao());
        }
        return taskConductor;
    }

    @NonNull
    @Override
    public EventBus<StartTrainingEvent> getStartTrainingEventBus() {
        return startTrainingEventBus;
    }

    public TrackerConductor getTrackerConductor() {
        return trackerConductor;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    protected void setUpTracker() {
        trackerConductor.addTracker(new FirebaseTracker(this));
    }
}
