package com.jex.take.data.service.util.result;

import com.jex.take.data.service.exception.ApiException;

public interface AsyncResult<T> {

    /**
     * Get exception, if the asynchronous invoking failure, you should use this method to get the
     * exception.
     *
     * @return The exception of the invoking.
     */
     ApiException getException();

    /**
     * Calling success or not.
     *
     * @return true for successful, false for failed.
     */
    boolean succeeded();

    /**
     * Get the data response from the server.
     *
     * @return Any type you incoming.
     */
    T getData();
}
