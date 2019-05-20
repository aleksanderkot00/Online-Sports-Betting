package com.github.aleksanderkot00.onlinesportsbetting.domain;

import java.time.LocalDateTime;
import java.util.Set;

public class Event {
    private LocalDateTime dateTime;
    private String teamOneName;
    private String teamTwoName;
    private Set<Results> possibleResults;
    private Results result;
}
