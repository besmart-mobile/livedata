package com.besmartmobile.livedata;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

/**
 * CustomMutableLiveData notifies new observer immediately if it has data.
 * This feature can be useful for views restoring their state during activity/fragment recreation.
 */
public class CustomMutableLiveData<T> extends MutableLiveData<T> {

    @Override
    public void observe(LifecycleOwner owner, final Observer<T> observer) {
        final boolean hasValue = getValue() != null;
        if (hasValue) {
            observer.onChanged(getValue());
        }
        Observer<T> observer1 = new Observer<T>() {
            boolean isNotFirstChange = !hasValue;

            @Override
            public void onChanged(@Nullable T t) {
                if (!isNotFirstChange) {
                    isNotFirstChange = true;
                    return;
                }
                observer.onChanged(t);
            }
        };
        super.observe(owner, observer1);
    }

    public void observe(LifecycleOwner owner, final NullSafeObserver<T> observer) {
        Observer<T> observer1 = new Observer<T>() {
            @Override
            public void onChanged(@Nullable T t) {
                if (t == null) {
                    throw new IllegalArgumentException("onChanged called with null value");
                }
                observer.onChangedNonNull(t);
            }
        };
        this.observe(owner, observer1);
    }
}
