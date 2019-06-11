package com.github.aleksanderkot00.onlinesportsbetting.service;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Bet;
import com.github.aleksanderkot00.onlinesportsbetting.domain.Slip;
import com.github.aleksanderkot00.onlinesportsbetting.domain.SlipState;
import com.github.aleksanderkot00.onlinesportsbetting.exception.BetNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.exception.SlipIsOrderedException;
import com.github.aleksanderkot00.onlinesportsbetting.exception.SlipNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.repository.BetRepository;
import com.github.aleksanderkot00.onlinesportsbetting.repository.SlipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlipService {

    private final SlipRepository slipRepository;
    private final BetRepository betRepository;

    @Autowired
    public SlipService(SlipRepository slipRepository, BetRepository betRepository) {
        this.slipRepository = slipRepository;
        this.betRepository = betRepository;
    }

    public List<Slip> getSlips() {
        return slipRepository.findAll();
    }

    public Slip getSlip(long slipId) {
        return slipRepository.findById(slipId).orElseThrow(SlipNotFoundException::new);
    }

    public Slip save(Slip slip) {
        return slipRepository.save(slip);
    }

    public Slip addBetToSlip(long slipId, long betId) {
        Slip slip = slipRepository.findById(slipId).orElseThrow(SlipNotFoundException::new);
        Bet bet = betRepository.findById(betId).orElseThrow(BetNotFoundException::new);
        slip.getBets().add(bet);
        return slipRepository.save(slip);
    }

    public Slip removeBetFromSlip(long slipId, long betId) {
        Slip slip = slipRepository.findById(slipId).orElseThrow(SlipNotFoundException::new);
        Bet bet = betRepository.findById(betId).orElseThrow(BetNotFoundException::new);
        slip.getBets().remove(bet);
        return slipRepository.save(slip);
    }

    public Slip emptyCartSlip(long slipId) {
        Slip cartSlip = slipRepository.findById(slipId).orElseThrow(SlipNotFoundException::new);
        if (cartSlip.getState().equals(SlipState.UNORDERED)) {
            cartSlip = new Slip();
        } else {
            throw new SlipIsOrderedException();
        }
        return cartSlip;
    }
}
