package com.github.mag0716.memorytraining.event;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by mag0716 on 2017/09/17.
 */
public class EventBus {

    private PublishSubject<IEvent> bus = PublishSubject.create();

    public Observable<IEvent> toObservable() {
        return bus;
    }

    public void send(@NonNull IEvent event) {
        bus.onNext(event);
    }
}
