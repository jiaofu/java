package com.jex.take.data.service.util;

import okhttp3.Request;

public class RestApiRequest<T> {
    public Request request;
    public RestApiJsonParser<T> jsonParser;
}
