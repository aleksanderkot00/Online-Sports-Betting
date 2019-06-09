package com.github.aleksanderkot00.onlinesportsbetting.api.exchange.rate.client;

import com.github.aleksanderkot00.onlinesportsbetting.api.exchange.rate.config.ExchangeRateConfig;
import com.github.aleksanderkot00.onlinesportsbetting.api.exchange.rate.dto.ExchangeRateDto;
import com.github.aleksanderkot00.onlinesportsbetting.api.exchange.rate.exception.ExchangeRateNotAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static java.util.Optional.ofNullable;

@Component
public class ExchangeRateClient {

    @Autowired
    private ExchangeRateConfig apiConfig;

    @Autowired
    private RestTemplate restTemplate;

    public ExchangeRateDto getExchangeRates() {

        URI url = UriComponentsBuilder.fromHttpUrl(apiConfig.getExchangeRateApiEndpoint())
                .queryParam("base", apiConfig.getExchangeRateApiBase())
                .build().encode().toUri();

           ExchangeRateDto exchangeRateDto = restTemplate.getForObject(url, ExchangeRateDto.class);
           return ofNullable(exchangeRateDto).orElseThrow(ExchangeRateNotAvailableException::new);
    }
}
