package com.github.aleksanderkot00.onlinesportsbetting.service;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Bet;
import com.github.aleksanderkot00.onlinesportsbetting.domain.BetSlip;
import com.github.aleksanderkot00.onlinesportsbetting.exception.BetNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.exception.BetSlipNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.repository.BetRepository;
import com.github.aleksanderkot00.onlinesportsbetting.repository.BetSlipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BetSlipService {

    @Autowired
    private BetSlipRepository betSlipRepository;

    @Autowired
    private BetRepository betRepository;

    public List<BetSlip> getBetSlips() {
        return betSlipRepository.findAll();
    }

    public BetSlip getBetSlip(long betSlipId) {
        return betSlipRepository.findById(betSlipId).orElseThrow(BetSlipNotFoundException::new);
    }

    public BetSlip save(BetSlip betSlip) {
        return betSlipRepository.save(betSlip);
    }

    public void deleteBetSlip(long betSlipId) {
        betSlipRepository.deleteById(betSlipId);
    }

    public BetSlip addBetToSlip(long betSlipId, long betId) {
        BetSlip betSlip = betSlipRepository.findById(betSlipId).orElseThrow(BetSlipNotFoundException::new);
        Bet bet = betRepository.findById(betId).orElseThrow(BetNotFoundException::new);
        betSlip.getBets().add(bet);
        return betSlipRepository.save(betSlip);
    }

    public BetSlip removeBetFromSlip(long betSlipId, long betId) {
        BetSlip betSlip = betSlipRepository.findById(betSlipId).orElseThrow(BetSlipNotFoundException::new);
        Bet bet = betRepository.findById(betId).orElseThrow(BetNotFoundException::new);
        betSlip.getBets().remove(bet);
        return betSlipRepository.save(betSlip);
    }
}
