package com.github.aleksanderkot00.onlinesportsbetting.domain;

import com.github.aleksanderkot00.onlinesportsbetting.exception.SlipNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.repository.SlipRepository;
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
public class SlipTestSuite {

    @Autowired
    private SlipRepository slipRepository;

    @Autowired
    private EventRepository eventRepository;

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testSaveAndFindAll() {
        //Given
        int initialNumberOfBetSlips = slipRepository.findAll().size();
        Slip slip1 = new Slip();
        slip1.setStake(new BigDecimal("132.12"));
        slip1.setTotalOdds(new BigDecimal("1.92"));
        Slip slip2 = new Slip();
        slip2.setStake(new BigDecimal("32.12"));
        slip2.setTotalOdds(new BigDecimal("3.45"));

        //When
        slipRepository.save(slip1);
        slipRepository.save(slip2);
        List<Slip> slips = slipRepository.findAll();
        int numberOfBetSlips = slips.size();

        //Then
        assertEquals(initialNumberOfBetSlips + 2, numberOfBetSlips);
        assertTrue(slips.contains(slip1));
        assertTrue(slips.contains(slip2));
    }

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testFindById() {
        //Given
        Slip slip = new Slip();
        slip.setStake(new BigDecimal("132.12"));
        slip.setTotalOdds(new BigDecimal("1.92"));
        slipRepository.save(slip);

        //When
        Slip foundSlip = slipRepository.findById(slip.getSlipId()).orElseThrow(SlipNotFoundException::new);

        //Then
        assertEquals(slip, foundSlip);
    }

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testDelete() {
        //Given
        int initialNumberOfBetSlips = slipRepository.findAll().size();
        Slip slip = new Slip();
        slip.setStake(new BigDecimal("132.12"));
        slip.setTotalOdds(new BigDecimal("1.92"));
        slipRepository.save(slip);

        //When
        slipRepository.delete(slip);
        List<Slip> slips = slipRepository.findAll();
        int numberOfBetSlips = slips.size();

        //Then
        assertEquals(initialNumberOfBetSlips, numberOfBetSlips);
        assertFalse(slips.contains(slip));
    }

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testDeleteById() {
        //Given
        int initialNumberOfBetSlips = slipRepository.findAll().size();
        Slip slip = new Slip();
        slip.setStake(new BigDecimal("132.12"));
        slip.setTotalOdds(new BigDecimal("1.92"));
        slipRepository.save(slip);

        //When
        slipRepository.deleteById(slip.getSlipId());
        List<Slip> slips = slipRepository.findAll();
        int numberOfBetSlips = slips.size();

        //Then
        assertEquals(initialNumberOfBetSlips, numberOfBetSlips);
        assertFalse(slips.contains(slip));
    }

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testRelationWithBets() {
        //Given
        Event event = new Event();
        event.setTeamOneName("Real Madryt");
        event.setTeamTwoName("Barcelona");
        event.setDateTime(LocalDateTime.of(2019, 12, 12, 20, 30, 0));
        event.setResult(BetResult.ONE);
        eventRepository.save(event);
        Bet betOne = new Bet();
        betOne.setActive(true);
        betOne.setEvent(event);
        betOne.setType(BetResult.ONE);
        betOne.setOdds(new BigDecimal("1.91"));
        Bet betTwo = new Bet();
        betTwo.setActive(true);
        betTwo.setEvent(event);
        betTwo.setType(BetResult.TWO);
        betTwo.setOdds(new BigDecimal("3.41"));
        Bet betZero = new Bet();
        betZero.setActive(true);
        betZero.setEvent(event);
        betZero.setType(BetResult.ZERO);
        betZero.setOdds(new BigDecimal("2.11"));

        Slip slip = new Slip();
        slip.setStake(new BigDecimal("132.12"));
        slip.setTotalOdds(new BigDecimal("1.92"));
        slip.getBets().add(betOne);
        slip.getBets().add(betZero);
        slip.getBets().add(betTwo);
        slipRepository.save(slip);

        //When
        Slip retrievedSlip = slipRepository.findById(slip.getSlipId()).orElseThrow(SlipNotFoundException::new);

        //Then
        assertEquals(3, retrievedSlip.getBets().size());
        assertTrue(retrievedSlip.getBets().contains(betOne));
        assertTrue(retrievedSlip.getBets().contains(betTwo));
        assertTrue(retrievedSlip.getBets().contains(betZero));
    }
}