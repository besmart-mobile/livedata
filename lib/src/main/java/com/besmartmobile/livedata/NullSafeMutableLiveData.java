package com.besmartmobile.livedata;

import android.arch.lifecycle.MutableLiveData;

import lombok.NonNull;

import static com.besmartmobile.livedata.LiveDataNotificationStrategy.Immediate;
import static com.besmartmobile.livedata.LiveDataNotificationStrategy.PostOnQueue;


public class NullSafeMutableLiveData<T> extends NullSafeLiveData<T> {

    private final MutableLiveData<T> mutableLiveData;

    public static <T> NullSafeMutableLiveData<T> create() {
        return create(Immediate);
    }

    public static <T> NullSafeMutableLiveData<T> create(@NonNull LiveDataNotificationStrategy notificationStrategy) {
        MutableLiveData<T> liveData = createMutableLiveData(notificationStrategy);
        return new NullSafeMutableLiveData<>(liveData);
    }

    public static <T> NullSafeMutableLiveData<T> create(@NonNull T t) {
        return create(t, Immediate);
    }

    public static <T> NullSafeMutableLiveData<T> create(@NonNull T t,
                                                        @NonNull LiveDataNotificationStrategy notificationStrategy) {
        MutableLiveData<T> liveData = createMutableLiveData(notificationStrategy);
        liveData.setValue(t);
        return new NullSafeMutableLiveData<>(liveData);
    }

    @NonNull
    private static <T> MutableLiveData<T> createMutableLiveData(@NonNull LiveDataNotificationStrategy notificationStrategy) {
        if (notificationStrategy == PostOnQueue) {
            return new MutableLiveData<>();
        } else if (notificationStrategy == Immediate) {
            return new CustomMutableLiveData<>();
        } else {
            throw new IllegalArgumentException("Not supported notificationStrategy: " + notificationStrategy);
        }
    }

    protected NullSafeMutableLiveData(@NonNull MutableLiveData<T> mutableLiveData) {
        super(mutableLiveData);
        this.mutableLiveData = mutableLiveData;
    }

    public void postValue(@NonNull T value) {
        mutableLiveData.postValue(value);
    }

    public void setValue(T value) {
        mutableLiveData.setValue(value);
    }
}
