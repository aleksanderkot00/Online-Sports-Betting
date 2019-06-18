package com.github.aleksanderkot00.onlinesportsbetting.api.exchange.rates.client;

import com.github.aleksanderkot00.onlinesportsbetting.api.exchange.rates.config.ExchangeRatesApiConfig;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.ExchangeRatesDto;
import com.github.aleksanderkot00.onlinesportsbetting.exception.ExchangeRatesNotAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static java.util.Optional.ofNullable;

@Service
public class ExchangeRatesApiClient {

    private final ExchangeRatesApiConfig apiConfig;
    private final RestTemplate restTemplate;

    @Autowired
    public ExchangeRatesApiClient(ExchangeRatesApiConfig apiConfig, RestTemplate restTemplate) {
        this.apiConfig = apiConfig;
        this.restTemplate = restTemplate;
    }

    public ExchangeRatesDto getExchangeRates() {

        URI url = UriComponentsBuilder.fromHttpUrl(apiConfig.getExchangeRateApiEndpoint())
                .queryParam("base", apiConfig.getExchangeRateApiBase())
                .build().encode().toUri();

           ExchangeRatesDto exchangeRatesDto = restTemplate.getForObject(url, ExchangeRatesDto.class);
           return ofNullable(exchangeRatesDto).orElseThrow(ExchangeRatesNotAvailableException::new);
    }
}
