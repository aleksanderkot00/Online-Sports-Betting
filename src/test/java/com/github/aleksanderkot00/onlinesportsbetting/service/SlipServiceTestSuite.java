package com.github.aleksanderkot00.onlinesportsbetting.service;

import com.github.aleksanderkot00.onlinesportsbetting.domain.*;
import com.github.aleksanderkot00.onlinesportsbetting.repository.BetRepository;
import com.github.aleksanderkot00.onlinesportsbetting.repository.SlipRepository;
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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SlipServiceTestSuite {

    @InjectMocks
    private SlipService slipService;

    @Mock
    private SlipRepository slipRepository;

    @Mock
    private BetRepository betRepository;

    @Before
    public void init() {
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

        Event event2 = new Event();
        event2.setTeamOneName("Man city");
        event2.setTeamTwoName("Liverpool");
        event2.setTeamOneScore(BigDecimal.ONE);
        event2.setTeamTwoScore(BigDecimal.ZERO);
        event2.setFinished(true);

        Bet betTwo = new Bet();
        betTwo.setActive(true);
        betTwo.setEvent(event2);
        betTwo.setType(BetType.TWO);
        betTwo.setOdds(new BigDecimal("3.41"));

        Bet betZero = new Bet();
        betZero.setActive(true);
        betZero.setEvent(new Event());
        betZero.setType(BetType.TWO);
        betZero.setOdds(new BigDecimal("1.41"));

        Slip slip = new Slip();
        slip.getBets().add(betOne);
        slip.getBets().add(betTwo);
        slip.setState(SlipState.ORDERED);
        Slip slip2 = new Slip();
        slip2.getBets().add(betZero);
        Slip slip3 = new Slip();
        List<Slip> slips = new ArrayList<>();
        slips.add(slip2);
        slips.add(slip3);

        when(slipRepository.findAll()).thenReturn(slips);
        when(slipRepository.findAllByState(SlipState.UNORDERED)).thenReturn(slips);
        when(slipRepository.findById(15l)).thenReturn(Optional.of(slip));
        when(slipRepository.findById(32l)).thenReturn(Optional.of(slip2));
        when(betRepository.findById(1l)).thenReturn(Optional.of(betZero));
        when(betRepository.findById(2l)).thenReturn(Optional.of(betTwo));
        when(slipRepository.save(any(Slip.class))).then(returnsFirstArg());
    }

    @Test
    public void testGetSlipsByState() {
        //When
        List<Slip> slips = slipService.getSlipsByState(SlipState.UNORDERED);

        //Then
        assertEquals(2, slips.size());
        assertEquals(SlipState.UNORDERED, slips.get(0).getState());
        assertEquals(SlipState.UNORDERED, slips.get(1).getState());
    }

    @Test
    public void testSave() {
        //Given
        Slip slip = new Slip();
        slip.setStake(new BigDecimal("142.11"));
        slip.setTotalOdds(new BigDecimal("41.53"));

        //When
        Slip retrievedSlip = slipService.save(slip);

        //Then
        assertEquals(new BigDecimal("142.11"), retrievedSlip.getStake());
        assertEquals(new BigDecimal("41.53"), retrievedSlip.getTotalOdds());
    }

    @Test
    public void testAddBetToSlip() {
        //When
        Slip retrievedSlip = slipService.addBetToSlip(15,1);

        //Then
        assertEquals(3, retrievedSlip.getBets().size());
    }

    @Test
    public void testRemoveBetFromSlip() {
        //When
        Slip retrievedSlip = slipService.removeBetFromSlip(15,2);

        //Then
        assertEquals(1, retrievedSlip.getBets().size());
    }

    @Test
    public void testEmptyCartSlip() {
        //When
        Slip retrievedSlip = slipService.emptyCartSlip(32);

        //Then
        assertEquals(0, retrievedSlip.getBets().size());
    }

    @Test
    public void testSettleSlip() {
        //When
        Slip retrievedSlip = slipService.settleSlip(15);

        //Then
        assertEquals(SlipState.LOST, retrievedSlip.getState());
    }
}