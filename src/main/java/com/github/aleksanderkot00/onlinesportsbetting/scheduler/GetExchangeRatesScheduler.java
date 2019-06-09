package com.github.aleksanderkot00.onlinesportsbetting.scheduler;

import com.github.aleksanderkot00.onlinesportsbetting.api.exchange.rate.client.ExchangeRateApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GetExchangeRatesScheduler {

    private final ExchangeRateApiClient apiClient;

    @Autowired
    public GetExchangeRatesScheduler(ExchangeRateApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Scheduled(fixedDelay = 5000)
    public void getTodayMatches() {
        System.out.println(apiClient.getExchangeRates());
    }
}
