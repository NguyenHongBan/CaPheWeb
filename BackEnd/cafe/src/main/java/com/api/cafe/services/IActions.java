package com.api.cafe.services;

import java.util.UUID;

public interface IActions<T> {
    CafeResponse getId(UUID id);

    CafeResponse search(String s);

    CafeResponse create(T t);

    CafeResponse update(UUID id, T t);

    CafeResponse delete(UUID id);
}
