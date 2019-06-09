package com.github.aleksanderkot00.onlinesportsbetting.api.football.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class FootballApiConfig {
    @Value("${api.football.premierleague.id")
    public static long premierLeagueId;

    @Value("${api.football.bundesliga.id")
    public static long bundesligaId;

    @Value("${api.football.seriea.id")
    public static long serieAId;

    @Value("${api.football.laliga.id")
    public static long laLigaId;

    @Value("${api.football.endpoint}")
    private String footballApiEndpoint;

    @Value("${api.football.key}")
    private String footballApiKey;
}
