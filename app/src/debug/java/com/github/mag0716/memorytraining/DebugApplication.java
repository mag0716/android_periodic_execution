package com.github.mag0716.memorytraining;

import com.facebook.stetho.Stetho;

/**
 * Created by mag0716 on 2017/04/30.
 */
public class DebugApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
