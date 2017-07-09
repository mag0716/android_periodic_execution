package com.github.mag0716.memorytraining;

import android.content.Context;
import android.support.annotation.NonNull;

import com.annimon.stream.Stream;
import com.facebook.stetho.Stetho;
import com.github.mag0716.memorytraining.model.Memory;
import com.github.mag0716.memorytraining.repository.database.MemoryDao;
import com.github.mag0716.memorytraining.service.TaskConductor;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import okio.BufferedSource;
import okio.Okio;
import timber.log.Timber;

/**
 * デバッグ用のアプリケーション情報
 * <p>
 * Stetho の有効化
 * デバッグ用の DB 設定
 * <p>
 * Created by mag0716 on 2017/04/30.
 */
public class DebugApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        initializeDatabaseForDebug(this);
        TaskConductor.registerTaskIfNeeded(this, database.memoryDao());
    }

    private void initializeDatabaseForDebug(@NonNull Context context) {
        if (database != null) {
            throw new IllegalStateException("Database initialized already.");
        }

        database = getDatabase();

        // TODO: repository 経由でのアクセスに変更し、DB アクセスを隠蔽する
        // insert debug data
        Completable.create(
                emitter -> {
                    final MemoryDao memoryDao = database.memoryDao();
                    List<Memory> memoryList = memoryDao.loadAll();
                    if (memoryList.isEmpty()) {
                        memoryDao.insertAll(loadTestData());
                    }
                    emitter.onComplete();
                })
                .subscribeOn(Schedulers.io())
                .subscribe(() -> Timber.d("completed to insert debug data."),
                        throwable -> Timber.w("failed to insert debug data.", throwable));
    }

    private List<Memory> loadTestData() {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<List<Memory>> adapter = moshi.adapter(Types.newParameterizedType(List.class, Memory.class));
        try {
            try (BufferedSource source = Okio.buffer(Okio.source(getAssets().open("test_data.json")))) {
                final long trainingDatetime = System.currentTimeMillis();
                List<Memory> testData = adapter.fromJson(source);
                if (testData == null) {
                    throw new IllegalStateException("test_data.json is empty.");
                }
                return Stream.of(testData)
                        .map(memory -> {
                            memory.setNextTrainingDatetime(trainingDatetime);
                            return memory;
                        })
                        .toList();
            }
        } catch (IOException e) {
            throw new IllegalStateException("test_data.json is invalid.");
        }
    }
}
