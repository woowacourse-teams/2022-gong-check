package com.woowacourse.gongcheck;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SupportRepository {

    @Autowired
    private EntityManager entityManager;

    public <T> T save(final T entity) {
        entityManager.persist(entity);
        entityManager.flush();
        entityManager.clear();
        return entity;
    }

    public <T> List<T> saveAll(final List<T> entities) {
        for (T entity : entities) {
            save(entity);
        }
        return entities;
    }
}
