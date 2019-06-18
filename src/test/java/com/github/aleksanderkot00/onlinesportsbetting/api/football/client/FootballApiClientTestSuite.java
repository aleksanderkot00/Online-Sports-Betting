package com.github.aleksanderkot00.onlinesportsbetting.api.football.client;

import com.github.aleksanderkot00.onlinesportsbetting.api.football.config.FootballApiConfig;
import com.github.aleksanderkot00.onlinesportsbetting.api.football.dto.FootballMatchDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FootballApiClientTestSuite {
    @InjectMocks
    private FootballApiClient apiClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private FootballApiConfig apiConfig;

    @Before
    public void init() {
        when(apiConfig.getFootballApiEndpoint()).thenReturn("http://test.com");
        when(apiConfig.getFootballApiKey()).thenReturn("mytestkey");
    }

    @Test
    public void testLastMatches() throws URISyntaxException {
        //Given
        URI url = new URI("http://test.com?action=get_events&APIkey=mytestkey&league_id=14&from="
                + LocalDate.now().minusDays(7) + "&to=" + LocalDate.now());

        FootballMatchDto matchDto = new FootballMatchDto();
        matchDto.setCountry("Test country");
        matchDto.setTeamOneScore("13");
        matchDto.setDate(LocalDate.of(2001, 12, 1));
        FootballMatchDto matchDto2 = new FootballMatchDto();
        matchDto2.setLeague("Test Liga");
        FootballMatchDto[] matches = {matchDto, matchDto2};

        when(restTemplate.getForObject(url, FootballMatchDto[].class)).thenReturn(matches);

        //When
        List<FootballMatchDto> retrievedMatches = apiClient.getLastMatches(14,7);

        //Then
        assertEquals(2, retrievedMatches.size());
        assertEquals("Test country", retrievedMatches.get(0).getCountry());
    }

    @Test
    public void testLastMatchesEmpty() throws URISyntaxException {
        //Given
        URI url = new URI("http://test.com?action=get_events&APIkey=mytestkey&league_id=14&from="
                + LocalDate.now().minusDays(7) + "&to=" + LocalDate.now());
        FootballMatchDto[] matches = new FootballMatchDto[0];

        when(restTemplate.getForObject(url, FootballMatchDto[].class)).thenReturn(matches);

        //When
        List<FootballMatchDto> retrievedMatches = apiClient.getLastMatches(14, 7);

        //Then
        assertEquals(0, retrievedMatches.size());
    }
}