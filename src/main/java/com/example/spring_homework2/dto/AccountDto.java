package com.example.spring_homework2.dto;

import com.example.spring_homework2.domain.Currency;

public class AccountDto {
    private Currency currency;
    private Double balance;

    public AccountDto(Currency currency, Double balance) {
        this.currency = currency;
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
