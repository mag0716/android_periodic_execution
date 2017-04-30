package com.github.mag0716.memorytraining;

import timber.log.Timber;

/**
 * Created by mag0716 on 2017/04/30.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO
        Timber.plant(new Timber.DebugTree());
    }
}
