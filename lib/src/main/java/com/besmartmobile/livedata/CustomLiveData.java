package com.besmartmobile.livedata;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

/**
 * CustomLiveData notifies new observer immediately if it has data.
 * This feature can be useful for views restoring their state during activity/fragment recreation.
 */
public class CustomLiveData<T> extends LiveData<T> {

    @Override
    public void observe(LifecycleOwner owner, final Observer<T> observer) {
        Observer<T> observer1 = new Observer<T>() {
            boolean isNotFirstChange;

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
        if (getValue() != null) {
            observer1.onChanged(getValue());
        }
    }
}
