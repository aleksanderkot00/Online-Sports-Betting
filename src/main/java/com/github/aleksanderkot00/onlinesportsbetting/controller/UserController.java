package com.github.aleksanderkot00.onlinesportsbetting.controller;

import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.UserDetailsDto;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.UserDto;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.UserRegistrationDto;
import com.github.aleksanderkot00.onlinesportsbetting.mapper.UserMapper;
import com.github.aleksanderkot00.onlinesportsbetting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserDto> getUsers(){
        return userMapper.mapToUserDtoList(userService.getUsers());
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable long userId){
        return userMapper.mapToUserDto(userService.getUser(userId));
    }

    @PostMapping
    public UserDto addUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        return userMapper.mapToUserDto(userService.addUser(userRegistrationDto));
    }

    @PatchMapping("/{userId}")
    public UserDto editUser(@PathVariable long userId, @RequestBody UserRegistrationDto userRegistrationDto) {
            return userMapper.mapToUserDto(userService.editUser(userId, userRegistrationDto));
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/details")
    public List<UserDetailsDto> getUsersDetails(){
        return userService.getUsers().stream()
                .map(UserDetailsDto::new)
                .collect(Collectors.toList());
    }
}
