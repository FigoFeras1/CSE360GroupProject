package com.sdpizza.groupproject.database;

import com.sdpizza.groupproject.model.Model;
import com.sdpizza.groupproject.model.User;

public interface Repository<T extends Model> {
    T get(long id);
    void add(T model);

    T update(T model);

    void remove(T model);
}
