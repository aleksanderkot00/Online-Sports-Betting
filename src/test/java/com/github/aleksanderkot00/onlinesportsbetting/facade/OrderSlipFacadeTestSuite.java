package com.github.aleksanderkot00.onlinesportsbetting.facade;

import com.github.aleksanderkot00.onlinesportsbetting.domain.*;
import com.github.aleksanderkot00.onlinesportsbetting.repository.UserRepository;
import com.github.aleksanderkot00.onlinesportsbetting.validator.CartSlipValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderSlipFacadeTestSuite {

    @InjectMocks
    private OrderSlipFacade orderSlipFacade;

    @Mock
    private CartSlipValidator validator;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testOrderValidSlip() {
        //Given
        User user = new User();
        user.setName("Test Name1");
        user.setLastName("Test Lastname1");
        user.setEmail("email1@test.com");
        user.setBalance(new BigDecimal("1231.11"));
        user.setEncryptedPassword("Password23");
        Role role = new Role();
        role.setRole("USER");
        user.getRoles().add(role);

        Event event = new Event();
        event.setTeamOneName("Man city");
        event.setTeamTwoName("Liverpool");
        event.setFinished(false);

        Bet betTwo = new Bet();
        betTwo.setActive(true);
        betTwo.setEvent(event);
        betTwo.setType(BetType.TWO);
        betTwo.setOdds(new BigDecimal("3.41"));

        Bet betZero = new Bet();
        betZero.setActive(true);
        betZero.setEvent(event);
        betZero.setType(BetType.TWO);
        betZero.setOdds(new BigDecimal("1.41"));

        Slip slip = new Slip();
        slip.getBets().add(betZero);
        slip.getBets().add(betTwo);
        slip.setUser(user);
        user.setCartSlip(slip);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        //When
        Slip retrievedSlip = orderSlipFacade.orderSlip(31);

        //Then
        assertEquals(SlipState.ORDERED, retrievedSlip.getState());
        verify(validator, times(1)).validateCartSlip(any(User.class));
    }
}