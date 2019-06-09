package com.github.aleksanderkot00.onlinesportsbetting.domain.dto;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Result;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BetDto {
    private long eventId;
    private Result type;
    private BigDecimal odds;
    private boolean isActive;
}
