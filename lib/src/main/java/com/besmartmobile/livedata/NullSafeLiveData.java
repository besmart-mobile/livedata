package com.besmartmobile.livedata;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;

import lombok.NonNull;

public class NullSafeLiveData<T> {

    private final LiveData<T> liveData;

    public NullSafeLiveData(@NonNull LiveData<T> liveData) {
        this.liveData = liveData;
    }

    public Observer<T> setObserver(LifecycleOwner owner, final NullSafeObserver<T> observer) {
        liveData.removeObservers(owner);
        return observe(owner, observer);
    }

    private Observer<T> observe(LifecycleOwner owner, final NullSafeObserver<T> observer) {
        NullSafeObserverAdapter<T> nullSafeObserverAdapter = getNullSafeObserverAdapter(observer);
        liveData.observe(owner, nullSafeObserverAdapter);
        return nullSafeObserverAdapter;
    }

    public Observer<T> observeForever(NullSafeObserver<T> observer) {
        NullSafeObserverAdapter<T> nullSafeObserverAdapter = getNullSafeObserverAdapter(observer);
        liveData.observeForever(nullSafeObserverAdapter);
        return nullSafeObserverAdapter;
    }

    public void removeObserver(final Observer<T> observer) {
        liveData.removeObserver(observer);
    }

    public void removeObservers(final LifecycleOwner owner) {
        liveData.removeObservers(owner);
    }

    public T getValue() {
        return liveData.getValue();
    }

    public boolean hasObservers() {
        return liveData.hasObservers();
    }

    public boolean hasActiveObservers() {
        return liveData.hasActiveObservers();
    }

    public LiveData<T> getInnerLiveData() {
        return liveData;
    }

    public boolean hasValue() {
        return getValue() != null;
    }

    @NonNull
    private NullSafeObserverAdapter<T> getNullSafeObserverAdapter(final NullSafeObserver<T> observer) {
        return new NullSafeObserverAdapter<T>() {
            @Override
            public void onChangedNonNull(@NonNull T t) {
                observer.onChangedNonNull(t);
            }
        };
    }
}
