package com.github.mag0716.memorytraining.view;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * View インタフェース
 * 単体テストを容易にするため、本インタフェースを継承した各画面の View インタフェースを実装すること
 * <p>
 * Created by mag0716 on 2017/04/27.
 */
public interface IView {

    /**
     * Context の取得
     *
     * @return Context
     */
    @NonNull
    Context getContext();
}
