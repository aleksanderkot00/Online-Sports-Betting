package com.github.aleksanderkot00.onlinesportsbetting.domain.dto;

import com.github.aleksanderkot00.onlinesportsbetting.domain.SlipState;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
public class SlipDto {
    private Set<BetDto> bets = new HashSet<>();
    private BigDecimal stake = BigDecimal.TEN;
    private SlipState state = SlipState.UNORDERED;
    private BigDecimal totalOdds = BigDecimal.ONE;
}
