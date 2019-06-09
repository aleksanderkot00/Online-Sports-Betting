package com.github.aleksanderkot00.onlinesportsbetting.controller;

import com.github.aleksanderkot00.onlinesportsbetting.domain.BetSlip;
import com.github.aleksanderkot00.onlinesportsbetting.service.BetSlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/slips")
public class BetSlipController {

    private final BetSlipService betSlipService;

    @Autowired
    public BetSlipController(BetSlipService betSlipService) {
        this.betSlipService = betSlipService;
    }

    @GetMapping
    public List<BetSlip> getBets() {
        return betSlipService.getBetSlips();
    }

    @GetMapping("/{betSlipId}")
    public BetSlip getBetSlip(@PathVariable long betSlipId) {
        return betSlipService.getBetSlip(betSlipId);
    }

    @DeleteMapping("/{betSlipId}")
    public void deleteBetSlip(@PathVariable long betSlipId) {
        betSlipService.deleteBetSlip(betSlipId);
    }

    @PutMapping("/{betSlipId}/bet/{betId}")
    public BetSlip addBetToSlip(@PathVariable long betSlipId, @PathVariable long betId) {
        return betSlipService.addBetToSlip(betSlipId, betId);
    }

    @DeleteMapping("/{betSlipId}/bet/{betId}")
    public BetSlip removeBetFromSlip(@PathVariable long betSlipId, @PathVariable long betId) {
        return betSlipService.removeBetFromSlip(betSlipId, betId);
    }
}
