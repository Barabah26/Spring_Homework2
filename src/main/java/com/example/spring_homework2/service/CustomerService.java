package com.example.spring_homework2.service;

import com.example.spring_homework2.domain.Currency;
import com.example.spring_homework2.domain.Customer;

import java.util.List;

public interface CustomerService {
    Customer save(Customer obj);
    boolean delete(Customer obj);
    void deleteAll(List<Customer> entities);
    void saveAll(List<Customer> entities);
    List<Customer> findAll();
    boolean deleteById(Long id);
    Customer getOne(long id);
    void createAccountForCustomer(Long id, Currency currency, Double amount);
    void deleteAccountFromCustomer(Long customerId, String accountNumber);
    Customer update(Long id, Customer updatedCustomer);
    void assignAccountsToCustomers();
    void deleteCustomerAccounts(Customer customer);
}
