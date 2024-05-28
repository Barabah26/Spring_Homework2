package com.example.spring_homework2.dao;

import java.util.List;

public interface Dao<T> {
    T save(T obj);
    boolean delete(T obj);
    void deleteAll();
    void saveAll();
    List<T> findAll();
    boolean deleteById(long id);
    T getOne(long id);
}
