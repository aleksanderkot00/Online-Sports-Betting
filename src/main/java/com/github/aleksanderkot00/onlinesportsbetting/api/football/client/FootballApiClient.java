package com.github.aleksanderkot00.onlinesportsbetting.api.football.client;

import com.github.aleksanderkot00.onlinesportsbetting.api.football.FootballMatchDto;
import com.github.aleksanderkot00.onlinesportsbetting.api.football.config.FootballApiConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

@Component
public class FootballApiClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(FootballApiClient.class);

    @Autowired
    private FootballApiConfig apiConfig;

    @Autowired
    private RestTemplate restTemplate;

    public List<FootballMatchDto> getMatches(long leagueId) {

        URI url = UriComponentsBuilder.fromHttpUrl(apiConfig.getFootballApiEndpoint())
                .queryParam("key", apiConfig.getFootballApiKey())
                .queryParam("league_id", leagueId)
                .queryParam("from", "name,id")
                .queryParam("to", "all")
                .build().encode().toUri();

        try {
            FootballMatchDto[] boardsResponse = restTemplate.getForObject(url, FootballMatchDto[].class);
            return Arrays.asList(ofNullable(boardsResponse).orElse(new FootballMatchDto[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }
}
