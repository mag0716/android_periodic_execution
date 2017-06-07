package com.github.mag0716.memorytraining;

import android.support.annotation.NonNull;

import com.github.mag0716.memorytraining.repository.database.ApplicationDatabase;

/**
 * アプリケーションの設定情報インタフェース
 * <p>
 * Created by mag0716 on 2017/05/21.
 */
public interface IConfiguration {

    /**
     * Database を返却する
     *
     * @return Database
     */
    @NonNull
    ApplicationDatabase getDatabase();

}
