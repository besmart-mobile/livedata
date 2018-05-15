package com.besmartmobile.livedata.functions;

@FunctionalInterface
public interface BiFunction<T, U, R> {
    R apply(T value1, U value2);
}
