package com.github.aleksanderkot00.onlinesportsbetting.api.football.controller;

import com.github.aleksanderkot00.onlinesportsbetting.api.football.client.FootballApiClient;
import com.github.aleksanderkot00.onlinesportsbetting.api.football.config.FootballApiConfig;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.FootballMatchDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = FootballApiController.class, secure = false)
public class FootballApiControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FootballApiClient footballApiClient;

    @MockBean
    private FootballApiConfig footballApiConfig;

    @Test
    public void testGetMatches() throws Exception {
        //Given
        FootballMatchDto footballMatchDto1 = FootballMatchDto.builder()
                .country("England")
                .league("Premier league")
                .teamOneName("Liverpool")
                .teamTwoName("Manchester City")
                .teamOneScore("1")
                .teamTwoScore("2")
                .date(LocalDate.of(2011, 11,4))
                .build();
        FootballMatchDto footballMatchDto2 = FootballMatchDto.builder()
                .country("England")
                .league("Premier league")
                .teamOneName("Arsenal")
                .teamTwoName("Chelsea")
                .teamOneScore("2")
                .teamTwoScore("4")
                .date(LocalDate.of(2014, 1,14))
                .build();
        List<FootballMatchDto> matches = new ArrayList<>();
        matches.add(footballMatchDto1);
        matches.add(footballMatchDto2);

        when(footballApiConfig.getPremierLeagueId()).thenReturn(13);
        when(footballApiClient.getLastMatches(13,30)).thenReturn(matches);

        //When&Then
        mockMvc.perform(get("/matches").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].country_name", is("England")))
                .andExpect(jsonPath("$[0].league_name", is("Premier league")))
                .andExpect(jsonPath("$[0].match_hometeam_name", is("Liverpool")))
                .andExpect(jsonPath("$[0].match_awayteam_name", is("Manchester City")))
                .andExpect(jsonPath("$[0].match_hometeam_score", is("1")))
                .andExpect(jsonPath("$[0].match_awayteam_score", is("2")))
                .andExpect(jsonPath("$[0].match_date", is("2011-11-04")))
                .andExpect(jsonPath("$[1].country_name", is("England")))
                .andExpect(jsonPath("$[1].league_name", is("Premier league")))
                .andExpect(jsonPath("$[1].match_hometeam_name", is("Arsenal")))
                .andExpect(jsonPath("$[1].match_awayteam_name", is("Chelsea")))
                .andExpect(jsonPath("$[1].match_hometeam_score", is("2")))
                .andExpect(jsonPath("$[1].match_awayteam_score", is("4")))
                .andExpect(jsonPath("$[1].match_date", is("2014-01-14")));
    }
}