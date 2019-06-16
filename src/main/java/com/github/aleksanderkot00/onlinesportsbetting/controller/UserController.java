package com.github.aleksanderkot00.onlinesportsbetting.controller;

import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.BalanceDto;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.UserDto;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.UserRegistrationDto;
import com.github.aleksanderkot00.onlinesportsbetting.exception.NotAuthorizeRequestException;
import com.github.aleksanderkot00.onlinesportsbetting.mapper.UserMapper;
import com.github.aleksanderkot00.onlinesportsbetting.service.BalanceService;
import com.github.aleksanderkot00.onlinesportsbetting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final BalanceService balanceService;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper, BalanceService balanceService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.balanceService = balanceService;
    }

    @GetMapping
    public List<UserDto> getUsers(){
        return userMapper.mapToUserDtoList(userService.getUsers());
    }

    @PostAuthorize("returnObject.email.equals(authentication.name)")
    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable long userId){
        return userMapper.mapToUserDto(userService.getUser(userId));
    }

    @PostMapping
    public UserDto addUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        return userMapper.mapToUserDto(userService.addUser(userRegistrationDto));
    }

    @PatchMapping("/{userId}")
    public UserDto editUser(@PathVariable long userId, @RequestBody UserRegistrationDto userRegistrationDto, Principal principal) {
        if (userId != userService.getUser(principal.getName()).getUserId()) {
            throw new NotAuthorizeRequestException();
        } else {
            return userMapper.mapToUserDto(userService.editUser(userId, userRegistrationDto));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/balance")
    public BalanceDto getBalance(Principal principal){
        return balanceService.getUserBalance(principal.getName());
    }
}
