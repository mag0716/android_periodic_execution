package com.github.mag0716.memorytraining.tracking;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.github.mag0716.memorytraining.model.tracking.NotifyEvent;
import com.github.mag0716.memorytraining.model.tracking.UserProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mag0716 on 2017/08/30.
 */
public class TrackerConductor {

    private final List<ITracker> trackerList = new ArrayList<>();

    public void addTracker(@NonNull ITracker tracker) {
        trackerList.add(tracker);
    }

    public void clear() {
        trackerList.clear();
    }

    public void trackUserInformation(@IntRange(from = 0) long trainingCount) {
        final UserProperty userProperty = new UserProperty(trainingCount);
        for (ITracker tracker : trackerList) {
            tracker.trackUserInformation(userProperty);
        }
    }

    public void trackNotifyEvent(@NonNull Context context, @NonNull String type, long notifyDatetime) {
        final NotifyEvent notifyEvent = new NotifyEvent(context, type, notifyDatetime);
        for (ITracker tracker : trackerList) {
            tracker.trackNotifyEvent(notifyEvent);
        }
    }
}
