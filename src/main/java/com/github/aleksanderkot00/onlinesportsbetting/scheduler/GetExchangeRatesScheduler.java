package com.github.aleksanderkot00.onlinesportsbetting.scheduler;

import com.github.aleksanderkot00.onlinesportsbetting.api.exchange.rates.client.ExchangeRatesApiClient;
import com.github.aleksanderkot00.onlinesportsbetting.mapper.ExchangeRatesMapper;
import com.github.aleksanderkot00.onlinesportsbetting.service.ExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GetExchangeRatesScheduler {

    private final ExchangeRatesApiClient client;
    private final ExchangeRatesService service;
    private final ExchangeRatesMapper mapper;

    @Autowired
    public GetExchangeRatesScheduler(ExchangeRatesApiClient client, ExchangeRatesService service, ExchangeRatesMapper mapper) {
        this.client = client;
        this.service = service;
        this.mapper = mapper;
    }

//    @Scheduled(cron = "0 0 10 * * *")
    @Scheduled(fixedDelay = 50000)
    public void saveRates() {
        service.save(mapper.mapToExchangeRates(client.getExchangeRates()));
    }
}
