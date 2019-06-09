package com.github.aleksanderkot00.onlinesportsbetting.api.exchange.rate.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ExchangeRateApiConfig {

    @Value("${api.exchange.rate.endpoint}")
    private String exchangeRateApiEndpoint;

    @Value("${api.exchange.rate.base}")
    private String exchangeRateApiBase;
}
