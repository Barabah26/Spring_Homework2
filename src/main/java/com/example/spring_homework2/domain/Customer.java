package com.example.spring_homework2.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Integer age;
    @JsonManagedReference
    @OneToMany
    private List<Account> accounts;
    @ManyToMany
    @JoinTable(name = "customer_employer",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "employer_id"))
    private List<Employer> employers = new ArrayList<>();

    public Customer(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.accounts = new ArrayList<>();
        this.employers = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Customer{" +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
