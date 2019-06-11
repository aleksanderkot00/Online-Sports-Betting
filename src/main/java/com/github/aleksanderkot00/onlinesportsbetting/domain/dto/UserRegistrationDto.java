package com.github.aleksanderkot00.onlinesportsbetting.domain.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private String name;
    private String lastName;
    private String email;
    private String password;
}