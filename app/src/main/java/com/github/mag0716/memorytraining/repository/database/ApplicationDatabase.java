package com.github.mag0716.memorytraining.repository.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.github.mag0716.memorytraining.model.Memory;

/**
 * Database 定義
 * <p>
 * Created by mag0716 on 2017/06/03.
 */
@Database(entities = {Memory.class}, version = 1)
public abstract class ApplicationDatabase extends RoomDatabase {
}
