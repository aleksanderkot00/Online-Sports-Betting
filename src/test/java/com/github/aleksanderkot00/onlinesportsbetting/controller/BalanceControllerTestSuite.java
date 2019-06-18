package com.github.aleksanderkot00.onlinesportsbetting.controller;

import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.BalanceDto;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.ValueDto;
import com.github.aleksanderkot00.onlinesportsbetting.service.BalanceService;
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
import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BalanceController.class, secure = false)
public class BalanceControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BalanceService balanceService;

    @Test
    public void testGetBalance() throws Exception {
        //Given
        BalanceDto balanceDto = BalanceDto.builder()
                .rateDate(LocalDate.of(2019,6,11))
                .eurBalance(new BigDecimal("1009.34"))
                .plnBalance(new BigDecimal("282.33"))
                .usdBalance(new BigDecimal("900.51"))
                .gbpBalance(new BigDecimal("1231.22"))
                .build();
        when(balanceService.getUserBalance(23)).thenReturn(balanceDto);

        //When&Then
        mockMvc.perform(get("/users/23/balance")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plnBalance", is(282.33)))
                .andExpect(jsonPath("$.eurBalance", is(1009.34)))
                .andExpect(jsonPath("$.usdBalance", is(900.51)))
                .andExpect(jsonPath("$.gbpBalance", is(1231.22)))
                .andExpect(jsonPath("$.rateDate",is("2019-06-11")));
    }

    @Test
    public void testMakePayment() throws Exception {
        //Given
        ValueDto valueDto = new ValueDto();
        valueDto.setValue(new BigDecimal("523.11"));

        Gson gson = new Gson();
        String jsonContent = gson.toJson(valueDto);

        //When&Then
        mockMvc.perform(patch("/users/1/payment")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isNoContent());
        verify(balanceService,times(1)).payment(1,valueDto.getValue());
    }
}