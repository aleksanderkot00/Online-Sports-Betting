package com.github.aleksanderkot00.onlinesportsbetting.service;

import com.github.aleksanderkot00.onlinesportsbetting.domain.ExchangeRates;
import com.github.aleksanderkot00.onlinesportsbetting.domain.User;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.BalanceDto;
import com.github.aleksanderkot00.onlinesportsbetting.exception.ExchangeRatesNotAvailableException;
import com.github.aleksanderkot00.onlinesportsbetting.exception.UserNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BalanceService {

    private final ExchangeRatesService ratesService;
    private final UserRepository userRepository;

    @Autowired
    public BalanceService(ExchangeRatesService ratesService, UserRepository userRepository) {
        this.ratesService = ratesService;
        this.userRepository = userRepository;
    }

    public BalanceDto getUserBalance(long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
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

    public User payment(long userId,BigDecimal value) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.addToBalance(value);
        return userRepository.save(user);
    }
}
