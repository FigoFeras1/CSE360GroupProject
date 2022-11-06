package com.sdpizza.groupproject.database;

import com.sdpizza.groupproject.entity.model.Model;

public interface Repository<T extends Model> {
    T get(long id);
    boolean add(T model);

    T update(T model);

    void remove(long id);
}
