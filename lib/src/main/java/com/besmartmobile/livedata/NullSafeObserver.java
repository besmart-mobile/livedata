package com.besmartmobile.livedata;

import lombok.NonNull;

public interface NullSafeObserver<T> {
    void onChangedNonNull(@NonNull T t);
}
