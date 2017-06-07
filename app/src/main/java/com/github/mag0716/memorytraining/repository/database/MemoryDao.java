package com.github.mag0716.memorytraining.repository.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

import com.github.mag0716.memorytraining.model.Memory;

import java.util.List;

/**
 * Memory テーブルアクセス用インタフェース定義
 * <p>
 * Created by mag0716 on 2017/06/03.
 */
@Dao
public interface MemoryDao {

    /**
     * 全ての Memory を取得
     *
     * @return List<Memory>
     */
    @Query("SELECT * from Memory")
    List<Memory> loadAll();

    /**
     * 追加
     *
     * @param memoryList 追加対象の Memory 一覧
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(@NonNull List<Memory> memoryList);

    /**
     * 追加
     *
     * @param memory 追加対象の Memory
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(@NonNull Memory memory);

    /**
     * 更新
     *
     * @param memory 更新対象の Memory
     */
    @Update
    void update(@NonNull Memory memory);

    /**
     * 削除
     *
     * @param memory 削除対象の Memory
     */
    @Delete
    void delete(@NonNull Memory memory);
}
