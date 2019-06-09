package com.github.aleksanderkot00.onlinesportsbetting.domain.dto;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Result;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDto {
    private LocalDateTime dateTime;
    private String teamOneName;
    private String teamTwoName;
    private Result result;
}
