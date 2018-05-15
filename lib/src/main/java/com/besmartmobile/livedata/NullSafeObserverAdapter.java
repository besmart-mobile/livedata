package com.besmartmobile.livedata;

import android.arch.lifecycle.Observer;

import lombok.NonNull;

public abstract class NullSafeObserverAdapter<T> implements Observer<T>, NullSafeObserver<T> {

    @Override
    public final void onChanged(T t) {
        onChangedNonNull(t);
    }

    @Override
    public abstract void onChangedNonNull(@NonNull T t);
}