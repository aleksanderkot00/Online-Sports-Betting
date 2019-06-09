package com.github.aleksanderkot00.onlinesportsbetting.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "EVENTS")
public class Event {

    @NotNull
    @Id
    @GeneratedValue
    private long eventId;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    @Size(min = 2, max = 35)
    private String teamOneName;

    @NotNull
    @Size(min = 2, max = 35)
    private String teamTwoName;

    private Result result;

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return eventId == event.eventId &&
                Objects.equals(dateTime, event.dateTime) &&
                Objects.equals(teamOneName, event.teamOneName) &&
                Objects.equals(teamTwoName, event.teamTwoName) &&
                result == event.result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, dateTime, teamOneName, teamTwoName, result);
    }
}
