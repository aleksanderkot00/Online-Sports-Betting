package com.github.aleksanderkot00.onlinesportsbetting.controller;

import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.UserDto;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.UserRegistrationDto;
import com.github.aleksanderkot00.onlinesportsbetting.mapper.UserMapper;
import com.github.aleksanderkot00.onlinesportsbetting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostAuthorize("returnObject.email == authentication.name")
    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable long userId){
        return userMapper.mapToUserDto(userService.getUser(userId));
    }

    @PostMapping
    public UserDto addUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        return userMapper.mapToUserDto(userService.addUser(userRegistrationDto));
    }

    @PostAuthorize("returnObject.email == authentication.name")
    @PatchMapping("/{userId}")
    public UserDto editUser(@PathVariable long userId, @RequestBody UserRegistrationDto userRegistrationDto) {
        return userMapper.mapToUserDto(userService.editUser(userId, userRegistrationDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
    }
}
