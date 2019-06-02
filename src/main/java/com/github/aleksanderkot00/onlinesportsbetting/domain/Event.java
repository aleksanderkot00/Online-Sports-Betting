package com.github.aleksanderkot00.onlinesportsbetting.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity(name = "EVENTS")
public class Event {

    @NotNull
    @Id
    @GeneratedValue
    private long eventId;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    @Size(min = 2, max = 25)
    private String teamOneName;

    @NotNull
    @Size(min = 2, max = 25)
    private String teamTwoName;

    private Results result;
}
