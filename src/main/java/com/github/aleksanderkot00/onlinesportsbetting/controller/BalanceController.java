package com.github.aleksanderkot00.onlinesportsbetting.controller;

import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.BalanceDto;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.UserDto;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.ValueDto;
import com.github.aleksanderkot00.onlinesportsbetting.mapper.UserMapper;
import com.github.aleksanderkot00.onlinesportsbetting.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class BalanceController {

    private final BalanceService balanceService;
    private final UserMapper userMapper;

    @Autowired
    public BalanceController(BalanceService balanceService, UserMapper userMapper) {
        this.balanceService = balanceService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{userId}/balance")
    public BalanceDto getBalance(@PathVariable long userId){
        return balanceService.getUserBalance(userId);
    }

    @PatchMapping("/{userId}/payment")
    public UserDto getBalance(@PathVariable long userId, @RequestBody ValueDto value){
        return userMapper.mapToUserDto(balanceService.payment(userId, value.getValue()));
    }
}
