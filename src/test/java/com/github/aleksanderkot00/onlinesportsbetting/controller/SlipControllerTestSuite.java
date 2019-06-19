package com.github.aleksanderkot00.onlinesportsbetting.controller;

import com.github.aleksanderkot00.onlinesportsbetting.domain.*;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.BetDto;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.SlipDto;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.ValueDto;
import com.github.aleksanderkot00.onlinesportsbetting.facade.OrderSlipFacade;
import com.github.aleksanderkot00.onlinesportsbetting.mapper.SlipMapper;
import com.github.aleksanderkot00.onlinesportsbetting.service.SlipService;
import com.github.aleksanderkot00.onlinesportsbetting.service.UserService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = SlipController.class, secure = false)
public class SlipControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SlipService slipService;

    @MockBean
    private UserService userService;

    @MockBean
    private SlipMapper slipMapper;

    @MockBean
    private OrderSlipFacade orderSlipFacade;

    @Test
    public void testGetSlips() throws Exception {
        //Given
        Slip slip = new Slip();
        slip.setState(SlipState.ORDERED);
        slip.setTotalOdds(new BigDecimal("9.1"));
        slip.setStake(new BigDecimal("100.99"));
        Slip slip2 = new Slip();
        slip2.setState(SlipState.WINNING);
        slip2.setTotalOdds(new BigDecimal("5.1"));
        slip2.setStake(new BigDecimal("31"));

        Set<Slip> slips = new HashSet<>();
        slips.add(slip);
        slips.add(slip2);

        SlipDto slipDto = new SlipDto(new HashSet<>(), slip.getStake(), slip.getState(),slip.getTotalOdds());
        SlipDto slipDto2 = new SlipDto(new HashSet<>(), slip2.getStake(), slip2.getState(),slip2.getTotalOdds());
        Set<SlipDto> slipsDto = new HashSet<>();
        slipsDto.add(slipDto);
        slipsDto.add(slipDto2);

        User user = new User();
        user.setName("Test Name1");
        user.setLastName("Test Lastname1");
        user.setEmail("email1@test.com");
        user.setBalance(new BigDecimal("1231.11"));
        user.setEncryptedPassword("Password23");
        Role role = new Role();
        role.setRole("USER");
        user.getRoles().add(role);
        user.setSlips(slips);

        when(slipMapper.mapToSlipDtoSet(slips)).thenReturn(slipsDto);
        when(userService.getUser(anyLong())).thenReturn(user);

        //When&Then
        mockMvc.perform(get("/users/13/slips").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
//                .andExpect(jsonPath("$[0].totalOdds", is(5.1)))
//                .andExpect(jsonPath("$[0].stake", is(31)))
//                .andExpect(jsonPath("$[0].state", is("WINNING")))
//                .andExpect(jsonPath("$[1].totalOdds", is(9.1)))
//                .andExpect(jsonPath("$[1].stake", is(100.99)))
//                .andExpect(jsonPath("$[1].state", is("ORDERED")));
    }

    @Test
    public void testGetSlip() throws Exception {
        //Given
        Slip slip = new Slip();
        slip.setState(SlipState.UNORDERED);
        slip.setTotalOdds(new BigDecimal("9.1"));
        slip.setStake(new BigDecimal("100.99"));

        SlipDto slipDto = new SlipDto(new HashSet<>(), slip.getStake(), slip.getState(),slip.getTotalOdds());

        User user = new User();
        user.setName("Test Name1");
        user.setLastName("Test Lastname1");
        user.setEmail("email1@test.com");
        user.setBalance(new BigDecimal("1231.11"));
        user.setEncryptedPassword("Password23");
        Role role = new Role();
        role.setRole("USER");
        user.getRoles().add(role);
        user.setCartSlip(slip);

        when(slipMapper.mapToSlipDto(slip)).thenReturn(slipDto);
        when(userService.getUser(anyLong())).thenReturn(user);

        //When&Then
        mockMvc.perform(get("/users/13/cart").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalOdds", is(9.1)))
                .andExpect(jsonPath("$.stake", is(100.99)))
                .andExpect(jsonPath("$.state", is("UNORDERED")));
    }

    @Test
    public void testEmptySlip() throws Exception {
        //Given
        Slip slip = new Slip();
        slip.setState(SlipState.UNORDERED);
        slip.setTotalOdds(new BigDecimal("9.1"));
        slip.setStake(new BigDecimal("100.99"));

        Slip emptySlip = new Slip();
        emptySlip.setState(SlipState.UNORDERED);
        emptySlip.setTotalOdds(new BigDecimal("9.1"));
        emptySlip.setStake(new BigDecimal("100.99"));

        SlipDto slipDto = new SlipDto(new HashSet<>(), slip.getStake(), slip.getState(), slip.getTotalOdds());

        User user = new User();
        user.setName("Test Name1");
        user.setLastName("Test Lastname1");
        user.setEmail("email1@test.com");
        user.setBalance(new BigDecimal("1231.11"));
        user.setEncryptedPassword("Password23");
        Role role = new Role();
        role.setRole("USER");
        user.getRoles().add(role);
        user.setCartSlip(slip);

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

        when(userService.getUser(anyLong())).thenReturn(user);
        when(slipService.emptyCartSlip(slip.getSlipId())).thenReturn(emptySlip);
        when(slipMapper.mapToSlipDto(emptySlip)).thenReturn(slipDto);

        //When&Then
        mockMvc.perform(put("/users/13/cart/empty").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalOdds", is(9.1)))
                .andExpect(jsonPath("$.stake", is(100.99)))
                .andExpect(jsonPath("$.state", is("UNORDERED")));
    }

    @Test
    public void testRemoveBet() throws Exception {
        //Given
        Slip slip = new Slip();
        slip.setState(SlipState.UNORDERED);
        slip.setTotalOdds(new BigDecimal("9.1"));
        slip.setStake(new BigDecimal("100.99"));

        User user = new User();
        user.setName("Test Name1");
        user.setLastName("Test Lastname1");
        user.setEmail("email1@test.com");
        user.setBalance(new BigDecimal("1231.11"));
        user.setEncryptedPassword("Password23");
        Role role = new Role();
        role.setRole("USER");
        user.getRoles().add(role);
        user.setCartSlip(slip);

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

        BetDto betDto = new BetDto(betOne.getBetId(), betOne.getEvent().getEventId(),
                betOne.getType(),betOne.getOdds(),betOne.isActive());

        Bet betTwo = new Bet();
        betTwo.setActive(true);
        betTwo.setEvent(new Event());
        betTwo.setType(BetType.ONE);
        betTwo.setOdds(new BigDecimal("2.13"));

        Set<BetDto> betsDto = new HashSet<>();
        betsDto.add(betDto);

        SlipDto slipDto = new SlipDto(betsDto, slip.getStake(), slip.getState(), slip.getTotalOdds());

        when(userService.getUser(anyLong())).thenReturn(user);
        when(slipService.removeBetFromSlip(anyLong(),anyLong())).thenReturn(slip);
        when(slipMapper.mapToSlipDto(slip)).thenReturn(slipDto);

        //When&Then
        mockMvc.perform(delete("/users/13/cart/bets/12").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalOdds", is(9.1)))
                .andExpect(jsonPath("$.stake", is(100.99)))
                .andExpect(jsonPath("$.bets", hasSize(1)));
    }

    public void testAddBet() throws Exception {
        //Given
        Slip slip = new Slip();
        slip.setState(SlipState.UNORDERED);
        slip.setTotalOdds(new BigDecimal("9.1"));
        slip.setStake(new BigDecimal("100.99"));

        User user = new User();
        user.setName("Test Name1");
        user.setLastName("Test Lastname1");
        user.setEmail("email1@test.com");
        user.setBalance(new BigDecimal("1231.11"));
        user.setEncryptedPassword("Password23");
        Role role = new Role();
        role.setRole("USER");
        user.getRoles().add(role);
        user.setCartSlip(slip);

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

        BetDto betDto = new BetDto(betOne.getBetId(), betOne.getEvent().getEventId(),
                betOne.getType(),betOne.getOdds(),betOne.isActive());

        Bet betTwo = new Bet();
        betTwo.setActive(true);
        betTwo.setEvent(new Event());
        betTwo.setType(BetType.ONE);
        betTwo.setOdds(new BigDecimal("2.13"));

        BetDto betDto2 = new BetDto(betTwo.getBetId(), betTwo.getEvent().getEventId(),
                betTwo.getType(),betTwo.getOdds(),betTwo.isActive());
        Set<BetDto> betsDto = new HashSet<>();
        betsDto.add(betDto);
        betsDto.add(betDto2);

        SlipDto slipDto = new SlipDto(betsDto, slip.getStake(), slip.getState(), slip.getTotalOdds());


        when(userService.getUser(anyLong())).thenReturn(user);
        when(slipService.addBetToSlip(anyLong(),anyLong())).thenReturn(slip);
        when(slipMapper.mapToSlipDto(slip)).thenReturn(slipDto);

        //When&Then
        mockMvc.perform(patch("/users/13/cart/bets/12").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalOdds", is(9.1)))
                .andExpect(jsonPath("$.stake", is(100.99)))
                .andExpect(jsonPath("$.bets", hasSize(2)));
    }

    @Test
    public void testOrderSlip() throws Exception {
        //Given
        Slip slip = new Slip();
        slip.setState(SlipState.UNORDERED);
        slip.setTotalOdds(new BigDecimal("9.1"));
        slip.setStake(new BigDecimal("100.99"));

        Slip newSlip = new Slip();

        SlipDto slipDto = new SlipDto(new HashSet<>(), newSlip.getStake(), newSlip.getState(), newSlip.getTotalOdds());

        User user = new User();
        user.setName("Test Name1");
        user.setLastName("Test Lastname1");
        user.setEmail("email1@test.com");
        user.setBalance(new BigDecimal("1231.11"));
        user.setEncryptedPassword("Password23");
        Role role = new Role();
        role.setRole("USER");
        user.getRoles().add(role);
        user.setCartSlip(slip);

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

        when(userService.getUser(anyLong())).thenReturn(user);
        when(orderSlipFacade.orderSlip(anyLong())).thenReturn(newSlip);
        when(slipMapper.mapToSlipDto(newSlip)).thenReturn(slipDto);

        //When&Then
        mockMvc.perform(put("/users/13/cart").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalOdds", is(1)))
                .andExpect(jsonPath("$.stake", is(10)))
                .andExpect(jsonPath("$.state", is("UNORDERED")));
    }

    @Test
    public void testChangeStake() throws Exception {
        //Given
        Slip slip = new Slip();
        slip.setState(SlipState.UNORDERED);
        slip.setTotalOdds(new BigDecimal("9.1"));
        slip.setStake(new BigDecimal("100.99"));

        SlipDto slipDto = new SlipDto(new HashSet<>(), slip.getStake(), slip.getState(), slip.getTotalOdds());

        when(slipService.setStake(anyLong(), any(BigDecimal.class))).thenReturn(slip);
        when(slipMapper.mapToSlipDto(slip)).thenReturn(slipDto);
        ValueDto valueDto = new ValueDto();
        valueDto.setValue(BigDecimal.valueOf(17));

        Gson gson = new Gson();
        String jsonContent = gson.toJson(valueDto);

        //When&Then
        mockMvc.perform(patch("/users/14/cart/stake").contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.totalOdds", is(9.1)))
                .andExpect(jsonPath("$.stake", is(100.99)))
                .andExpect(jsonPath("$.state", is("UNORDERED")));

    }
}