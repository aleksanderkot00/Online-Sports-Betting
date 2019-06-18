package com.github.aleksanderkot00.onlinesportsbetting.repository;

import com.github.aleksanderkot00.onlinesportsbetting.domain.ExchangeRates;
import com.github.aleksanderkot00.onlinesportsbetting.exception.ExchangeRatesNotAvailableException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExchangeRatesRepositoryTestSuite {

    @Autowired
    private ExchangeRatesRepository ratesRepository;

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testSave() {
        //Given
        int initialNumberOfRates = ratesRepository.findAll().size();
        ExchangeRates rates = new ExchangeRates();
        rates.setPoundRate(BigDecimal.ONE);
        rates.setPoundRate(BigDecimal.TEN);
        rates.setPoundRate(BigDecimal.ZERO);
        rates.setDate(LocalDate.now());

        //When
        ratesRepository.save(rates);
        List<ExchangeRates> ratesList = ratesRepository.findAll();

        //Then
        assertTrue(ratesList.contains(rates));
        assertEquals(initialNumberOfRates + 1, ratesList.size());
    }

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testGetLastRates() {
        //Given
        ExchangeRates rates = new ExchangeRates();
        rates.setPoundRate(BigDecimal.ONE);
        rates.setPoundRate(BigDecimal.TEN);
        rates.setPoundRate(BigDecimal.ZERO);
        rates.setDate(LocalDate.now());
        ratesRepository.save(rates);

        //When
        ExchangeRates retrievedRates = ratesRepository.getLastRates().orElseThrow(ExchangeRatesNotAvailableException::new);

        //Then
        assertEquals(rates, retrievedRates);
    }

}