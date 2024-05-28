package com.example.spring_homework2.service;

import com.example.spring_homework2.dao.AccountDao;
import com.example.spring_homework2.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultAccountService implements AccountService {
    private final AccountDao accountDao;

    @Autowired
    public DefaultAccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void save(Account account) {
        accountDao.save(account);
    }

    @Override
    public boolean delete(Account account) {
        return accountDao.delete(account);
    }

    @Override
    public void deleteAll(List<Account> accounts) {
        accountDao.deleteAll(accounts);
    }

    @Override
    public void saveAll(List<Account> accounts) {
        accountDao.saveAll(accounts);
    }

    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    @Override
    public boolean deleteById(long id) {
        return accountDao.deleteById(id);
    }

    @Override
    public Account getOne(long id) {
        return accountDao.getOne(id);
    }

    @Override
    public Account findByNumber(String number){
        return accountDao.findByNumber(number);
    }

    @Override
    public Account depositToAccount(String accountNumber, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero");
        }

        Account account = accountDao.findByNumber(accountNumber);
        if (account != null) {
            Double balance = account.getBalance();
            account.setBalance(balance + amount);
            return account;
        } else {
            throw new IllegalArgumentException("Account with number " + accountNumber + " not found");
        }
    }

    @Override
    public boolean withdrawFromAccount(String accountNumber, double amount) {
        if (amount <= 0){
            throw new IllegalArgumentException("Incorrect amount" + amount);
        }
        Account account = accountDao.findByNumber(accountNumber);
        if (account != null) {
            double balance = account.getBalance();
            if (balance >= amount) {
                account.setBalance(balance - amount);
                accountDao.update(account);
                return true;
            } else {
                throw new RuntimeException("Insufficient funds in account with number: " + accountNumber);
            }
        } else {
            throw new RuntimeException("Account not found with number: " + accountNumber);
        }
    }

    @Override
    public void transferMoney(String fromAccountNumber, String toAccountNumber, double amount) {
        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new IllegalArgumentException("From and To account numbers cannot be the same");
        }

        if (amount <= 0){
            throw new IllegalArgumentException("Incorrect amount" + amount);
        }

        Account fromAccount = accountDao.findByNumber(fromAccountNumber);
        Account toAccount = accountDao.findByNumber(toAccountNumber);
        if (fromAccount != null && toAccount != null) {
            double balanceFrom = fromAccount.getBalance();
            if (balanceFrom >= amount) {
                double balanceTo = toAccount.getBalance();
                fromAccount.setBalance(balanceFrom - amount);
                toAccount.setBalance(balanceTo + amount);
                accountDao.update(fromAccount);
                accountDao.update(toAccount);
            } else {
                throw new RuntimeException("Insufficient funds in account with number: " + fromAccountNumber);
            }
        } else {
            throw new RuntimeException("One or both accounts not found");
        }
    }
}
