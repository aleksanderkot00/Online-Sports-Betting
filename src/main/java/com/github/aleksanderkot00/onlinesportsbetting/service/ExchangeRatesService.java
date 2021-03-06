package com.github.aleksanderkot00.onlinesportsbetting.service;

import com.github.aleksanderkot00.onlinesportsbetting.domain.ExchangeRates;
import com.github.aleksanderkot00.onlinesportsbetting.exception.ExchangeRatesNotAvailableException;
import com.github.aleksanderkot00.onlinesportsbetting.repository.ExchangeRatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class ExchangeRatesService {

    private final ExchangeRatesRepository ratesRepository;

    @Autowired
    public ExchangeRatesService(ExchangeRatesRepository ratesRepository) {
        this.ratesRepository = ratesRepository;
    }

    public ExchangeRates save(ExchangeRates exchangeRates) {
        return ratesRepository.save(exchangeRates);
    }

    public ExchangeRates getLastRates() {
        return ratesRepository.getLastRates().orElseThrow(ExchangeRatesNotAvailableException::new);
    }
}
