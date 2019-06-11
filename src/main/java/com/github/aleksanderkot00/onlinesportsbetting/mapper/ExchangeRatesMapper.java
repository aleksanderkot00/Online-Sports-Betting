package com.github.aleksanderkot00.onlinesportsbetting.mapper;

import com.github.aleksanderkot00.onlinesportsbetting.api.exchange.rates.dto.ExchangeRatesDto;
import com.github.aleksanderkot00.onlinesportsbetting.domain.ExchangeRates;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRatesMapper {
    public ExchangeRates mapToExchangeRates(ExchangeRatesDto exchangeRatesDto) {
        ExchangeRates exchangeRates = new ExchangeRates();
        exchangeRates.setDate(exchangeRatesDto.getDate());
        exchangeRates.setEuroRate(exchangeRatesDto.getRates().getEuroRate());
        exchangeRates.setDollarRate(exchangeRatesDto.getRates().getDollarRate());
        exchangeRates.setPoundRate(exchangeRatesDto.getRates().getPoundRate());

        return exchangeRates;
    }
}
