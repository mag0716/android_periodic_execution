package com.github.mag0716.memorytraining;

import com.github.mag0716.memorytraining.model.OrmaDatabase;

import io.reactivex.annotations.NonNull;

/**
 * Created by mag0716 on 2017/05/21.
 */
public interface IConfiguration {

    /**
     * OrmaDatabase を返却する
     * @return OrmaDatabase
     */
    @NonNull
    OrmaDatabase getOrma();

}
