package com.api.cafe.services;

public interface IActions2<T> {
    CafeResponse create(T t);

    CafeResponse deleteUser(String s);

    CafeResponse deleteAll(String s);
}
