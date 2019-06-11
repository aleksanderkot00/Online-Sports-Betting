package com.github.aleksanderkot00.onlinesportsbetting.domain.dto;

import com.github.aleksanderkot00.onlinesportsbetting.domain.BetResult;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BetDto {
    private long betId;
    private long eventId;
    private BetResult type;
    private BigDecimal odds;
    private boolean isActive;
}
