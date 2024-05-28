package com.example.spring_homework2.dao;

import com.example.spring_homework2.domain.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomerDao implements Dao<Customer> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Customer save(Customer customer) {
        String sql = "INSERT INTO customers(name, email, age) VALUES(?,?,?)";
        jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getAge());
        return customer;
    }

    @Override
    public boolean delete(Customer customer) {
        String sql = "DELETE FROM customers WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, customer.getId());
        return rowsAffected > 0;
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM customers";
        jdbcTemplate.update(sql);
    }

    @Override
    public void saveAll(Customer customer) {
        String sql = "INSERT INTO customers(name, email, age) VALUES(?,?,?)";
        jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getAge());
    }


    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customers", new BeanPropertyRowMapper<>(Customer.class));
    }

    @Override
    public boolean deleteById(long id) {
        String sql = "DELETE FROM customers WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public Customer getOne(long id) {
        String sql = "SELECT * FROM customers WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Customer.class), id);
    }


    public Customer update(Customer updatedCustomer) {
        String sql = "UPDATE customers SET name = ?, email = ?, age = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, updatedCustomer.getName(), updatedCustomer.getEmail(), updatedCustomer.getAge(), updatedCustomer.getId());

        if (rowsAffected == 0) {
            throw new RuntimeException("Customer not found with id: " + updatedCustomer.getId());
        }
        return updatedCustomer;
    }

}
