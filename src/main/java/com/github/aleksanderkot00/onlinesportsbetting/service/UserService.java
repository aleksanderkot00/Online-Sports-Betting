package com.github.aleksanderkot00.onlinesportsbetting.service;

import com.github.aleksanderkot00.onlinesportsbetting.domain.User;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.UserDto;
import com.github.aleksanderkot00.onlinesportsbetting.exception.UserNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User editUser(long userId, UserDto userDto) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if (userDto.getName() != "" && userDto.getName() != null) user.setName(userDto.getName());
        if (userDto.getLastName() != "" && userDto.getLastName() != null) user.setLastName(userDto.getLastName());
        if (userDto.getEmail() != "" && userDto.getEmail() != null) user.setEmail(userDto.getEmail());
        if (userDto.getPassword() != "" && userDto.getPassword() != null) user.setPassword(userDto.getPassword());

        return userRepository.save(user);
    }

    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }
}
