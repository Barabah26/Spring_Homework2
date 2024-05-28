package com.example.spring_homework2.dao;

import com.example.spring_homework2.domain.Account;
import com.example.spring_homework2.domain.Currency;
import com.example.spring_homework2.domain.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccountDao implements Dao<Account> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Account save(Account account) {
        String sql = "INSERT INTO accounts(number, currency, balance, customer_id) VALUES(?,?,?,?)";
        jdbcTemplate.update(sql, account.getNumber(), account.getCurrency(), account.getBalance(), account.getCustomer().getId());
        return account;
    }

    public boolean delete(Account account) {
        String sql = "DELETE FROM accounts WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, account.getId());
        return rowsAffected > 0;
    }

    public void deleteAll() {
        String sql = "DELETE FROM accounts";
        jdbcTemplate.update(sql);
    }

    @Override
    public void saveAll(Account account) {
        String sql = "INSERT INTO accounts(number, currency, balance, customer_id) VALUES(?,?,?,?)";
        jdbcTemplate.update(sql, account.getNumber(), account.getCurrency().toString(), account.getBalance(), account.getCustomer().getId());
    }

    @Override
    public List<Account> findAll() {
        return jdbcTemplate.query("SELECT * FROM ACCOUNTS", new BeanPropertyRowMapper<>(Account.class));
    }

    @Override
    public boolean deleteById(long id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM accounts WHERE id = ?", id);
        return rowsAffected > 0;
    }


    @Override
    public Account getOne(long id) {
        String sql = "SELECT * FROM accounts WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Account.class), id);
    }

    public Account findByNumber(String number) {
        String sql = "SELECT * FROM accounts WHERE number = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Account.class), number);
    }

    public void update(Account account) {
        String sql = "UPDATE accounts SET number = ?, currency = ?, balance = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, account.getNumber(), account.getCurrency().toString(), account.getBalance(), account.getId());

        if (rowsAffected == 0) {
            throw new RuntimeException("Customer not found with id: " + account.getId());
        }
    }

}
