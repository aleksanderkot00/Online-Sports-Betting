package com.github.aleksanderkot00.onlinesportsbetting.domain;

import com.github.aleksanderkot00.onlinesportsbetting.exception.BetSlipNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.repository.BetSlipRepository;
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
public class BetSlipTestSuite {

    @Autowired
    private BetSlipRepository betSlipRepository;

    @Autowired
    private EventRepository eventRepository;


    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testSaveAndFindAll() {
        //Given
        int initialNumberOfBetSlips = betSlipRepository.findAll().size();
        BetSlip betSlip1 = new BetSlip();
        betSlip1.setStake(new BigDecimal("132.12"));
        betSlip1.setTotalOdds(new BigDecimal("1.92"));
        BetSlip betSlip2 = new BetSlip();
        betSlip2.setStake(new BigDecimal("32.12"));
        betSlip2.setTotalOdds(new BigDecimal("3.45"));

        //When
        betSlipRepository.save(betSlip1);
        betSlipRepository.save(betSlip2);
        List<BetSlip> betSlips = betSlipRepository.findAll();
        int numberOfBetSlips = betSlips.size();

        //Then
        assertEquals(initialNumberOfBetSlips + 2, numberOfBetSlips);
        assertTrue(betSlips.contains(betSlip1));
        assertTrue(betSlips.contains(betSlip2));
    }

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testFindById() {
        //Given
        BetSlip betSlip = new BetSlip();
        betSlip.setStake(new BigDecimal("132.12"));
        betSlip.setTotalOdds(new BigDecimal("1.92"));
        betSlipRepository.save(betSlip);

        //When
        BetSlip foundBetSlip = betSlipRepository.findById(betSlip.getBetSlipId()).orElseThrow(BetSlipNotFoundException::new);

        //Then
        assertEquals(betSlip, foundBetSlip);
    }

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testDelete() {
        //Given
        int initialNumberOfBetSlips = betSlipRepository.findAll().size();
        BetSlip betSlip = new BetSlip();
        betSlip.setStake(new BigDecimal("132.12"));
        betSlip.setTotalOdds(new BigDecimal("1.92"));
        betSlipRepository.save(betSlip);

        //When
        betSlipRepository.delete(betSlip);
        List<BetSlip> betSlips = betSlipRepository.findAll();
        int numberOfBetSlips = betSlips.size();

        //Then
        assertEquals(initialNumberOfBetSlips, numberOfBetSlips);
        assertFalse(betSlips.contains(betSlip));
    }

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testDeleteById() {
        //Given
        int initialNumberOfBetSlips = betSlipRepository.findAll().size();
        BetSlip betSlip = new BetSlip();
        betSlip.setStake(new BigDecimal("132.12"));
        betSlip.setTotalOdds(new BigDecimal("1.92"));
        betSlipRepository.save(betSlip);

        //When
        betSlipRepository.deleteById(betSlip.getBetSlipId());
        List<BetSlip> betSlips = betSlipRepository.findAll();
        int numberOfBetSlips = betSlips.size();

        //Then
        assertEquals(initialNumberOfBetSlips, numberOfBetSlips);
        assertFalse(betSlips.contains(betSlip));
    }

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testRelationWithBets() {
        //Given
        Event event = new Event();
        event.setTeamOneName("Real Madryt");
        event.setTeamTwoName("Barcelona");
        event.setDateTime(LocalDateTime.of(2019, 12, 12, 20, 30, 0));
        event.setResult(Result.ONE);
        eventRepository.save(event);
        Bet betOne = new Bet();
        betOne.setActive(true);
        betOne.setEvent(event);
        betOne.setType(Result.ONE);
        betOne.setOdds(new BigDecimal("1.91"));
        Bet betTwo = new Bet();
        betTwo.setActive(true);
        betTwo.setEvent(event);
        betTwo.setType(Result.TWO);
        betTwo.setOdds(new BigDecimal("3.41"));
        Bet betZero = new Bet();
        betZero.setActive(true);
        betZero.setEvent(event);
        betZero.setType(Result.ZERO);
        betZero.setOdds(new BigDecimal("2.11"));

        BetSlip betSlip = new BetSlip();
        betSlip.setStake(new BigDecimal("132.12"));
        betSlip.setTotalOdds(new BigDecimal("1.92"));
        betSlip.getBets().add(betOne);
        betSlip.getBets().add(betZero);
        betSlip.getBets().add(betTwo);
        betSlipRepository.save(betSlip);

        //When
        BetSlip retrievedBetSlip = betSlipRepository.findById(betSlip.getBetSlipId()).orElseThrow(BetSlipNotFoundException::new);

        //Then
        assertEquals(3, retrievedBetSlip.getBets().size());
        assertTrue(retrievedBetSlip.getBets().contains(betOne));
        assertTrue(retrievedBetSlip.getBets().contains(betTwo));
        assertTrue(retrievedBetSlip.getBets().contains(betZero));
    }
}