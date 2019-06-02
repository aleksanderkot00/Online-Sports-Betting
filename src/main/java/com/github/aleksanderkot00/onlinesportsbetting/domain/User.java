package com.github.aleksanderkot00.onlinesportsbetting.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
public class User {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private long userId;

    @NotNull
    @Size(min = 2, max = 12)
    private String name;

    @NotNull
    @Size(min = 2, max = 12)
    private String lastName;

    @NotNull
    @Email
    private String email;
    private BigDecimal balance;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
