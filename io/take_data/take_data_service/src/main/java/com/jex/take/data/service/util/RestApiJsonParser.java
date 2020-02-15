package com.jex.take.data.service.util;

@FunctionalInterface
public interface RestApiJsonParser<T> {
    T parseJson(JsonWrapper json);
}
