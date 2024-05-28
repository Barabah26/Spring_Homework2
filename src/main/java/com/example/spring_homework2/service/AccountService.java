package com.example.spring_homework2.service;

import com.example.spring_homework2.domain.Account;

import java.util.List;

public interface AccountService {
    void save(Account account);
    boolean delete(Account account);
    void deleteAll();
    void saveAll(Account account);
    List<Account> findAll();
    boolean deleteById(long id);
    Account getOne(long id);
    Account findByNumber(String number);
    Account depositToAccount(String accountNumber, double amount);
    boolean withdrawFromAccount(String accountNumber, double amount);
    void transferMoney(String fromAccountNumber, String toAccountNumber, double amount);
}
