package com.maids.cc.backend.library.services;

import com.maids.cc.backend.library.entities.Base;
import com.maids.cc.backend.library.repositories.BaseRepository;
import jakarta.transaction.Transactional;
import org.hibernate.query.UnknownParameterException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class BaseServices<T extends Base> {

    @Autowired
    protected BaseRepository<T> baseRepository;

    public List<T> findAll() {
        return baseRepository.findAll();
    }

    public T findById(Long id) {
        Optional<T> entity = baseRepository.findById(id);
        if (entity.isEmpty())
            throw new RuntimeException("entity not found");
        return entity.get();
    }

    @Transactional
    public T create(T entity) {
        // To make sure new entity is created
        entity.setId(null);
        return baseRepository.save(entity);
    }

    @Transactional
    public T update(Long id, T entity) {
        T savedEntity = baseRepository.findById(id).orElse(null);

        if (savedEntity != null){
            entity.setId(id);
            return baseRepository.save(entity);
        }
        throw new UnknownParameterException("Entity not found.");
    }

    @Transactional
    public void delete(Long id){
        baseRepository.deleteById(id);
    }
}
