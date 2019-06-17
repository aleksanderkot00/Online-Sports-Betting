package com.github.aleksanderkot00.onlinesportsbetting.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "USERS")
public class User {

    @NotNull
    @Id
    @GeneratedValue
    private long userId;

    @NotNull
    @Size(min = 2, max = 15)
    private String name;

    @NotNull
    @Size(min = 2, max = 15)
    private String lastName;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @Column(precision = 9, scale = 2)
    @Min(value = 0)
    private BigDecimal balance = BigDecimal.ZERO;

    @NotNull
    private String encryptedPassword;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="CART_SLIP_ID")
    private Slip cartSlip = new Slip();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Slip> slips = new HashSet<>();

    @NotNull
    private boolean active = true;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "USERS_ROLES",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private Set<Role> roles = new HashSet<>();

    public void addToBalance(BigDecimal value) {
        balance = balance.add(value);
    }
}
