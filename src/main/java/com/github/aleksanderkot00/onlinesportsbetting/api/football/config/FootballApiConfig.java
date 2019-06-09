package com.github.aleksanderkot00.onlinesportsbetting.api.football.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class FootballApiConfig {

    @Value("${api.football.endpoint}")
    private String footballApiEndpoint;

    @Value("${api.football.key}")
    private String footballApiKey;

    @Value("${api.football.premierleague.id}")
    private int premierLeagueId;

    @Value("${api.football.bundesliga.id}")
    private int bundesligaId;

    @Value("${api.football.seriea.id}")
    private int serieAId;

    @Value("${api.football.laliga.id}")
    private int laLigaId;
}
