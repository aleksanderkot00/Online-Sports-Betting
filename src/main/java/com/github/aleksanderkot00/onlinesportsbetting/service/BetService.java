package com.github.aleksanderkot00.onlinesportsbetting.service;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Bet;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.BetDto;
import com.github.aleksanderkot00.onlinesportsbetting.exception.BetNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.repository.BetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BetService {

    @Autowired
    private BetRepository betRepository;

    public List<Bet> getBets() {
        return betRepository.findAll();
    }

    public Bet getBet(long betId) {
        return betRepository.findById(betId).orElseThrow(BetNotFoundException::new);
    }

    public Bet addBet(Bet bet) {
        return betRepository.save(bet);
    }

    public Bet changeActivity(long betId) {
        Bet bet = betRepository.findById(betId).orElseThrow(BetNotFoundException::new);
        bet.setActive(!bet.isActive());
        return betRepository.save(bet);
    }

    public void deleteBet(long betId) {
        betRepository.deleteById(betId);
    }
}
