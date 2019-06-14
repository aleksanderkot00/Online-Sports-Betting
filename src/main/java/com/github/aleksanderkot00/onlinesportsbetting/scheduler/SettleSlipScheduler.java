package com.github.aleksanderkot00.onlinesportsbetting.scheduler;

import com.github.aleksanderkot00.onlinesportsbetting.facade.SettleSlipsFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SettleSlipScheduler {

    private final SettleSlipsFacade settleSlipsFacade;

    @Autowired
    public SettleSlipScheduler(SettleSlipsFacade settleSlipsFacade) {
        this.settleSlipsFacade = settleSlipsFacade;
    }

    @Scheduled(cron = "0 0 */2 * * *")
    public void settleSlips() {
        settleSlipsFacade.SettleSlips();
    }
}
