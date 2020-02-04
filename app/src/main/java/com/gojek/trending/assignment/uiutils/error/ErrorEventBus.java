package com.gojek.trending.assignment.uiutils.error;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class ErrorEventBus {
    public static final int ACTION_RETRY_CLICKED = 1;

    private static ErrorEventBus mInstance;

    public static ErrorEventBus getInstance() {
        if (mInstance == null) {
            mInstance = new ErrorEventBus();
        }
        return mInstance;
    }

    private ErrorEventBus() {}

    private PublishSubject<Integer> fragmentEventSubject = PublishSubject.create();

    public Observable<Integer> getErrorEventObservable() {
        return fragmentEventSubject;
    }

    public void postFragmentAction(Integer actionId) {
        fragmentEventSubject.onNext(actionId);
    }
}