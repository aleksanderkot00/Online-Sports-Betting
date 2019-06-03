package com.github.aleksanderkot00.onlinesportsbetting.controller;

import com.github.aleksanderkot00.onlinesportsbetting.domain.User;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.UserDto;
import com.github.aleksanderkot00.onlinesportsbetting.mapper.UserMapper;
import com.github.aleksanderkot00.onlinesportsbetting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable long userId){
        return userService.getUser(userId);
    }

    @PostMapping
    public User addUser(UserDto userDto) {
        return userService.addUser(userMapper.mapToUser(userDto));
    }
}
