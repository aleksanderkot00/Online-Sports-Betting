package com.github.aleksanderkot00.onlinesportsbetting.mapper;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Role;
import com.github.aleksanderkot00.onlinesportsbetting.domain.User;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.UserDto;
import com.github.aleksanderkot00.onlinesportsbetting.exception.RoleNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public User mapToUser(UserDto userDto) {
        User user = new User();
        user.setActive(true);
        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setBalance(BigDecimal.ZERO);
        Role role = roleRepository.findByRole("USER").orElseThrow(RoleNotFoundException::new);
        user.getRoles().add(role);

        return user;
    }
}
