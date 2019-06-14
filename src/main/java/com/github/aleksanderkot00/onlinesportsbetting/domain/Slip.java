package com.github.aleksanderkot00.onlinesportsbetting.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;

@Data
@Entity(name = "SLIPS")
public class Slip {

    @NotNull
    @Id
    @GeneratedValue
    private long slipId;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "SLIPS_BETS",
            joinColumns = @JoinColumn(name = "SLIP_ID"),
            inverseJoinColumns = @JoinColumn(name = "BET_ID")
    )
    private Set<Bet> bets = new HashSet<>();

    @NotNull
    @Column(precision = 9, scale = 2)
    private BigDecimal stake = BigDecimal.TEN;

    @NotNull
    private SlipState state = SlipState.UNORDERED;

    @NotNull
    private BigDecimal totalOdds = BigDecimal.ONE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    public void refreshTotalOdds() {
        BigDecimal odds = BigDecimal.ONE;
        for (Bet bet: bets) {
            odds = odds.multiply(bet.getOdds());
        }
        totalOdds = odds;
    }
}
