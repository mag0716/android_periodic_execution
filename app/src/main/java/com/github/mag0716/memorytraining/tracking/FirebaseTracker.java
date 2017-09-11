package com.github.mag0716.memorytraining.tracking;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.github.mag0716.memorytraining.model.tracking.NotifyEvent;
import com.github.mag0716.memorytraining.model.tracking.UserProperty;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by mag0716 on 2017/09/01.
 */

public class FirebaseTracker implements ITracker {

    private static final String NOTIFY_EVENT_NAME = "notify";

    // region trackUserInformation param

    /**
     * ユーザーのレベル
     */
    private static final String USER_PROPERTY_LEVEL = "level";

    // endregion

    // region trackNotifyEvent param
    /**
     * 定期実行 API
     */
    private static final String NOTIFY_EVENT_PARAM_TYPE = "type";
    /**
     * 定期実行精度
     */
    private static final String NOTIFY_EVENT_PARAM_ACCURACY = "accuracy";
    /**
     * 充電中かどうか
     */
    private static final String NOTIFY_EVENT_PARAM_CHARGE = "charge";
    /**
     * ネットワーク接続中かどうか
     */
    private static final String NOTIFY_EVENT_PARAM_NETWORK = "network";
    /**
     * スクリーンが点灯しているかどうか
     */
    private static final String NOTIFY_EVENT_PARAM_SCREEN = "screen";
    /**
     * Doze 中かどうか
     */
    private static final String NOTIFY_EVENT_PARAM_DOZE_MODE = "doze";

    // endregion

    private final FirebaseAnalytics firebaseAnalytics;

    public FirebaseTracker(@NonNull Context context) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    @Override
    public void trackUserInformation(@NonNull UserProperty userProperty) {
        firebaseAnalytics.setUserProperty(USER_PROPERTY_LEVEL, userProperty.getLevel());
    }

    @Override
    public void trackNotifyEvent(@NonNull NotifyEvent notifyEvent) {
        final Bundle bundle = new Bundle();
        bundle.putString(NOTIFY_EVENT_PARAM_TYPE, notifyEvent.getType());
        bundle.putLong(NOTIFY_EVENT_PARAM_ACCURACY, notifyEvent.getAccuracy());
        bundle.putString(NOTIFY_EVENT_PARAM_CHARGE, notifyEvent.getCharge().name());
        bundle.putString(NOTIFY_EVENT_PARAM_NETWORK, notifyEvent.getNetwork().name());
        bundle.putString(NOTIFY_EVENT_PARAM_SCREEN, notifyEvent.getDisplay().name());
        bundle.putString(NOTIFY_EVENT_PARAM_DOZE_MODE, notifyEvent.getDoze().name());
        firebaseAnalytics.logEvent(NOTIFY_EVENT_NAME, bundle);
    }

}
