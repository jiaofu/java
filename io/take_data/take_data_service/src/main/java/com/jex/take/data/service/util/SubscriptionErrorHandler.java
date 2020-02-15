package com.jex.take.data.service.util;

import com.jex.take.data.service.exception.ApiException;

@FunctionalInterface
public interface SubscriptionErrorHandler {

    void onError(ApiException exception);
}
