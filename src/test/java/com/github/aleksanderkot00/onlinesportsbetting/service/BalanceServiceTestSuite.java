package com.github.aleksanderkot00.onlinesportsbetting.service;

import com.github.aleksanderkot00.onlinesportsbetting.domain.ExchangeRates;
import com.github.aleksanderkot00.onlinesportsbetting.domain.User;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.BalanceDto;
import com.github.aleksanderkot00.onlinesportsbetting.exception.ExchangeRatesNotAvailableException;
import com.github.aleksanderkot00.onlinesportsbetting.exception.UserNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BalanceServiceTestSuite {

    @InjectMocks
    private BalanceService balanceService;

    @Mock
    private ExchangeRatesService ratesService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void init() {
        User user = new  User();
        user.setBalance(new BigDecimal("2000"));
        user.setEmail("mail@gmail.com");
        user.setName("test name");
        user.setLastName("testLastName");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    }

    @Test
    public void testGetUserBalance() {
        //Given
        ExchangeRates rates = new ExchangeRates();
        rates.setPoundRate(new BigDecimal("3"));
        rates.setEuroRate(new BigDecimal("2"));
        rates.setDollarRate(new BigDecimal("1.5"));
        rates.setDate(LocalDate.now());
        when(ratesService.getLastRates()).thenReturn(rates);

        //When

        BalanceDto balanceDto = balanceService.getUserBalance(12l);

        //Then
        assertTrue(balanceDto.getPlnBalance().compareTo(new BigDecimal("2000")) == 0);
        assertTrue(balanceDto.getEurBalance().compareTo(new BigDecimal("4000")) == 0);
        assertTrue(balanceDto.getGbpBalance().compareTo(new BigDecimal("6000")) == 0);
        assertTrue(balanceDto.getUsdBalance().compareTo(new BigDecimal("3000")) == 0);
        assertTrue(balanceDto.getRateDate().equals(LocalDate.now()));
    }

    @Test
    public void testGetUserBalanceWithoutExchangeRates() {
        //Given
        ExchangeRates rates = new ExchangeRates();
        rates.setPoundRate(new BigDecimal("3"));
        rates.setEuroRate(new BigDecimal("2"));
        rates.setDollarRate(new BigDecimal("1.5"));
        rates.setDate(LocalDate.now());
        when(ratesService.getLastRates()).thenThrow(new ExchangeRatesNotAvailableException());

        //When
        BalanceDto balanceDto = balanceService.getUserBalance(123);

        //Then
        assertTrue(balanceDto.getPlnBalance().compareTo(new BigDecimal("2000")) == 0);
        assertNull(balanceDto.getEurBalance());
        assertNull(balanceDto.getGbpBalance());
        assertNull(balanceDto.getRateDate());
        assertNull(balanceDto.getUsdBalance());
    }

    @Test
    public void testPayment() {
        //Given
        User user = userRepository.findById(13l).orElseThrow(UserNotFoundException::new);

        //When
        balanceService.payment(13l, new BigDecimal("1351"));

        //Then
        assertTrue(user.getBalance().compareTo(new BigDecimal("3351")) == 0);
    }
}