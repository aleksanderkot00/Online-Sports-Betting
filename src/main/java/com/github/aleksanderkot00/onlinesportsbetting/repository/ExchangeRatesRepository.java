package com.github.aleksanderkot00.onlinesportsbetting.repository;

import com.github.aleksanderkot00.onlinesportsbetting.domain.ExchangeRates;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ExchangeRatesRepository extends CrudRepository<ExchangeRates, Long> {
    @Override
    ExchangeRates save(ExchangeRates exchangeRates);

    @Query(nativeQuery = true)
    Optional<ExchangeRates> getLastRates();
}
