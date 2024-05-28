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
    private List<Account> accounts;
    private final JdbcTemplate jdbcTemplate;

//    public AccountDao() {
//        this.accounts = new ArrayList<>();
////        initializeDefaultAccounts();
//    }

//    private void initializeDefaultAccounts() {
//        List<Customer> customers = DefaultCustomers.createCustomers();
//        for (Customer customer : customers) {
//            Account account = new Account(getCurrencyForCustomer(customer.getName()), customer);
//            save(account);
//        }
//    }

//    private static Currency getCurrencyForCustomer(String name) {
//        String[] names = DefaultCustomers.names;
//        for (int i = 0; i < names.length; i++) {
//            if (names[i].equals(name)) {
//                return Currency.values()[i];
//            }
//        }
//        throw new IllegalArgumentException("Unknown customer name: " + name);
//    }

    @Override
    public Account save(Account account) {
        String sql = "INSERT INTO ACCOUNTS(number, currency, balance, customer_id) VALUES(?,?,?,?)";
        jdbcTemplate.update(sql, account.getNumber(), account.getCurrency(), account.getBalance(), account.getCustomer().getId());

//        accounts.add(account);
//        return account;
        return account;
    }

    public boolean delete(Account account) {
        String sql = "DELETE FROM ACCOUNTS WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, account.getId());
        return rowsAffected > 0;
    }

    public void deleteAll() {
        String sql = "DELETE FROM ACCOUNTS";
        jdbcTemplate.update(sql);
    }

    @Override
    public void saveAll() {
        String sql = "INSERT INTO ACCOUNTS(number, currency, balance, customer_id) VALUES(?,?,?,?)";
        jdbcTemplate.batchUpdate(sql, accounts, accounts.size(), (ps, account) -> {
            ps.setString(1, account.getNumber());
            ps.setString(2, account.getCurrency().name());
            ps.setDouble(3, account.getBalance());
            ps.setLong(4, account.getCustomer().getId());
        });
    }

    @Override
    public List<Account> findAll() {
        return jdbcTemplate.query("SELECT * FROM ACCOUNTS", new BeanPropertyRowMapper<>());
    }

    @Override
    public boolean deleteById(long id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM accounts WHERE id = ?", id);
        return rowsAffected > 0;
    }


    @Override
    public Account getOne(long id) {
        for (Account account: accounts){
            if (account.getId() == id){
                return account;
            }
        }
        return null;
    }

    public Account findByNumber(String number) {
        for (Account account: accounts){
            if (account.getNumber().equals(number)){
                return account;
            }
        }
        return null;
    }

    public void update(Account account) {
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            if (acc.getId().equals(account.getId())) {
                accounts.set(i, account);
                return;
            }
        }
        throw new RuntimeException("Customer not found with id: " + account.getId());
    }

}
