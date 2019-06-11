package com.github.aleksanderkot00.onlinesportsbetting.service;

import com.github.aleksanderkot00.onlinesportsbetting.domain.ExchangeRates;
import com.github.aleksanderkot00.onlinesportsbetting.domain.User;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.BalanceDto;
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
        ExchangeRates rates = ratesService.getLastRates();
        BigDecimal plnBalance = user.getBalance();
        BalanceDto balanceDto = new BalanceDto();
        balanceDto.setRateDate(rates.getDate());
        balanceDto.setPlnBalance(plnBalance);
        balanceDto.setEurBalance(plnBalance.multiply(rates.getEuroRate()));
        balanceDto.setUsdBalance(plnBalance.multiply(rates.getDollarRate()));
        balanceDto.setGbpBalance(plnBalance.multiply(rates.getPoundRate()));
        System.out.println(plnBalance.multiply(rates.getPoundRate()));

        return balanceDto;
    }
}
