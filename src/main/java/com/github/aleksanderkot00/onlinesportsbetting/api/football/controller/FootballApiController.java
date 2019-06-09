package com.github.aleksanderkot00.onlinesportsbetting.api.football.controller;

import com.github.aleksanderkot00.onlinesportsbetting.api.football.dto.FootballMatchDto;
import com.github.aleksanderkot00.onlinesportsbetting.api.football.client.FootballApiClient;
import com.github.aleksanderkot00.onlinesportsbetting.api.football.config.FootballApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/matches")
public class FootballApiController {

    @Autowired
    private FootballApiClient footballApiClient;

    @Autowired
    private FootballApiConfig footballApiConfig;

    @GetMapping
    public List<FootballMatchDto> getMatches() {
        return footballApiClient.getLastMatches(footballApiConfig.getPremierLeagueId(), 30);
    }
}
