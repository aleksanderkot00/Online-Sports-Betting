package com.github.aleksanderkot00.onlinesportsbetting.domain.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
public final class UserDto {
    private final long userId;
    private final String name;
    private final String lastName;
    private final String email;
    private final BigDecimal balance;
    private final boolean active;
    @Getter(AccessLevel.NONE)
    private final Set<String> roles;

    public Set<String> getRoles() {
        return new HashSet<>(roles);
    }
}
