package com.github.aleksanderkot00.onlinesportsbetting.scheduler;

import com.github.aleksanderkot00.onlinesportsbetting.domain.SlipState;
import com.github.aleksanderkot00.onlinesportsbetting.service.SlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SettleSlipScheduler {

    private final SlipService slipService;

    @Autowired
    public SettleSlipScheduler(SlipService slipService) {
        this.slipService = slipService;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void settleSlips() {
        slipService.getSlipsByState(SlipState.ORDERED).forEach(slipService::settleSlip);
    }
}
