package com.jex.take.data.service.util;

@FunctionalInterface
public interface SubscriptionListener<T> {

    /**
     * onReceive will be called when get the data sent by server.
     *
     * @param data The data send by server.
     */
    void onReceive(T data);
}