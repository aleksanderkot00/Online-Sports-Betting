package com.github.aleksanderkot00.onlinesportsbetting.api.football.controller;

import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.FootballMatchDto;
import com.github.aleksanderkot00.onlinesportsbetting.api.football.client.FootballApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/matches")
public class FootballApiController {

    private final FootballApiClient footballApiClient;

    @Autowired
    public FootballApiController(FootballApiClient footballApiClient) {
        this.footballApiClient = footballApiClient;
    }

    @GetMapping("league/{leagueId}/days/{days}")
    public List<FootballMatchDto> getMatches(@PathVariable int leagueId, @PathVariable int days) {
        return footballApiClient.getLastMatches(leagueId, days);
    }
}
