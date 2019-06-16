package com.github.aleksanderkot00.onlinesportsbetting.service;

import com.github.aleksanderkot00.onlinesportsbetting.domain.ExchangeRates;
import com.github.aleksanderkot00.onlinesportsbetting.domain.User;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.BalanceDto;
import com.github.aleksanderkot00.onlinesportsbetting.exception.ExchangeRatesNotAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BalanceService {

    private final ExchangeRatesService ratesService;
    private final UserService userService;

    @Autowired
    public BalanceService(ExchangeRatesService ratesService, UserService userService) {
        this.ratesService = ratesService;
        this.userService = userService;
    }

    public BalanceDto getUserBalance(String email) {
        User user = userService.getUser(email);
        BigDecimal plnBalance = user.getBalance();
        try {
            ExchangeRates rates = ratesService.getLastRates();
            return BalanceDto.builder()
                    .rateDate(rates.getDate())
                    .plnBalance(plnBalance)
                    .eurBalance(plnBalance.multiply(rates.getEuroRate()))
                    .gbpBalance(plnBalance.multiply(rates.getPoundRate()))
                    .usdBalance(plnBalance.multiply(rates.getDollarRate()))
                    .build();
        } catch (ExchangeRatesNotAvailableException e) {
            return BalanceDto.builder()
                    .plnBalance(plnBalance)
                    .build();
        }
    }
}
