package com.github.mag0716.memorytraining;

import android.content.Context;
import android.support.annotation.NonNull;

import com.facebook.stetho.Stetho;
import com.github.gfx.android.orma.Inserter;
import com.github.mag0716.memorytraining.model.Memory;
import com.github.mag0716.memorytraining.model.OrmaDatabase;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.util.List;

import io.reactivex.CompletableSource;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okio.BufferedSource;
import okio.Okio;
import timber.log.Timber;

/**
 * Created by mag0716 on 2017/04/30.
 */
public class DebugApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        initializeOrmaDebug(this);
    }

    private void initializeOrmaDebug(@NonNull Context context) {
        if(orma != null) {
            throw new IllegalStateException("OrmaDatabase initialized already.");
        }
        orma = OrmaDatabase.builder(context)
                .name(null) // In-Memory
                .build();

        // insert data
        orma.prepareInsertIntoMemoryAsSingle()
                .subscribeOn(Schedulers.io())
                .flatMapObservable((Function<Inserter<Memory>, ObservableSource<?>>) memoryInserter
                        -> memoryInserter.executeAllAsObservable(loadTestData()))
                .subscribe(id -> {},
                        Timber::e,
                        () -> Timber.d("completed to insert test data."));
    }

    private List<Memory> loadTestData() {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<List<Memory>> adapter = moshi.adapter(Types.newParameterizedType(List.class, Memory.class));
        try {
            try (BufferedSource source = Okio.buffer(Okio.source(getAssets().open("test_data.json")))) {
                return adapter.fromJson(source);

                // TODO: add next_training_datetime
            }
        } catch (IOException e) {
            throw new IllegalStateException("test_data.json is invalid.");
        }
    }
}