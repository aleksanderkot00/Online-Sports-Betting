package com.github.aleksanderkot00.onlinesportsbetting.service;

import com.github.aleksanderkot00.onlinesportsbetting.domain.BetSlip;
import com.github.aleksanderkot00.onlinesportsbetting.exception.BetSlipNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.repository.BetSlipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BetSlipService {

    @Autowired
    private BetSlipRepository betSlipRepository;

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
}
