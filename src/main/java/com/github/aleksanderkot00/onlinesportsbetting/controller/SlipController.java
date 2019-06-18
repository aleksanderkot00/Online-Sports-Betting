package com.github.aleksanderkot00.onlinesportsbetting.controller;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Slip;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.SlipDto;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.ValueDto;
import com.github.aleksanderkot00.onlinesportsbetting.facade.OrderSlipFacade;
import com.github.aleksanderkot00.onlinesportsbetting.mapper.SlipMapper;
import com.github.aleksanderkot00.onlinesportsbetting.service.SlipService;
import com.github.aleksanderkot00.onlinesportsbetting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/users")
public class SlipController {

    private final SlipService slipService;
    private final UserService userService;
    private final SlipMapper slipMapper;
    private final OrderSlipFacade orderSlipFacade;

    @Autowired
    public SlipController(SlipService slipService, UserService userService, SlipMapper slipMapper, OrderSlipFacade orderSlipFacade) {
        this.slipService = slipService;
        this.userService = userService;
        this.slipMapper = slipMapper;
        this.orderSlipFacade = orderSlipFacade;
    }

    @GetMapping("/{userId}/slips")
    public Set<SlipDto> getSlips(@PathVariable long userId) {
        return slipMapper.mapToSlipDtoSet(userService.getUser(userId).getSlips());
    }

    @GetMapping("/{userId}/cart")
    public SlipDto getCartSlip(@PathVariable long userId) {
        return slipMapper.mapToSlipDto(userService.getUser(userId).getCartSlip());
    }

    @PutMapping("/{userId}/cart/empty")
    public SlipDto emptySlip(@PathVariable long userId) {
        return slipMapper.mapToSlipDto(slipService.emptyCartSlip(userService.getUser(userId).getCartSlip().getSlipId()));
    }

    @PatchMapping("/{userId}/cart/bets/{betId}")
    public SlipDto addBetToSlip(@PathVariable long userId, @PathVariable long betId) {
        return slipMapper.mapToSlipDto(slipService.addBetToSlip(userService.getUser(userId).getCartSlip().getSlipId(), betId));
    }

    @DeleteMapping("/{userId}/cart/bets/{betId}")
    public SlipDto removeBetFromSlip(@PathVariable long userId, @PathVariable long betId) {
        return slipMapper.mapToSlipDto(slipService.removeBetFromSlip(userService.getUser(userId).getCartSlip().getSlipId(), betId));
    }

    @PutMapping("/{userId}/cart")
    public SlipDto orderCartSlip(@PathVariable long userId) {
            Slip cartSlip = orderSlipFacade.orderSlip(userId);
            return slipMapper.mapToSlipDto(cartSlip);
    }

    @PatchMapping("/{userId}/cart/stake")
    public SlipDto changeStake(@PathVariable long userId, @RequestBody ValueDto value) {
        return slipMapper.mapToSlipDto(slipService.setStake(userId, value.getValue()));
    }
}
