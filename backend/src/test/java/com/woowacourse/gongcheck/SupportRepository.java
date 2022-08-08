package com.woowacourse.gongcheck;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
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
        entityManager.flush();
        entityManager.clear();
        return entities;
    }

    public <T> Optional<T> findById(final Class<T> entityClass, final Object id) {
        entityManager.clear();
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    public <T> T getById(final Class<T> entityClass, final Object id) {
        entityManager.clear();
        return Optional.ofNullable(entityManager.find(entityClass, id))
                .orElseThrow(EntityNotFoundExcpetion::new);
    }

    static class EntityNotFoundExcpetion extends RuntimeException {

        public EntityNotFoundExcpetion() {
            super("Entity를 찾을 수 없습니다.");
        }
    }
}
