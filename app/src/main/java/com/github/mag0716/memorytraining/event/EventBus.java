package com.github.mag0716.memorytraining.event;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by mag0716 on 2017/09/17.
 */
public class EventBus<E extends IEvent> {

    private PublishSubject<E> bus = PublishSubject.create();

    public Observable<E> toObservable() {
        return bus;
    }

    public void send(@NonNull E event) {
        bus.onNext(event);
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }
}
