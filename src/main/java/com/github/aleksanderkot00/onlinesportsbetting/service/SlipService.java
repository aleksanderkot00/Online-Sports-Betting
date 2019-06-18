package com.github.aleksanderkot00.onlinesportsbetting.service;

import com.github.aleksanderkot00.onlinesportsbetting.domain.*;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.ValueDto;
import com.github.aleksanderkot00.onlinesportsbetting.exception.BetNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.exception.SlipIsOrderedException;
import com.github.aleksanderkot00.onlinesportsbetting.exception.SlipNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.exception.UserNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.repository.BetRepository;
import com.github.aleksanderkot00.onlinesportsbetting.repository.SlipRepository;
import com.github.aleksanderkot00.onlinesportsbetting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Transactional
@Service
public class SlipService {

    private final SlipRepository slipRepository;
    private final BetRepository betRepository;
    private final UserRepository userRepository;

    @Autowired
    public SlipService(SlipRepository slipRepository, BetRepository betRepository, UserRepository userRepository) {
        this.slipRepository = slipRepository;
        this.betRepository = betRepository;
        this.userRepository = userRepository;
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

    public Slip settleSlip(Slip slip) {
        slip.getBets().stream()
                .filter(bet -> bet.getResult().equals(BetResult.NOT_FINISHED))
                .forEach(Bet::settle);
        long lostBetsNumber = slip.getBets().stream()
                .filter(bet -> bet.getResult().equals(BetResult.LOST)).count();
        long notFinishedBetsNumber = slip.getBets().stream()
                .filter(bet -> bet.getResult().equals(BetResult.NOT_FINISHED)).count();
        if (lostBetsNumber > 0) {
            slip.setState(SlipState.LOST);

        } else if (notFinishedBetsNumber == 0) {
            User user = userRepository.findBySlipsContains(slip).orElseThrow(UserNotFoundException::new);
            user.addToBalance(slip.getStake().multiply(slip.getTotalOdds()));
            slip.setState(SlipState.WINNING);
        }
        return slipRepository.save(slip);
    }

    public Slip setStake(long userId, BigDecimal stake) {
        Slip slip = userRepository.findById(userId).orElseThrow(UserNotFoundException::new).getCartSlip();
        slip.setStake(stake);
        return slipRepository.save(slip);
    }
}
