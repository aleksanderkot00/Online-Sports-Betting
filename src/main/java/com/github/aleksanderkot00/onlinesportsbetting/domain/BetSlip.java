package com.github.aleksanderkot00.onlinesportsbetting.domain;

import java.math.BigDecimal;
import java.util.Set;

public class BetSlip {
    private long id;
    private Set<Bet> bets;
    private BigDecimal stake;
    private BigDecimal totalOdds;
}
