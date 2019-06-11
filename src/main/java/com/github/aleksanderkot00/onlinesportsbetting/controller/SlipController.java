package com.github.aleksanderkot00.onlinesportsbetting.controller;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Slip;
import com.github.aleksanderkot00.onlinesportsbetting.domain.User;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.SlipDto;
import com.github.aleksanderkot00.onlinesportsbetting.mapper.SlipMapper;
import com.github.aleksanderkot00.onlinesportsbetting.service.SlipService;
import com.github.aleksanderkot00.onlinesportsbetting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/slips")
public class SlipController {

    private final SlipService slipService;
    private final UserService userService;
    private final SlipMapper slipMapper;

    @Autowired
    public SlipController(SlipService slipService, UserService userService, SlipMapper slipMapper) {
        this.slipService = slipService;
        this.userService = userService;
        this.slipMapper = slipMapper;
    }

    @GetMapping
    public SlipDto getSlip(Principal principal) {
        return slipMapper.mapToSlipDto(userService.getUser(principal.getName()).getCartSlip());
    }

    @PutMapping
    public SlipDto emptySlip(Principal principal) {
        User user = userService.getUser(principal.getName());
        return slipMapper.mapToSlipDto(slipService.getSlip(user.getCartSlip().getSlipId()));
    }

    @PutMapping("/bets/{betId}")
    public Slip addBetToSlip(@PathVariable long betId, Principal principal) {
        User user = userService.getUser(principal.getName());
        return slipService.addBetToSlip(user.getCartSlip().getSlipId(), betId);
    }

    @DeleteMapping("/bets/{betId}")
    public Slip removeBetFromSlip(@PathVariable long betId, Principal principal) {
        User user = userService.getUser(principal.getName());
        return slipService.removeBetFromSlip(user.getCartSlip().getSlipId(), betId);
    }
}
