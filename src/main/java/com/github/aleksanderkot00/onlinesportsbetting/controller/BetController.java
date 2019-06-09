package com.github.aleksanderkot00.onlinesportsbetting.controller;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Bet;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.BetDto;
import com.github.aleksanderkot00.onlinesportsbetting.mapper.BetMapper;
import com.github.aleksanderkot00.onlinesportsbetting.service.BetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bets")
public class BetController {

    private final BetService betService;
    private final BetMapper betMapper;

    @Autowired
    public BetController(BetService betService, BetMapper betMapper) {
        this.betService = betService;
        this.betMapper = betMapper;
    }

    @GetMapping
    public List<Bet> getBets() {
        return betService.getBets();
    }

    @GetMapping("/{betId}")
    public Bet getBet(@PathVariable long betId) {
        return betService.getBet(betId);
    }

    @PostMapping
    public Bet addBet(@RequestBody BetDto betDto) {
        return betService.addBet(betMapper.mapToBet(betDto));
    }

    @PutMapping("/{betId}")
    public Bet changeActivity(@PathVariable long betId) {
        return betService.changeActivity(betId);
    }

    @DeleteMapping("/{betId}")
    public void deleteBet(@PathVariable long betId) {
        betService.deleteBet(betId);
    }
}
