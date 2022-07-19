package com.mainlab.common;

@FunctionalInterface
public interface OperationUnit {
    void operate();

    default int getLockId() {
        return 0;
    }

    default int getOrder() {
        return 0;
    }

    default boolean isCallbackPossible() {
        return false;
    }

    default void callback() {
    }
}
