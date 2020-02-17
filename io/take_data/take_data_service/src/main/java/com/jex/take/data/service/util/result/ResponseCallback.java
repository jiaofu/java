package com.jex.take.data.service.util.result;

@java.lang.FunctionalInterface
public interface ResponseCallback<T> {

    /**
     * Be called when the request successful.
     *
     * @param response The {@link AsyncResult} of the asynchronous invoking.
     */
    void onResponse(T response);
}
