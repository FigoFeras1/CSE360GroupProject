package com.sdpizza.groupproject.database.repository;

import com.sdpizza.groupproject.entity.model.Model;

/* I hate you */
public interface IRepository<T extends Model> {
    T get(long id);
    boolean add(T model);

    T update(T model);

    boolean remove(long id);
}
