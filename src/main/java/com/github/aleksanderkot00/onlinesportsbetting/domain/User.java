package com.github.aleksanderkot00.onlinesportsbetting.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
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
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="CARTSLIP_ID")
    private Slip cartSlip = new Slip();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                active == user.active &&
                Objects.equals(name, user.name) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(balance, user.balance) &&
                Objects.equals(encryptedPassword, user.encryptedPassword) &&
                Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, lastName, email, balance, encryptedPassword, active, roles);
    }
}
