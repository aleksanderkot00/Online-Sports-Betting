package com.github.aleksanderkot00.onlinesportsbetting.service;

import com.github.aleksanderkot00.onlinesportsbetting.domain.ExchangeRates;
import com.github.aleksanderkot00.onlinesportsbetting.repository.ExchangeRatesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeRatesServiceTestSuite {

    @InjectMocks
    private ExchangeRatesService ratesService;

    @Mock
    private ExchangeRatesRepository ratesRepository;

    @Test
    public void testSave() {
        //Given
        ExchangeRates rates = new ExchangeRates();
        rates.setDate(LocalDate.now());
        rates.setDollarRate(BigDecimal.ONE);
        rates.setEuroRate(BigDecimal.TEN);
        rates.setPoundRate(new BigDecimal("2.2"));
        when(ratesRepository.save(any(ExchangeRates.class))).then(returnsFirstArg());

        //When
        ExchangeRates retrievedRates = ratesService.save(rates);

        //Then
        assertEquals(BigDecimal.ONE, retrievedRates.getDollarRate());
        assertEquals(BigDecimal.TEN, retrievedRates.getEuroRate());
        assertEquals(new BigDecimal("2.2"), retrievedRates.getPoundRate());
        assertEquals(LocalDate.now(), retrievedRates.getDate());
    }

    @Test
    public void testGetLastRates() {
        //Given
        ExchangeRates rates = new ExchangeRates();
        rates.setDate(LocalDate.now());
        rates.setDollarRate(BigDecimal.ONE);
        rates.setEuroRate(BigDecimal.TEN);
        rates.setPoundRate(new BigDecimal("2.2"));
        when(ratesRepository.getLastRates()).thenReturn(Optional.of(rates));

        //When
        ExchangeRates retrievedRates = ratesService.getLastRates();

        //Then
        assertEquals(BigDecimal.ONE, retrievedRates.getDollarRate());
        assertEquals(BigDecimal.TEN, retrievedRates.getEuroRate());
        assertEquals(new BigDecimal("2.2"), retrievedRates.getPoundRate());
        assertEquals(LocalDate.now(), retrievedRates.getDate());
    }
}