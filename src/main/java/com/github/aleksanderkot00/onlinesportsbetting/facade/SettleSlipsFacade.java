package com.github.aleksanderkot00.onlinesportsbetting.facade;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Bet;
import com.github.aleksanderkot00.onlinesportsbetting.domain.BetResult;
import com.github.aleksanderkot00.onlinesportsbetting.domain.Slip;
import com.github.aleksanderkot00.onlinesportsbetting.domain.SlipState;
import com.github.aleksanderkot00.onlinesportsbetting.service.BetService;
import com.github.aleksanderkot00.onlinesportsbetting.service.SlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettleSlipsFacade {

    private final BetService betService;
    private final SlipService slipService;

    @Autowired
    public SettleSlipsFacade(BetService betService, SlipService slipService) {
        this.betService = betService;
        this.slipService = slipService;
    }

    public void SettleSlips() {
        List<Slip> orderedSlips = slipService.getSlipsByState(SlipState.ORDERED);

        for (Slip slip : orderedSlips) {
            slip.getBets().stream()
                    .filter(bet -> bet.getResult().equals(BetResult.NOT_FINISHED))
                    .map(Bet::getBetId)
                    .forEach(betService::settleBet);
            slipService.settleSlip(slip.getSlipId());
        }
    }
}
