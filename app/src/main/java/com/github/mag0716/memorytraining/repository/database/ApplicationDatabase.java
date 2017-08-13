package com.github.mag0716.memorytraining.repository.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;

import com.github.mag0716.memorytraining.model.Memory;

/**
 * Database 定義
 * <p>
 * Created by mag0716 on 2017/06/03.
 */
@Database(entities = {Memory.class}, version = 2)
public abstract class ApplicationDatabase extends RoomDatabase {

    /**
     * memory.db に total_count カラムを追加
     */
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Memory ADD COLUMN total_count INTEGER DEFAULT 0");
        }
    };

    /**
     * Memory テーブルアクセス用の DAO 返却
     *
     * @return MemoryDao
     */
    public abstract MemoryDao memoryDao();
}
