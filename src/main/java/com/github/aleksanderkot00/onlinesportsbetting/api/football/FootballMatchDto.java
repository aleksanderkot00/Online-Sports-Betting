package com.github.aleksanderkot00.onlinesportsbetting.api.football;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FootballMatchDto {

    @JsonProperty("country_name")
    private String country;

    @JsonProperty("league_name")
    private String league;

    @JsonProperty("match_date")
    private LocalDate date;

    @JsonProperty("match_hometeam_name")
    private String teamOneName;

    @JsonProperty("match_hometeam_score")
    private int teamOneScore;

    @JsonProperty("match_awayteam_name")
    private String teamTwoName;

    @JsonProperty("match_awayteam_score")
    private int teamTwoScore;
}
