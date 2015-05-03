package com.geoffreymoller.vision.days.service.repository;

import com.geoffreymoller.vision.days.service.domain.EntityWithLongId;

public interface CrudDao<T extends EntityWithLongId> {
    T get(Long id);

    int count();

    boolean exists(Long id);

    T save(T instance);

    void delete(Long id);
}
