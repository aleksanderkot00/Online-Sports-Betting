package com.github.aleksanderkot00.onlinesportsbetting.controller;

import com.github.aleksanderkot00.onlinesportsbetting.domain.BetSlip;
import com.github.aleksanderkot00.onlinesportsbetting.service.BetSlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/slips")
public class BetSlipController {

    @Autowired
    private BetSlipService betSlipService;

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
}
