package com.github.aleksanderkot00.onlinesportsbetting.controller;

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
    public List<BetDto> getBets() {
        return betMapper.mapToBetDtoList(betService.getBets());
    }

    @GetMapping("/{betId}")
    public BetDto getBet(@PathVariable long betId) {
        return betMapper.mapToBetDto(betService.getBet(betId));
    }

    @PostMapping
    public BetDto addBet(@RequestBody BetDto betDto) {
        return betMapper.mapToBetDto(betService.addBet(betMapper.mapToBet(betDto)));
    }

    @PutMapping("/{betId}")
    public BetDto changeActivity(@PathVariable long betId) {
        return betMapper.mapToBetDto(betService.changeActivity(betId));
    }

    @DeleteMapping("/{betId}")
    public void deleteBet(@PathVariable long betId) {
        betService.deleteBet(betId);
    }
}
