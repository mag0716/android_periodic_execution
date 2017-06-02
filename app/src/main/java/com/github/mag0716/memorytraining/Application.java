package com.github.mag0716.memorytraining;

import android.support.annotation.RestrictTo;

import com.github.mag0716.memorytraining.model.OrmaDatabase;

import timber.log.Timber;

/**
 * Created by mag0716 on 2017/04/30.
 */
public class Application extends android.app.Application implements IConfiguration {

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    protected static OrmaDatabase orma;

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: ログ出力を抑制する
        Timber.plant(new Timber.DebugTree());
    }

    @Override
    public OrmaDatabase getOrma() {
        if(orma == null) {
            orma = OrmaDatabase.builder(this)
                    .build();
        }
        return orma;
    }
}
