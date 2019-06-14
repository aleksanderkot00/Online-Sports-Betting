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

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class SlipService {

    private final SlipRepository slipRepository;
    private final BetRepository betRepository;

    @Autowired
    public SlipService(SlipRepository slipRepository, BetRepository betRepository) {
        this.slipRepository = slipRepository;
        this.betRepository = betRepository;
    }

    public List<Slip> getSlipsByState(SlipState state) {
        return slipRepository.findAllByState(state);
    }

    public Slip save(Slip slip) {
        return slipRepository.save(slip);
    }

    public Slip addBetToSlip(long slipId, long betId) {
        Slip slip = slipRepository.findById(slipId).orElseThrow(SlipNotFoundException::new);
        Bet bet = betRepository.findById(betId).orElseThrow(BetNotFoundException::new);
        slip.getBets().add(bet);
        slip.refreshTotalOdds();
        return slipRepository.save(slip);
    }

    public Slip removeBetFromSlip(long slipId, long betId) {
        Slip slip = slipRepository.findById(slipId).orElseThrow(SlipNotFoundException::new);
        Bet bet = betRepository.findById(betId).orElseThrow(BetNotFoundException::new);
        slip.getBets().remove(bet);
        slip.refreshTotalOdds();
        return slipRepository.save(slip);
    }

    public Slip emptyCartSlip(long slipId) {
        Slip cartSlip = slipRepository.findById(slipId).orElseThrow(SlipNotFoundException::new);
        if (cartSlip.getState().equals(SlipState.UNORDERED)) {
            cartSlip.getBets().clear();
            cartSlip.refreshTotalOdds();
        } else {
            throw new SlipIsOrderedException();
        }
        return slipRepository.save(cartSlip);
    }
}
