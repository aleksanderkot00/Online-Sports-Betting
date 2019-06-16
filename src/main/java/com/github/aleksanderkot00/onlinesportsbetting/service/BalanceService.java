package com.github.aleksanderkot00.onlinesportsbetting.service;

import com.github.aleksanderkot00.onlinesportsbetting.domain.ExchangeRates;
import com.github.aleksanderkot00.onlinesportsbetting.domain.User;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.BalanceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Transactional
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
        return BalanceDto.builder()
                .rateDate(rates.getDate())
                .plnBalance(plnBalance)
                .eurBalance(plnBalance.multiply(rates.getEuroRate()))
                .gbpBalance(plnBalance.multiply(rates.getPoundRate()))
                .usdBalance(plnBalance.multiply(rates.getDollarRate()))
                .build();
    }
}
