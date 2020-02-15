package com.jex.take.data.service.util;


@FunctionalInterface
public interface Handler<T> {

    void handle(T t);
}
