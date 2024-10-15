package com.maids.cc.backend.library.controllers;

import com.maids.cc.backend.library.entities.Base;
import com.maids.cc.backend.library.services.BaseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public abstract class BaseController<T extends Base, S extends BaseServices<T>> {

    @Autowired
    protected S services;

    @GetMapping
    public List<T> findAll() {
        return services.findAll();
    }

    @GetMapping("/{id}")
    public T findById(
            @PathVariable("id")Long id
    ) {
        return services.findById(id);
    }

    @PostMapping
    public T create(
            @RequestBody T entity
    ) {
        return services.create(entity);
    }

    @PutMapping("/{id}")
    public T update(@PathVariable("id") Long id, @RequestBody T entity) {
        return services.update(id, entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        services.delete(id);
    }
}
