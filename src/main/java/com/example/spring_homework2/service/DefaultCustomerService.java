package com.example.spring_homework2.service;

import com.example.spring_homework2.dao.AccountDao;
import com.example.spring_homework2.dao.CustomerDao;
import com.example.spring_homework2.domain.Account;
import com.example.spring_homework2.domain.Currency;
import com.example.spring_homework2.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DefaultCustomerService implements CustomerService {
    private final CustomerDao customerDao;
    private final AccountDao accountDao;

    @Autowired
    public DefaultCustomerService(CustomerDao customerDao, AccountDao accountDao) {
        this.customerDao = customerDao;
        this.accountDao = accountDao;
    }

    @Override
    public Customer save(Customer customer) {
        return customerDao.save(customer);
    }

    @Override
    public boolean delete(Customer customer) {
        return customerDao.delete(customer);
    }

    @Override
    public void deleteAll(List<Customer> customers) {
        customerDao.deleteAll(customers);
    }

    @Override
    public void saveAll(List<Customer> customers) {
        customerDao.saveAll(customers);
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public boolean deleteById(Long id) {
        return customerDao.deleteById(id);
    }

    @Override
    public Customer getOne(long id) {
        return customerDao.getOne(id);
    }


    @Override
    public void createAccountForCustomer(Long id, Currency currency, Double amount) {
        Customer customer = customerDao.getOne(id);

        if (customer == null) {
            throw new IllegalArgumentException("Customer not found with id: " + id);
        }

        Account account = new Account(currency, customer);
        account.setBalance(amount);

        boolean accountExists = customer.getAccounts().stream()
                .anyMatch(e -> e.getNumber().equals(account.getNumber()));

        if (accountExists) {
            throw new IllegalArgumentException("Account with number " + account.getNumber() + " already exists for customer with id: " + id);
        }

        List<Account> customerAccounts = customer.getAccounts();
        customerAccounts.add(account);

        accountDao.save(account);
    }

    @Override
    public void deleteCustomerAccounts(Customer customer) {
        List<Account> accounts = customer.getAccounts();
        if (!accounts.isEmpty()){
            for (Account account : accounts) {
                accountDao.deleteById(account.getId());
            }
            accounts.clear();
            customerDao.update(customer);;
        } else {
            throw new IllegalArgumentException("Accounts are absent");
        }

    }

    @Override
    public void deleteAccountFromCustomer(Long customerId, String accountNumber) {
        Customer customer = customerDao.getOne(customerId);
        if (customer != null) {
            Account accountToDelete = customer.getAccounts().stream()
                    .filter(account -> account.getNumber().equals(accountNumber))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Account with number " + accountNumber + " not found"));

            // Видаляємо акаунт з акаунтів клієнта
            boolean removed = customer.getAccounts().removeIf(account -> account.getNumber().equals(accountNumber));
            if (removed) {
                accountDao.delete(accountToDelete);
            }
        } else {
            throw new IllegalArgumentException("Customer not found with id: " + customerId);
        }
    }

    @Override
    public Customer update(Long id, Customer customer) {
        Customer currentCustomer = customerDao.getOne(id);
        if (currentCustomer == null) {
            throw new IllegalArgumentException("Customer with id " + id + " not found");
        }

        currentCustomer.setName(customer.getName());
        currentCustomer.setEmail(customer.getEmail());
        currentCustomer.setAge(customer.getAge());

        for (Account account : currentCustomer.getAccounts()) {
            account.setCustomer(currentCustomer);
            accountDao.update(account);
        }

        return customerDao.update(currentCustomer);
    }

    @Override
    public void assignAccountsToCustomers() {

        List<Account> allAccounts = accountDao.findAll();
        List<Customer> allCustomers = customerDao.findAll();

        for (Account account : allAccounts) {
            Customer customer = account.getCustomer();
            if (customer != null) {
                for (Customer cust : allCustomers) {
                    if (cust.getId().equals(customer.getId())) {
                        boolean accountExists = cust.getAccounts().stream()
                                .anyMatch(existingAccount -> existingAccount.getNumber().equals(account.getNumber()));

                        if (!accountExists) {
                            cust.getAccounts().add(account);
                        } else {
                            log.warn("Account with number " + account.getNumber() + " already exists for customer with id: " + customer.getId());
                        }
                        break;
                    }
                }
            }
        }
    }
}
