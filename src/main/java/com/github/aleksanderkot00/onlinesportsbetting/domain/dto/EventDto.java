package com.github.aleksanderkot00.onlinesportsbetting.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EventDto {
    private long eventId;
    private LocalDateTime dateTime;
    private String categoryName;
    private String teamOneName;
    private String teamTwoName;
    private BigDecimal teamOneScore;
    private BigDecimal teamTwoScore;
}
