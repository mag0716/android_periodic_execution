package com.github.mag0716.memorytraining;

public class TestApplication extends Application {

    @Override
    protected void setUpTracker() {
        // テスト実行時には不要なのでトラッキングは無効化する
    }

    @Override
    protected void setUpLeakCanary() {
        // テスト実行時は不要なのでLeakCanaryは初期化しない
    }
}
