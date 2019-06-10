package com.github.aleksanderkot00.onlinesportsbetting.repository;

import com.github.aleksanderkot00.onlinesportsbetting.domain.ExchangeRates;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ExchangeRatesRepository extends CrudRepository<ExchangeRates, Long> {
    @Override
    ExchangeRates save(ExchangeRates exchangeRates);

    @Override
    List<ExchangeRates> findAll();

    @Override
    Optional<ExchangeRates> findById(Long id);
}
