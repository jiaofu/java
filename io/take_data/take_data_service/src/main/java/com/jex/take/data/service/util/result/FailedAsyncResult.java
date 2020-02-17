package com.jex.take.data.service.util.result;

import com.jex.take.data.service.exception.ApiException;

public class FailedAsyncResult<T> implements AsyncResult<T> {

    private final ApiException exception;

    public FailedAsyncResult(ApiException exception) {
        this.exception = exception;
    }

    @Override
    public ApiException getException() {
        return exception;
    }

    @Override
    public boolean succeeded() {
        return false;
    }

    @Override
    public T getData() {
        return null;
    }
}
