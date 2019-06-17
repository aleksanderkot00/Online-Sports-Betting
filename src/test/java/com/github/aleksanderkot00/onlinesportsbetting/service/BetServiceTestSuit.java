package com.github.aleksanderkot00.onlinesportsbetting.service;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Bet;
import com.github.aleksanderkot00.onlinesportsbetting.domain.BetResult;
import com.github.aleksanderkot00.onlinesportsbetting.domain.BetType;
import com.github.aleksanderkot00.onlinesportsbetting.domain.Event;
import com.github.aleksanderkot00.onlinesportsbetting.repository.BetRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BetServiceTestSuit {

    @InjectMocks
    private BetService betService;

    @Mock
    private BetRepository betRepository;

    @Before
    public void init() {
        when(betRepository.save(any(Bet.class))).then(returnsFirstArg());

        Event event = new Event();
        event.setTeamOneName("Real Madryt");
        event.setTeamTwoName("Barcelona");
        event.setDateTime(LocalDateTime.of(2019, 12, 12, 20, 30, 0));
        event.setTeamOneScore(BigDecimal.ONE);
        event.setTeamTwoScore(BigDecimal.ZERO);
        event.setFinished(true);
        Bet betOne = new Bet();
        betOne.setActive(true);
        betOne.setEvent(event);
        betOne.setType(BetType.ONE);
        betOne.setOdds(new BigDecimal("1.91"));
        Bet betTwo = new Bet();
        betTwo.setActive(true);
        betTwo.setEvent(event);
        betTwo.setType(BetType.TWO);
        betTwo.setOdds(new BigDecimal("3.41"));
        List<Bet> bets = new ArrayList<>();
        bets.add(betOne);
        bets.add(betTwo);

        when(betRepository.findAll()).thenReturn(bets);
        when(betRepository.findById(anyLong())).thenReturn(Optional.of(betOne));
    }

    @Test
    public void testGetBets() {
        //Given
        //When
        List<Bet> returnBets = betService.getBets();

        //Then
        assertEquals(2, returnBets.size());
        assertTrue( returnBets.get(0).isActive());
        assertEquals(new BigDecimal("3.41"), returnBets.get(1).getOdds());
    }

    @Test
    public void testGetBet() {
        //Given
        //When
        Bet returnBet = betService.getBet(14l);

        //Then
        assertTrue( returnBet.isActive());
        assertEquals(new BigDecimal("1.91"), returnBet.getOdds());
    }

    @Test
    public void addBet() {
        //Given
        Event event = new Event();
        event.setTeamOneName("Real Madryt");
        event.setTeamTwoName("Barcelona");
        event.setDateTime(LocalDateTime.of(2019, 12, 12, 20, 30, 0));
        event.setTeamOneScore(BigDecimal.ONE);
        event.setTeamOneScore(BigDecimal.ZERO);
        Bet betOne = new Bet();
        betOne.setActive(true);
        betOne.setEvent(event);
        betOne.setType(BetType.ONE);
        betOne.setOdds(new BigDecimal("1.91"));

        //When
        Bet returnBet = betService.addBet(betOne);

        //Then
        assertTrue( returnBet.isActive());
        assertEquals(new BigDecimal("1.91"), returnBet.getOdds());
        assertEquals("Real Madryt", returnBet.getEvent().getTeamOneName());
        assertEquals("Barcelona", returnBet.getEvent().getTeamTwoName());
        assertEquals(BetType.ONE, returnBet.getType());
    }

    @Test
    public void testChangeActivity() {
        //When
        Bet returnBet = betService.changeActivity(14l);

        //Then
        assertFalse(returnBet.isActive());
        assertEquals(new BigDecimal("1.91"), returnBet.getOdds());
        assertEquals("Real Madryt", returnBet.getEvent().getTeamOneName());
        assertEquals("Barcelona", returnBet.getEvent().getTeamTwoName());
        assertEquals(BetType.ONE, returnBet.getType());
    }

    @Test
    public void testDeleteBet() {
        //When
        betService.deleteBet(14l);

        //Then
        verify(betRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testSettle() {
        //When
        Bet settledBet = betService.settleBet(12l);
        //Then
        assertEquals(BetResult.WINNING, settledBet.getResult());
    }
}