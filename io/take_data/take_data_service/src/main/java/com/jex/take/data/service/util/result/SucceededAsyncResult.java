package com.jex.take.data.service.util.result;

import com.jex.take.data.service.exception.ApiException;

public class SucceededAsyncResult<T> implements AsyncResult<T> {

    private final T data;

    public SucceededAsyncResult(T data) {
        this.data = data;
    }

    @Override
    public ApiException getException() {
        return null;
    }

    @Override
    public boolean succeeded() {
        return true;
    }

    @Override
    public T getData() {
        return data;
    }
}
