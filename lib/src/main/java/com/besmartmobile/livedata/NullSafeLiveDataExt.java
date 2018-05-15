package com.besmartmobile.livedata;

import android.support.annotation.Nullable;

import com.besmartmobile.livedata.functions.BiFunction;

import lombok.NonNull;

// Noninstantiable utility class
public final class NullSafeLiveDataExt {

    // Suppress default constructor for noninstantiability
    private NullSafeLiveDataExt() {
        throw new AssertionError();
    }

    public static NullSafeLiveData<Boolean> any(@NonNull final NullSafeLiveData<Boolean> arg0,
                                                @NonNull final NullSafeLiveData<Boolean> arg1) {
        return combine(arg0, arg1, new BiFunction<Boolean, Boolean, Boolean>() {
            @Override
            public Boolean apply(Boolean value1, Boolean value2) {
                return value1 || value2;
            }
        });
    }

    public static <T, U, R> NullSafeLiveData<R> combine(@NonNull final NullSafeLiveData<T> arg0,
                                                        @NonNull final NullSafeLiveData<U> arg1,
                                                        @NonNull final BiFunction<T, U, R> combiningFunction) {
        final NullSafeMediatorLiveData<R> result = NullSafeMediatorLiveData.create();
        if (arg0.getValue() != null && arg1.getValue() != null) {
            R combiningFunctionResult = combiningFunction.apply(arg0.getValue(), arg1.getValue());
            result.setValue(combiningFunctionResult);
        }
        result.addSource(arg0, new NullSafeObserverAdapter<T>() {
            @Override
            public void onChangedNonNull(@NonNull T t) {
                result.setValue(combiningFunction.apply(arg0.getValue(), arg1.getValue()));
            }
        });
        result.addSource(arg1, new NullSafeObserverAdapter<U>() {
            @Override
            public void onChangedNonNull(@Nullable U u) {
                result.setValue(combiningFunction.apply(arg0.getValue(), arg1.getValue()));
            }
        });
        return result;
    }
}
