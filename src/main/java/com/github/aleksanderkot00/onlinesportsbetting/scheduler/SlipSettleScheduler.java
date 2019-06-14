package com.github.aleksanderkot00.onlinesportsbetting.scheduler;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Slip;
import com.github.aleksanderkot00.onlinesportsbetting.domain.SlipState;
import com.github.aleksanderkot00.onlinesportsbetting.service.SlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SlipSettleScheduler {

    private final SlipService slipService;

    @Autowired
    public SlipSettleScheduler(SlipService slipService) {
        this.slipService = slipService;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void settleSlips() {
        List<Slip> orderedSlips =slipService.getSlipsByState(SlipState.ORDERED);
    }
}
