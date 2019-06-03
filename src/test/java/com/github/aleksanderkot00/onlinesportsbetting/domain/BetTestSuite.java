package com.github.aleksanderkot00.onlinesportsbetting.domain;

import com.github.aleksanderkot00.onlinesportsbetting.exception.BetNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.repository.BetRepository;
import com.github.aleksanderkot00.onlinesportsbetting.repository.EventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BetTestSuite {

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private EventRepository eventRepository;

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testSaveAndFindAll() {
        //Given
        Event event = createEventInDb();

        int initialNumberOfBets = betRepository.findAll().size();
        Bet betOne = new Bet();
        betOne.setActive(true);
        betOne.setEvent(event);
        betOne.setType(Results.ONE);
        betOne.setOdds(new BigDecimal("1.91"));

        Bet betTwo = new Bet();
        betTwo.setActive(true);
        betTwo.setEvent(event);
        betTwo.setType(Results.TWO);
        betTwo.setOdds(new BigDecimal("3.41"));

        Bet betZero = new Bet();
        betZero.setActive(true);
        betZero.setEvent(event);
        betZero.setType(Results.TWO);
        betZero.setOdds(new BigDecimal("3.41"));

        //When
        betRepository.save(betOne);
        betRepository.save(betTwo);
        betRepository.save(betZero);
        List<Bet> bets = betRepository.findAll();
        int numberOfBets = bets.size();

        //Then
        assertEquals(initialNumberOfBets + 3, numberOfBets);
        assertTrue(bets.contains(betOne));
        assertTrue(bets.contains(betTwo));
        assertTrue(bets.contains(betZero));
    }

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testFindById() {
        //Given
        Event event = createEventInDb();

        Bet betZero = new Bet();
        betZero.setActive(true);
        betZero.setEvent(event);
        betZero.setType(Results.TWO);
        betZero.setOdds(new BigDecimal("3.41"));
        betRepository.save(betZero);

        //When
        Bet foundBet = betRepository.findById(betZero.getBetId()).orElseThrow(BetNotFoundException::new);

        //Then
        assertEquals(betZero, foundBet);
    }

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testDelete() {
        //Given
        Event event = createEventInDb();

        int initialNumberOfBets = betRepository.findAll().size();
        Bet betZero = new Bet();
        betZero.setActive(true);
        betZero.setEvent(event);
        betZero.setType(Results.TWO);
        betZero.setOdds(new BigDecimal("3.41"));
        betRepository.save(betZero);

        //When
        betRepository.delete(betZero);
        List<Bet> bets = betRepository.findAll();
        int numberOfBets = bets.size();

        //Then
        assertEquals(initialNumberOfBets, numberOfBets);
        assertFalse(bets.contains(betZero));
    }

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testDeleteById() {
        //Given
        Event event = createEventInDb();

        int initialNumberOfBets = betRepository.findAll().size();
        Bet betZero = new Bet();
        betZero.setActive(true);
        betZero.setEvent(event);
        betZero.setType(Results.TWO);
        betZero.setOdds(new BigDecimal("3.41"));
        betRepository.save(betZero);

        //When
        betRepository.deleteById(betZero.getBetId());
        List<Bet> bets = betRepository.findAll();
        int numberOfBets = bets.size();

        //Then
        assertEquals(initialNumberOfBets, numberOfBets);
        assertFalse(bets.contains(betZero));
    }

    private Event createEventInDb() {
        Event event = new Event();
        event.setTeamOneName("Real Madryt");
        event.setTeamTwoName("Barcelona");
        event.setDateTime(LocalDateTime.of(2019, 12, 12, 20, 30, 0));
        event.setResult(Results.ONE);
        eventRepository.save(event);
        return event;
    }
}