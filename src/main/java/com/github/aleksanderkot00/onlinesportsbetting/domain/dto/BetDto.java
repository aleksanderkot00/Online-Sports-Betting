package com.github.aleksanderkot00.onlinesportsbetting.domain.dto;

import com.github.aleksanderkot00.onlinesportsbetting.domain.BetType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class BetDto {
    private long betId;
    private long eventId;
    private BetType type;
    private BigDecimal odds;
    private boolean isActive;
}
