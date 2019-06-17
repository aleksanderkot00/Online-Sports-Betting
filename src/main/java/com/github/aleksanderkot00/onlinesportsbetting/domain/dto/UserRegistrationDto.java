package com.github.aleksanderkot00.onlinesportsbetting.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {
    private String name;
    private String lastName;
    private String email;
    private String password;
}
