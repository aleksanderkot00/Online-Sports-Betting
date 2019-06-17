package com.github.aleksanderkot00.onlinesportsbetting.aop;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Slip;
import com.github.aleksanderkot00.onlinesportsbetting.domain.SlipState;
import com.github.aleksanderkot00.onlinesportsbetting.domain.details.LoginTryDateTime;
import com.github.aleksanderkot00.onlinesportsbetting.domain.details.SlipOrderDetails;
import com.github.aleksanderkot00.onlinesportsbetting.domain.details.SlipSettleDetails;
import com.github.aleksanderkot00.onlinesportsbetting.exception.SlipNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.repository.LoginTryDateTimeRepository;
import com.github.aleksanderkot00.onlinesportsbetting.repository.SlipOrderDetailsRepository;
import com.github.aleksanderkot00.onlinesportsbetting.repository.SlipRepository;
import com.github.aleksanderkot00.onlinesportsbetting.repository.SlipSettleDetailsRepository;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class Watcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(Watcher.class);
    private final LoginTryDateTimeRepository loginTryDateTimeRepository;
    private final SlipOrderDetailsRepository slipOrderDetailsRepository;
    private final SlipSettleDetailsRepository slipSettleDetailsRepository;
    private final SlipRepository slipRepository;

    @Autowired
    public Watcher(LoginTryDateTimeRepository loginTryDateTimeRepository, SlipOrderDetailsRepository slipOrderDetailsRepository,
                   SlipSettleDetailsRepository slipSettleDetailsRepository, SlipRepository slipRepository) {
        this.loginTryDateTimeRepository = loginTryDateTimeRepository;
        this.slipOrderDetailsRepository = slipOrderDetailsRepository;
        this.slipSettleDetailsRepository = slipSettleDetailsRepository;
        this.slipRepository = slipRepository;
    }

    @Before("execution(* com.github.aleksanderkot00.onlinesportsbetting.controller.UserController.getUsersDetails(..))")
    public void saveLoginDateTime() {
        LOGGER.info("Some is trying to login.");
        loginTryDateTimeRepository.save(new LoginTryDateTime());
    }

    @AfterReturning(pointcut = "execution(* com.github.aleksanderkot00.onlinesportsbetting.facade.OrderSlipFacade.orderSlip(..))",
            returning = "retVal")
    public void saveSlipOrderDetails(Object retVal) {
        Slip cartSlip = (Slip) retVal;
        LOGGER.info(cartSlip.getUser().getEmail() + " has ordered a slip.");
        slipOrderDetailsRepository.save(
                SlipOrderDetails.builder()
                        .odds(cartSlip.getTotalOdds())
                        .orderDateTime(LocalDateTime.now())
                        .slip(cartSlip)
                        .stake(cartSlip.getStake())
                        .build()
        );
    }

    @AfterReturning("execution(* com.github.aleksanderkot00.onlinesportsbetting.service.SlipService.settleSlip(..))" +
            "&& args(slipId)")
    public void saveSlipSettleDetails(Object slipId) {
        long id = (long) slipId;
        Slip slip = slipRepository.findById(id).orElseThrow(SlipNotFoundException::new);
        if (slip.getState().equals(SlipState.LOST)) {
            slipSettleDetailsRepository.save(
                    SlipSettleDetails.builder()
                            .settleDateTime(LocalDateTime.now())
                            .odds(slip.getTotalOdds())
                            .slip(slip)
                            .stake(slip.getStake())
                            .winning(false)
                            .build()
            );
            LOGGER.info("Slip " + slip.getSlipId() + " has lost.");
        } else if (slip.getState().equals(SlipState.WINNING)) {
            slipSettleDetailsRepository.save(
                    SlipSettleDetails.builder()
                            .settleDateTime(LocalDateTime.now())
                            .odds(slip.getTotalOdds())
                            .slip(slip)
                            .stake(slip.getStake())
                            .winning(true)
                            .build()
            );
            LOGGER.info("Slip " + slip.getSlipId() + " has won.");
        }
    }
}