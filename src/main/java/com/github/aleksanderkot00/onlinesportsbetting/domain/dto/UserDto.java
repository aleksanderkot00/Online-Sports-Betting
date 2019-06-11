package com.github.aleksanderkot00.onlinesportsbetting.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDto {
    private long userId;
    private String name;
    private String lastName;
    private String email;
    private BigDecimal balance;
    private boolean active;
    private Set<String> roles = new HashSet<>();
}
