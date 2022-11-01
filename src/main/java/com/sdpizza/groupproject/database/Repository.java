package com.sdpizza.groupproject.database;

import com.sdpizza.groupproject.entity.model.Model;

public interface Repository<T extends Model> {
    T get(long id);
    void add(T model);

    T update(T model);

    void remove(T model);
}
