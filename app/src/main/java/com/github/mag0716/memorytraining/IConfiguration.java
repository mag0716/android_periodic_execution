package com.github.mag0716.memorytraining;

import android.arch.persistence.room.Database;
import android.support.annotation.NonNull;

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
    Database getDatabase();

}
