package com.github.mag0716.memorytraining;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Database;
import android.support.annotation.NonNull;

import timber.log.Timber;

/**
 * アプリケーション情報
 * アプリケーションのライフサイクルと同じ生存期間のインスタンスを保持する
 * <p>
 * Created by mag0716 on 2017/04/30.
 */
@SuppressLint("Registered")
public class Application extends android.app.Application implements IConfiguration {

//    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
//    protected static OrmaDatabase orma;

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: ログ出力を抑制する
        Timber.plant(new Timber.DebugTree());
    }

    @NonNull
    @Override
    public Database getDatabase() {
        return null;
    }

//    @Override
//    public OrmaDatabase getOrma() {
//        if (orma == null) {
//            orma = OrmaDatabase.builder(this)
//                    .build();
//        }
//        return orma;
//    }
}
