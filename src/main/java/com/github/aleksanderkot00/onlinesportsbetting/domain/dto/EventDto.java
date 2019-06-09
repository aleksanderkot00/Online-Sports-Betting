package com.github.aleksanderkot00.onlinesportsbetting.domain.dto;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Result;

import java.time.LocalDateTime;

public class EventDto {
    private LocalDateTime dateTime;
    private String teamOneName;
    private String teamTwoName;
    private Result result;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getTeamOneName() {
        return teamOneName;
    }

    public void setTeamOneName(String teamOneName) {
        this.teamOneName = teamOneName;
    }

    public String getTeamTwoName() {
        return teamTwoName;
    }

    public void setTeamTwoName(String teamTwoName) {
        this.teamTwoName = teamTwoName;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
