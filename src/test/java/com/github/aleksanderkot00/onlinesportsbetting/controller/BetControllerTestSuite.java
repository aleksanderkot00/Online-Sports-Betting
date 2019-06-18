package com.github.aleksanderkot00.onlinesportsbetting.controller;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Bet;
import com.github.aleksanderkot00.onlinesportsbetting.domain.BetType;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.BetDto;
import com.github.aleksanderkot00.onlinesportsbetting.mapper.BetMapper;
import com.github.aleksanderkot00.onlinesportsbetting.service.BetService;
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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BetController.class, secure = false)
public class BetControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BetService betService;

    @MockBean
    private BetMapper betMapper;

    @Test
    public void testGetBets() throws Exception {
        //Given
        Bet betOne = new Bet();
        betOne.setActive(true);
        betOne.setType(BetType.ONE);
        betOne.setOdds(new BigDecimal("1.91"));

        Bet betTwo = new Bet();
        betTwo.setActive(false);
        betTwo.setType(BetType.TWO);
        betTwo.setOdds(new BigDecimal("3.41"));

        List<Bet> bets = new ArrayList<>();
        bets.add(betOne);
        bets.add(betTwo);

        BetDto betOneDto = BetDto.builder()
                .isActive(betOne.isActive())
                .odds(betOne.getOdds())
                .type(betOne.getType())
                .build();
        BetDto betTwoDto = BetDto.builder()
                .isActive(betTwo.isActive())
                .odds(betTwo.getOdds())
                .type(betTwo.getType())
                .build();
        List<BetDto> betsDto = new ArrayList<>();
        betsDto.add(betOneDto);
        betsDto.add(betTwoDto);

        when(betMapper.mapToBetDtoList(bets)).thenReturn(betsDto);
        when(betService.getBets()).thenReturn(bets);

        //When&Then
        mockMvc.perform(get("/bets").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].active", is(true)))
                .andExpect(jsonPath("$[0].odds", is(1.91)))
                .andExpect(jsonPath("$[0].type", is("ONE")))
                .andExpect(jsonPath("$[1].active", is(false)))
                .andExpect(jsonPath("$[1].odds", is(3.41)))
                .andExpect(jsonPath("$[1].type", is("TWO")));

    }

    @Test
    public void testGetBet() throws Exception {
        //Given
        Bet betOne = new Bet();
        betOne.setActive(true);
        betOne.setType(BetType.ONE);
        betOne.setOdds(new BigDecimal("1.91"));

        BetDto betOneDto = BetDto.builder()
                .isActive(betOne.isActive())
                .odds(betOne.getOdds())
                .type(betOne.getType())
                .build();

        when(betMapper.mapToBetDto(betOne)).thenReturn(betOneDto);
        when(betService.getBet(anyLong())).thenReturn(betOne);

        //When&Then
        mockMvc.perform(get("/bets/15").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active", is(true)))
                .andExpect(jsonPath("$.odds", is(1.91)))
                .andExpect(jsonPath("$.type", is("ONE")));

    }

    @Test
    public void testAddBet() throws Exception {
        //Given
        Bet betOne = new Bet();
        betOne.setActive(true);
        betOne.setType(BetType.ONE);
        betOne.setOdds(new BigDecimal("1.91"));

        BetDto betOneDto = BetDto.builder()
                .isActive(betOne.isActive())
                .odds(betOne.getOdds())
                .type(betOne.getType())
                .build();
        Gson gson = new Gson();
        String jsonContent = gson.toJson(betOneDto);

        when(betMapper.mapToBet(any(BetDto.class))).thenReturn(betOne);
        when(betMapper.mapToBetDto(betOne)).thenReturn(betOneDto);
        when(betService.addBet(any(Bet.class))).then(returnsFirstArg());

        //When&Then
        mockMvc.perform(post("/bets").contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active", is(true)))
                .andExpect(jsonPath("$.odds", is(1.91)))
                .andExpect(jsonPath("$.type", is("ONE")));
    }

    @Test
    public void testEditBet() throws Exception {
        //Given
        Bet betOne = new Bet();
        betOne.setActive(false);
        betOne.setType(BetType.ONE);
        betOne.setOdds(new BigDecimal("1.91"));

        BetDto betOneDto = BetDto.builder()
                .isActive(betOne.isActive())
                .odds(betOne.getOdds())
                .type(betOne.getType())
                .build();
        Gson gson = new Gson();
        String jsonContent = gson.toJson(betOneDto);

        when(betMapper.mapToBetDto(betOne)).thenReturn(betOneDto);
        when(betService.changeActivity(anyLong())).thenReturn(betOne);

        //When&Then
        mockMvc.perform(patch("/bets/41").contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active", is(false)))
                .andExpect(jsonPath("$.odds", is(1.91)))
                .andExpect(jsonPath("$.type", is("ONE")));
    }

    @Test
    public void testDeleteUser() throws Exception {
        //When&Then
        mockMvc.perform(delete("/bets/13")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(betService, times(1)).deleteBet(13);
    }
}