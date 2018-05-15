package com.besmartmobile.livedata;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.besmartmobile.livedata.functions.BiFunction;

import lombok.NonNull;

// Noninstantiable utility class
public final class LiveDataExt {

    // Suppress default constructor for noninstantiability
    private LiveDataExt() {
        throw new AssertionError();
    }

    public static LiveData<Boolean> or(@NonNull final LiveData<Boolean> arg0,
                                       @NonNull final LiveData<Boolean> arg1) {


        return combine(arg0, arg1, new BiFunction<Boolean, Boolean, Boolean>() {
            @Override
            public Boolean apply(Boolean value1, Boolean value2) {
                return value1 || value2;
            }
        });
    }

    public static <T, U, R> LiveData<R> combine(@NonNull final LiveData<T> arg0,
                                                @NonNull final LiveData<U> arg1,
                                                @NonNull final BiFunction<T, U, R> combiningFunction) {


        final MediatorLiveData<R> result = new MediatorLiveData<>();
        result.addSource(arg0, new Observer<T>() {
            @Override
            public void onChanged(@Nullable T t) {
                result.setValue(combiningFunction.apply(arg0.getValue(), arg1.getValue()));
            }
        });
        result.addSource(arg1, new Observer<U>() {
            @Override
            public void onChanged(@Nullable U u) {
                result.setValue(combiningFunction.apply(arg0.getValue(), arg1.getValue()));
            }
        });
        return result;
    }
}
