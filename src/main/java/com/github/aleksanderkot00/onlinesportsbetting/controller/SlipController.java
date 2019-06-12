package com.github.aleksanderkot00.onlinesportsbetting.controller;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Slip;
import com.github.aleksanderkot00.onlinesportsbetting.domain.User;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.SlipDto;
import com.github.aleksanderkot00.onlinesportsbetting.facade.SlipOrderFacade;
import com.github.aleksanderkot00.onlinesportsbetting.mapper.SlipMapper;
import com.github.aleksanderkot00.onlinesportsbetting.service.SlipService;
import com.github.aleksanderkot00.onlinesportsbetting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/slips")
public class SlipController {

    private final SlipService slipService;
    private final UserService userService;
    private final SlipMapper slipMapper;
    private final SlipOrderFacade slipOrderFacade;

    @Autowired
    public SlipController(SlipService slipService, UserService userService, SlipMapper slipMapper, SlipOrderFacade slipOrderFacade) {
        this.slipService = slipService;
        this.userService = userService;
        this.slipMapper = slipMapper;
        this.slipOrderFacade = slipOrderFacade;
    }

    @GetMapping
    public SlipDto getSlip(Principal principal) {
        return slipMapper.mapToSlipDto(userService.getUser(principal.getName()).getCartSlip());
    }

    @DeleteMapping
    public SlipDto emptySlip(Principal principal) {
        User user = userService.getUser(principal.getName());
        return slipMapper.mapToSlipDto(slipService.emptyCartSlip(user.getCartSlip().getSlipId()));
    }

    @PatchMapping("/bets/{betId}")
    public SlipDto addBetToSlip(@PathVariable long betId, Principal principal) {
        User user = userService.getUser(principal.getName());
        return slipMapper.mapToSlipDto(slipService.addBetToSlip(user.getCartSlip().getSlipId(), betId));
    }

    @DeleteMapping("/bets/{betId}")
    public SlipDto removeBetFromSlip(@PathVariable long betId, Principal principal) {
        User user = userService.getUser(principal.getName());
        return slipMapper.mapToSlipDto(slipService.removeBetFromSlip(user.getCartSlip().getSlipId(), betId));
    }

    @PutMapping()
    public ResponseEntity orderCartSlip(Principal principal) {
        try {
            Slip cartSlip = slipOrderFacade.orderSlip(principal.getName());
            return ResponseEntity.accepted().body(slipMapper.mapToSlipDto(cartSlip));
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(emptySlip(principal));
        }
    }
}
