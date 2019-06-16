package com.github.aleksanderkot00.onlinesportsbetting.domain.dto;

import com.github.aleksanderkot00.onlinesportsbetting.domain.SlipState;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
public final class SlipDto {
    @Getter(AccessLevel.NONE)
    private final Set<BetDto> bets;
    private final BigDecimal stake;
    private final SlipState state;
    private final BigDecimal totalOdds;

    public Set<BetDto> getBets() {
        return new HashSet<>(bets);
    }
}
