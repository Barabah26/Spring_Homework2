package com.example.spring_homework2.dao;

import com.example.spring_homework2.domain.Customer;

import java.util.ArrayList;
import java.util.List;

public class DefaultCustomers {
    private static long customerId = 0;

    public static String[] names = {"Jack Smith", "Arisha Doe", "Michael Johnson"};

    public static List<Customer> createCustomers() {
        List<Customer> customers = new ArrayList<>();
        for (String name : names) {
            String email = getCustomerEmail(name);
            int age = getCustomerAge(name);
            customerId++;
            Customer newCustomer = new Customer(name, email, age);
            newCustomer.setId(customerId);
            customers.add(newCustomer);
        }
        return customers;
    }

    public static String getCustomerEmail(String name) {
        return name.toLowerCase().replace(" ", "") + "@gmail.com";
    }

    public static int getCustomerAge(String name) {
        return name.length() % 50 + 20;
    }
}
