package com.github.mag0716.memorytraining.tracking;

import android.support.annotation.NonNull;

import com.github.mag0716.memorytraining.model.tracking.NotifyEvent;
import com.github.mag0716.memorytraining.model.tracking.UserProperty;

/**
 * Created by mag0716 on 2017/08/30.
 */
public interface ITracker {

    /**
     * ユーザー情報のトラッキング
     *
     * @param userProperty ユーザ情報
     */
    void trackUserInformation(@NonNull UserProperty userProperty);

    /**
     * 通知イベントのトラッキング
     *
     * @param notifyEvent 通知イベント
     */
    void trackNotifyEvent(@NonNull NotifyEvent notifyEvent);
}
