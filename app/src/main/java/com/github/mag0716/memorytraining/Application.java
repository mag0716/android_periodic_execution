package com.github.mag0716.memorytraining;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import com.github.mag0716.memorytraining.repository.database.ApplicationDatabase;
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

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        // TODO: ログ出力を抑制する
        Timber.plant(new Timber.DebugTree());
    }

    @NonNull
    @Override
    public ApplicationDatabase getDatabase() {
        if (database == null) {
            database = Room.databaseBuilder(this, ApplicationDatabase.class, DB_NAME)
                    .addMigrations(ApplicationDatabase.MIGRATION_1_2)
                    .build();
        }
        return database;
    }
}
