package com.example.spring_homework2.dao;

import com.example.spring_homework2.domain.Employer;

import java.util.List;

public class EmployerDao implements Dao<Employer> {
    @Override
    public Employer save(Employer obj) {
        return null;
    }

    @Override
    public boolean delete(Employer obj) {
        return false;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void saveAll(Employer employer) {

    }

    @Override
    public List<Employer> findAll() {
        return null;
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }

    @Override
    public Employer getOne(long id) {
        return null;
    }
}
