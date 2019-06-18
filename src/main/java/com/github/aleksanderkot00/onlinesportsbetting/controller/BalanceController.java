package com.github.aleksanderkot00.onlinesportsbetting.controller;

import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.BalanceDto;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.ValueDto;
import com.github.aleksanderkot00.onlinesportsbetting.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class BalanceController {

    private final BalanceService balanceService;

    @Autowired
    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping("/{userId}/balance")
    public ResponseEntity getGetBalance(@PathVariable long userId){
        return ResponseEntity.ok(balanceService.getUserBalance(userId));
    }

    @PatchMapping("/{userId}/payment")
    public ResponseEntity makePayment(@PathVariable long userId, @RequestBody ValueDto value){
        balanceService.payment(userId, value.getValue());
        return ResponseEntity.noContent().build();
    }
}
