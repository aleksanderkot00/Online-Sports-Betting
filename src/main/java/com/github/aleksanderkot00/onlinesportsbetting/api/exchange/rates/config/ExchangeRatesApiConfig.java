package com.github.aleksanderkot00.onlinesportsbetting.api.exchange.rates.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ExchangeRatesApiConfig {

    @Value("${api.exchange.rates.endpoint}")
    private String exchangeRateApiEndpoint;

    @Value("${api.exchange.rates.base}")
    private String exchangeRateApiBase;
}
