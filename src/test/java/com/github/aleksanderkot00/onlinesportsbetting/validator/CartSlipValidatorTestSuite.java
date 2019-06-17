package com.github.aleksanderkot00.onlinesportsbetting.validator;

import com.github.aleksanderkot00.onlinesportsbetting.domain.*;
import com.github.aleksanderkot00.onlinesportsbetting.exception.NotValidCartSlipException;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CartSlipValidatorTestSuite {

    @Test
    public void testEmptyCartSlip() {
        //Given
        CartSlipValidator validator = new CartSlipValidator();

        Slip slip = new Slip();
        slip.setStake(new BigDecimal("132.12"));
        slip.setTotalOdds(new BigDecimal("1.92"));

        User user = User.builder()
                .active(true)
                .balance(new BigDecimal("2000"))
                .cartSlip(slip)
                .email("mail@gmail.com")
                .name("test name")
                .lastName("testLastName")
                .build();

        //When&Then
        try {
            validator.validateCartSlip(user);
            assert false;
        } catch (NotValidCartSlipException e) {
            assert true;
        }
    }

    @Test
    public void testCartSlipWithInactiveBet() {
        //Given
        CartSlipValidator validator = new CartSlipValidator();

        Slip slip = new Slip();
        slip.setStake(new BigDecimal("132.12"));
        slip.setTotalOdds(new BigDecimal("1.92"));
        slip.setState(SlipState.UNORDERED);

        Event event = new Event();
        event.setTeamOneName("Real Madryt");
        event.setTeamTwoName("Barcelona");
        event.setDateTime(LocalDateTime.of(2019, 12, 12, 20, 30, 0));

        Bet betOne = new Bet();
        betOne.setActive(false);
        betOne.setEvent(event);
        betOne.setType(BetType.ONE);
        betOne.setOdds(new BigDecimal("1.91"));
        slip.getBets().add(betOne);

        User user = User.builder()
                .active(true)
                .balance(new BigDecimal("2000"))
                .cartSlip(slip)
                .email("mail@gmail.com")
                .name("test name")
                .lastName("testLastName")
                .build();

        //When&Then
        try {
            validator.validateCartSlip(user);
            assert false;
        } catch (NotValidCartSlipException e) {
            assert true;
        }
    }

    @Test
    public void testCartSlipWithBetsFromTheSameEvent() {
        //Given
        CartSlipValidator validator = new CartSlipValidator();

        Slip slip = new Slip();
        slip.setStake(new BigDecimal("132.12"));
        slip.setTotalOdds(new BigDecimal("1.92"));
        slip.setState(SlipState.UNORDERED);

        Event event = new Event();
        event.setTeamOneName("Real Madryt");
        event.setTeamTwoName("Barcelona");
        event.setDateTime(LocalDateTime.of(2019, 12, 12, 20, 30, 0));

        Bet betOne = new Bet();
        betOne.setActive(true);
        betOne.setEvent(event);
        betOne.setType(BetType.ONE);
        betOne.setOdds(new BigDecimal("1.91"));
        slip.getBets().add(betOne);

        Bet betTwo = new Bet();
        betTwo.setActive(true);
        betTwo.setEvent(event);
        betTwo.setType(BetType.TWO);
        betTwo.setOdds(new BigDecimal("2.42"));
        slip.getBets().add(betTwo);

        User user = User.builder()
                .active(true)
                .balance(new BigDecimal("2000"))
                .cartSlip(slip)
                .email("mail@gmail.com")
                .name("test name")
                .lastName("testLastName")
                .build();

        //When&Then
        try {
            validator.validateCartSlip(user);
            assert false;
        } catch (NotValidCartSlipException e) {
            assert true;
        }
    }

    @Test
    public void testTooExpensiveCartSlip() {
        //Given
        CartSlipValidator validator = new CartSlipValidator();

        Slip slip = new Slip();
        slip.setStake(new BigDecimal("2000.01"));
        slip.setTotalOdds(new BigDecimal("1.12"));

        Event event = new Event();
        event.setTeamOneName("Real Madryt");
        event.setTeamTwoName("Barcelona");
        event.setDateTime(LocalDateTime.of(2019, 12, 12, 20, 30, 0));

        Bet betOne = new Bet();
        betOne.setActive(true);
        betOne.setEvent(event);
        betOne.setType(BetType.ONE);
        betOne.setOdds(new BigDecimal("1.91"));
        slip.getBets().add(betOne);

        User user = User.builder()
                .active(true)
                .balance(new BigDecimal("2000"))
                .cartSlip(slip)
                .email("mail@gmail.com")
                .name("test name")
                .lastName("testLastName")
                .build();

        //When&Then
        try {
            validator.validateCartSlip(user);
            assert false;
        } catch (NotValidCartSlipException e) {
            assert true;
        }
    }

    @Test
    public void testValidCartSlip() {
        //Given
        CartSlipValidator validator = new CartSlipValidator();

        Slip slip = new Slip();
        slip.setStake(new BigDecimal("2000"));
        slip.setTotalOdds(new BigDecimal("1.12"));

        Event event = new Event();
        event.setTeamOneName("Real Madryt");
        event.setTeamTwoName("Barcelona");
        event.setDateTime(LocalDateTime.of(2019, 12, 12, 20, 30, 0));

        Bet betOne = new Bet();
        betOne.setActive(true);
        betOne.setEvent(event);
        betOne.setType(BetType.ONE);
        betOne.setOdds(new BigDecimal("1.91"));
        slip.getBets().add(betOne);

        User user = User.builder()
                .active(true)
                .balance(new BigDecimal("2000"))
                .cartSlip(slip)
                .email("mail@gmail.com")
                .name("test name")
                .lastName("testLastName")
                .build();

        //When&Then
        try {
            validator.validateCartSlip(user);
            assert true;
        } catch (NotValidCartSlipException e) {
            assert false;
        }
    }
}