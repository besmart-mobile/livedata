package com.besmartmobile.livedata;

import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;

import lombok.NonNull;

public class NullSafeMediatorLiveData<T> extends NullSafeMutableLiveData<T> {

    private final MediatorLiveData<T> mediatorLiveData;

    public static <T> NullSafeMediatorLiveData<T> create() {
        MediatorLiveData<T> liveData = new MediatorLiveData<>();
        return new NullSafeMediatorLiveData<>(liveData);
    }

    public static <T> NullSafeMediatorLiveData<T> create(@NonNull T t) {

        MediatorLiveData<T> liveData = new MediatorLiveData<>();
        liveData.setValue(t);
        return new NullSafeMediatorLiveData<>(liveData);
    }

    protected NullSafeMediatorLiveData(@NonNull MediatorLiveData<T> mediatorLiveData) {
        super(mediatorLiveData);
        this.mediatorLiveData = mediatorLiveData;
    }

    @MainThread
    public <S> void addSource(NullSafeLiveData<S> source, NullSafeObserverAdapter<S> onChanged) {
        mediatorLiveData.addSource(source.getInnerLiveData(), onChanged);
    }

    @MainThread
    public <S> void removeSource(NullSafeLiveData<S> toRemove) {
        mediatorLiveData.removeSource(toRemove.getInnerLiveData());
    }
}
