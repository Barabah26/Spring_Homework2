package com.example.spring_homework2.dao;

import com.example.spring_homework2.domain.Account;
import com.example.spring_homework2.domain.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccountDao implements Dao<Account> {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    @Transactional
    public Account save(Account account) {
        if (account.getId() == null) {
            entityManager.persist(account);
        } else {
            entityManager.merge(account);
        }
        return account;
    }

    @Override
    @Transactional
    public boolean delete(Account account) {
        if (entityManager.contains(account)) {
            entityManager.remove(account);
            return true;
        } else {
            Account managedAccount = entityManager.find(Account.class, account.getId());
            if (managedAccount != null) {
                entityManager.remove(managedAccount);
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM Account").executeUpdate();
    }

    @Override
    @Transactional
    public void saveAll(Account account) {
        save(account);
    }

    @Override
    public List<Account> findAll() {
        return entityManager.createQuery("SELECT a FROM Account a", Account.class).getResultList();
    }

    @Override
    public boolean deleteById(long id) {
        Account account = entityManager.find(Account.class, id);
        if (account != null) {
            entityManager.remove(account);
            return true;
        }
        return false;
    }

    @Override
    public Account getOne(long id) {
        return entityManager.find(Account.class, id);
    }

    public Account findByNumber(String number) {
        try {
            return entityManager.createQuery("SELECT a FROM Account a WHERE a.number = :number", Account.class)
                    .setParameter("number", number)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Account with number " + number + " is not found");
        }
    }

    @Transactional
    public void update(Account account) {
        entityManager.merge(account);
    }
}
