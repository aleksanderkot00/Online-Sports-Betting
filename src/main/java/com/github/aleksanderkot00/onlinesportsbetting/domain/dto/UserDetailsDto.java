package com.github.aleksanderkot00.onlinesportsbetting.domain.dto;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Role;
import com.github.aleksanderkot00.onlinesportsbetting.domain.User;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class UserDetailsDto {
    private long userId;
    private String email;
    private String encryptedPassword;
    private boolean active;
    private Set<String> roles;

    public UserDetailsDto(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.encryptedPassword = user.getEncryptedPassword();
        this.active = user.isActive();
        this.roles = user.getRoles().stream()
                .map(Role::getRole)
                .collect(Collectors.toSet());
    }
}
