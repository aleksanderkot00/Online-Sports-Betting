package com.github.aleksanderkot00.onlinesportsbetting.mapper;

import com.github.aleksanderkot00.onlinesportsbetting.domain.User;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.UserDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UserMapper {
    public User mapToUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setBalance(BigDecimal.ZERO);

        return user;
    }
}
