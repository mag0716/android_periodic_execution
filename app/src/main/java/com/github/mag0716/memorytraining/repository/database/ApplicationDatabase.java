package com.github.mag0716.memorytraining.repository.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

import com.github.mag0716.memorytraining.model.Memory;

/**
 * Database 定義
 * <p>
 * Created by mag0716 on 2017/06/03.
 */
@Database(entities = {Memory.class}, version = 3)
public abstract class ApplicationDatabase extends RoomDatabase {

    /**
     * memory.db に total_count カラムを追加
     */
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Memory ADD COLUMN total_count INTEGER NOT NULL DEFAULT 0");
        }
    };

    /**
     * プライマリキーに @NonNull 指定が必須になった
     */
    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // noop
        }
    };

    /**
     * Memory テーブルアクセス用の DAO 返却
     *
     * @return MemoryDao
     */
    public abstract MemoryDao memoryDao();
}
